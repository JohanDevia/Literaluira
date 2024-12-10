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

	public List<Libro> obtenerTodosLosLibros() {
		return libroRepository.findAll();
	}

	public List<Libro> obtenerLibrosPorIdioma(String idioma) {
		return libroRepository.findByIdioma(idioma);
	}

	public Libro guardarLibro(Libro libro) {
		return libroRepository.save(libro);
	}
}