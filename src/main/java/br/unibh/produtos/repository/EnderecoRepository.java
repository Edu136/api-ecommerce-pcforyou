package br.unibh.produtos.repository;

import br.unibh.produtos.entity.Endereco;
import br.unibh.produtos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    List<Endereco> findByUser(User user);
}
