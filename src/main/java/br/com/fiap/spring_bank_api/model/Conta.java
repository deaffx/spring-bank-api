package br.com.fiap.spring_bank_api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "contas")
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numero;

    @Column(nullable = false)
    private String agencia;

    @Column(nullable = false)
    private String nomeTitular;

    @Column(nullable = false, unique = true)
    private String cpfTitular;

    @Column(nullable = false)
    private LocalDate dataAbertura;

    @Column(nullable = false)
    private double saldo;

    @Column(nullable = false)
    private boolean ativa;

    @Column(nullable = false)
    private String tipo; // "corrente" - "poupança" - "salário"
}
