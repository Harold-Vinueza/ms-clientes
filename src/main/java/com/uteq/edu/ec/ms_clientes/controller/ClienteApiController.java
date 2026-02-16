package com.uteq.edu.ec.ms_clientes.controller;

import com.uteq.edu.ec.ms_clientes.api.ApiApi;
import com.uteq.edu.ec.ms_clientes.openapi.model.Cliente;
import com.uteq.edu.ec.ms_clientes.openapi.model.ClienteInput;
import com.uteq.edu.ec.ms_clientes.service.ClienteService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ClienteApiController implements ApiApi {

    private final ClienteService service;

    public ClienteApiController(ClienteService service) {
        this.service = service;
    }

    // =========================
    // GET /api/clientes
    // =========================
    @Override
    public ResponseEntity<List<Cliente>> apiClientesGet() {

        List<Cliente> clientes = service.listarClientes()
                .stream()
                .map(this::toApiModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(clientes);
    }

    // =========================
    // GET /api/clientes/{id}
    // =========================
    @Override
    public ResponseEntity<Cliente> apiClientesIdGet(Long id) {

        var entity = service.buscarPorId(id);

        if (entity == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(toApiModel(entity));
    }

    // =========================
    // POST /api/clientes
    // =========================
    @Override
    public ResponseEntity<Void> apiClientesPost(@Valid ClienteInput input) {

        service.guardarCliente(toEntity(input));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // =========================
    // PUT /api/clientes/{id}
    // =========================
    @Override
    public ResponseEntity<Void> apiClientesPut(Long id, @Valid ClienteInput input) {

        var clienteDB = service.buscarPorId(id);
        if (clienteDB == null) {
            return ResponseEntity.notFound().build();
        }

        clienteDB.setCedula(input.getCedula());
        clienteDB.setNombre(input.getNombre());
        clienteDB.setApellido(input.getApellido());
        clienteDB.setEdad(input.getEdad());
        clienteDB.setCorreo(input.getCorreo());
        clienteDB.setTelefono(input.getTelefono());
        clienteDB.setDireccion(input.getDireccion());
        clienteDB.setEstado(input.getEstado().getValue());

        service.guardarCliente(clienteDB);
        return ResponseEntity.ok().build();
    }

    // =========================
    // DELETE /api/clientes/{id}
    // =========================
    @Override
    public ResponseEntity<Void> apiClientesDelete(Long id) {

        var clienteDB = service.buscarPorId(id);
        if (clienteDB == null) {
            return ResponseEntity.notFound().build();
        }

        service.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    // =========================
    // MAPPERS
    // =========================
    private Cliente toApiModel(com.uteq.edu.ec.ms_clientes.model.Cliente e) {

        Cliente c = new Cliente();

        c.setId(e.getId());
        c.setCedula(e.getCedula());
        c.setNombre(e.getNombre());
        c.setApellido(e.getApellido());
        c.setEdad(e.getEdad());
        c.setCorreo(e.getCorreo());
        c.setTelefono(e.getTelefono());
        c.setDireccion(e.getDireccion());
        c.setEstado(Cliente.EstadoEnum.fromValue(e.getEstado()));

        if (e.getFechaRegistro() != null) {
            c.setFechaRegistro(
                    e.getFechaRegistro().atOffset(ZoneOffset.of("-05:00"))
            );
        }

        return c;
    }

    private com.uteq.edu.ec.ms_clientes.model.Cliente toEntity(ClienteInput in) {

        com.uteq.edu.ec.ms_clientes.model.Cliente e =
                new com.uteq.edu.ec.ms_clientes.model.Cliente();

        e.setCedula(in.getCedula());
        e.setNombre(in.getNombre());
        e.setApellido(in.getApellido());
        e.setEdad(in.getEdad());
        e.setCorreo(in.getCorreo());
        e.setTelefono(in.getTelefono());
        e.setDireccion(in.getDireccion());
        e.setEstado(in.getEstado().getValue());

        return e;
    }
}
