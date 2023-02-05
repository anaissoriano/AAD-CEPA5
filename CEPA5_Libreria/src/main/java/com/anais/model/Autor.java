package com.anais.model;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "Publisher")
public class Autor {
	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	private Long idAutor;
	
	@Column(name = "name")
	private String nameAutor;
	
	@Column(name = "version")
	private int versionAutor;
	
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="BookPublisher", // Tabla que mantiene la relacion N-N
        joinColumns=@JoinColumn(name="publisher_id"), // columna que almacena el id de cliente en la tabla clientes-direcciones
        inverseJoinColumns=@JoinColumn(name="book_id")) // columna que almacena el id de la direccion en la tabla clientes-direcciones
	@ToString.Exclude
	private List<Libro> listaLibros = new ArrayList<>();


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Autor other = (Autor) obj;
		if (idAutor == null) {
			if (other.idAutor != null)
				return false;
		} else if (!idAutor.equals(other.idAutor))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAutor == null) ? 0 : idAutor.hashCode());
		return result;
	}
}
