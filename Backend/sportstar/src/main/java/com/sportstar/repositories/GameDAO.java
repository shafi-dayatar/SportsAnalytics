package com.sportstar.repositories;

import java.util.List;

import com.sportstar.exceptions.GameIDException;
import com.sportstar.model.entities.Game;
import com.sportstar.model.entities.Player;

public interface GameDAO {
	public void createGame(Game game);
	public Game getGameDetails(int gameId) throws GameIDException;
	public void updateEndTime(int gamedId);
	public Game analyseGame(int gameId);
	public List<Game> getAllGames(Player player);	
}
