package com.sportstar.service;

import java.util.List;

import com.sportstar.exceptions.GameIDException;
import com.sportstar.model.dto.GameDto;

public interface GameService {

	void startGame(String emailId);

	GameDto getGameById(String gameId) throws GameIDException;

	GameDto updateEndTime(String gameId) throws GameIDException;

	GameDto analyseGame(String gameId) throws GameIDException;
	
	List<GameDto> getAllGames(String emailId);
}
