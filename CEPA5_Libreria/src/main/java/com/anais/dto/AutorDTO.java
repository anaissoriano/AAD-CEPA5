package com.anais.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.anais.model.Autor;
import com.anais.model.Libro;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;

@Data
public class AutorDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private long idAutor;
	private String nameAutor;
	private int versionAutor;
	
	
	@ToString.Exclude
	@JsonIgnoreProperties("listaAutores")
	private List<LibroDTO> listaLibros=new ArrayList<>();
	
	public static AutorDTO convertToDTO(Autor autor) {
		
		AutorDTO autorDTO = new AutorDTO();
		autorDTO.setIdAutor(autor.getIdAutor());
		autorDTO.setNameAutor(autor.getNameAutor());
		autorDTO.setVersionAutor(autor.getVersionAutor());
		
		for (Libro elLibro:autor.getListaLibros()) {
			LibroDTO libroDTO = LibroDTO.convertToDTO(elLibro, autorDTO);
			autorDTO.getListaLibros().add(libroDTO);
		}
		
		return autorDTO;
	}
	
	public static Autor converToEntity(AutorDTO autordto) {
		Autor autor = new Autor();
		
		autor.setIdAutor(autordto.getIdAutor());
		autor.setNameAutor(autordto.getNameAutor());
		autor.setVersionAutor(autordto.getVersionAutor());
		
		for(int i = 0;i< autordto.getListaLibros().size();i++ ) {
			
			Libro libro = LibroDTO.convertToEntity(autordto.getListaLibros().get(i),autor);
			autor.getListaLibros().add(libro);
			
		}
		return autor;
	}
	
	public AutorDTO() {
		super();
		this.listaLibros = new ArrayList <LibroDTO>();
	}
	
	
}
