package com.uteq.edu.ec.ms_clientes.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // NUEVO CAMPO
    @Column(nullable = false, unique = true, length = 10)
    private String cedula;

    private String nombre;
    private String apellido;

    // NUEVO CAMPO
    @Column(nullable = false)
    private Integer edad;

    @Column(unique = true)
    private String correo;

    private String telefono;
    private String direccion;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(nullable = false)
    private String estado;

    // SE EJECUTA SOLO AL CREAR
    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now(ZoneId.systemDefault());
        if (this.estado == null) {
            this.estado = "ACTIVO";
        }
    }

    // SE EJECUTA AL ACTUALIZAR
    @PreUpdate
    public void preUpdate() {
        if (this.estado == null) {
            this.estado = "ACTIVO";
        }
    }

    // ================= GETTERS Y SETTERS =================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
