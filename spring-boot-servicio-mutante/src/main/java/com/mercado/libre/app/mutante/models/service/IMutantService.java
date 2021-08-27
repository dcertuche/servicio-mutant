package com.mercado.libre.app.mutante.models.service;

import java.util.List;
import java.util.Map;

import com.mercado.libre.app.mutante.models.dto.EstadisticaDto;
import com.mercado.libre.app.mutante.models.entity.Mutant;
import com.mercado.libre.app.mutante.models.exception.ForbiddenException;

public interface IMutantService {

	public Mutant saveMutante(Mutant mutant);

	public Map<String, Object> findAllMutant() throws ForbiddenException;

	public void metodoMover(List<Mutant> lista, EstadisticaDto estadisticaDto);

	public Map<String, Object> validarMutant(String[] dna) throws ForbiddenException;

}
