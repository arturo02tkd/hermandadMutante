package com.mx.hermandad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.hermandad.model.Dna;
import com.mx.hermandad.model.Verificasion;
import com.mx.hermandad.services.ClsResServices;
import com.mx.hermandad.utils.apiErros.ExceptionManager;

/**
 * hermandad de mutantes
 * 
 * @author Luis Arturo Aguilar Limas
 * @version 1.0
 * @sinse 12/06/2022
 */

@RestController
@RequestMapping("/api")
public class Hermandad {

	@Autowired
	private ClsResServices userService;

	@GetMapping("/stats")
	public ResponseEntity<Verificasion> estadistica() {
		return ResponseEntity.ok(userService.estadistica());
	}

	@PostMapping("/mutante")
	public ResponseEntity<String> isMutant(@RequestBody Dna dna) {
		try {
			if (userService.isMutant(dna.getDna())) {
				return new ResponseEntity<>("Es mutante", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Es Humano", HttpStatus.FORBIDDEN);
			}
		} catch (ExceptionManager e) {
			throw new ExceptionManager(e.getStatus(), e.getMessage(), e);
		}

	}

}
