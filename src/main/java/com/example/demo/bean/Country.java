package com.example.demo.bean;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Country")
public class Country {
    @Id         //primary key
    @Column(name = "id")
    int CountryId;

    @Column(name="country_name")    //same as TableName Columns
    String CountryName;

    @Column(name="country_capital") //country_capital is the Column name in Country in DB
    String Capital;
}
