package com.devsuperior.clientes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.clientes.entities.ClienteEntity;

public interface ClienteRepository extends JpaRepository <ClienteEntity, Long> {

}
