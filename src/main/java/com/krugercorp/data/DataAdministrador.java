/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krugercorp.data;

import com.krugercorp.entity.DTOEstadoVacunacion;
import com.krugercorp.entity.DTOUsuario;
import com.krugercorp.util.Convert;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author JEZ
 */
public class DataAdministrador {
    
    private final DataConnection connection;

    public DataAdministrador(DataConnection connection) throws Exception {
        this.connection = connection;
    }

    public List<DTOEstadoVacunacion> getFilterState(String state) throws SQLException {
        String query = "select V.*,U.nombre,U.Apellido,U.correo from ft_vacunacion V left join ft_usuarios U on v.cedula = u.cedula where estado_vacunacion = '"+state+"'";
        Convert convert = new Convert(connection.select(query));
        List<DTOEstadoVacunacion> dtoUsuario = convert.mapToList(DTOEstadoVacunacion.class);
        if (dtoUsuario.size() == 1) {
            if (dtoUsuario.get(0).getEstadoVac().equalsIgnoreCase("1")) {
                dtoUsuario.get(0).setEstadoVac("Vacunado");
            } else {
                dtoUsuario.get(0).setEstadoVac("No Vacunado");
            }
        } else {
            for (int i = 0; i < dtoUsuario.size(); i++) {
                if (dtoUsuario.get(i).getEstadoVac().equalsIgnoreCase("1")) {
                    dtoUsuario.get(i).setEstadoVac("Vacunado");
                } else {
                    dtoUsuario.get(i).setEstadoVac("No Vacunado");
                }
            }
        }
        return dtoUsuario;
    }
    public List<DTOEstadoVacunacion> getFilterType(String type) throws SQLException {
        String query = "select V.*,U.nombre,U.Apellido,U.correo from ft_vacunacion V left join ft_usuarios U on v.cedula = u.cedula where  tipo_vacuna LIKE '%"+type+"%' ORDER BY tipo_vacuna";
        Convert convert = new Convert(connection.select(query));
        List<DTOEstadoVacunacion> dtoUsuario = convert.mapToList(DTOEstadoVacunacion.class);
        return dtoUsuario;
    }
    
    public List<DTOEstadoVacunacion> getFilterRange(String range) throws SQLException {
        String [] data = range.split(",");
        String query = "select V.*,U.nombre,U.Apellido,U.correo from ft_vacunacion V left join ft_usuarios U on v.cedula = u.cedula where fecha_vacunacion BETWEEN '"+data[0]+"' and '"+data[1]+"' ";
        Convert convert = new Convert(connection.select(query));
        List<DTOEstadoVacunacion> dtoUsuario = convert.mapToList(DTOEstadoVacunacion.class);
        return dtoUsuario;
    }
    public int updateUsuario(String cedula, String nombre, String apellido, String correo, String telefono, String rol) throws SQLException {
        String update = "UPDATE ft_usuarios SET nombre = '"+nombre+"'::character varying,"
                + " apellido = '"+apellido+"'::character varying, correo = '"+correo+"'::character varying, telefono = '"+telefono+"'::character varying, rol = '"+rol+"'::character varying WHERE cedula = '"+cedula+"'";
        int val = connection.allOption(update);
        return val;
    }
    public List<DTOUsuario> getFilterRol(String rol) throws SQLException {
        String query = "select cedula,nombre,apellido,correo,rol from ft_usuarios where rol = '" + rol + "'";
        Convert convert = new Convert(connection.select(query));
        List<DTOUsuario> dtoUsuario = convert.mapToList(DTOUsuario.class);
        return dtoUsuario;
    }
    public int deleteUsuario(String cedula) throws SQLException {
        
        String query = "Select cedula from ft_usuarios where cedula = '" + cedula + "'";
        ResultSet rs = connection.select(query);
        int val = 0;
        if (rs.next()) {
            String deleteVac = "DELETE FROM ft_vacunacion WHERE cedula IN ('" + cedula + "')";
            connection.allOption(deleteVac);
            
            String delete = "DELETE FROM ft_usuarios WHERE cedula IN ('" + cedula + "')";
            val = connection.allOption(delete);
        }
        return val;
    }
}
