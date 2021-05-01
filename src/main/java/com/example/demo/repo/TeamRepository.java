package com.example.demo.repo;

import com.example.demo.Model.Team;

import org.springframework.data.repository.CrudRepository;


public interface TeamRepository extends CrudRepository<Team,Long> {
    
    Team findByTeamName(String teamName);
}
