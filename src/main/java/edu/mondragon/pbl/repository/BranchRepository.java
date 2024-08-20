package edu.mondragon.pbl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.mondragon.pbl.entity.Branch;
import edu.mondragon.pbl.entity.City;


public interface BranchRepository extends JpaRepository<Branch, Integer> {
    
    Branch findByBranchCode(String branchCode);

    @Query(value="SELECT distinct city FROM branch", nativeQuery=true)
    List<String> findDistinctCity();

    List<Branch> findByCity(City city);

}
