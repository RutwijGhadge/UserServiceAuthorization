package com.example.demo.Repository;

import com.example.demo.bean.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country,Integer> {
    //JpaRepository<Bean Class , Data Type of Primary Key>
}
