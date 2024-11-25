package com.devsuperior.clientes.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.devsuperior.clientes.dto.ClienteDTO;
import com.devsuperior.clientes.entities.ClienteEntity;
import com.devsuperior.clientes.exceptions.DatabaseException;
import com.devsuperior.clientes.exceptions.ResourceNotFoundException;
import com.devsuperior.clientes.repositories.ClienteRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Transactional(readOnly = true)
	public ClienteDTO findById(Long id) {
		ClienteEntity clienteEntity = clienteRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Cliente inexistente"));
		return new ClienteDTO(clienteEntity);
	}
	
	@Transactional(readOnly = true)
	public Page<ClienteDTO> findAll(Pageable pageable) {
		Page<ClienteEntity> clienteEntity = clienteRepository.findAll(pageable);
		return clienteEntity.map(x -> new ClienteDTO(x));
	}
	
	//Verification==============================================================================
	@Transactional
	public ClienteDTO insert(ClienteDTO dto) {
		if (dto.getName() == null || dto.getName().trim().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
	    }
		
		if (dto.getBirthDate() != null && dto.getBirthDate().isAfter(LocalDate.now())) {
	        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
	    }
		
		ClienteEntity entity = new ClienteEntity();
		copyDtoToEntity(dto, entity);
		entity = clienteRepository.save(entity);
		return new ClienteDTO(entity);

	}
	
	@Transactional
	public ClienteDTO update(Long id, ClienteDTO dto) {
		if (!clienteRepository.existsById(id)) {
    		throw new ResourceNotFoundException("Cliente inexistente");
    	}
		if (dto.getName() == null || dto.getName().trim().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
	    }
		
		if (dto.getBirthDate() != null && dto.getBirthDate().isAfter(LocalDate.now())) {
	        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
	    }
		
		ClienteEntity entity = clienteRepository.getReferenceById(id);		
		copyDtoToEntity(dto, entity);
		entity = clienteRepository.save(entity);
		return new ClienteDTO(entity);
		

	}
	//===================================================================================================
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!clienteRepository.existsById(id)) {
    		throw new ResourceNotFoundException("Cliente inexistente");
    	}
    	try {
            clienteRepository.deleteById(id);    		
    	}
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }    		
	}
	
	private void copyDtoToEntity(ClienteDTO dto, ClienteEntity entity) {
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setBirthDate(dto.getBirthDate());	
		entity.setChildren(dto.getChildren());
	}
}	
