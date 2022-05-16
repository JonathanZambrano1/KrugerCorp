/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.management;

import com.example.data.DataConnection;
import com.example.data.DataUsuario;
import com.example.entity.DTOEstadoVacunacion;
import com.example.entity.DTOUsuario;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author JEZ
 */
public class Usuario {
    
    private final DataUsuario dataUsuario;
    
    public Usuario(DataConnection connection) throws Exception{
        this.dataUsuario = new DataUsuario(connection);
    }
 
    public DTOUsuario getUsuario(String cedula,String password) throws SQLException{
        if (!cedula.equalsIgnoreCase("") || !password.equalsIgnoreCase("")) {
            return dataUsuario.getUsuario(cedula,password);
        }else{
            return null;
        }
    }

    public String insertUsuario(DTOUsuario insert) throws SQLException {
        String validate = validationInsert(insert);
        String resp = "";
        if (validate.equalsIgnoreCase("")) {
            int info = dataUsuario.insertUsuario(insert.getCedula(), insert.getNombre(), insert.getApellido(),
                    insert.getCorreo(),insert.getFechaNacimiento(),insert.getDireccion(),insert.getTelefono(),insert.getRol());
            if (info == 0) {
                resp = "Error al hacer el insert";
            } else {
                resp = "Insertado Correctamente";
            }
        }else{
            resp = validate;
        }
        return resp;
    }
    
    public String validationInsert(DTOUsuario insert) {
        
        String info = "";

        if (insert.getCedula().length() == 10 && !insert.getCedula().equalsIgnoreCase("")) {
            if (insert.getNombre().equalsIgnoreCase("") || insert.getApellido().equalsIgnoreCase("")
                    || insert.getCorreo().equalsIgnoreCase("") || insert.getFechaNacimiento().equalsIgnoreCase("") || insert.getDireccion().equalsIgnoreCase("")
                    || insert.getTelefono().equalsIgnoreCase("") || insert.getRol().equalsIgnoreCase("")) {
                info = "Error un campo está vacio";
            }
            
            /*if(insert.getNombre().matches(".*\\d.*") || insert.getApellido().matches(".*\\d.*")){
                info = "Error el Nombre o el Apellido no puede contener numeros";
            }*/

            if(!insert.getNombre().matches("[a-zA-Z]+") || !insert.getApellido().matches("[a-zA-Z]+")){
                info = "Error el Nombre o el Apellido no puede contener numeros ni caracteres especiales";
            }
            
            Pattern pat = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher mat = pat.matcher(insert.getCorreo());
            if (!mat.find()) {
                info = "El Correo no es válido";
            }
        } else {
            info = "La cedula debe ser numérica y tener 10 digitos";
        }
        return info;
    }
    public String darAlta(String cedula) throws SQLException {
        String info = "";
        if(!cedula.equalsIgnoreCase("")){
            int data =  dataUsuario.darAlta(cedula);
                if (data == 2) {
                    info = "Error al dar de alta, el usuario no existe o ya fue dado de alta";
                } else if(data == 1) {
                    info = "Usuario: "+cedula+" dado de alta correctamente";
                }
        }else{
            info = "Tiene que existir la cédula";
        }
        return info;
    }
    
    
    public String updateVacunacion(DTOEstadoVacunacion updateVac) throws SQLException {
        String validate = validationVacunacion(updateVac);
        String resp = "";
        if (validate.equalsIgnoreCase("")) {
            int info = dataUsuario.updateVacunacion(updateVac.getCedula(), updateVac.getEstadoVac(), updateVac.getTipoVacuna(),
                    updateVac.getFechaVacuna(), updateVac.getNumeroDosis());
            if (info == 0) {
                resp = "Error al hacer la modificación";
            } else {
                resp = "Usuario: " + updateVac.getCedula() + " Modificado Correctamente";
            }
        } else {
            resp = validate;
        }
        return resp;
    }

    public String validationVacunacion(DTOEstadoVacunacion updateVac) {

        String info = "";

        if (updateVac.getCedula().length() == 10 && !updateVac.getCedula().equalsIgnoreCase("")) {
            if (updateVac.getEstadoVac().equalsIgnoreCase("") || updateVac.getFechaVacuna().equalsIgnoreCase("")
                    || updateVac.getTipoVacuna().equalsIgnoreCase("")) {
                info = "Error un campo está vacio";
            }
        } else {
            info = "La cedula debe ser numérica y tener 10 digitos";
        }
        return info;
    }
    
    
    
}
