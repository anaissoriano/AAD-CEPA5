package com.anais.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.anais.model.Autor;
import com.anais.model.Libro;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;

@Data
public class LibroDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long idLibro;
	private String titleLibro;
	private int versionLibro;

	@ToString.Exclude
	@JsonBackReference
	private List<AutorDTO> listaAutores;

	public static LibroDTO convertToDTO(Libro libro, AutorDTO autor) {

		LibroDTO libroDTO = new LibroDTO();
		libroDTO.setIdLibro(libro.getIdLibro());
		libroDTO.setTitleLibro(libro.getTitleLibro());
		libroDTO.setVersionLibro(libro.getVersionLibro());
		libroDTO.getListaAutores().add(autor);
		
		return libroDTO;

	}

	public static Libro convertToEntity(LibroDTO libroDTO, Autor autor) {
		
		Libro libro = new Libro();		
		libro.setIdLibro(libroDTO.getIdLibro());
		libro.setTitleLibro(libroDTO.getTitleLibro());
		libro.setVersionLibro(libroDTO.getVersionLibro());
		libro.getListaAutores().add(autor);
		
		return libro;
	}
	
	public LibroDTO() {
		super();
		this.listaAutores= new ArrayList<>();
	}

}
