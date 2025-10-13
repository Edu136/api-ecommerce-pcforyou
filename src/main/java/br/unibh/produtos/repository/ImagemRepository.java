package br.unibh.produtos.repository;

import br.unibh.produtos.entity.ImagemProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagemRepository extends JpaRepository<ImagemProduto, Long> {
    void deleteImagemById(Long id);
}
