package com.example.demo.service;
import com.example.demo.bean.Country;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CountryService {
    static HashMap<Integer, Country>CountryMap;

    public CountryService() {//initialized and Entry made in HashMaz
        CountryMap=new HashMap<Integer, Country>();
        Country india=new Country(1,"India","Delhi");
        Country usa=new Country(2,"USA","Washington DC");
        Country China=new Country(3,"China","Beijing");

        CountryMap.put(1,india);
        CountryMap.put(2,usa);
        CountryMap.put(3,China);
    }

    public List getAllCountries(){
        List countries=new ArrayList<>(CountryMap.values());
        return countries;
    }
    public Country getCountryByID(int id){
        return CountryMap.get(id);
    }

    public Country getCountryByName(String Name){
        Country country=null;

        for(int i:CountryMap.keySet()){
            if(CountryMap.get(i).getCountryName().equals(Name)){
                country=CountryMap.get(i);
            }
        }
        return country;
    }

    public Country addCountry(Country country){
        country.setCountryId(getMaxID());
        CountryMap.put(getMaxID(),country);
        return country;
    }

    public static int getMaxID(){
        int max=0;
        for(int id:CountryMap.keySet()){
            if(max<=id)
                max=id;
        }
        return max+1;
    }
}
