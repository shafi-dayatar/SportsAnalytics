package com.sportstar.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.sportstar.exceptions.PlayerDoesNotExistsException;
import com.sportstar.model.entities.Player;

@Transactional
@Repository
public class PlayerDAOJpaImpl implements PlayerDAO {

	@PersistenceContext	
	private EntityManager entityManager;
	
	@Transactional
	public Player getPlayer(String emailId) throws PlayerDoesNotExistsException {
		Query query = entityManager.createNamedQuery("Player.getPlayerDetails");
		query.setParameter("emailid", emailId);
		List list =  query.getResultList();
		if(list.size() <= 0) {
			throw new PlayerDoesNotExistsException();
		}
		return (Player) list.get(0);
	}

	@Transactional
	public void createPlayer(Player player) {
		entityManager.persist(player);
	}

}
