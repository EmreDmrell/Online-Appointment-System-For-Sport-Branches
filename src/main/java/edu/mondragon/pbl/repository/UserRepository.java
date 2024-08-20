package edu.mondragon.pbl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mondragon.pbl.entity.User;



public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
    Optional<User> findById(Integer id);

}