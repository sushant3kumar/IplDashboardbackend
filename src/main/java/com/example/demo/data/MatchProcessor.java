package com.example.demo.data;

import java.time.LocalDate;

import com.example.demo.Model.Match;

import org.springframework.batch.item.ItemProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatchProcessor implements ItemProcessor<MatchInput, Match> {

  private static final Logger log = LoggerFactory.getLogger(MatchProcessor.class);

  @Override
  public Match process(final MatchInput matchInput) throws Exception {

    final Match transformedMatch = new Match();

    transformedMatch.setId(Long.parseLong(matchInput.getId()));
    transformedMatch.setCity(matchInput.getCity());
    transformedMatch.setDate(LocalDate.parse(matchInput.getDate()));
    transformedMatch.setPlayerOfMatch(matchInput.getPlayerOfMatch());
    transformedMatch.setVenue(matchInput.getVenue());
    transformedMatch.setTeam1(matchInput.getTeam1());
    transformedMatch.setTeam2(matchInput.getTeam2());
    transformedMatch.setTossWinner(matchInput.getTossWinner());
    transformedMatch.setTossDecision(matchInput.getTossDecision());
    transformedMatch.setWinner(matchInput.getWinner());
    transformedMatch.setResult(matchInput.getResult());
    transformedMatch.setResultMargin(matchInput.getResultMargin());

    log.info("Converting (" + matchInput + ") into (" + transformedMatch + ")");

    return transformedMatch;
  }

}
