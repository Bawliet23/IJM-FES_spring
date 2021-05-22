package com.stage.project.Dao;

import com.stage.project.entities.Activite;
import com.stage.project.entities.Horraire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface HorraireRepository extends JpaRepository<Horraire,Long> {
    Horraire findFirstByDemiHeureNomAndJourNom(String demiHeure_id,String jour_id);
    Horraire findFirstByDemiHeureNomAndJourNomAndActiviteId(String demiHeure_id,String jour_id,Long id);
    Horraire findFirstByDemiHeureNomAndJourNomAndProfCin(String demiHeure_id,String jour_id,String cin);
    List<Horraire>findAllByProfCinAndJourNom(String cin,String jour);
    Horraire findFirstById(String id);
    List<Horraire>findByDemiHeureNomAndJourNom(String demiHeure_id,String jour_id);
    List<Horraire>findAllByDemiHeureNomAndJourNomAndActiviteIsNot(String demiHeure_nom, String jour_nom, Activite activite);
}
