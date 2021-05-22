package com.stage.project.Dao;

import com.stage.project.entities.LigneNvMatiere;
import com.stage.project.entities.Matiere;
import com.stage.project.entities.Niveau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LignNvMatiereRepository extends JpaRepository<LigneNvMatiere,Long> {
    LigneNvMatiere findFirstById(String id);
    LigneNvMatiere findFirstByMatiereNomAndNiveauNv(String matiere_id, int niveau_id);
    void deleteAllByMatiereNom(String nom);
}
