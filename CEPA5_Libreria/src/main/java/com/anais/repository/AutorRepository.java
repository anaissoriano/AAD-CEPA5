package com.anais.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anais.model.Autor;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface AutorRepository extends JpaRepository<Autor, Long> {

	List<Autor> findByName(String name);
	List<Autor> findByNameAndVersion(String name, int version);

}
