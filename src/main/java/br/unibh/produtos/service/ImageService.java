package br.unibh.produtos.service;

import br.unibh.produtos.entity.ImagemProduto;
import br.unibh.produtos.entity.Produto;
import br.unibh.produtos.repository.ImagemRepository;
import br.unibh.produtos.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageService {
    private final ImagemRepository imagemRepository;
    private final ProdutoRepository produtoRepository;

    public ImageService(ImagemRepository imagemRepository, ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
        this.imagemRepository = imagemRepository;
    }

    @Transactional
    public void deleteImageById(Long id) {
        ImagemProduto imagemProduto = imagemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Imagem com ID " + id + " não encontrada."));

        imagemRepository.deleteImagemById(imagemProduto.getId());
    }

    public byte[] getFirstImageByProdutoId(Long idProduto){
        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new IllegalArgumentException("Produto com ID " + idProduto + " não encontrado."));
        ImagemProduto imagemProduto = imagemRepository.findFirstByProduto(produto);
        return imagemProduto.getImagem();
    }
}
