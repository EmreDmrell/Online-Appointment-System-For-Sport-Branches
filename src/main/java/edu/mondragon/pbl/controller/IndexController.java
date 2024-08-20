package edu.mondragon.pbl.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import edu.mondragon.pbl.entity.Banner;
import edu.mondragon.pbl.entity.Branch;
import edu.mondragon.pbl.entity.Role;
import edu.mondragon.pbl.entity.Sport;
import edu.mondragon.pbl.entity.User;
import edu.mondragon.pbl.repository.BannerRepository;
import edu.mondragon.pbl.repository.BranchRepository;
import edu.mondragon.pbl.repository.SportRepository;
import edu.mondragon.pbl.service.UserServiceImpl;

@Controller
public class IndexController {

	@Autowired
	UserServiceImpl userServiceImpl;

	@Autowired
	BannerRepository bannerRepository;

	@Autowired
	BranchRepository branchRepository;

	@Autowired
	SportRepository sportRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping(path={"/index", "/"})
	public String index(Model model) {
		Optional<Banner> banner = bannerRepository.findById(1);
		model.addAttribute("banner", banner);
		return "index";
	}

	@GetMapping("/contact")
	public String showContactPage() {
		return "contact";
	}

	@GetMapping("/denied")
	public String accessDenied() {
		return "error";
	}

	@GetMapping(path="/setup")
    public String addSport() {
        List<Branch> branchs = branchRepository.findAll();
        List<Sport> sports = sportRepository.findAll();
        for (Branch branch : branchs) {
            branch.setSports(sports);
            branchRepository.save(branch);
        }
		Optional<User> user = userServiceImpl.findByUsername("admin");
		if (user.isEmpty()) {
			User admin = new User();
			admin.setRole(Role.ADMIN);
			admin.setFirstName("admin");
			admin.setLastName("admin");
			admin.setEmail("admin@admin.admin");
			admin.setUsername("admin");
			String encodedPassword = bCryptPasswordEncoder.encode("admin");
        	admin.setPassword(encodedPassword);
			userServiceImpl.saveUser(admin);
		}
        return "redirect:/index";
    }

}

