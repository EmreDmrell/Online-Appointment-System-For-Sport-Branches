package edu.mondragon.pbl.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mondragon.pbl.entity.Banner;
import edu.mondragon.pbl.entity.Branch;
import edu.mondragon.pbl.entity.City;
import edu.mondragon.pbl.entity.Feedback;
import edu.mondragon.pbl.entity.Role;
import edu.mondragon.pbl.entity.Sport;
import edu.mondragon.pbl.entity.TimeSlot;
import edu.mondragon.pbl.entity.User;
import edu.mondragon.pbl.repository.BannerRepository;
import edu.mondragon.pbl.repository.BranchRepository;
import edu.mondragon.pbl.repository.CityRepository;
import edu.mondragon.pbl.repository.FeedbackRepository;
import edu.mondragon.pbl.repository.SportRepository;
import edu.mondragon.pbl.repository.TimeSlotRepository;
import edu.mondragon.pbl.service.UserServiceImpl;




@Controller
@RequestMapping(path="/admin")
public class AdminController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    BannerRepository bannerRepository;

    @Autowired
    SportRepository sportRepository;

    @Autowired
    TimeSlotRepository timeSlotRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping(path="/username")
    @ResponseBody
    public String currentUsername(Principal principal) {
        return principal.getName();
    }

    @GetMapping(path="/feedbacks")
    public String showAllFeedbacks(Model model) {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        model.addAttribute("feedbacks", feedbacks);
        return "feedback_list";
    }

    @GetMapping(path="/feedback/{topic}")
    public String showServiceFeedback(@PathVariable("topic") String topic, Model model) {
        List<Feedback> feedbacks = feedbackRepository.findByTopic(topic);
        if (topic.equals("all")) {
            feedbacks = feedbackRepository.findAll();
        }
        // List<String> topics = feedbackRepository.findDistinctTopic();
        List<String> topics = Arrays.asList("all", "overall", "service", "facility", "other");
        model.addAttribute("topics", topics);
        model.addAttribute("thisTopic", topic);
        model.addAttribute("feedbacks", feedbacks);
        return "feedback_list";
    }

    @GetMapping(path="/userlist")
    public String showUserList(Model model) {
        List<User> userList = userServiceImpl.findAll();
        model.addAttribute("userList", userList);
        return "user_list";
    }

    @GetMapping(path="/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        User user = userServiceImpl.findById(id).get();
        userServiceImpl.deleteUser(user);
        return "user_deleted";
    }

    @GetMapping(path="/user/ban/{id}")
    public String banUser(@PathVariable("id") Integer id) {
        User user = userServiceImpl.findById(id).get();
        user.setRole(Role.BAN);
        userServiceImpl.saveUser(user);
        return "user_banned";
    }

    @GetMapping(path="/user/unban/{id}")
    public String unbanUser(@PathVariable("id") Integer id) {
        User user = userServiceImpl.findById(id).get();
        user.setRole(Role.USER);
        userServiceImpl.saveUser(user);
        return "user_unbanned";
    }

    @GetMapping(path="/banner/form")
    public String showBannerForm(Model model) {
        Banner banner = new Banner();
        model.addAttribute("banner", banner);
        return "banner_form";
    }

    @PostMapping(path="/banner/save")
    public String saveBanner(Banner banner, Model model) {
        banner.setId(1);
        bannerRepository.save(banner);
        return "banner_saved";
    }

    @GetMapping(path="/banner/delete")
    public String deleteBanner() {
        Optional<Banner> banner = bannerRepository.findById(1);
        if (banner.isPresent()) {
            bannerRepository.delete(banner.get());
        }
        // return "banner_deleted";
        return "redirect:/index";
    }

    @GetMapping(path="/branch/form")
    public String showBranchForm(Model model) {
        List<Sport> sports = sportRepository.findAll();
        model.addAttribute("sports", sports);
        model.addAttribute("branch", new Branch());
        return "branch_form";
    }

    @PostMapping(path="/branch/save")
    public String saveBranchConfig(Branch branch, @RequestParam("city_name") String city_name, 
    @RequestParam("sportList") List<Integer> sportList) {
        String cn = city_name.toLowerCase();
        List<Sport> sports = new ArrayList<Sport>();
        Optional<City> city = cityRepository.findByName(cn);
        if (city.isPresent()) {
            branch.setCity(city.get());
        } else {
            City new_city = new City();
            new_city.setName(cn);
            cityRepository.save(new_city);
            branch.setCity(new_city);
        }
        for (Integer sportId : sportList) {
            Sport sport = sportRepository.findById(sportId).get();
            sports.add(sport);
        }
        branch.setSports(sports);
        branchRepository.save(branch);
        return "branch_saved";
    }

    @GetMapping(path="/booking/location")
    public String showBranchList(Model model) {
        Map<String, List<Branch>> dic = new TreeMap<String, List<Branch>>();
        List<City> cities = cityRepository.findAll();
        for (City city : cities) {
            List<Branch> branches = branchRepository.findByCity(city);
            dic.put(city.getName(), branches);
        }
        model.addAttribute("dic", dic);
        return "admin_booking_location";
    }

    @GetMapping(path="/booking/{branchCode}")
    public String showUserBookingList(@PathVariable("branchCode") String branchCode, Model model) {
        Branch branch = branchRepository.findByBranchCode(branchCode);
        List<TimeSlot> timeSlots = timeSlotRepository.findByBranch(branch);
        model.addAttribute("branch", branch);
        model.addAttribute("timeSlots", timeSlots);
        return "admin_booking_list";
    }

}
