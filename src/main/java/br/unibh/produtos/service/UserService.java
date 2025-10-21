package br.unibh.produtos.service;

import br.unibh.produtos.dto.UserCreateDTO;
import br.unibh.produtos.dto.LoginRequestDTO;
import br.unibh.produtos.dto.UserResponseDTO;
import br.unibh.produtos.entity.User;
import br.unibh.produtos.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public UserService (UserRepository userRepository, PasswordHasher passwordHasher){
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

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

    public boolean validaLogin (LoginRequestDTO req){
        if(passwordHasher.checkPassword(req.senha(), userRepository.findUserByEmail(req.email()).get().getPassword())){
            return true;
        }
        return false;
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow();
    }

    public List<UserResponseDTO> findAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        user.getEndereco().stream().map(endereco -> endereco.getId()).toList()
                )).toList();
    }

    public void editPassword(Long id, String newPassword) {
        if(newPassword.length() < 6){
            throw new RuntimeException("Senha deve ter no mínimo 6 caracteres");
        }
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        String senhaEmHash = passwordHasher.hashPassword(newPassword);
        user.setPassword(senhaEmHash);
        userRepository.save(user);
    }
}
