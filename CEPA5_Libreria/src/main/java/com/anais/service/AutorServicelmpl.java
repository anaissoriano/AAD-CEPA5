package com.anais.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anais.dto.AutorDTO;
import com.anais.model.Autor;
import com.anais.repository.AutorRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class AutorServicelmpl implements AutorService {

	@Autowired
	private AutorRepository autorRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public AutorDTO saveAutor(AutorDTO autorDTO) {

		Autor autor = AutorDTO.converToEntity(autorDTO);
		Autor newAutor = autorRepository.save(autor);

		return AutorDTO.convertToDTO(newAutor);
	}

	@Override
	public AutorDTO getAutorById(Long id) {
		
		Optional<Autor> autor = autorRepository.findById(id);
		if(autor.isPresent()) {
			return AutorDTO.convertToDTO(autor.get());
		}else {
			return null;
		}
	}

	public List<AutorDTO> listAllAutors() {
		List<Autor> lista = autorRepository.findAll();
		List<AutorDTO> listaResultado = new ArrayList<AutorDTO>();
		for (int i = 0; i < lista.size(); ++i) {
		    listaResultado.add(AutorDTO.convertToDTO(lista.get(i)));
		}
		return listaResultado;
	}

	@Override
	public void deleteAutor(Long id) {
		autorRepository.deleteById(id);
	}

}
