package com.anais.service;

import java.util.List;

import com.anais.dto.AutorDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface AutorService {
	
	AutorDTO saveAutor(AutorDTO autorDTO);
	AutorDTO getAutorById(Long id);
	List<AutorDTO> listAllAutors();
	void deleteAutor(Long id);

}
