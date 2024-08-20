package edu.mondragon.pbl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mondragon.pbl.entity.Banner;


public interface BannerRepository extends JpaRepository<Banner, Integer> {
    
    Optional<Banner> findById(Integer id);

}
