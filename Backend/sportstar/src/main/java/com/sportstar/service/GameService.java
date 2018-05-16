package com.sportstar.service;

import java.util.List;

import com.sportstar.exceptions.GameIDException;
import com.sportstar.model.entities.Game;

public interface GameService {

	Game startGame(Game gameData);

	Game getGameById(String gameId) throws GameIDException;

	Game updateEndTime(String gameId) throws GameIDException;

	Game analyseGame(String gameId) throws GameIDException;
	
	List<Game> getAllGames(String emailId);
}
