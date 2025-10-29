package br.unibh.produtos.repository;

import br.unibh.produtos.entity.Endereco;
import br.unibh.produtos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    List<Endereco> findByUser(User user);
    @Query("SELECT e.id AS enderecoId, e.user.id AS userId " +
            "FROM Endereco e WHERE e.user.id IN :userIds")
    List<EnderecoIdProjection> findEnderecoIdsByUserIds(List<Long> userIds);
}
