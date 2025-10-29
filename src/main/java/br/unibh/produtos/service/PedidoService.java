package br.unibh.produtos.service;

import br.unibh.produtos.dto.*;
import br.unibh.produtos.entity.*;
import br.unibh.produtos.repository.ItemPedidoRepository;
import br.unibh.produtos.repository.PedidoRepository;
import br.unibh.produtos.repository.ProdutoRepository;
import br.unibh.produtos.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UserRepository userRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         UserRepository userRepository,
                         ProdutoRepository produtoRepository,
                         ItemPedidoRepository itemPedidoRepository) { // <-- Novo
        this.pedidoRepository = pedidoRepository;
        this.userRepository = userRepository;
        this.produtoRepository = produtoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @Transactional
    public PedidoResponseDTO createPedido(PedidoRequestDTO request) {

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + request.userId()));

        List<Long> produtoIds = request.itens().stream()
                .map(ItemPedidoRequestDTO::produtoId)
                .toList();

        List<Produto> produtosDoBanco = produtoRepository.findAllById(produtoIds);

        Map<Long, Produto> produtosMap = produtosDoBanco.stream()
                .collect(Collectors.toMap(Produto::getId, p -> p));

        Pedido novoPedido = new Pedido();
        novoPedido.setUser(user);

        double totalPedido = 0.0;
        List<ItemPedido> itensParaSalvar = new ArrayList<>();

        // 5. Validar cada item do carrinho
        for (ItemPedidoRequestDTO itemRequest : request.itens()) {
            Produto produto = produtosMap.get(itemRequest.produtoId());

            if (produto == null) {
                throw new RuntimeException("Produto não encontrado: " + itemRequest.produtoId());
            }
            if (produto.getStatus() != ProdutoStatus.DISPONIVEL) {
                throw new RuntimeException("O produto '" + produto.getNome() + "' não está disponível.");
            }
            if (produto.getQuantidade() < itemRequest.quantidade()) {
                throw new RuntimeException("Estoque insuficiente para '" + produto.getNome() + "'. Solicitado: "
                        + itemRequest.quantidade() + ", Em estoque: " + produto.getQuantidade());
            }

            produto.setQuantidade(produto.getQuantidade() - itemRequest.quantidade());
            if (produto.getQuantidade() == 0) {
                produto.setStatus(ProdutoStatus.INDISPONIVEL);
            }

            ItemPedido novoItem = new ItemPedido();
            novoItem.setProduto(produto);
            novoItem.setPedido(novoPedido);
            novoItem.setQuantidade(itemRequest.quantidade());
            novoItem.setPrecoUnitario(produto.getPreco());

            itensParaSalvar.add(novoItem);
            totalPedido += (produto.getPreco() * itemRequest.quantidade());
        }

        novoPedido.setItens(itensParaSalvar);
        Pedido pedidoSalvo = pedidoRepository.save(novoPedido);

        List<SimpleProdutoDTO> produtosResponse = pedidoSalvo.getItens().stream()
                .map(item -> new SimpleProdutoDTO(
                        item.getProduto().getId(),
                        item.getProduto().getNome(),
                        item.getPrecoUnitario(),
                        item.getQuantidade()
                ))
                .collect(Collectors.toList());

        return new PedidoResponseDTO(
                pedidoSalvo.getId(),
                user.getId(),
                user.getName(),
                produtosResponse,
                totalPedido
        );
    }

    @Transactional(readOnly = true)
    public PedidoResponseDTO getPedidoById(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + id));

        // Carrega os itens do pedido
        List<ItemPedido> itens = pedido.getItens();

        double total = 0.0;
        List<SimpleProdutoDTO> produtosDTO = new ArrayList<>();

        for (ItemPedido item : itens) {
            produtosDTO.add(new SimpleProdutoDTO(
                    item.getProduto().getId(),
                    item.getProduto().getNome(),
                    item.getPrecoUnitario(),
                    item.getQuantidade()
            ));
            total += (item.getPrecoUnitario() * item.getQuantidade());
        }

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getUser().getId(),
                pedido.getUser().getName(),
                produtosDTO,
                total
        );
    }

    @Transactional
    public List<PedidoResponseDTO> getPedidosByUserId(Long userId) {
        List<Pedido> pedidos = pedidoRepository.findByUserId(userId);

        List<PedidoResponseDTO> pedidosDTO = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            double total = 0.0;
            List<SimpleProdutoDTO> produtosDTO = new ArrayList<>();

            for (ItemPedido item : pedido.getItens()) {
                produtosDTO.add(new SimpleProdutoDTO(
                        item.getProduto().getId(),
                        item.getProduto().getNome(),
                        item.getPrecoUnitario(),
                        item.getQuantidade()
                ));
                total += (item.getPrecoUnitario() * item.getQuantidade());
            }

            pedidosDTO.add(new PedidoResponseDTO(
                    pedido.getId(),
                    pedido.getUser().getId(),
                    pedido.getUser().getName(),
                    produtosDTO,
                    total
            ));
        }

        return pedidosDTO;
    }
}