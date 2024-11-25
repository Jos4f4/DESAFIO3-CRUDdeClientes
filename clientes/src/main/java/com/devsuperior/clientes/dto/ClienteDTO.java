package com.devsuperior.clientes.dto;

import java.time.LocalDate;

import com.devsuperior.clientes.entities.ClienteEntity;

public class ClienteDTO {
	
	private Long id;
	private String name;
	private String cpf;
	private LocalDate birthDate;
	private Integer children;
	
	public ClienteDTO() {
		
	}

	public ClienteDTO(Long id, String name, String cpf, LocalDate birthDate, Integer children) {
		super();
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.birthDate = birthDate;
		this.children = children;
	}
	
	public ClienteDTO(ClienteEntity entity) {
		super();
		this.id = entity.getId();
		this.name = entity.getName();
		this.cpf = entity.getCpf();
		this.birthDate= entity.getBirthDate();
		this.children = entity.getChildren();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Integer getChildren() {
		return children;
	}

	public void setChildren(Integer children) {
		this.children = children;
	}

}
