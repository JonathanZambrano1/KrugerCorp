/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krugercorp.controller;

import com.krugercorp.data.DataConnection;
import com.krugercorp.entity.DTOEstadoVacunacion;
import com.krugercorp.entity.DTOUsuario;
import com.krugercorp.management.Usuario;
import com.krugercorp.util.AnnotationExclusionStrategy;
import com.krugercorp.util.DTOResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JEZ
 */
@RestController
public class ControladorUsuario {
    
    @GetMapping("login/{cedula}/{password}")
    public ResponseEntity login(@PathVariable String cedula,@PathVariable String password) throws SQLException, JSONException {
        DataConnection connection = null;
        DTOResponse d = new DTOResponse();
        Map<String, Object> obj = new HashMap<>();
        try {
            connection = new DataConnection();
            connection.open();
            Usuario usuario = new Usuario(connection);
            DTOUsuario access = usuario.getUsuario(cedula,password);
            if(access != null){
                obj.put("Usuario", access);
            }else{
                obj.put("Usuario", "Error usuario y/o contraseña incorrecta");
            }
            
        } catch (Exception e) {
            obj.put("Usuario", e.fillInStackTrace());
        } finally {
            connection.close();
        }
        d.setData(obj);
        Gson objGson = new GsonBuilder().setExclusionStrategies(new AnnotationExclusionStrategy()).create();
        return ResponseEntity
                .ok(objGson.toJson(d));

    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String registerUser(@RequestBody String json) throws SQLException, JSONException {
        DataConnection connection = null;
        JSONObject data = new JSONObject();
        Gson gson = new Gson();
        DTOUsuario insert = gson.fromJson(json, DTOUsuario.class);
        try {
            connection = new DataConnection();
            connection.open();
            Usuario usuario = new Usuario(connection);
            String Info = usuario.insertUsuario(insert);
            data.put("Info", Info);
        } catch (Exception e) {
            data.put("Usuario", e.fillInStackTrace());
        } finally {
            connection.close();
        }
        return data.toString();

    }

    @RequestMapping(value = "/updateVacunacion", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateVacunacion(@RequestBody String json) throws SQLException, JSONException {
        DataConnection connection = null;
        JSONObject data = new JSONObject();
        Gson gson = new Gson();
        DTOEstadoVacunacion updateVacunacion = gson.fromJson(json, DTOEstadoVacunacion.class);
        try {
            connection = new DataConnection();
            connection.open();
            Usuario usuario = new Usuario(connection);
            String Info = usuario.updateVacunacion(updateVacunacion);
            data.put("Info", Info);
        } catch (Exception e) {
            data.put("Usuario", e.fillInStackTrace());
        } finally {
            connection.close();
        }
        return data.toString();

    }

}
