/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.management;

import com.example.data.DataAdministrador;
import com.example.data.DataConnection;
import com.example.entity.DTOEstadoVacunacion;
import com.example.entity.DTOUsuario;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author JEZ
 */
public class Administrador {

    private final DataAdministrador dataAdministrador;
    Usuario usuario;

    public Administrador(DataConnection connection) throws Exception {
        this.dataAdministrador = new DataAdministrador(connection);
    }

    public List<DTOEstadoVacunacion> getFilterState(String state) throws SQLException {
        return dataAdministrador.getFilterState(state);
    }
    public List<DTOEstadoVacunacion> getFilterType(String type) throws SQLException {
        return dataAdministrador.getFilterType(type);
    }
    public List<DTOEstadoVacunacion> getFilterRange(String range) throws SQLException {
        return dataAdministrador.getFilterRange(range);
    }
    public List<DTOUsuario> getFilterRol(String rol) throws SQLException {
        return dataAdministrador.getFilterRol(rol);
    }
    public String deleteUsuario(String cedula) throws SQLException {
        int info = dataAdministrador.deleteUsuario(cedula);
        if (info == 0) {
           return "Error al eliminar el usuario";
        } else {
           return "Usuario eliminado correctamente";
        }
    }
    public String updateUsuario(DTOUsuario update) throws SQLException{
        String validate = validationUpdate(update);
        String resp = "";
        if (validate.equalsIgnoreCase("")) {
            int info = dataAdministrador.updateUsuario(update.getCedula(), update.getNombre(), update.getApellido(),
                    update.getCorreo(),update.getTelefono(),update.getRol());
            if (info == 0) {
                resp = "Error al hacer la modificación";
            } else {
                resp = "Usuario: "+update.getCedula()+" Modificado Correctamente";
            }
        }else{
            resp = validate;
        }
        return resp;
    }
    public String validationUpdate(DTOUsuario update) {
        
        String info = "";

        if (update.getCedula().length() == 10 && !update.getCedula().equalsIgnoreCase("")) {
            if (update.getNombre().equalsIgnoreCase("") || update.getApellido().equalsIgnoreCase("")
                    || update.getCorreo().equalsIgnoreCase("") || update.getFechaNacimiento().equalsIgnoreCase("") || update.getDireccion().equalsIgnoreCase("")
                    || update.getTelefono().equalsIgnoreCase("") || update.getRol().equalsIgnoreCase("")) {
                info = "Error un campo está vacio";
            }
            
            /*if(update.getNombre().matches(".*\\d.*") || update.getApellido().matches(".*\\d.*")){
                info = "Error el Nombre o el Apellido no puede contener numeros";
            }*/
            
            if (!update.getNombre().matches("[a-zA-Z]+") || !update.getApellido().matches("[a-zA-Z]+")) {
                info = "Error el Nombre o el Apellido no puede contener numeros ni caracteres especiales";
            }
            
            Pattern pat = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher mat = pat.matcher(update.getCorreo());
            if (!mat.find()) {
                info = "El Correo no es válido";
            }
        } else {
            info = "La cedula debe ser numérica y tener 10 digitos";
        }
        return info;
    }
    
}
