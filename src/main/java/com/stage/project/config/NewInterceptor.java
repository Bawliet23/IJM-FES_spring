package com.stage.project.config;

import com.stage.project.Dao.JournalisationRepository;
import com.stage.project.entities.Journalisation;
import com.stage.project.entities.User;
import com.stage.project.services.UserPrincipal;
import org.apache.commons.io.IOUtils;
import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Scanner;

@Component
public class NewInterceptor implements HandlerInterceptor {

    @Autowired
    JournalisationRepository journalisationRepository;



    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String action = null;
        if (request.getMethod().equals("DELETE")) action = "delete";
        else if (request.getMethod().equals("POST")) action = "ajouter";
        else if (!request.getMethod().equals("GET")) action = "modifier";
        if (action != null) {
            System.out.println(request.getServletPath());
            UserPrincipal myUserDetails = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = myUserDetails.getUser();
            Date date = new Date();
            Journalisation journalisation = new Journalisation(action, request.getServletPath(), user.getFirstName() + " " + user.getLastName(), date);
            journalisationRepository.save(journalisation);
        }
    }
}
