package br.unibh.produtos.controller;

import br.unibh.produtos.dto.PedidoRequestDTO;
import br.unibh.produtos.dto.PedidoResponseDTO;
import br.unibh.produtos.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> createPedido(@Valid @RequestBody PedidoRequestDTO request) {
        PedidoResponseDTO response = pedidoService.createPedido(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> getPedidoById(@PathVariable Long id) {
        PedidoResponseDTO response = pedidoService.getPedidoById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PedidoResponseDTO>> getPedidosByUserId(@PathVariable Long userId) {
        List<PedidoResponseDTO> response = pedidoService.getPedidosByUserId(userId);
        return ResponseEntity.ok(response);
    }
}