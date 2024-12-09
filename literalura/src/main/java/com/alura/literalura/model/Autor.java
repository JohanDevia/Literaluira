package com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // usamos id autogenerado
	private Long id;

	@Column(nullable = false)
	private String nombre;

	private Integer anioNacimiento;

	private Integer anioFallecimiento;

	@ManyToOne
	@JoinColumn(name = "libro_id", nullable = false) // Relación con `Libro`
	private Libro libro;

	// Constructor vacío necesario para JPA
	public Autor() {}

	// Constructor útil
	public Autor(String nombre, Integer anioNacimiento, Integer anioFallecimiento, Libro libro) {
		this.nombre = nombre;
		this.anioNacimiento = anioNacimiento;
		this.anioFallecimiento = anioFallecimiento;
		this.libro = libro;
	}

	// Getters y setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getAnioNacimiento() {
		return anioNacimiento;
	}

	public void setAnioNacimiento(Integer anioNacimiento) {
		this.anioNacimiento = anioNacimiento;
	}

	public Integer getAnioFallecimiento() {
		return anioFallecimiento;
	}

	public void setAnioFallecimiento(Integer anioFallecimiento) {
		this.anioFallecimiento = anioFallecimiento;
	}

	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}

	@Override
	public String toString() {
		return "Autor{" +
						"id=" + id +
						", nombre='" + nombre + '\'' +
						", anioNacimiento=" + anioNacimiento +
						", anioFallecimiento=" + anioFallecimiento +
						'}';
	}
}
