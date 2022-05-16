/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.util;

import com.google.gson.annotations.SerializedName;
import lombok.Setter;

/**
 *
 * @author JEZ
 */
public class DTOResponse<T>{
   @SerializedName("data")
   @Setter
   private T data;
}
