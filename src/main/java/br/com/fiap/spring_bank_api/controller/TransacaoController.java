package br.com.fiap.spring_bank_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.transaction.Transactional;

import br.com.fiap.spring_bank_api.model.Conta;
import br.com.fiap.spring_bank_api.service.ContaService;
import br.com.fiap.spring_bank_api.repository.ContaRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transacoes")
@RequiredArgsConstructor
public class TransacaoController {
    private final ContaService contaService;
    private final ContaRepository contaRepository;

    @Transactional
    @PostMapping("/deposito/{id}")
    public ResponseEntity<Conta> depositar(@PathVariable Long id, @RequestParam double valor) {
        if (valor <= 0) {
            return ResponseEntity.badRequest().body(null);
        }

        Conta conta = contaService.buscarPorId(id).orElseThrow();
        conta.setSaldo(conta.getSaldo() + valor);
        contaRepository.save(conta);

        return ResponseEntity.ok(conta);
    }

    @Transactional
    @PostMapping("/saque/{id}")
    public ResponseEntity<Conta> sacar(@PathVariable Long id, @RequestParam double valor) {
        if (valor <= 0) {
            return ResponseEntity.badRequest().body(null);
        }

        Conta conta = contaService.buscarPorId(id).orElseThrow();
        if (conta.getSaldo() < valor) {
            return ResponseEntity.badRequest().body(null);
        }

        conta.setSaldo(conta.getSaldo() - valor);
        contaRepository.save(conta);

        return ResponseEntity.ok(conta);
    }

    @Transactional
    @PostMapping("/pix")
    public ResponseEntity<Conta> pix(@RequestParam Long origem, @RequestParam Long destino, @RequestParam double valor) {
        if (valor <= 0) {
            return ResponseEntity.badRequest().body(null);
        }

        Conta contaOrigem = contaService.buscarPorId(origem).orElseThrow();
        Conta contaDestino = contaService.buscarPorId(destino).orElseThrow();

        if (contaOrigem.getSaldo() < valor) {
            return ResponseEntity.badRequest().body(null);
        }

        contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
        contaDestino.setSaldo(contaDestino.getSaldo() + valor);

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);

        return ResponseEntity.ok(contaOrigem);
    }
}
