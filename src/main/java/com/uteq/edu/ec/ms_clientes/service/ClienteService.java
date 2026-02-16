package com.uteq.edu.ec.ms_clientes.service;

import com.uteq.edu.ec.ms_clientes.model.Cliente;
import com.uteq.edu.ec.ms_clientes.repository.ClienteRepository;
import com.uteq.edu.ec.ms_clientes.validation.CedulaEcuatorianaValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> listarClientes() {
        return repository.findAll();
    }

    public Cliente guardarCliente(Cliente cliente) {

        // 🔥 VALIDACIÓN REAL DE CÉDULA
        if (!CedulaEcuatorianaValidator.esCedulaValida(cliente.getCedula())) {
            throw new RuntimeException("La cédula ingresada no es válida");
        }

        return repository.save(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void eliminarCliente(Long id) {
        repository.deleteById(id);
    }
}
