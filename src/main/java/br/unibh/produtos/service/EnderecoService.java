package br.unibh.produtos.service;

import br.unibh.produtos.dto.EnderecoBuscaDTO;
import br.unibh.produtos.dto.EnderecoCreateDTO;
import br.unibh.produtos.dto.EnderecoResponseDTO;
import br.unibh.produtos.entity.Endereco;
import br.unibh.produtos.entity.User;
import br.unibh.produtos.repository.EnderecoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {
    private final UserService userService;
    private final EnderecoRepository enderecoRepository;

    public EnderecoService(UserService userService,EnderecoRepository enderecoRepository) {
        this.userService = userService;
        this.enderecoRepository = enderecoRepository;

    }


    public EnderecoResponseDTO criarNovoEndereco(EnderecoCreateDTO req){
        User userEncontrado = userService.findById(req.userId());

        Endereco novoEndereco = new Endereco();

        novoEndereco.setCep(req.cep());
        novoEndereco.setLogradouro(req.logradouro());
        novoEndereco.setNumero(req.numero());
        novoEndereco.setComplemento(req.complemento());
        novoEndereco.setBairro(req.bairro());
        novoEndereco.setCidade(req.cidade());
        novoEndereco.setEstado(req.estado());
        novoEndereco.setUser(userEncontrado);

        enderecoRepository.save(novoEndereco);

        return new EnderecoResponseDTO(
                novoEndereco.getId(),
                novoEndereco.getCep(),
                novoEndereco.getLogradouro(),
                novoEndereco.getNumero(),
                novoEndereco.getComplemento(),
                novoEndereco.getBairro(),
                novoEndereco.getCidade(),
                novoEndereco.getEstado()
        );
    }

    public EnderecoResponseDTO editarEndereco(EnderecoCreateDTO req, Long id){

        Endereco enderecoEncontrado = enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

        enderecoEncontrado.setCep(req.cep());
        enderecoEncontrado.setLogradouro(req.logradouro());
        enderecoEncontrado.setNumero(req.numero());
        enderecoEncontrado.setBairro(req.bairro());
        enderecoEncontrado.setCidade(req.cidade());
        enderecoEncontrado.setEstado(req.estado());

        if(req.complemento() != null){
            enderecoEncontrado.setComplemento(req.complemento());
        }

        enderecoRepository.save(enderecoEncontrado);

        return new EnderecoResponseDTO(
                enderecoEncontrado.getId(),
                enderecoEncontrado.getCep(),
                enderecoEncontrado.getLogradouro(),
                enderecoEncontrado.getNumero(),
                enderecoEncontrado.getComplemento(),
                enderecoEncontrado.getBairro(),
                enderecoEncontrado.getCidade(),
                enderecoEncontrado.getEstado()
        );
    }

    public void deletarEndereco(Long id){
        Endereco enderecoEncontrado = enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
        enderecoRepository.delete(enderecoEncontrado);
    }

    public List<EnderecoResponseDTO> buscarEnderecoPorIdUsuario(Long id){
        User userEncontrado = userService.findById(id);
        List<Endereco> enderecos = enderecoRepository.findByUser(userEncontrado);
        return enderecos.stream().map(endereco -> new EnderecoResponseDTO(
                endereco.getId(),
                endereco.getCep(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado()
        )).toList();
    }
}
