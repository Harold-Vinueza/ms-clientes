package com.uteq.edu.ec.ms_clientes.repository;

import com.uteq.edu.ec.ms_clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
