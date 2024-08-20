package edu.mondragon.pbl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mondragon.pbl.entity.Sport;


public interface SportRepository extends JpaRepository<Sport, Integer> {

    Optional<Sport> findById(Integer id);

}
