package com.mercado.libre.app.mutante.models.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mercado.libre.app.mutante.models.constamts.MessageExceptionConstans;
import com.mercado.libre.app.mutante.models.dao.MutantDao;
import com.mercado.libre.app.mutante.models.dto.EstadisticaDto;
import com.mercado.libre.app.mutante.models.entity.AdnMutant;
import com.mercado.libre.app.mutante.models.entity.Mutant;
import com.mercado.libre.app.mutante.models.exception.ForbiddenException;
import com.mercado.libre.app.mutante.util.Util;

@Service
public class MutantImpl implements IMutantService {

	private List<String> cadenasAdn;

	@Autowired
	private MutantDao mutantDao;

	@Override
	@Transactional
	public Mutant saveMutante(Mutant mutant) {
		return mutantDao.save(mutant);
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> findAllMutant() throws ForbiddenException {
		Map<String, Object> response = new HashMap<>();
		List<Mutant> lista = (List<Mutant>) mutantDao.findAll();
		if (lista.isEmpty()) {
			throw new ForbiddenException(MessageExceptionConstans.MSG_NOT_DATA);
		}
		EstadisticaDto estadisticaDto = new EstadisticaDto();
		metodoMover(lista, estadisticaDto);
		response.put("ADN", estadisticaDto);
		return response;
	}

	private Boolean isMutant(String[][] matrizDna, String[] dna) {
		Boolean isMutant = false;
		Mutant mutant = new Mutant();
		List<AdnMutant> listaMutant = new ArrayList<>();
		cadenasAdn = new ArrayList<>();
		validarSecuenciaAdnOblicua(matrizDna);
		validarSecuenciaAdnOblicuaInversa(matrizDna);
		validarSecuenciaAdnHorizontal(matrizDna);
		validarSecuenciaAdnVertical(matrizDna);
		if (!cadenasAdn.isEmpty()) {
			for (String adn : cadenasAdn) {
				listaMutant.add(new AdnMutant(adn));
			}
		}
		if (!cadenasAdn.isEmpty() && cadenasAdn.size() > 1) {
			isMutant = true;
			mutant.setMutante(true);
		} else {
			mutant.setMutante(false);
			listaMutant = null;
		}

		mutant.setAdn(Arrays.toString(dna));
		mutant.setItemsAdn(listaMutant);
		saveMutante(mutant);
		return isMutant;
	}

	@Override
	public void metodoMover(List<Mutant> lista, EstadisticaDto estadisticaDto) {
		estadisticaDto
				.setCountMutantDna(lista.stream().filter(dna -> dna.getMutante()).collect(Collectors.toList()).size());
		estadisticaDto
				.setCountHumanDna(lista.stream().filter(dna -> !dna.getMutante()).collect(Collectors.toList()).size());
		estadisticaDto.setRatio((float) estadisticaDto.getCountMutantDna() / estadisticaDto.getCountHumanDna());
	}

	@Override
	public Map<String, Object> validarMutant(final String[] dna) throws ForbiddenException {

		Map<String, Object> response = new HashMap<>();
		String matrizDna[][] = Util.obtenerArray(dna);
		if (matrizDna == null) {
			throw new ForbiddenException(MessageExceptionConstans.MSG_DATA_NO_VALID);
		}
		if (!isMutant(matrizDna, dna)) {
			throw new ForbiddenException(MessageExceptionConstans.MSG_DATA_NOT_MUTATNT);
		}
		response.put("mensaje", "El dna ha sido leido con éxito");
		response.put("DNA", Arrays.toString(dna));
		response.put("Boolean", true);
		return response;
	}

	private void validarSecuenciaAdnOblicua(String matrixDna[][]) {
		for (int i = 0; i < matrixDna.length - 3; i++) {
			for (int j = 0; j < matrixDna[0].length - 3; j++) {
				if (matrixDna[i][j].equals(matrixDna[i + 1][j + 1]) && matrixDna[i][j].equals(matrixDna[i + 2][j + 2])
						&& matrixDna[i][j].equals(matrixDna[i + 3][j + 3])) {
					cadenasAdn.add(matrixDna[i][j].concat(matrixDna[i + 1][j + 1]).concat(matrixDna[i + 2][j + 2])
							.concat(matrixDna[i + 3][j + 3]));
				}
			}
		}
	}

	private void validarSecuenciaAdnOblicuaInversa(String matrixDna[][]) {
		for (int i = 0; i < matrixDna.length - 3; i++) {
			for (int j = 5; j >= matrixDna[0].length - 3; j--) {
				if (matrixDna[i][j].equals(matrixDna[i + 1][j - 1]) && matrixDna[i][j].equals(matrixDna[i + 2][j - 2])
						&& matrixDna[i][j].equals(matrixDna[i + 3][j - 3])) {
					cadenasAdn.add(matrixDna[i][j].concat(matrixDna[i + 1][j - 1]).concat(matrixDna[i + 2][j - 2])
							.concat(matrixDna[i + 3][j - 3]));
				}
			}
		}
	}

	private void validarSecuenciaAdnHorizontal(String matrixDna[][]) {
		for (int i = 0; i < matrixDna.length; i++) {
			for (int j = 0; j < matrixDna[0].length - 3; j++) {
				if (matrixDna[i][j].equals(matrixDna[i][j + 1]) && matrixDna[i][j].equals(matrixDna[i][j + 2])
						&& matrixDna[i][j].equals(matrixDna[i][j + 3])) {
					cadenasAdn.add(matrixDna[i][j].concat(matrixDna[i][j + 1]).concat(matrixDna[i][j + 2])
							.concat(matrixDna[i][j + 3]));
				}
			}
		}
	}

	private void validarSecuenciaAdnVertical(String matrixDna[][]) {
		for (int i = 0; i < matrixDna.length; i++) {
			for (int j = 0; j < matrixDna[0].length - 3; j++) {
				if (matrixDna[i][j].equals(matrixDna[j + 1][i]) && matrixDna[i][j].equals(matrixDna[j + 2][i])
						&& matrixDna[i][j].equals(matrixDna[j + 3][i])) {
					cadenasAdn.add(matrixDna[i][j].concat(matrixDna[j + 1][i]).concat(matrixDna[j + 2][i])
							.concat(matrixDna[j + 3][i]));
				}
			}
		}
	}
}
