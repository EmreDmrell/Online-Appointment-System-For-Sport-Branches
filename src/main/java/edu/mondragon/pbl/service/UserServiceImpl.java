package edu.mondragon.pbl.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mondragon.pbl.entity.User;
import edu.mondragon.pbl.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
        System.out.println("USER UPDATE");
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public Optional<User> findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }
    
}
