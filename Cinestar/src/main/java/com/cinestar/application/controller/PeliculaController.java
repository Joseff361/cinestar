package com.cinestar.application.controller;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinestar.application.entity.Funcion;
import com.cinestar.application.entity.Pelicula;
import com.cinestar.application.entity.Sede;
import com.cinestar.application.service.FuncionService;
import com.cinestar.application.service.PeliculaService;

@Controller
public class PeliculaController {

	@Autowired
	PeliculaService peliculaService;
	@Autowired
	FuncionService funcionService;
	
	private static final String PELICULA_LIST = "peliculaList"; 

	/**
	 * Retorna lista de Peliculas
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping({"/peliculas","/","/index"})
	public String peliculas(Model model) {
		model.addAttribute(PELICULA_LIST, peliculaService.getPeliculas());
		return "index";// html
	}

	/**
	 * Retorna lista de Peliculas
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/peliculas/categoria")
	public String peliculasCategoria(@RequestParam String genero, Model model) {
		model.addAttribute(PELICULA_LIST, peliculaService.getPeliculasByGenero(genero));
		return "index";// html
	}

	/**
	 * Retorna pelicula por id y las sedes donde esté
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/peliculas/{id}")
	public String peliculasId(@PathVariable Long id, Model model) {
		Set<Sede> sedes = new LinkedHashSet<>();
		for (Funcion func : funcionService.getFunciones()) {

			/* guardar en un lista de sedes todas las funciones con la peli ID
			   el operador == puede fallar, java no tiene buen soporte con el objeto Long*/
			if (id != null && func.getPelicula().getId()!= null && func.getPelicula().getId().equals(id)) {
				sedes.add(func.getSala().getSede());
			}

		}
	
		Optional<Pelicula> optional = peliculaService.getPelicula(id);
		if(optional.isPresent()) {
			Pelicula p = optional.get();
			model.addAttribute(PELICULA_LIST, p);
		}
		model.addAttribute("sedeList", sedes);
		return "pelicula";// html
	}
}
