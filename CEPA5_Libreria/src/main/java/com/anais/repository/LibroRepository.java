package com.anais.repository;

import org.springframework.stereotype.Repository;
import com.anais.model.*;


import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface LibroRepository extends JpaRepository<Libro, Long>{
	
	  @Query(value = "select A.* from Publisher A, BookPublisher B"
			 + "where A.id = B.publisher_id AND B.book_id = : book_id", nativeQuery = true )
	  public List<Libro> getLibrosByAutor(@Param("book_id") Long idLibro);
	  
	  @Modifying
	  @Query( value = "delete from BookPublisher BP where" + 
	   	"BP.publisher_id=:idAutor and BP.book_id=:idLibro",nativeQuery = true)
	  public void deleteLibroFromAutor(
			  @Param("idAutor") Long idAutor,
			  @Param("idLibro") Long idLibro);
}