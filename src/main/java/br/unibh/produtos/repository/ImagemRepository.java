package br.unibh.produtos.repository;

import br.unibh.produtos.entity.ImagemProduto;
import br.unibh.produtos.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagemRepository extends JpaRepository<ImagemProduto, Long> {
    void deleteImagemById(Long id);

    @Query("SELECT p.imagem FROM ImagemProduto p WHERE p.produto.id = :idProduto ORDER BY p.id ASC LIMIT 1")
    byte[] findFirstImageBytesByProdutoId(Long idProduto);

    @Query("SELECT i FROM ImagemProduto i WHERE i.produto.id IN :produtoIds")
    List<ImagemProduto> findAllByProdutoIds(List<Long> produtoIds);

    @Query("SELECT i.id AS imagemId, i.produto.id AS produtoId " +
            "FROM ImagemProduto i WHERE i.produto.id IN :produtoIds")
    List<ImagemIdProjection> findImagemIdsByProdutoIds(List<Long> produtoIds);
}
