package com.sportstar.repositories;

import com.sportstar.exceptions.PlayerDoesNotExistsException;
import com.sportstar.model.entities.Player;

public interface PlayerDAO {

	Player getPlayer(String emailId) throws PlayerDoesNotExistsException;

	void createPlayer(Player player);

	boolean checkCredentials(String userName, String password);

}
