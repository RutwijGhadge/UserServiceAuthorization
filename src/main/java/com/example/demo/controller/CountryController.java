package com.example.demo.controller;

import com.example.demo.bean.Country;
import com.example.demo.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/get")
public class CountryController {
    @Autowired
    public CountryService countryService;

    @GetMapping("/country")
    public List<Country> getCountries(){
        return countryService.getAllCountries();
    }

    @GetMapping("/getCountry/{id}")
    public ResponseEntity<Country> getById(@PathVariable(value = "id") int id){
        try{
            Country country= countryService.getCountryByID(id);
            return new ResponseEntity<Country>(country, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/getCountry/countryName")
    public ResponseEntity<Country> getCountryByName(@RequestParam(value = "Name") String name){
        //Name -> should be used as a key in PostMan while passing Request
        Country country=null;
        try{
             country= countryService.getCountryByName(name);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Country>(country,HttpStatus.OK);
    }

    @PostMapping("/addCountry")
    public Country addCountry(@RequestBody Country country){
        return countryService.addCountry(country);
    }

    @PutMapping("/updateCountry/{id}")
    public ResponseEntity<Country> UpdateCountry(@PathVariable(value = "id")int id , @RequestBody Country country){
        try{
            Country existCountry=countryService.getCountryByID(id);
            existCountry.setCountryName(country.getCountryName());
            existCountry.setCapital(country.getCapital());
            Country updatedCountry= countryService.updateCountry(existCountry);
            return new ResponseEntity<Country>(updatedCountry,HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/deleteCountry/{id}")
    public Response DeleteCountry(@PathVariable(value = "id")int id){
            Response response=countryService.deleteCountry(id);
            return response;
    }

}
