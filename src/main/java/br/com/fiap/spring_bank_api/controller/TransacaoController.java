package br.com.fiap.spring_bank_api.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.spring_bank_api.model.Conta;
import br.com.fiap.spring_bank_api.service.ContaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transacoes")
@RequiredArgsConstructor
public class TransacaoController {
    private final ContaService contaService;

    @PostMapping("/deposito/{id}")
    public ResponseEntity<Conta> depositar(@PathVariable Long id, @RequestParam double valor) {
        Conta conta = contaService.buscarPorId(id).orElseThrow();
        conta.setSaldo(conta.getSaldo() + valor);
        return ResponseEntity.ok(conta);
    }

    @PostMapping("/saque/{id}")
    public ResponseEntity<Conta> sacar(@PathVariable Long id, @RequestParam double valor) {
        Conta conta = contaService.buscarPorId(id).orElseThrow();
        if (conta.getSaldo() < valor) return ResponseEntity.badRequest().build();
        conta.setSaldo(conta.getSaldo() - valor);
        return ResponseEntity.ok(conta);
    }

    @PostMapping("/pix")
    public ResponseEntity<Conta> pix(@RequestParam Long origem, @RequestParam Long destino, @RequestParam double valor) {
        Conta contaOrigem = contaService.buscarPorId(origem).orElseThrow();
        Conta contaDestino = contaService.buscarPorId(destino).orElseThrow();
        if (contaOrigem.getSaldo() < valor) return ResponseEntity.badRequest().build();
        contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
        contaDestino.setSaldo(contaDestino.getSaldo() + valor);
        return ResponseEntity.ok(contaOrigem);
    }
}
