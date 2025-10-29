package br.unibh.produtos.service;

import br.unibh.produtos.dto.*;
import br.unibh.produtos.entity.Categoria;
import br.unibh.produtos.entity.Produto;
import br.unibh.produtos.entity.ProdutoStatus;
import br.unibh.produtos.repository.CategoriaRepository;
import br.unibh.produtos.repository.ImagemIdProjection;
import br.unibh.produtos.repository.ImagemRepository;
import br.unibh.produtos.repository.ProdutoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ImageService imageService;
    private final ImagemRepository imagemRepository;
    private final CategoriaRepository categoriaRepository;


    public ProdutoService(ProdutoRepository produtoRepository, ImageService imageService, ImagemRepository imagemRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.imageService = imageService;
        this.imagemRepository = imagemRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public ProdutosResponseDTO createProduto(ProdutoCreateDTO request) {
        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        Produto novoProduto = new Produto();
        novoProduto.setNome(request.nome());
        novoProduto.setDescricao(request.descricao());
        novoProduto.setPreco(request.preco());
        novoProduto.setQuantidade(request.quantidade());
        novoProduto.setStatus(ProdutoStatus.DISPONIVEL);
        novoProduto.setCategoria(categoria);

        produtoRepository.save(novoProduto);
        return new ProdutosResponseDTO(
                novoProduto.getId(),
                novoProduto.getNome(),
                novoProduto.getDescricao(),
                novoProduto.getPreco(),
                novoProduto.getQuantidade(),
                novoProduto.getStatus(),
                Collections.emptyList(),
                novoProduto.getCategoria().getNomeCategoria()
        );
    }

    @Transactional(readOnly = true)
    public List<ProdutosResponseDTO> getAllProdutos() {

        List<Produto> produtos = produtoRepository.findAll();

        if (produtos.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> produtoIds = produtos.stream().map(Produto::getId).toList();

        List<ImagemIdProjection> imagens = imagemRepository.findImagemIdsByProdutoIds(produtoIds);

        Map<Long, List<Long>> imagensPorProdutoId = imagens.stream()
                .collect(Collectors.groupingBy(
                        ImagemIdProjection::getProdutoId,
                        Collectors.mapping(ImagemIdProjection::getImagemId, toList())
                ));

        return produtos.stream()
                .map(produto -> {

                    String nomeCategoria = null;
                    if(produto.getCategoria() != null){
                        nomeCategoria = produto.getCategoria().getNomeCategoria();
                    }

                    return new ProdutosResponseDTO(
                            produto.getId(),
                            produto.getNome(),
                            produto.getDescricao(),
                            produto.getPreco(),
                            produto.getQuantidade(),
                            produto.getStatus(),
                            imagensPorProdutoId.getOrDefault(produto.getId(), Collections.emptyList()),
                            nomeCategoria
                    );
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProdutosResponseDTO> getProdutosByStatus(String status) {
        ProdutoStatus produtoStatus;
        try {
            produtoStatus = ProdutoStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Status inválido");
        }

        List<Produto> produtos = produtoRepository.findByStatus(produtoStatus);

        if (produtos.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> produtoIds = produtos.stream().map(Produto::getId).toList();

        List<ImagemIdProjection> imagens = imagemRepository.findImagemIdsByProdutoIds(produtoIds);

        Map<Long, List<Long>> imagensPorProdutoId = imagens.stream()
                .collect(Collectors.groupingBy(
                        ImagemIdProjection::getProdutoId,
                        Collectors.mapping(ImagemIdProjection::getImagemId, toList())
                ));

        return produtos.stream()
                .map(produto -> {

                    String nomeCategoria = null;
                    if(produto.getCategoria() != null){
                        nomeCategoria = produto.getCategoria().getNomeCategoria();
                    }

                    return new ProdutosResponseDTO(
                            produto.getId(),
                            produto.getNome(),
                            produto.getDescricao(),
                            produto.getPreco(),
                            produto.getQuantidade(),
                            produto.getStatus(),
                            imagensPorProdutoId.getOrDefault(produto.getId(), Collections.emptyList()),
                            nomeCategoria
                        );
                    })
                    .toList();
    }

    @Transactional
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

    @Transactional
    public ProdutosResponseDTO editarProduto(Long id, ProdutoEditDTO request) {
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

        List<Long> imagemIds = imagemRepository.findImagemIdsByProdutoIds(Collections.singletonList(id))
                .stream()
                .map(ImagemIdProjection::getImagemId)
                .toList();

        String nomeCategoria = null;
        if(produtoExistente.getCategoria() != null){
            nomeCategoria = produtoExistente.getCategoria().getNomeCategoria();
        }

        return new ProdutosResponseDTO(
                produtoExistente.getId(),
                produtoExistente.getNome(),
                produtoExistente.getDescricao(),
                produtoExistente.getPreco(),
                produtoExistente.getQuantidade(),
                produtoExistente.getStatus(),
                imagemIds,
                nomeCategoria
        );
    }

    @Transactional
    public void deleteProduto(Long id) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        List<Long> imagensIds = imagemRepository.findImagemIdsByProdutoIds(Collections.singletonList(id))
                .stream()
                .map(ImagemIdProjection::getImagemId)
                .toList();

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
