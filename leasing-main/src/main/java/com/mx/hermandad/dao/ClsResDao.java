package com.mx.hermandad.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mx.hermandad.model.RegistroMutante;
/**
 * hermandad de mutantes 
 * 
 * @author Luis Arturo Aguilar Limas
 * @version 1.0
 * @sinse 12/06/2022
 */
public interface ClsResDao extends JpaRepository<RegistroMutante, Integer>{
   
}
