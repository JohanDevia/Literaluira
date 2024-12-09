package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "titulo")
	private String titulo;

	private Integer descargas;

	@ElementCollection
	@CollectionTable(name = "libro_idiomas", joinColumns = @JoinColumn(name = "libro_id"))
	@Column(name = "idioma")
	private List<String> idioma;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "libro_id")
	private List<Autor> autores;

	// Constructor vacío para JPA
	public Libro() {}

	// Constructor que recibe un objeto DatosLibro
	public Libro(DatosLibro datos) {
		this.titulo = datos.titulo();
		this.descargas = datos.descargas();
		this.idioma = datos.idioma();

		// Convertir los autores de DatosAutor a Autor
		this.autores = datos.autores().stream()
						.map(datoAutor -> new Autor(datoAutor.nombre(), datoAutor.anioNacimiento(), datoAutor.anioFallecimiento(), this))
						.collect(Collectors.toList());
	}

	// Getters y setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getDescargas() {
		return descargas;
	}

	public void setDescargas(Integer descargas) {
		this.descargas = descargas;
	}

	public List<String> getIdioma() {
		return idioma;
	}

	public void setIdioma(List<String> idioma) {
		this.idioma = idioma;
	}

	public List<Autor> getAutores() {
		return autores;
	}

	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

	@Override
	public String toString() {
		return "Libro{" +
						"id=" + id +
						", titulo='" + titulo + '\'' +
						", descargas=" + descargas +
						", idioma=" + idioma +
						", autores=" + autores +
						'}';
	}
}
