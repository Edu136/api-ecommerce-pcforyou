package br.unibh.produtos.repository;

import br.unibh.produtos.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    List<Pedido> findByUserId(Long userId);
}
