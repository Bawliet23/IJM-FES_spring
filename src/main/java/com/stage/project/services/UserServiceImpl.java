package com.stage.project.services;

import com.stage.project.Dao.EtudiantRepository;
import com.stage.project.Dao.ProfRepository;
import com.stage.project.Dao.UserRepository;
import com.stage.project.entities.Etudiant;
import com.stage.project.entities.Prof;
import com.stage.project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service

public class UserServiceImpl implements UserService {

    @Autowired
	private UserRepository userRepository;
    @Autowired
	ProfRepository profRepository;
    @Autowired
	EtudiantRepository etudiantRepository;

	@Override
	@Transactional()
	public User findUserByUsername(String usernameOrEmail) {
		User user = null;
		try {
			user = userRepository.findUserByUsername(usernameOrEmail);
		} catch (Exception e) {
			throw e;
		}
		return user;
	}

	@Override
	public Page<User> findAll(Optional<String> role, Optional<Integer> page) {
		if (role.isPresent() && !role.get().equals(""))return userRepository.findAllByRole_RoleName(role.get(), PageRequest.of(page.orElse(0), 10));
        else return userRepository.findAll(PageRequest.of(page.orElse(0), 10));
	}

	@Override
	public ResponseEntity<String> getPassword(String nom,String id) {
		if (nom.equals("prof")){
			Prof prof=profRepository.findFirstByCin(id);
			return new ResponseEntity<>(prof.getUser().getPassString(), HttpStatus.OK);
		}else{
			Etudiant etudiant=etudiantRepository.findFirstById(Long.valueOf(id));
			return new ResponseEntity<>(etudiant.getUser().getPassString(), HttpStatus.OK);
		}
	}

}
