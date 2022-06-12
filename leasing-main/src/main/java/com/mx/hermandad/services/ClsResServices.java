package com.mx.hermandad.services;

import com.mx.hermandad.model.Verificasion;

/**
 * hermandad de mutantes 
 * 
 * @author Luis Arturo Aguilar Limas
 * @version 1.0
 * @sinse 12/06/2022
 */
public interface ClsResServices {
    public boolean isMutant(String[] dna);
    public Verificasion estadistica();
}
