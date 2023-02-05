package com.anais.service;

import java.util.List;

import com.anais.dto.AutorDTO;
import com.anais.dto.LibroDTO;

public interface LibroService {

	List<LibroDTO> listAllLibrosByAutor(AutorDTO autordto);
	void saveNewLibro(LibroDTO librodto, AutorDTO autordto);
	List<LibroDTO> listAllLibros(AutorDTO autordto);
	LibroDTO getLibroById(long idLbro);
	void deleteLibroFromAutor(Long idLibro, Long idAutor);
	
	LibroDTO saveLibro(LibroDTO newLibro);
	List<LibroDTO> listAllLibros();
	void deleteLibro(Long idLibro);
	

}
