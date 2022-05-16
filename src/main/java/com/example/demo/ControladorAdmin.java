/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import com.example.data.DataConnection;
import com.example.entity.DTOEstadoVacunacion;
import com.example.entity.DTOUsuario;
import com.example.management.Administrador;
import com.example.util.AnnotationExclusionStrategy;
import com.example.util.DTOResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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
public class ControladorAdmin {
    
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateUsuario(@RequestBody String json) throws SQLException, JSONException {
        DataConnection connection = null;
        JSONObject data = new JSONObject();
        Gson gson = new Gson();
        DTOUsuario update = gson.fromJson(json, DTOUsuario.class);
        try {
            connection = new DataConnection();
            connection.open();
            Administrador admin = new Administrador(connection);
            String Info = admin.updateUsuario(update);
            data.put("Info", Info);
        } catch (Exception e) {
            data.put("Usuario", e.fillInStackTrace());
        } finally {
            connection.close();
        }
        return data.toString();
        
    }
    
    @PutMapping("delete/{cedula}")
    public String deleteUsuario(@PathVariable String cedula) throws SQLException, JSONException {
        DataConnection connection = null;
        JSONObject data = new JSONObject();
        try {
            connection = new DataConnection();
            connection.open();
            Administrador admin = new Administrador(connection);
            String info = admin.deleteUsuario(cedula);
            data.put("Info", info);
        } catch (Exception e) {
            data.put("Info", e.fillInStackTrace());
        } finally {
            connection.close();
        }
        return data.toString();

    }
    
    /*@GetMapping("filter/rol/{search}")
    public ResponseEntity filterRol(@PathVariable String rol) throws SQLException, JSONException {
        DataConnection connection = null;
        DTOResponse d = new DTOResponse();
        Map<String, Object> obj = new HashMap<>();
        try {
            connection = new DataConnection();
            connection.open();
            Administrador admin = new Administrador(connection);
            List<DTOUsuario> user = admin.getFilterRol(rol);
            if (user.size() <= 0) {
                obj.put("Info", "Error el rol no existe");
            } else {
                obj.put("Info", user);
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

    }*/
    

    
    @GetMapping("filter/{option}/{info}")
    public ResponseEntity filterState(@PathVariable String option,
            @PathVariable String info) throws SQLException, JSONException {
        DataConnection connection = null;
        //JSONObject data = new JSONObject();
        DTOResponse d = new DTOResponse();
        Map<String, Object> obj = new HashMap<>();
        try {
            connection = new DataConnection();
            connection.open();
            Administrador admin = new Administrador(connection);
            String message = "";
            if (option.equalsIgnoreCase("state")) {
                List<DTOEstadoVacunacion> user = admin.getFilterState(info);
                if(user.size() <= 0){
                    if(info.equalsIgnoreCase("1")){
                       obj.put("Info", "No existen ningun usuario con ese estado"); 
                    }else{
                       obj.put("Info", "Error al digitar el estado esta entre 0 - 1"); 
                    }
                    
                }else{
                    obj.put("Info", user);
                }
            } else if (option.equalsIgnoreCase("type")) {
                List<DTOEstadoVacunacion> user = admin.getFilterType(info);
                if (user.size() <= 0) {
                    obj.put("Info", "No se encuentra ningun usuario con esa vacuna");
                } else {
                    obj.put("Info", user);
                }
            } else if (option.equalsIgnoreCase("range")) {
                List<DTOEstadoVacunacion> user = admin.getFilterRange(info);
                if (user.size() <= 0) {
                    obj.put("Info", "No se encuentra ningun usuario con ese rango");
                } else {
                    obj.put("Info", user);
                }
            } else if (option.equalsIgnoreCase("rol")) {
                List<DTOUsuario> user = admin.getFilterRol(info);
                if (user.size() <= 0) {
                    obj.put("Info", "Error el rol no existe");
                } else {
                    obj.put("Info", user);
                }
            } else {
                message = "Error la opcion solicitada no es válida";
            }

        } catch (Exception e) {
            obj.put("Info", e.fillInStackTrace());   
        } finally {
            connection.close();
        }
        d.setData(obj);
        Gson objGson = new GsonBuilder().setExclusionStrategies(new AnnotationExclusionStrategy()).create();
        return ResponseEntity
                .ok(objGson.toJson(d));
    }

}
