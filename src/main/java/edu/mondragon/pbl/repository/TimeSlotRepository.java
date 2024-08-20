package edu.mondragon.pbl.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mondragon.pbl.entity.Branch;
import edu.mondragon.pbl.entity.Sport;
import edu.mondragon.pbl.entity.TimeSlot;
import edu.mondragon.pbl.entity.User;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {

    Optional<TimeSlot> findByDateAndSportAndBranchAndUser(String date, Sport sport, Branch branch, User user);
    List<TimeSlot> findByUser(User user);
    List<TimeSlot> findByBranch(Branch branch);

}