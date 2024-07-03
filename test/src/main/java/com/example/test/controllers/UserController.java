package com.example.test.controllers;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.test.model.User;
import com.example.test.model.UsersDto;
import com.example.test.repository.UsersRepository;

import jakarta.validation.Valid;
@Controller
@RequestMapping("/users")
public class UserController {

    private UsersRepository repo;

    public UserController(UsersRepository repo) {
        this.repo = repo;
    }
//    @ControllerAdvice
//    public class GlobalExceptionHandler {
//
//        @ExceptionHandler(Exception.class)
//        public String handleException(Model model, Exception ex) {
//            model.addAttribute("errorMessage", ex.getMessage());
//            return "error"; 
//        }
//    }


    @GetMapping({"","/"})
    public String showUsersList(Model model) {
        List<User> users = repo.findAll();
        model.addAttribute("users", users);
        return "users/index";
    }
    
    @GetMapping("/create")
    public String showCreatePage(Model model) {
    	UsersDto usersDto = new UsersDto();
    	model.addAttribute("usersDto", usersDto);
		return "users/create";		
    }
    
    @PostMapping("/create")
    public String createUser(
    	@Valid @ModelAttribute UsersDto usersDto,
    	BindingResult result
    	) { 
    	
    	User users = new User();
    	users.setUsername(usersDto.getUsername());
    	users.setEmail(usersDto.getEmail());

    	repo.save(users);
    	
    	return "redirect:/users";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable String id, Model model) {
        try {
        	
            User user = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
            
            UsersDto usersDto = new UsersDto();
            usersDto.setId(user.getId());
            usersDto.setUsername(user.getUsername());
            usersDto.setEmail(user.getEmail());
            
            model.addAttribute("usersDto", usersDto);
            
        } catch (Exception e) {
            System.out.println("Error fetching user: " + e.getMessage());
            return "redirect:/users"; 
        }
        return "users/edit"; 
    }

    
    @PostMapping("/edit")
    public String updateUser(@Valid @ModelAttribute UsersDto usersDto, BindingResult result) {
    	System.out.print("id : id" + usersDto.getId());
        if (result.hasErrors()) {
            return "users/edit";
        }
        try {
            User userToUpdate = repo.findById(usersDto.getId()).get();
          
            userToUpdate.setUsername(usersDto.getUsername());
            userToUpdate.setEmail(usersDto.getEmail());
            
            repo.save(userToUpdate); 
            
            return "redirect:/users"; 
        } catch (Exception e) {
            System.out.println("Error message: " + e.getMessage());
            return "redirect:/users"; 
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        try {
            repo.deleteById(id); 
            
            return "redirect:/users";
        } catch (Exception e) {
            System.out.print("Error deleting user: " + e.getMessage());
            return "redirect:/users";
        }
    }

}