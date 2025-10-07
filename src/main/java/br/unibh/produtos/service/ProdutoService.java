package br.unibh.produtos.service;

import br.unibh.produtos.dto.VenderProdutoRequestDTO;
import br.unibh.produtos.entity.Produto;
import br.unibh.produtos.entity.ProdutoStatus;
import br.unibh.produtos.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import br.unibh.produtos.dto.CreateProdutoRequestDTO;
import br.unibh.produtos.dto.ReponseCreateProdutoDTO;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }


    public ReponseCreateProdutoDTO createProduto(CreateProdutoRequestDTO request) {
        Produto novoProduto = new Produto();
        novoProduto.setNome(request.nome());
        novoProduto.setDescricao(request.descricao());
        novoProduto.setPreco(request.preco());
        novoProduto.setQuantidade(request.quantidade());
        novoProduto.setStatus(ProdutoStatus.DISPONIVEL);

        produtoRepository.save(novoProduto);
        return new ReponseCreateProdutoDTO(
                novoProduto.getId(),
                novoProduto.getNome(),
                novoProduto.getDescricao(),
                novoProduto.getPreco(),
                novoProduto.getQuantidade(),
                novoProduto.getStatus()
        );
    }

    public List<Produto> getAllProdutos() {
        return produtoRepository.findAll();
    }

    public void venderProduto(Long produtoId, VenderProdutoRequestDTO requestDTO) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (produto.getQuantidade() < requestDTO.quantidade()) {
            throw new RuntimeException("Quantidade insuficiente em estoque");
        }

        if(produto.getStatus() != ProdutoStatus.DISPONIVEL) {
            throw new RuntimeException("Produto não está disponível para venda");
        }

        if(produto.getQuantidade() - requestDTO.quantidade() == 0) {
            produto.setStatus(ProdutoStatus.INDISPONIVEL);
        }

        produto.setQuantidade(produto.getQuantidade() - requestDTO.quantidade());
        produtoRepository.save(produto);
    }
}
