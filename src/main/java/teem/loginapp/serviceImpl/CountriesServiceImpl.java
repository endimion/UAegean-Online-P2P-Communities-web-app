/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.serviceImpl;

import teem.loginapp.model.dao.CountriesMngRepository;
import teem.loginapp.model.dmo.CountriesMngDMO;
import teem.loginapp.service.CountriesService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * @author nikos
 */
@Service
public class CountriesServiceImpl implements CountriesService{

    @Autowired
    private CountriesMngRepository countriesRepo;
    
    @Override
    @Cacheable("countries")
    public List<CountriesMngDMO> findAll() {
        return  countriesRepo.findAll().stream().sorted( (c1, c2) ->{
            if(c1.getName().toLowerCase().equals("greece")){
                return -1;
            }
            if(c2.getName().toLowerCase().equals("greece")){
                return 1;
            }
            return c1.getName().compareTo(c2.getName());
        }).map( country ->{
              country.setName(country.getName().trim());
              return country;
        }).collect(Collectors.toList()); 
               
         
    }
    
    
    
    
}
