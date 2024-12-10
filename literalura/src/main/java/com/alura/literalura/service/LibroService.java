package com.alura.literalura.service;

import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

	@Autowired
	private LibroRepository libroRepository;

	/**
	 * Obtiene todos los libros registrados en la base de datos.
	 *
	 * @return Lista de libros.
	 */
	public List<Libro> obtenerTodosLosLibros() {
		return libroRepository.findAll();
	}

	/**
	 * Obtiene libros filtrados por idioma.
	 *
	 * @param idioma Idioma en formato ISO (por ejemplo, "en", "es").
	 * @return Lista de libros en el idioma especificado.
	 */
	public List<Libro> obtenerLibrosPorIdioma(String idioma) {
		return libroRepository.findByIdioma(idioma);
	}

	/**
	 * Guarda un libro en la base de datos.
	 *
	 * @param libro Objeto Libro a guardar.
	 * @return El libro guardado.
	 */
	public Libro guardarLibro(Libro libro) {
		return libroRepository.save(libro);
	}
}