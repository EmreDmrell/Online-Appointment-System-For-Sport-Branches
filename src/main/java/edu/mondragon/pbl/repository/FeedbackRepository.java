package edu.mondragon.pbl.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.mondragon.pbl.entity.Feedback;


public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    
    Optional<Feedback> findById(Integer id);
    List<Feedback> findByTopic(String topic);

    @Query(value="SELECT distinct topic FROM feedback", nativeQuery=true)
    List<String> findDistinctTopic();

}