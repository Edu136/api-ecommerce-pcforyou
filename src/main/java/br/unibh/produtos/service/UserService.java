package br.unibh.produtos.service;

import br.unibh.produtos.dto.UserCreateDTO;
import br.unibh.produtos.dto.LoginRequestDTO;
import br.unibh.produtos.dto.UserResponseDTO;
import br.unibh.produtos.entity.User;
import br.unibh.produtos.repository.EnderecoIdProjection;
import br.unibh.produtos.repository.EnderecoRepository;
import br.unibh.produtos.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final EnderecoRepository enderecoRepository;

    public UserService (UserRepository userRepository, PasswordHasher passwordHasher, EnderecoRepository enderecoRepository){
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.enderecoRepository = enderecoRepository;
    }

    @Transactional
    public UserResponseDTO createUser(UserCreateDTO user) {
        if(userRepository.findUserByEmail(user.email()).isPresent()){
            throw new RuntimeException("Usuário já existe");
        }

        if(user.senha().length() < 6){
            throw new RuntimeException("Senha deve ter no mínimo 6 caracteres");
        }

        String senhaEmHash = passwordHasher.hashPassword(user.senha());
        User userCreated = new User();
        userCreated.setEmail(user.email());
        userCreated.setName(user.nome());
        userCreated.setPassword(senhaEmHash);
        userRepository.save(userCreated);

        return new UserResponseDTO(
                userCreated.getId(),
                userCreated.getEmail(),
                userCreated.getName(),
                List.of()
        );
    }

    @Transactional(readOnly = true)
    public UserResponseDTO validaLogin (LoginRequestDTO req){


        User user = userRepository.findUserByEmail(req.email())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        if(passwordHasher.checkPassword(req.senha(), user.getPassword())){

            List<Long> enderecoIds = user.getEndereco().stream()
                    .map(endereco -> endereco.getId())
                    .toList();

            return new UserResponseDTO(
                    user.getId(),
                    user.getEmail(),
                    user.getName(),
                    enderecoIds
            );
        }

        throw new RuntimeException("Credenciais inválidas");
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow();
    }


    public List<UserResponseDTO> findAllUsers(){
        List<User> users = userRepository.findAll();

        if(users.isEmpty()){
            return Collections.emptyList();
        }

        List<Long> userIds = users.stream().map(User::getId).toList();

        List<EnderecoIdProjection> enderecos = enderecoRepository.findEnderecoIdsByUserIds(userIds);

        Map<Long, List<Long>> enderecosPorUserId = enderecos.stream()
                .collect(groupingBy(
                        EnderecoIdProjection::getUserId,
                        mapping(EnderecoIdProjection::getEnderecoId, toList())
                ));

        return users.stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        enderecosPorUserId.getOrDefault(user.getId(), Collections.emptyList())
                )).toList();
    }

    @Transactional
    public void editPassword(Long id, String newPassword) {
        if(newPassword.length() < 6){
            throw new RuntimeException("Senha deve ter no mínimo 6 caracteres");
        }
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        String senhaEmHash = passwordHasher.hashPassword(newPassword);
        user.setPassword(senhaEmHash);
        userRepository.save(user);
    }

    public User findByEmail(String email){
        return userRepository.findUserByEmail(email).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
