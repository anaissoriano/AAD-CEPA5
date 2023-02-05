package com.anais.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.anais.app.Application;
import com.anais.service.AutorService;
import com.anais.service.LibroService;
import com.anais.dto.AutorDTO;
import com.anais.dto.LibroDTO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LibroController {

	private static final Logger myLog = LoggerFactory.getLogger(Application.class);

	@Autowired
	private HttpServletRequest context;

	@Autowired
	private AutorService autorService;

	@Autowired
	private LibroService libroService;

	// Lista de libros
	@GetMapping("/libros")
	public ResponseEntity<List<LibroDTO>> listLibros() {
		myLog.info(context.getMethod() + " from " + context.getRemoteHost());
		List<LibroDTO> losLibros = libroService.listAllLibros();

		if (losLibros == null || losLibros.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(losLibros, HttpStatus.OK);

	}

	// Libro x id
	@GetMapping("/libros/{idLibro}")
	public ResponseEntity<LibroDTO> showLibroById(@PathVariable Long idLibro){
		
		myLog.info(context.getMethod()+context.getRequestURI()+" from "+context.getRemoteHost());
		LibroDTO elLibro = libroService.getLibroById(idLibro);				
		
		if (elLibro == null )
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(elLibro, HttpStatus.OK);
		
	}
	

	// ADD libro
	@PostMapping("/libros")
	public ResponseEntity<LibroDTO> addLibro(@RequestBody LibroDTO newLibro) {

		myLog.info(context.getMethod() + context.getRequestURI());
		LibroDTO elLibro = libroService.saveLibro(newLibro);

		if (elLibro == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(elLibro, HttpStatus.OK);

	}
	
	// UPDATE libro
	@PutMapping("/libros")
	public ResponseEntity<LibroDTO> updateLibro(@RequestBody LibroDTO updLibro) {

		myLog.info(context.getMethod() + context.getRequestURI());

		LibroDTO elLibro = libroService.getLibroById(updLibro.getIdLibro());

		if (elLibro == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else {
			LibroDTO elLibroUPD =libroService.saveLibro(elLibro);
			return new ResponseEntity<>(elLibroUPD, HttpStatus.OK);
		}

	}


	// Borrar libro x id
	@DeleteMapping("libros/{idLibro}")
	public ResponseEntity<String> deleteAutor(@PathVariable Long idLibro) {
		libroService.deleteLibro(idLibro);
		return new ResponseEntity<>("Libro borrado satisfactoriamente", HttpStatus.OK);
	}

	@PutMapping("/autores/{idAutor}/libros")
	public ResponseEntity<AutorDTO> addLibroToAutor(@PathVariable Long idAutor, @RequestBody LibroDTO newLibroDTO) {
		myLog.info(context.getMethod() + context.getRequestURI());
		AutorDTO elAutorDTO = autorService.getAutorById(idAutor);
		if (elAutorDTO == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else {
			libroService.saveNewLibro(newLibroDTO, elAutorDTO);
			AutorDTO elAutorUPD = autorService.getAutorById(idAutor);
			return new ResponseEntity<>(elAutorUPD, HttpStatus.OK);

		}
	}

	@GetMapping("/autores/{idAutor}/libros")
	public ResponseEntity<List<LibroDTO>> listLibrosAutor(@PathVariable Long idAutor) {

		AutorDTO autordto = autorService.getAutorById(idAutor);

		if (autordto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			List<LibroDTO> libros = libroService.listAllLibrosByAutor(autordto);
			if (libros == null || libros.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			else
				return new ResponseEntity<>(libros, HttpStatus.OK);
		}

	}

	@DeleteMapping("/autores/{idAutor}/libros/{idLibro}")
	public ResponseEntity<LibroDTO> removeLibroFromAutor(@PathVariable Long idLibro, @PathVariable Long idAutor) {

		AutorDTO elAutorDTO = autorService.getAutorById(idAutor);
		LibroDTO elLibroDTO = libroService.getLibroById(idLibro);

		if (elAutorDTO == null || elLibroDTO == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else {
			libroService.deleteLibroFromAutor(idAutor, idLibro);
			return new ResponseEntity<>(elLibroDTO, HttpStatus.OK);
		}

	}

}
