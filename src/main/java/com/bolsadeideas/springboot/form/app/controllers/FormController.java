package com.bolsadeideas.springboot.form.app.controllers;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.form.app.editors.CountryPropertyEditor;
import com.bolsadeideas.springboot.form.app.editors.RolesEditor;
import com.bolsadeideas.springboot.form.app.editors.UpperCaseEditor;
import com.bolsadeideas.springboot.form.app.models.domain.Country;
import com.bolsadeideas.springboot.form.app.models.domain.Role;
import com.bolsadeideas.springboot.form.app.models.domain.User;
import com.bolsadeideas.springboot.form.app.services.CountryService;
import com.bolsadeideas.springboot.form.app.services.RoleService;
import com.bolsadeideas.springboot.form.app.validation.UserValidator;

@Controller
@SessionAttributes("user")
public class FormController {
	
	@Autowired
	private UserValidator validator;
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private CountryPropertyEditor countryEditor;
	
	@Autowired
	private RolesEditor roleEditor;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validator);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "birthDate",new CustomDateEditor(dateFormat, true));
		
		binder.registerCustomEditor(String.class, "name",new UpperCaseEditor());
		binder.registerCustomEditor(String.class, "lastName",new UpperCaseEditor());
		
		binder.registerCustomEditor(Country.class, "country", countryEditor);
		binder.registerCustomEditor(Role.class, "roles", roleEditor);

	}
	
	@ModelAttribute("genres")
	public List<String> genres(){
		return Arrays.asList("Male", "Female");
	}
	
	@ModelAttribute("rolesList")
	public List<Role> rolesList(){
		return this.roleService.doList();
	}
	
	@ModelAttribute("countriesList")
	public List<Country> countriesList(){
		return countryService.doList();
	}
	
	@ModelAttribute("rolesListString")
	public List<String> rolesListString(){
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_ADMIN");
		roles.add("ROLE_USER");
		roles.add("ROLE_MODERATOR");
		return roles;
	}
	
	@ModelAttribute("rolesListMap")
	public Map<String,String> rolesListMap(){
		Map <String, String> roles = new HashMap<String,String>();
		roles.put("ROLE_ADMIN", "Admin");
		roles.put("ROLE_USER", "User");
		roles.put("ROLE_MODERATOR", "Moderator");
		return roles;
	}
	
	@ModelAttribute("countries")
	public List<String> countries(){
		return Arrays.asList("Argentina","España","Mexico","Chile","Colombia","Peru","Venezuela");
	}
	
	@ModelAttribute("countriesMap")
	public Map<String,String> countriesMap(){
		Map <String, String> countries = new HashMap<String,String>();
		countries.put("ES", "España");
		countries.put("MX", "Mexico");
		countries.put("CL", "Chile");
		countries.put("AR", "Argentina");
		countries.put("PE", "Peru");
		countries.put("CO", "Colombia");
		countries.put("VE", "Venezuela");
		return countries;
	}
	
	@GetMapping({"/form","/",""})
	public String form (Model model) {
		User user = new User();
//		user.setName("John");
//		user.setLastName("Doe");
		user.setIdentifier("12.456.789-K");
		user.setEnable(true);
		user.setCountry(new Country(4,"AR", "Argentina"));
		user.setSecretValue("Some Secret Value...");
		user.setRoles(Arrays.asList(new Role(2,"User","ROLE_USER")));
		model.addAttribute("tittle","USER ACCESS FORM");
		model.addAttribute("formTitle","Sign up Form");
		model.addAttribute("user",user);
		return "form";
	}
	
	@PostMapping("/form")
	public String processForm(@Valid User user, BindingResult result, Model model) {
		
		//validator.validate(user, result);
		
		//only if the form has any errors
		if(result.hasErrors()) {
			
			model.addAttribute("tittle","User Access Form");
			model.addAttribute("formTitle","Sign up Form");
			
			return "form";
		}
		return "redirect:/see";
	}
	
	@GetMapping("/see")
	public String see(@SessionAttribute(name="user", required = false) User user, Model model, SessionStatus status) {
		
		if(user==null) {
			return "redirect:/form";
		}
		
		model.addAttribute("tittle","Form Result");
		status.setComplete();
		return "result";
	}

}
