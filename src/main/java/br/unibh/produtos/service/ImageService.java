package br.unibh.produtos.service;

import br.unibh.produtos.dto.ImagemProdutoCreateDTO;
import br.unibh.produtos.entity.ImagemProduto;
import br.unibh.produtos.entity.Produto;
import br.unibh.produtos.repository.ImagemRepository;
import br.unibh.produtos.repository.ProdutoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    @PersistenceContext
    private EntityManager entityManager;

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

    @Transactional(readOnly = true)
    public byte[] getFirstImageByProdutoId(Long idProduto) {
        String sql = "SELECT imagem FROM imagem_produto WHERE produto_id = :idProduto ORDER BY id ASC LIMIT 1";

        List<byte[]> resultados = entityManager.createNativeQuery(sql)
                .setParameter("idProduto", idProduto)
                .unwrap(org.hibernate.query.NativeQuery.class)
                .addScalar("imagem", org.hibernate.type.StandardBasicTypes.BINARY)
                .getResultList();

        if (resultados.isEmpty() || resultados.get(0) == null) {
            throw new RuntimeException("Imagem do produto não encontrada para o ID: " + idProduto);
        }

        return resultados.get(0);
    }

    @Transactional
    public void salvarImagem(Long produtoId, String nomeArquivoPersonalizado, MultipartFile file) throws IOException {

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        byte[] imagemBytes = file.getBytes();

        String nomeArquivo = nomeArquivoPersonalizado != null ?
                nomeArquivoPersonalizado : file.getOriginalFilename();

        Long produtoForeignKeyId = produto.getId();

        String sql = "INSERT INTO imagem_produto (imagem, nome_arquivo, produto_id) VALUES (?, ?, ?)";

        try {
            entityManager.createNativeQuery(sql)
                    .setParameter(1, imagemBytes)
                    .setParameter(2, nomeArquivo)
                    .setParameter(3, produtoForeignKeyId)
                    .executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao executar inserção nativa da imagem.", e);
        }
    }
}
