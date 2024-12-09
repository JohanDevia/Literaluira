package com.alura.literalura.service;

import com.alura.literalura.model.DatosLibro;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RespuestaAPI {
	@JsonAlias("results") private List<DatosLibro> libros;

	public List<DatosLibro> getLibros() {
		return libros;
	}
	public void setLibros(List<DatosLibro> libros) {
		this.libros = libros;
	}

}
