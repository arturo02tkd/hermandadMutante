package com.mx.hermandad.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RegistroMutante")
public class RegistroMutante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name="secADN")
	private String secADN;
	
	@Column (name ="humano")
	private boolean humano;
	
	public int getId() {
		return id;
	}

	public String getSecADN() {
		return secADN;
	}

	public boolean isHumano() {
		return humano;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSecADN(String secADN) {
		this.secADN = secADN;
	}

	public void setHumano(boolean humano) {
		this.humano = humano;
	}	
}
