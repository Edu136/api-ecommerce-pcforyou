package br.unibh.produtos.controller;

import br.unibh.produtos.dto.UserChangePasswordDTO;
import br.unibh.produtos.dto.UserCreateDTO;
import br.unibh.produtos.dto.LoginRequestDTO;
import br.unibh.produtos.dto.UserResponseDTO;
import br.unibh.produtos.entity.User;
import br.unibh.produtos.repository.UserRepository;
import br.unibh.produtos.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateDTO request) {
        UserResponseDTO  responseDTO = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login (@Valid @RequestBody LoginRequestDTO request){
        UserResponseDTO responseDTO = userService.validaLogin(request);
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/editPassword/{id}")
    public ResponseEntity<Void> editPassword(@PathVariable Long id, @Valid @RequestBody UserChangePasswordDTO req) {
        userService.editPassword(id, req.novaSenha());
        return ResponseEntity.ok().build();
    }
}
