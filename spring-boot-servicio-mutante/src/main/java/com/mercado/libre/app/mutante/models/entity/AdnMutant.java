package com.mercado.libre.app.mutante.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "mutant_adns")
public class AdnMutant implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	@NotEmpty(message = "no puede estar vacio")
	private String adn;
	
	public AdnMutant() {
	}

	public AdnMutant(String adn) {
		this.adn = adn;
	}

	public String getAdn() {
		return adn;
	}

	public void setAdn(String adn) {
		this.adn = adn;
	}

	private static final long serialVersionUID = -5377246276693632038L;

}
