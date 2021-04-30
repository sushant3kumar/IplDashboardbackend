package com.example.demo.Batchprocessing;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.example.demo.Model.Team;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

  private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

  private final EntityManager em;

  @Autowired
  public JobCompletionNotificationListener(EntityManager em) {
    this.em = em;
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("!!! JOB FINISHED! Time to verify the results");

      HashMap<String, Team> map = new HashMap<>();

      em.createQuery("select m.team1, count(*) from Match m group by m.team1", Object[].class).getResultList().stream()
          .map(te -> new Team((String) te[0], (long) te[1])).forEach(t -> map.put(t.getTeamName(), t));

      em.createQuery("select m.team2, count(*) from Match m group by m.team2", Object[].class).getResultList().stream()
          .forEach(t -> {

            Team team = map.get((String) t[0]);
            if (team != null)
              (team).setTotalMatches((team).getTotalMatches() + (long) t[1]);
          });

      em.createQuery("select m.winner, count(*) from Match m group by m.winner", Object[].class).getResultList()
          .stream().forEach(team -> {
            Team team1 = map.get((String) team[0]);
            if(team1!=null)
              team1.setTotalWins((long) team[1]);
          });

      map.values().forEach(team -> em.persist(team));

      map.values().forEach(team -> System.out.println(team));

    }
  }
}
