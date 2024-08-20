package edu.mondragon.pbl.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.mondragon.pbl.entity.Branch;
import edu.mondragon.pbl.entity.City;
import edu.mondragon.pbl.entity.Sport;
import edu.mondragon.pbl.entity.TimeSlot;
import edu.mondragon.pbl.entity.User;
import edu.mondragon.pbl.repository.BranchRepository;
import edu.mondragon.pbl.repository.CityRepository;
import edu.mondragon.pbl.repository.SportRepository;
import edu.mondragon.pbl.repository.TimeSlotRepository;
import edu.mondragon.pbl.service.UserServiceImpl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping(path="/booking")
public class BookingController {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private SportRepository sportRepository;

    @Autowired
    CityRepository cityRepository;

    @GetMapping(path="/location")
    public String showBranchList(Model model) {
        Map<String, List<Branch>> dic = new TreeMap<String, List<Branch>>();
        List<City> cities = cityRepository.findAll();
        for (City city : cities) {
            List<Branch> branches = branchRepository.findByCity(city);
            dic.put(city.getName(), branches);
        }
        model.addAttribute("dic", dic);
        return "booking_location";
    }

    @GetMapping(path="/location/{city}")
    public String getToCity(@PathVariable("city") String city) {
        String redirect = "redirect:/location#" + city;
        return redirect;
    }

    @GetMapping(path="/{branchCode}")
    public String showSportSelection(@PathVariable("branchCode") String branchCode, Model model) {
        Branch branch = branchRepository.findByBranchCode(branchCode);
        List<Sport> sports = branch.getSports();
        model.addAttribute("branch", branch);
        model.addAttribute("sports", sports);
        return "booking_facilities";
    }

    @GetMapping(path="/{branchCode}/{sportId}")
    public String showBookingForm(@PathVariable("branchCode") String branchCode, @PathVariable("sportId") Integer sportId, Model model) {
        Branch branch = branchRepository.findByBranchCode(branchCode);
        Sport sport = sportRepository.findById(sportId).get();
        model.addAttribute("timeSlot", new TimeSlot());
        model.addAttribute("branch", branch);
        model.addAttribute("sport", sport);
        return "booking_form";
    }

    @PostMapping(path="/save")
    public String processBooking(TimeSlot timeSlot, @RequestParam("timeList") List<Integer> timeList, Principal principal, 
    @RequestParam("branchCode") String branchCode, @RequestParam("sportId") String sportId, @RequestParam("date") String date
    ) {
        String username = principal.getName();
        User user = userServiceImpl.findByUsername(username).get();
        Branch branch = branchRepository.findByBranchCode(branchCode);
        Sport sport = sportRepository.findById(Integer.parseInt(sportId)).get();
        Optional<TimeSlot> ts = timeSlotRepository.findByDateAndSportAndBranchAndUser(date, sport, branch, user);
        if (ts.isPresent()) {
            timeSlot = ts.get();
        }
        timeSlot.setUser(user);
        timeSlot.setBranch(branch);
        timeSlot.setSport(sport);
        timeSlot.setDate(date);
        timeSlot.setTime(timeList);
        timeSlotRepository.save(timeSlot);
        return "booking_complete";
    }

    @GetMapping(path="/list")
    public String listAllBooking(Model model, Principal principal) {
        String username = principal.getName();
        User user = userServiceImpl.findByUsername(username).get();
        List<TimeSlot> timeSlots = timeSlotRepository.findByUser(user);
        model.addAttribute("timeSlots", timeSlots);
        return "booking_list";
    }

    @GetMapping("/edit/{timeSlotId}")
    public String showEditBookingForm(Model model, @PathVariable("timeSlotId") Integer timeSlotId) {
        TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId).get();
        Branch branch = timeSlot.getBranch();
        Sport sport = timeSlot.getSport();
        model.addAttribute("timeSlot", timeSlot);
        model.addAttribute("timeSlotid", timeSlotId);
        model.addAttribute("sport", sport);
        model.addAttribute("branch", branch);
        return "booking_edit_form";
    }

    @PostMapping(path="/save/{timeSlotId}")
    public String saveBooking(@PathVariable("timeSlotId") Integer timeSlotId, @RequestParam("timeList") List<Integer> timeList, @RequestParam("date") String date) {
        TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId).get();
        timeSlot.setDate(date);
        timeSlot.setTime(timeList);
        timeSlotRepository.save(timeSlot);
        return "booking_complete";
    }

    @GetMapping("/delete/{timeSlotId}")
    public String deleteBooking(Model model, @PathVariable("timeSlotId") Integer timeSlotId) {
        TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId).get();
        timeSlotRepository.delete(timeSlot);
        return "redirect:/booking/list";
    }

}
