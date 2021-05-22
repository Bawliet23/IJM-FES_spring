package com.stage.project.controllers;

import com.stage.project.entities.Data.PaimentData;
import com.stage.project.services.PaimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/paiments")
public class PaimentController {

    @Autowired
    PaimentService paimentService;

    @GetMapping
    public Map<String,Object> getAll(@RequestParam Optional<String> annee,@RequestParam Optional<String> mois,@RequestParam Optional<Integer>page){
        return paimentService.getAll(annee,mois,page);
    }

    @PostMapping
    public Long store(@RequestBody PaimentData paimentData){
        return paimentService.add(paimentData);
    }

    @PutMapping("{id}")
    public Long update(@PathVariable("id") Long id,@RequestBody PaimentData paimentData){
        return paimentService.update(paimentData);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        paimentService.delete(id);
    }

    @GetMapping("/{id}")
    public Map<String,Object> get(@PathVariable("id") Long id){
        return paimentService.getOne(id);
    }

}
