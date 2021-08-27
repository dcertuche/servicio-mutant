package com.mercado.libre.app.mutante.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercado.libre.app.mutante.models.dto.EstadisticaDto;
import com.mercado.libre.app.mutante.models.entity.Mutant;
import com.mercado.libre.app.mutante.models.exception.ForbidenException;
import com.mercado.libre.app.mutante.models.service.IMutantService;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class MutantController {

	@Autowired
	private IMutantService mutantService;

	@GetMapping("/ver")
	public List<Mutant> show() {
		return mutantService.findAllMutant();
	}

	@GetMapping("/stats")
	public ResponseEntity<?> estadidtica() {
		Map<String, Object> response = new HashMap<>();
		List<Mutant> lista = mutantService.findAllMutant();
		if (lista.isEmpty()) {
			response.put("mensaje", "Error: no se puedo consultar, no existe datos en la base de datos v2.0");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		EstadisticaDto estadisticaDto = new EstadisticaDto();
		try {
			mutantService.metodoMover(lista, estadisticaDto);
		} catch (Exception e) {
			response.put("mensaje", "error al consultar en la base de datos v2.0");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("ADN", estadisticaDto);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PostMapping("/mutant")
	public ResponseEntity<?> validar(@RequestBody String[] dna) {

		try {
			Map<String, Object> response = mutantService.validarMutant(dna);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (ForbidenException e) {
			Map<String, Object> response = new HashMap<>();
			response.put("mensaje", e.getMsg());
			response.put("DNA", Arrays.toString(dna));
			response.put("Boolean", false);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.FORBIDDEN);
		}
	}

}
