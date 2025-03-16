package br.com.fiap.spring_bank_api.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class Conta {
    private Long id;
    private String numero;
    private String agencia;
    private String nomeTitular;
    private String cpfTitular;
    private LocalDate dataAbertura;
    private double saldo;
    private boolean ativa;
    private String tipo;
}