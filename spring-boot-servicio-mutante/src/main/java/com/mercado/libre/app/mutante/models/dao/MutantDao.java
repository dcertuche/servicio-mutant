package com.mercado.libre.app.mutante.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.mercado.libre.app.mutante.models.entity.Mutant;

public interface MutantDao extends CrudRepository<Mutant, Long> {

}
