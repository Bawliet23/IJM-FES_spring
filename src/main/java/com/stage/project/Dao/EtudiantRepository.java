package com.stage.project.Dao;

import com.stage.project.entities.Etudiant;
import com.stage.project.entities.Groupe;
import com.stage.project.entities.Individu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface EtudiantRepository extends JpaRepository<Etudiant,Long> {
    Etudiant findFirstById(Long id);
    List<Etudiant>findAllByType_Nom(String nom);
    Page<Etudiant>findByPrenomContainingOrNomContaining(String prenom,String nom,Pageable pageable);
    Page<Etudiant>findByNumeroEquals(int numero,Pageable pageable);
    Page<Etudiant>findAll(Pageable pageable);
    Page<Etudiant>findAllByActivitiesGrpIsNotContainingAndTypeNom(Groupe groupe,String prenom, Pageable pageable);
    Page<Etudiant>findAllByActivitiesGrpIsNotContainingAndPrenomContainingOrNomContainingAndTypeNom(Groupe groupe,String prenom,String nom,String typeNom,Pageable pageable);
    Page<Etudiant>findAllByActivitiesIndIsNotContainingAndTypeNom(Individu individu,String prenom,Pageable pageable);
    Page<Etudiant>findAllByActivitiesIndIsNotContainingAndPrenomContainingOrNomContainingAndTypeNom(Individu individu,String prenom,String nom,String typeNom,Pageable pageable);
}
