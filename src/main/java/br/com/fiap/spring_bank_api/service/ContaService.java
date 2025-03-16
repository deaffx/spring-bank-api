package br.com.fiap.spring_bank_api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import br.com.fiap.spring_bank_api.model.Conta;
import br.com.fiap.spring_bank_api.repository.ContaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContaService {
    private final ContaRepository contaRepository;

    public Conta criarConta(Conta conta) {
        validarConta(conta);
        return contaRepository.salvar(conta);
    }

    public List<Conta> listarTodas() {
        return contaRepository.listarTodas();
    }

    public Optional<Conta> buscarPorId(Long id) {
        return contaRepository.buscarPorId(id);
    }

    public Optional<Conta> buscarPorCpf(String cpf) {
        return contaRepository.buscarPorCpf(cpf);
    }

    public void encerrarConta(Long id) {
        contaRepository.encerrarConta(id);
    }

    private void validarConta(Conta conta) {
        if (conta.getNomeTitular() == null || conta.getNomeTitular().isEmpty()) {
            throw new IllegalArgumentException("Nome do titular é obrigatório.");
        }
        if (conta.getCpfTitular() == null || conta.getCpfTitular().isEmpty()) {
            throw new IllegalArgumentException("CPF do titular é obrigatório.");
        }
        if (conta.getDataAbertura().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de abertura não pode ser no futuro.");
        }
        if (conta.getSaldo() < 0) {
            throw new IllegalArgumentException("O saldo inicial não pode ser negativo.");
        }
        if (!List.of("corrente", "poupança", "salário").contains(conta.getTipo().toLowerCase())) {
            throw new IllegalArgumentException("Tipo de conta inválido.");
        }
    }
}