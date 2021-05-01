package com.example.demo.Controller;

import com.example.demo.repo.MatchRepository;
import com.example.demo.repo.TeamRepository;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.Model.Team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    MatchRepository matchRepository;

    public MatchRepository getMatchRepository() {
        return matchRepository;
    }


    public void setMatchRepository(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }


    public TeamRepository getTeamRepository() {
        return teamRepository;
    }
    

    public void setTeamRepository(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }


    @RequestMapping("/teams/{teamName}")
    public Team getTeam(@PathVariable String teamName)
    {
        Team team = this.teamRepository.findByTeamName(teamName);
        team.setLatestMatches(matchRepository.getLatestMatches(teamName, 4));
        return team;

    }

    @RequestMapping("/")
    public List<String> getAllTeams()
    {
        List<String> results= new ArrayList<>();
        this.teamRepository.findAll().forEach(t -> results.add(t.getTeamName()));
        return results;
    }
    
}
