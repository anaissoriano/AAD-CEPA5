package com.anais.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.anais.dto.AutorDTO;
import com.anais.dto.LibroDTO;
import com.anais.model.Libro;
import com.anais.model.Autor;
import com.anais.repository.AutorRepository;
import com.anais.repository.LibroRepository;


public class LibroServicelmpl implements LibroService {

	@Autowired
	private LibroRepository libroRepository;

	@Autowired
	private AutorRepository autorRepository;

	@Override
	public List<LibroDTO> listAllLibros(AutorDTO autordto) {

		List<Libro> lista = (List<Libro>) libroRepository.getLibrosByAutor(autordto.getIdAutor());
		List<LibroDTO> listaResultado = new ArrayList<LibroDTO>();
		for (int i = 0; i < lista.size(); ++i) {
			listaResultado.add(LibroDTO.convertToDTO(lista.get(i), autordto));
		}

		return listaResultado;
	}


	@Override
	public List<LibroDTO> listAllLibrosByAutor(AutorDTO autordto) {
			List<Libro> lista = (List<Libro>) libroRepository.getLibrosByAutor(autordto.getIdAutor());
			List<LibroDTO> listaResultados = new ArrayList<LibroDTO>();
			for(int i =0; i<lista.size(); ++i) {
				listaResultados.add(LibroDTO.convertToDTO(lista.get(i), autordto));
			}
			return listaResultados;
	}

	@Override
	public void saveNewLibro(LibroDTO librodto, AutorDTO autordto) {		
		Optional <Autor> autor = autorRepository.findById(autordto.getIdAutor());
		if(autor.isPresent()) {
			Libro libro = LibroDTO.convertToEntity(librodto, autor.get());
			autor.get().getListaLibros().add(libro);
			libroRepository.save(libro);
		}

	}

	@Override
	public LibroDTO getLibroById(long idLbro) {
		Optional<Libro> elLibro = libroRepository.findById(idLbro);
		if(elLibro.isPresent()) {
			LibroDTO librodto = LibroDTO.convertToDTO(elLibro.get(), null);
			return librodto;
		}else
		
			return null;
	}

	@Override
	public void deleteLibroFromAutor(Long idLibro, Long idAutor) {
		libroRepository.deleteLibroFromAutor(idAutor, idLibro);
	}


	@Override
	public LibroDTO saveLibro(LibroDTO libroDTO) {
		Libro libro = LibroDTO.convertToEntity(libroDTO, null);
		Libro newLibro = libroRepository.save(libro);
		return LibroDTO.convertToDTO(newLibro, null);
	}


	@Override
	public List<LibroDTO> listAllLibros() {
		List<Libro> lista = libroRepository.findAll();
		List<LibroDTO> listaResultado = new ArrayList<LibroDTO>();
		for (int i = 0; i < lista.size(); ++i) {
		    listaResultado.add(LibroDTO.convertToDTO(lista.get(i), null));
		}
		return listaResultado;
	}


	@Override
	public void deleteLibro(Long idLibro) {
		libroRepository.deleteById(idLibro);
		
	}

}
