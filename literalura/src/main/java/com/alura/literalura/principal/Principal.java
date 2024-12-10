package com.alura.literalura.principal;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.DatosAutor;
import com.alura.literalura.model.DatosLibro;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import com.alura.literalura.service.RespuestaAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
@Component

public class Principal {

	private final LibroRepository libroRepository;

	@Autowired
	public Principal(LibroRepository libroRepository) {
		this.libroRepository = libroRepository;
	}
	@Autowired
	private AutorRepository autorRepository;

	private Scanner teclado = new Scanner(System.in);
	private ConsumoAPI consumoAPI = new ConsumoAPI();
	private final String URL_BASE = "https://gutendex.com/books/";
	private final String URL_BUSQUEDA_LIBRO = "?search=";
	private ConvierteDatos conversor = new ConvierteDatos();
	private List<DatosLibro> librosRegistrados = new ArrayList<>();
	//private LibroRepository repositorio;


	public void menuInteractivo() {
		int opcion;

		do {
			System.out.println("\nPor favor seleccione la opción deseada.");
			System.out.println("1 - Buscar libro por título.");
			System.out.println("2 - Lista de libros registrados.");
			System.out.println("3 - Lista de autores registrados.");
			System.out.println("4 - Lista de autores vivos en un determinado año.");
			System.out.println("5 - lista de libros por idioma.");
			System.out.println("0 - Salir.");

			try {
				opcion = Integer.parseInt(teclado.nextLine());

				switch (opcion) {
					case 1:
						buscarLibroPorTitulo();
						break;
					case 2:
						listarLibrosRegistrados();
						break;
					case 3:
						listarAutoresRegistrados();
						break;
					case 4:
						listarAutoresVivosPorAnio();
						break;
					case 5:
						System.out.println("Seleccione un idioma:");
						System.out.println("1. Inglés");
						System.out.println("2. Español");
						int idiomaSeleccionado = teclado.nextInt();
						teclado.nextLine();
						if (idiomaSeleccionado == 1) {
							mostrarLibrosPorIdioma("en");
						} else if (idiomaSeleccionado == 2) {
							mostrarLibrosPorIdioma("es");
						} else {
							System.out.println("Opción no válida.");
						}
						break;
					case 0:
						System.out.println("Saliendo del programa...");
						break;
					default:
						System.out.println("Opción no válida. Por favor intente de nuevo.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Entrada no válida. Por favor, ingrese un número.");
				opcion = -1; // Asegura que el programa no salga del bucle
			}
		} while (opcion != 0);
	}



	private void buscarLibroPorTitulo() {
		System.out.print("Ingrese el título del libro a buscar: ");
		var nombreLibro = teclado.nextLine();
		var json = consumoAPI.obtenerDatos(URL_BASE + URL_BUSQUEDA_LIBRO + nombreLibro.replace(" ", "%20"));
		var respuestaAPI = conversor.obtenerDatos(json, RespuestaAPI.class);

		if (respuestaAPI.getLibros().isEmpty()) {
			System.out.println("No se encontró ningún libro con el título especificado.");
		} else {
			for (DatosLibro datos : respuestaAPI.getLibros()) {
				if (!librosRegistrados.contains(datos)) {
					Libro libro = new Libro(datos);
					libroRepository.save(libro);
					//librosRegistrados.add(datos);
				}
				System.out.println("Título: " + datos.titulo());
				System.out.print("Autores: ");
				for (DatosAutor autor : datos.autores()) {
					System.out.print(autor.nombre() + " ");
				}
				System.out.println("\nIdioma: " + datos.idioma());
				System.out.println("Descargas: " + datos.descargas());
				System.out.println();
			}
		}
	}


	private void listarLibrosRegistrados() {
		List<Libro> libros = libroRepository.findAll();
		if (libros.isEmpty()) {
			System.out.println("No hay libros registrados.");
		} else {
			System.out.println("Lista de libros registrados:");
			for (Libro libro : libros) {
				System.out.println("Título: " + libro.getTitulo());
			}
		}
	}

	private void listarAutoresRegistrados() {
		List<Autor> autores = autorRepository.findAll();
		if (autores.isEmpty()) {
			System.out.println("No hay autores registrados.");
		} else {
			System.out.println("Lista de autores registrados:");
			autores.stream()
							.map(Autor::getNombre)
							.distinct()
							.forEach(autor -> System.out.println("Autor: " + autor));
		}
	}

	private void listarAutoresVivosPorAnio() {
		System.out.print("Ingrese el año para buscar autores vivos: ");
		int anio;

		try {
			anio = Integer.parseInt(teclado.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("Año no válido. Por favor ingrese un número entero.");
			return;
		}

		// Consultar los autores en la base de datos
		List<Autor> autores = autorRepository.findAll();
		if (autores.isEmpty()) {
			System.out.println("No hay autores registrados.");
			return;
		}

		System.out.println("\nAutores vivos en el año " + anio + ":");
		Set<String> autoresVivos = new HashSet<>();

		for (Autor autor : autores) {
			// Asegurarse de que el autor tiene fechas válidas de nacimiento y fallecimiento
			if (autor.getAnioNacimiento() <= anio &&
							(autor.getAnioFallecimiento() == null || autor.getAnioFallecimiento() >= anio)) {
				autoresVivos.add(autor.getNombre());
			}
		}

		if (autoresVivos.isEmpty()) {
			System.out.println("No se encontraron autores vivos en el año " + anio + ".");
		} else {
			autoresVivos.forEach(autor -> System.out.println("Autor: " + autor));
		}
	}

	private void mostrarLibrosPorIdioma(String idioma) {
		List<Libro> libros = libroRepository.findByIdioma(idioma);
		if (libros.isEmpty()) {
			System.out.println("No hay libros disponibles en este idioma.");
		} else {
			System.out.println("Libros disponibles en " + (idioma.equals("en") ? "Inglés" : "Español") + ":");
			libros.forEach(libro -> System.out.println(libro.getTitulo()));
		}
	}
}