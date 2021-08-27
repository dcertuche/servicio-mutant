package com.mercado.libre.app.mutante.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercado.libre.app.mutante.models.exception.ForbiddenException;
import com.mercado.libre.app.mutante.models.service.IMutantService;

@RestController
@RequestMapping("/api")
public class MutantController {

	@Autowired
	private IMutantService mutantService;

	@GetMapping("/stats")
	public ResponseEntity<?> estadistica() {
		try {
			Map<String, Object> response = mutantService.findAllMutant();
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (ForbiddenException e) {
			Map<String, Object> response = new HashMap<>();
			response.put("mensaje", e.getMsg());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.FORBIDDEN);
		}
	}

	@PostMapping("/mutant")
	public ResponseEntity<?> validar(@RequestBody String[] dna) {
		try {
			Map<String, Object> response = mutantService.validarMutant(dna);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (ForbiddenException e) {
			Map<String, Object> response = new HashMap<>();
			response.put("mensaje", e.getMsg());
			response.put("DNA", Arrays.toString(dna));
			response.put("Boolean", false);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.FORBIDDEN);
		}
	}

}
