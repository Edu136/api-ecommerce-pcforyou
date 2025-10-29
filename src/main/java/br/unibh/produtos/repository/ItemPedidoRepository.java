package br.unibh.produtos.repository;

import br.unibh.produtos.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido , Long> {
}
