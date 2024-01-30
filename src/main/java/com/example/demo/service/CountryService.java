package com.example.demo.service;
import com.example.demo.Repository.CountryRepository;
import com.example.demo.bean.Country;
import com.example.demo.controller.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Service
public class CountryService {

    @Autowired
    CountryRepository countryRepository;
    //static HashMap<Integer, Country>CountryMap;

 /*   public CountryService() {//initialized and Entry made in HashMaz
        CountryMap=new HashMap<Integer, Country>();
        Country india=new Country(1,"India","Delhi");
        Country usa=new Country(2,"USA","Washington DC");
        Country China=new Country(3,"China","Beijing");

        CountryMap.put(1,india);
        CountryMap.put(2,usa);
        CountryMap.put(3,China);
    }*/

    public List<Country> getAllCountries(){

        return countryRepository.findAll();
     /*   List countries=new ArrayList<>(CountryMap.values());
        return countries;*/
    }
    public Country getCountryByID(int id){
        return countryRepository.findById(id).get();
        //return CountryMap.get(id);
    }

    public Country getCountryByName(String Name){
        List<Country>countries=countryRepository.findAll();
        Country con=null;
        for(Country country:countries){
            if(country.getCountryName().equalsIgnoreCase(Name))
                con=country;
        }

        return con;
      /*  Country country=null;

        for(int i:CountryMap.keySet()){
            if(CountryMap.get(i).getCountryName().equals(Name)){
                country=CountryMap.get(i);
            }
        }
        return country;*/
    }

    public Country addCountry(Country country){
        int size=countryRepository.findAll().size();
        country.setCountryId(size+1);
        countryRepository.save(country);
        return country;

       /* country.setCountryId(getMaxID());
        CountryMap.put(getMaxID(),country);
        return country;*/
    }

    public Country updateCountry(Country country){
        countryRepository.save(country);
        return country;
    }

    public Response deleteCountry(int id){
        countryRepository.deleteById(id);
        return new Response("Deleted Successfully");
    }

}
