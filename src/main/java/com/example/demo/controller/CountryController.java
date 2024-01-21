package com.example.demo.controller;

import com.example.demo.bean.Country;
import com.example.demo.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/get")
public class CountryController {
    @Autowired
    public CountryService countryService;

    @GetMapping("/country")
    public List getCountries(){
        return countryService.getAllCountries();
    }

    @GetMapping("/getCountry/{id}")
    public Country getById(@PathVariable(value = "id") int id){
        return countryService.getCountryByID(id);
    }

    @GetMapping("/Name/{Name}")
    public Country getCountryByName(@PathVariable(value = "Name") String Name){
        return countryService.getCountryByName(Name);
    }

    @PostMapping("/addCountry")
    public Country addCountry(@RequestBody Country country){
        return countryService.addCountry(country);
    }



}
