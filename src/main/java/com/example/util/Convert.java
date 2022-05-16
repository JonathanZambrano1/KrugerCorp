/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author JEZ
 */
public class Convert {

    ResultSet data;

    public Convert(ResultSet data) {
        this.data = data;
    }

    public <T> List<T> mapToList(Class<T> outputClass) {
        List<T> outputList = new ArrayList();
        if (this.data == null) {
            return outputList;
        }
        if (!outputClass.isAnnotationPresent(Entity.class)) {
        }
        try {
            ResultSetMetaData rsmd = this.data.getMetaData();
            Field[] fields = outputClass.getDeclaredFields();
            while (this.data.next()) {
                T bean = outputClass.newInstance();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    String rsColumnName = rsmd.getColumnName(i);
                    Object rsColumnValue = this.data.getObject(i);
                    for (Field field : fields) {
                        String beanColumnName = field.getName();

                        if (field.isAnnotationPresent(Column.class)) {
                            Column column = (Column) field.getAnnotation(Column.class);
                            if (!column.name().isEmpty()) {
                                beanColumnName = column.name();
                            }
                        }
                        if (beanColumnName.equalsIgnoreCase(rsColumnName)) {
                            BeanUtils.setProperty(bean, field.getName(), rsColumnValue);
                            break;
                        }
                    }
                }
                outputList.add(bean);
            }
            this.data.close();
        } catch (IllegalAccessException | InstantiationException | SecurityException | InvocationTargetException | SQLException e) {
            String error = "asda";
        }
        return outputList;
    }

    public <T> T mapToSingle(Class<T> outputClass) {
        List<T> outputList = mapToList(outputClass);
        if (outputList.size() <= 1) {
            if (outputList.isEmpty()) {
                return null;
            }
        }
        return (T) outputList.get(0);
    }

    public static List<Map<String, Object>> getData(JSONArray data, String orden) throws Exception {
        List<Map<String, Object>> rows = new ArrayList<>();
     
        if(orden.equals("ASC"))
        for (int i = 0; i < data.length(); i++) {
            Map<String, Object> row = new LinkedHashMap<>();
            JSONObject aux = data.getJSONObject(i);
            Iterator<String> iter = aux.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                Object value = aux.get(key);
                  row.put(key,value);
            }
            rows.add(row);
        }
        else if(orden.equals("DESC"))
            for (int i = (data.length()-1); i >=0 ; i--) {
            Map<String, Object> row = new LinkedHashMap<>();
            JSONObject aux = data.getJSONObject(i);
            Iterator<String> iter = aux.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                Object value = aux.get(key);
                  row.put(key,value);
            }
            rows.add(row);
        }
//        while (data.next()) {
//            Map<String, Object> row = new LinkedHashMap<>();
//            for (int i = 1; i <= columns; i++) {
//                row.put(data.getColumnName(i), this.data.getString(i, ""));
//            }
//            rows.add(row);
//        }
        return rows;
    }
    public static Date formatFechaToDate(String fecha)  {
        Date fechaFormateada=null;
         try{
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
         fechaFormateada= formato.parse( fecha);
         }catch(Exception e){
             System.out.println(e);
         }
        return fechaFormateada;
    }
    
     public static Date formatHoraToHour(String fecha)  {
        Date fechaFormateada=null;
         try{
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
         fechaFormateada= formato.parse( fecha);
         }catch(Exception e){
             System.out.println(e);
         }
        return fechaFormateada;
    }
      public static String formatFecha(String fecha) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date fechaFormateada= formato.parse(fecha);
        SimpleDateFormat formateadorFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date= formateadorFecha.format(fechaFormateada);
        return date;
    }
}
