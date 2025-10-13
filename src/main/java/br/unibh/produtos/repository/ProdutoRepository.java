package br.unibh.produtos.repository;

import br.unibh.produtos.entity.Produto;
import br.unibh.produtos.entity.ProdutoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto>findByStatus(ProdutoStatus status);
}
