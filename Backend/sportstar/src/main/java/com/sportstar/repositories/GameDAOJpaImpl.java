package com.sportstar.repositories;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.sportstar.exceptions.GameIDException;
import com.sportstar.model.entities.Game;
import com.sportstar.model.entities.Player;

@Repository
public class GameDAOJpaImpl implements GameDAO {
	
	@PersistenceContext	
	private EntityManager entityManager;
	
	@Transactional
	public void createGame(Game game) {
		entityManager.persist(game);
	}

	@Transactional
	public Game getGameDetails(int gameId) throws GameIDException {
		Query query = entityManager.createNamedQuery("Game.getGameDetails");
		query.setParameter("gameid", gameId);
		List list =  query.getResultList();
		if(list.size() <= 0) {
			throw new GameIDException();
		}
		return (Game) list.get(0);
	}

	@Transactional
	public void updateEndTime(int gameId) {
		Query query = entityManager.createQuery(
			      "UPDATE Game b SET b.endTime = :endTime " +
			      "WHERE b.gameid = :gameid");
		query.setParameter("endTime", java.sql.Time.valueOf(LocalTime.now()));
		query.setParameter("gameid", gameId);
		query.executeUpdate();
	}

	@Transactional
	public Game analyseGame(int gameId) {
		// TODO  call python machine learning service to analyse the game
		return null;
	}

	@Transactional
	public List<Game> getAllGames(Player player) {
		
		Query query = entityManager.createNamedQuery("Game.getGames");
		query.setParameter("player", player);
		List<Game> list =  query.getResultList();
		return list;
	}

}
