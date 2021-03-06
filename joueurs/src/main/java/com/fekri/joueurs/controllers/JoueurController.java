package com.fekri.joueurs.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.fekri.joueurs.entities.Joueur;
import com.fekri.joueurs.service.JoueurService;

@Controller
public class JoueurController {
	@Autowired
	JoueurService joueurService;
	@RequestMapping("/showCreate")
	public String showCreate()
	{
		return "enregistrerJoueur";
	}
	
	@RequestMapping("/saveJoueur")
	public String saveJoueur(@ModelAttribute("joueur") Joueur joueur, 
							  @RequestParam("date") String date,
							  ModelMap modelMap) throws ParseException 
	{
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date jourdesignature = dateformat.parse(String.valueOf(date));
        joueur.setjourdesignature(jourdesignature);
      
		 Joueur saveJoueur = joueurService.saveJoueur(joueur);
		 String msg ="Joueur enregistré avec Id "+saveJoueur.getIdJoueur();
		 modelMap.addAttribute("msg", msg);
		return "enregistrerJoueur";
	}
	@RequestMapping("/ListeJoueur")
	public String listeProduits(ModelMap modelMap,
			@RequestParam (name="page",defaultValue = "0") int page,
			@RequestParam (name="size", defaultValue = "4") int size)
	{
		Page<Joueur> prods = joueurService.getAlljoueursParPage(page, size);
		modelMap.addAttribute("joueurs", prods);		
		modelMap.addAttribute("pages", new int[prods.getTotalPages()]);	
		modelMap.addAttribute("currentPage", page);	
		return "ListeJoueur";	
	}
	@RequestMapping("/supprimerJoueur")
	public String supprimerJoueur(@RequestParam("id") Long id,ModelMap modelMap)
	{
		Joueur p= new Joueur();
		p.setIdJoueur(id);
		joueurService.deleteJoueur(p);
		List<Joueur> joues = joueurService.getAlljoueurs();
		modelMap.addAttribute("joueurs", joues);	
		return "ListeJoueur";	
	}
	@RequestMapping("/modifierJoueur")
	public String modifierJoueur(@RequestParam("id") Long id,ModelMap modelMap)
	{
	Joueur p= joueurService.getJoueur(id);
	modelMap.addAttribute("joueur", p);
	return "updateJoueur";
	}
	@RequestMapping("/updateJoueur")
	public String updateJoueur(@ModelAttribute("joueur") Joueur joueur,
								@RequestParam("date") String date,
			                    ModelMap modelMap) throws ParseException 
	{
		
		//conversion de la date 
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date jourdesignature = dateformat.parse(String.valueOf(date));
        joueur.setjourdesignature(jourdesignature);
        
        joueurService.updateJoueur(joueur);
		  List<Joueur> joues = joueurService.getAlljoueurs();
		  modelMap.addAttribute("joueurs", joues);	
		
		return "ListeJoueur";
	}
	
	
	
	
	

}

