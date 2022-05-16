/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krugercorp.data;

import com.krugercorp.entity.DTOUsuario;
import com.krugercorp.util.Convert;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author JEZ
 */
public class DataUsuario {

    private final DataConnection connection;

    public DataUsuario(DataConnection connection) throws Exception {
        this.connection = connection;
    }

    public DTOUsuario getUsuario(String cedula,String password) throws SQLException {
        String query = "select cedula,nombre,apellido,correo,rol from ft_usuarios where cedula = '" + cedula + "' and pass = '" + password + "'";
        Convert convert = new Convert(connection.select(query));
        DTOUsuario dtoUsuario = convert.mapToSingle(DTOUsuario.class);
        return dtoUsuario;
    }
    
    public int insertUsuario(String cedula,String nombre, String apellido, String correo, String fechaNacimiento,String direccion, String telefono, String rol) throws SQLException{
        String insert = "INSERT INTO ft_usuarios (cedula,nombre,apellido,correo,fecha_nacimiento,direccion,telefono,rol) "
                + "VALUES "
                + "('"+cedula+"'::character varying,'"+nombre+"'::character varying,'"+apellido+"'::character varying,'"+correo+"'::character varying,'"+fechaNacimiento+"'::date,'"+direccion+"'::character varying,'"+telefono+"'::character varying,'"+rol+"'::character varying)";
        int val = connection.allOption(insert);
        if(val == 1){       
            String vacunacion = "INSERT INTO ft_vacunacion (cedula,estado_vacunacion,tipo_vacuna,fecha_vacunacion,numero_dosis) "
                    + "VALUES "
                    + "('" + cedula + "'::character varying,0::integer,null,null,0::integer)";
            connection.allOption(vacunacion);
        }
        return val;
    }
    
    public int darAlta(String cedula) throws SQLException {
        String query = "Select alta from ft_usuarios where cedula = '" + cedula + "'";
        ResultSet rs = connection.select(query);
        int information = 2;
        if(rs.next()){
            if (rs.getString(1).equalsIgnoreCase("0")) {
                String update = "UPDATE ft_usuarios SET alta = '1'::integer, pass = '" + cedula + "'::character varying WHERE (cedula = '" + cedula + "'::character varying)";
                connection.allOption(update);
                return information = 1;
            }else{
                return information = 2;
            }

        }else{
            return information = 2;
        }
    }
    
    
    public int updateVacunacion(String cedula, String estadoVac, String tipoVac, String fechaVac, int numeroDosis) throws SQLException {
        String update = "UPDATE ft_vacunacion SET estado_vacunacion = '" + estadoVac + "'::character varying,"
                + " tipo_vacuna = '" + tipoVac + "'::character varying, fecha_vacunacion = '" + fechaVac + "'::date, numero_dosis = '" + numeroDosis + "'::integer WHERE cedula = '" + cedula + "'";
        int val = connection.allOption(update);
        return val;
    }
    
}
