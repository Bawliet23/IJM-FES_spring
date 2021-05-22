package com.stage.project.controllers;

import com.stage.project.Dao.ProfRepository;
import com.stage.project.Dao.SalleRepository;
import com.stage.project.Dao.UserRepository;
import com.stage.project.entities.Prof;
import com.stage.project.entities.Salle;
import com.stage.project.entities.User;
import com.stage.project.services.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@Controller
public class TestController {


    private final UserRepository userRepository;

    @Autowired
    SalleRepository salleRepository;

    @Autowired
    ProfRepository profRepository;

    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(){
        return "You are authenticated";
    }

    @RequestMapping(value = { "/", "/hey" })
    @ResponseBody
    public String home(Principal principal){
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return principal.getName()+" role ="+authorities.toArray()[0];
    }

    @RequestMapping("/seances")
    @ResponseBody
    public String test(){
        UserPrincipal myUserDetails = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user= myUserDetails.getUser();
        if(user.getRole().getRoleName().equals("ROLE_ADMIN"))return "admin "+user.getUsername();
        return user.getProf().getPrenom() +" "+user.getProf().getNom();
    }

    @PutMapping("/salles")
    @ResponseBody
    public String create(@RequestBody Salle salle){
        salleRepository.save(salle);
        return "done";
    }

    @GetMapping("/profs")
    @ResponseBody
    public Prof getProf(Principal principal){
        return null;
    }

}
