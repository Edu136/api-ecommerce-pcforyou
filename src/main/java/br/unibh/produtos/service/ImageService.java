package br.unibh.produtos.service;

import br.unibh.produtos.entity.ImagemProduto;
import br.unibh.produtos.repository.ImagemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    private final ImagemRepository imagemRepository;

    public ImageService(ImagemRepository imagemRepository) {
        this.imagemRepository = imagemRepository;
    }

    @Transactional
    public void deleteImageById(Long id) {
        ImagemProduto imagemProduto = imagemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Imagem com ID " + id + " não encontrada."));

        imagemRepository.deleteImagemById(imagemProduto.getId());
    }
}
