/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krugercorp.entity;

import javax.persistence.Column;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author JEZ
 */
public class DTOUsuario {
    
    @Getter @Setter
    @Column(name="cedula")
    private String cedula;

    @Getter @Setter
    @Column(name="pass")
    private String password;
    
    @Getter @Setter
    @Column(name="nombre")
    private String nombre;
    
    @Getter @Setter
    @Column(name="apellido")
    private String apellido;
    
    @Getter @Setter
    @Column(name="correo")
    private String correo;
    
    @Getter @Setter
    @Column(name="fecha_nacimiento")
    private String fechaNacimiento;
    
    @Getter @Setter
    @Column(name="direccion")
    private String direccion;
    
    @Getter @Setter
    @Column(name="telefono")
    private String telefono;
    
    @Getter @Setter
    @Column(name="rol")
    private String rol;

    
}
