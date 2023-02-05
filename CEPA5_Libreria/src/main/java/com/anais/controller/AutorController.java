package com.anais.controller;

import com.anais.app.*;
import com.anais.service.AutorService;
import com.anais.service.LibroService;
import com.anais.dto.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
public class AutorController {

	private static final Logger myLog = LoggerFactory.getLogger(Application.class);

	@Autowired
	private HttpServletRequest context;

	@Autowired
	private AutorService autorService;

	@Autowired
	private LibroService libroService;

	@Value("${nombre}")
	private String nombre;

	@Value("${proyecto}")
	private String nombreProyecto;

	@Value("${asignatura}")
	private String nombreAsignatura;

	// WELCOME
	@GetMapping("/")
	public String index() {

		myLog.info(context.getMethod() + " from " + context.getRemoteHost());
		return "Welcome to " + nombreProyecto + " , " + nombreAsignatura + " de " + nombre;

	}

	// Lista de autores
	@GetMapping("/autores")
	public ResponseEntity<List<AutorDTO>> listAutores() {

		myLog.info(context.getMethod() + " from " + context.getRemoteHost());
		List<AutorDTO> losAutores = autorService.listAllAutors();

		if (losAutores == null || losAutores.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(losAutores, HttpStatus.OK);

	}

	// SHOW Autor x ID
	@GetMapping("/autores/{idAutor}")
	public ResponseEntity<AutorDTO> showAutorById(@PathVariable Long idAutor) {

		myLog.info(context.getMethod() + context.getRequestURI() + " from " + context.getRemoteHost());
		AutorDTO elAutor = autorService.getAutorById(idAutor);

		if (elAutor == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(elAutor, HttpStatus.OK);

	}

	// ADD autor
	@PostMapping("/autores")
	public ResponseEntity<AutorDTO> addAutor(@RequestBody AutorDTO newAutor) {

		myLog.info(context.getMethod() + context.getRequestURI());
		AutorDTO elAutor = autorService.saveAutor(newAutor);

		if (elAutor == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(elAutor, HttpStatus.OK);

	}

	// UPDATE autor
	@PutMapping("/autores")
	public ResponseEntity<AutorDTO> updateAutor(@RequestBody AutorDTO updAutor) {

		myLog.info(context.getMethod() + context.getRequestURI());

		AutorDTO elAutor = autorService.getAutorById(updAutor.getIdAutor());

		if (elAutor == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else {
			AutorDTO elAutorUPD = autorService.saveAutor(elAutor);
			return new ResponseEntity<>(elAutorUPD, HttpStatus.OK);
		}

	}


	// Borrar autor x id
	@DeleteMapping("autores/{idAutor}")
	public ResponseEntity<String> deleteAutor(@PathVariable Long idAutor) {
		autorService.deleteAutor(idAutor);
		return new ResponseEntity<>("Autor borrado satisfactoriamente", HttpStatus.OK);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<String> handleError(MethodArgumentTypeMismatchException e) {

		myLog.warn("Method Argument Type Mismatch", e);
		String message = String.format("Method Argument Type Mismatch: %s", e.getMessage());
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

	}

}
