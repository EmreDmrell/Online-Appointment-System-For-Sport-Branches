package edu.mondragon.pbl.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mondragon.pbl.entity.City;


public interface CityRepository extends JpaRepository<City, Integer> {
    
    Optional<City> findByName(String cityName);

}
