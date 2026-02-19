package com.uteq.edu.ec.ms_clientes.controller;

import com.uteq.edu.ec.ms_clientes.model.Cliente;
import com.uteq.edu.ec.ms_clientes.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    // ======================
    // VISTA PRINCIPAL
    // ======================

    @GetMapping("/web")
    public String vista(Model model) {
        model.addAttribute("clientes", service.listarClientes());
        model.addAttribute("cliente", new Cliente());
        return "clientes";
    }

    // ======================
    // GUARDAR / EDITAR CLIENTE
    // ======================

    @PostMapping("/web/guardar")
    public String guardarWeb(@ModelAttribute Cliente cliente, Model model) {

        try {

            if (cliente.getId() != null) {
                Cliente clienteDB = service.buscarPorId(cliente.getId());
                cliente.setFechaRegistro(clienteDB.getFechaRegistro());
            }

            service.guardarCliente(cliente);

        } catch (RuntimeException e) {

            model.addAttribute("clientes", service.listarClientes());
            model.addAttribute("cliente", cliente);
            model.addAttribute("errorMensaje", e.getMessage());

            return "clientes";
        }

        return "redirect:/clientes/web";
    }

    // ======================
    // EDITAR
    // ======================

    @GetMapping("/web/editar/{id}")
    public String editarWeb(@PathVariable Long id, Model model) {
        model.addAttribute("clientes", service.listarClientes());
        model.addAttribute("cliente", service.buscarPorId(id));
        return "clientes";
    }

    // ======================
    // ELIMINAR
    // ======================

    @GetMapping("/web/eliminar/{id}")
    public String eliminarWeb(@PathVariable Long id) {
        service.eliminarCliente(id);
        return "redirect:/clientes/web";
    }

    // ======================
    // ACTUALIZAR SOLO ESTADO
    // ======================

    @PostMapping("/web/estado/{id}")
    public String actualizarEstado(@PathVariable Long id,
                                   @RequestParam String estado) {

        Cliente cliente = service.buscarPorId(id);

        if (cliente != null) {
            cliente.setEstado(estado);
            service.guardarCliente(cliente);
        }

        return "redirect:/clientes/web";
    }
}
