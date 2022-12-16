package com.bolsadeideas.springboot.form.app.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.form.app.models.domain.Country;

@Service
public class CountryServiceImplement implements CountryService{

	private List<Country> list;
	
	public CountryServiceImplement() {
		this.list = Arrays.asList(
				new Country(1,"ES", "España"),
				new Country(2,"MX", "Mexico"),
				new Country(3,"CL", "Chile"),
				new Country(4,"AR", "Argentina"),
				new Country(5,"PE", "Peru"),
				new Country(6,"CO", "Colombia"),
				new Country(7,"VE", "Venezuela"));
	}

	@Override
	public List<Country> doList() {
		return list;
	}

	@Override
	public Country getById(Integer id) {
		Country result = null;
		for(Country country: this.list) {
			if(id == country.getId()) {
				result = country;
				break;
			}
		}
		return result;
	}

}
