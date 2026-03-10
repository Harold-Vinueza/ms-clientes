package com.uteq.edu.ec.ms_clientes.service;

import com.uteq.edu.ec.ms_clientes.model.Cliente;
import com.uteq.edu.ec.ms_clientes.repository.ClienteRepository;
import com.uteq.edu.ec.ms_clientes.validation.CedulaEcuatorianaValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    public static final String CEDULA_CONSUMIDOR_FINAL = "9999999999";

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> listarClientes() {
        return repository.findAll();
    }

    public Cliente guardarCliente(Cliente cliente) {
        return guardarCliente(cliente, false);
    }

    public Cliente guardarCliente(Cliente cliente, boolean consumidorFinal) {

        validarCedulaYReglas(cliente, consumidorFinal);
        validarDuplicado(cliente);

        return repository.save(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Cliente buscarPorCedula(String cedula) {
        return repository.findByCedula(cedula).orElse(null);
    }

    public void eliminarCliente(Long id) {
        repository.deleteById(id);
    }

    public boolean esConsumidorFinal(String cedula) {
        return CEDULA_CONSUMIDOR_FINAL.equals(cedula);
    }

    private void validarCedulaYReglas(Cliente cliente, boolean consumidorFinal) {

        if (cliente.getCedula() == null || cliente.getCedula().trim().isEmpty()) {
            throw new RuntimeException("La cédula es obligatoria");
        }

        String cedula = cliente.getCedula().trim();

        if (esConsumidorFinal(cedula)) {
            if (!consumidorFinal) {
                throw new RuntimeException("La cédula 9999999999 es exclusiva para Consumidor Final. Use el botón correspondiente.");
            }

            validarDatosConsumidorFinal(cliente);
            return;
        }

        if (cedula.length() != 10) {
            throw new RuntimeException("La cédula debe tener exactamente 10 dígitos");
        }

        if (!CedulaEcuatorianaValidator.esCedulaValida(cedula)) {
            throw new RuntimeException("La cédula ingresada no es válida");
        }
    }

    private void validarDuplicado(Cliente cliente) {
        Cliente existente = buscarPorCedula(cliente.getCedula());

        if (existente != null) {
            if (cliente.getId() == null || !existente.getId().equals(cliente.getId())) {
                throw new RuntimeException("Ya existe un cliente registrado con esa cédula");
            }
        }
    }

    private void validarDatosConsumidorFinal(Cliente cliente) {

        if (!"CONSUMIDOR".equalsIgnoreCase(valorSeguro(cliente.getNombre()))) {
            throw new RuntimeException("El nombre para Consumidor Final debe ser CONSUMIDOR");
        }

        if (!"FINAL".equalsIgnoreCase(valorSeguro(cliente.getApellido()))) {
            throw new RuntimeException("El apellido para Consumidor Final debe ser FINAL");
        }

        if (cliente.getEdad() == null || cliente.getEdad() != 0) {
            throw new RuntimeException("La edad para Consumidor Final debe ser 0");
        }

        if (!"consumidor.final@na.ec".equalsIgnoreCase(valorSeguro(cliente.getCorreo()))) {
            throw new RuntimeException("El correo para Consumidor Final debe ser consumidor.final@na.ec");
        }

        if (!"0000000000".equals(valorSeguro(cliente.getTelefono()))) {
            throw new RuntimeException("El teléfono para Consumidor Final debe ser 0000000000");
        }

        if (!"NO REGISTRA".equalsIgnoreCase(valorSeguro(cliente.getDireccion()))) {
            throw new RuntimeException("La dirección para Consumidor Final debe ser NO REGISTRA");
        }
    }

    private String valorSeguro(String valor) {
        return valor == null ? "" : valor.trim();
    }
}