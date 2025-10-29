package br.unibh.produtos.repository;

import br.unibh.produtos.entity.Produto;
import br.unibh.produtos.entity.ProdutoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findById(Long id);

    @Query("SELECT p FROM Produto p WHERE p.status = :status")
    List<Produto> findByStatus(ProdutoStatus status);
}
