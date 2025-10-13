package br.unibh.produtos.service;

import br.unibh.produtos.dto.*;
import br.unibh.produtos.entity.Produto;
import br.unibh.produtos.entity.ProdutoStatus;
import br.unibh.produtos.repository.ImagemRepository;
import br.unibh.produtos.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ImageService imageService;


    public ProdutoService(ProdutoRepository produtoRepository, ImageService imageService) {
        this.produtoRepository = produtoRepository;
        this.imageService = imageService;
    }


    public ProdutoCreateResponseDTO createProduto(ProdutoCreateDTO request) {
        Produto novoProduto = new Produto();
        novoProduto.setNome(request.nome());
        novoProduto.setDescricao(request.descricao());
        novoProduto.setPreco(request.preco());
        novoProduto.setQuantidade(request.quantidade());
        novoProduto.setStatus(ProdutoStatus.DISPONIVEL);

        produtoRepository.save(novoProduto);
        return new ProdutoCreateResponseDTO(
                novoProduto.getId(),
                novoProduto.getNome(),
                novoProduto.getDescricao(),
                novoProduto.getPreco(),
                novoProduto.getQuantidade(),
                novoProduto.getStatus()
        );
    }

    public List<ProdutosResponseDTO> getAllProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream()
                .map(produto -> new ProdutosResponseDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getDescricao(),
                        produto.getPreco(),
                        produto.getQuantidade(),
                        produto.getStatus(),
                        produto.getImagens().stream().map(imagem -> imagem.getId()).toList()
                ))
                .toList();
    }


    public List<ProdutosResponseDTO> getProdutosByStatus(String status) {
        ProdutoStatus produtoStatus;
        try {
            produtoStatus = ProdutoStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Status inválido");
        }

        List<Produto> produtos = produtoRepository.findByStatus(produtoStatus);
        return produtos.stream()
                .map(produto -> new ProdutosResponseDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getDescricao(),
                        produto.getPreco(),
                        produto.getQuantidade(),
                        produto.getStatus(),
                        produto.getImagens().stream().map(imagem -> imagem.getId()).toList()
                ))
                .toList();
    }

    public void venderProduto(Long produtoId, ProdutoVenderRequestDTO requestDTO) {
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

    public ProdutoCreateResponseDTO editarProduto(Long id, ProdutoEditDTO request) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if(request == null){
            throw new RuntimeException("Requisição inválida");
        }

        if(request.nome() == null && request.descricao() == null && request.preco() == null && request.quantidade() == null){
            throw new RuntimeException("Nenhum campo para atualizar");
        }

        if(request.preco() != null && request.preco() < 0){
            throw new RuntimeException("Preço não pode ser menor que zero.");
        }

        if(request.quantidade() != null && request.quantidade() < 0){
            throw new RuntimeException("Quantidade não pode ser menor que zero.");
        }

        if(request.nome() != null){
            produtoExistente.setNome(request.nome());
        }

        if(request.descricao() != null){
            produtoExistente.setDescricao(request.descricao());
        }

        if(request.preco() != null){
            produtoExistente.setPreco(request.preco());
        }

        if(request.quantidade() != null && request.quantidade() >= 0){
            produtoExistente.setQuantidade(request.quantidade());
        }

        if (produtoExistente.getQuantidade() > 0) {
            produtoExistente.setStatus(ProdutoStatus.DISPONIVEL);
        } else {
            produtoExistente.setStatus(ProdutoStatus.INDISPONIVEL);
        }

        produtoRepository.save(produtoExistente);

        return new ProdutoCreateResponseDTO(
                produtoExistente.getId(),
                produtoExistente.getNome(),
                produtoExistente.getDescricao(),
                produtoExistente.getPreco(),
                produtoExistente.getQuantidade(),
                produtoExistente.getStatus()
        );
    }

    public void deleteProduto(Long id) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        List<Long> imagensIds = produtoExistente.getImagens().stream().map(imagem -> imagem.getId()).toList();

        if (imagensIds.isEmpty()){
            produtoRepository.delete(produtoExistente);
            return;
        }
        for (Long imagemId : imagensIds) {
            imageService.deleteImageById(imagemId);
        }

        produtoRepository.delete(produtoExistente);
    }
}
