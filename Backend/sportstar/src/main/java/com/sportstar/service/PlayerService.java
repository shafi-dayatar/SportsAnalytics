package com.sportstar.service;

import com.sportstar.model.entities.Player;

public interface PlayerService {

	Player getPlayer(String emailId);

	void createPlayer(Player playerDto);
	
	boolean authenticate(String userName, String password);

}
