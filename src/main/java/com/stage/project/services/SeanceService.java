package com.stage.project.services;

import com.stage.project.entities.Presence;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SeanceService {
    Map<String,Object> getPresences(Long id);
}
