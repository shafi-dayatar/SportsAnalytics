package com.sportstar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sportstar.exceptions.PlayerDoesNotExistsException;
import com.sportstar.model.entities.Player;
import com.sportstar.repositories.PlayerDAO;


@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {
		
	@Autowired
	private PlayerDAO playerDao;
	
	@Override
	public Player getPlayer(String emailId) {
		Player player = null;
		try {
			player = playerDao.getPlayer(emailId);
		} catch (PlayerDoesNotExistsException e) {
			//e.printStackTrace();
		}
		return player;
	}

	
	@Override
	public void createPlayer(Player playerDto) {
		playerDao.createPlayer(playerDto);		
	}
	
	

	@Override
	public boolean authenticate(String userName, String password) {
		return playerDao.checkCredentials(userName,password);
	}
		
}
