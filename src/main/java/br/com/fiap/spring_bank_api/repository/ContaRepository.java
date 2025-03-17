package br.com.fiap.spring_bank_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fiap.spring_bank_api.model.Conta;
import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findByCpfTitular(String cpf);
}
