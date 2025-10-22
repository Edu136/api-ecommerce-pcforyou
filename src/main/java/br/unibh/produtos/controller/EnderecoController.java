package br.unibh.produtos.controller;

import br.unibh.produtos.dto.EnderecoCreateDTO;
import br.unibh.produtos.dto.EnderecoResponseDTO;
import br.unibh.produtos.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EnderecoController {
    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<EnderecoResponseDTO>> getEnderecoById(@PathVariable Long id){
        List<EnderecoResponseDTO> response = enderecoService.buscarEnderecoPorIdUsuario(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<EnderecoResponseDTO> addEndereco(@Valid @RequestBody EnderecoCreateDTO req){
        EnderecoResponseDTO response = enderecoService.criarNovoEndereco(req);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<EnderecoResponseDTO> editarEndereco( @PathVariable Long id, @Valid @RequestBody EnderecoCreateDTO req){
        EnderecoResponseDTO response = enderecoService.editarEndereco(req, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable Long id){
        enderecoService.deletarEndereco(id);
        return ResponseEntity.noContent().build();
    }
}
