package br.com.fiap.spring_bank_api.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import br.com.fiap.spring_bank_api.model.Conta;

@Repository
public class ContaRepository {
    private List<Conta> contas = new ArrayList<>();
    private Long idSequence = 1L;

    public List<Conta> listarTodas() {
        return contas;
    }

    public Optional<Conta> buscarPorId(Long id) {
        return contas.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public Optional<Conta> buscarPorCpf(String cpf) {
        return contas.stream().filter(c -> c.getCpfTitular().equals(cpf)).findFirst();
    }

    public Conta salvar(Conta conta) {
        conta.setId(idSequence++);
        contas.add(conta);
        return conta;
    }

    public void encerrarConta(Long id) {
        buscarPorId(id).ifPresent(conta -> conta.setAtiva(false));
    }
}
