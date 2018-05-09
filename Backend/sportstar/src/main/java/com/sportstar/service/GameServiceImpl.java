package com.sportstar.service;

import java.sql.Date;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sportstar.exceptions.GameIDException;
import com.sportstar.model.dto.GameDto;
import com.sportstar.model.entities.Game;
import com.sportstar.model.entities.Player;
import com.sportstar.repositories.GameDAO;
import com.sportstar.repositories.PlayerDAO;

@Service
@Transactional(readOnly = true)
public class GameServiceImpl implements GameService {
	
		
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GameDAO gameDao;
	
	@Autowired
	private PlayerDAO playerDao;

	public void startGame(String emailId) {
		Player player = null;
		try {
			player = playerDao.getPlayer(emailId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Game game = new Game();
		game.setPlayedOn(getCurrentDateinSQL());
		game.setStartTime(java.sql.Time.valueOf(LocalTime.now()));
		game.setPlayer(player);
		gameDao.createGame(game);
		
	}	
	private Date getCurrentDateinSQL() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date currentDate = calendar.getTime();
		java.sql.Date date = new java.sql.Date(currentDate.getTime());
		return date;
	}

	public GameDto getGameById(String gameId) throws GameIDException {
		int gameIdInt = Integer.parseInt(gameId);
		Game game = gameDao.getGameDetails(gameIdInt);
		GameDto gameDto = modelMapper.map(game,GameDto.class);
		return gameDto;		
	}

	public GameDto updateEndTime(String gameId) throws GameIDException {
		int gameIdInt = Integer.parseInt(gameId);
		gameDao.updateEndTime(gameIdInt);
		Game game = gameDao.getGameDetails(gameIdInt);
		GameDto gameDto = modelMapper.map(game,GameDto.class);
		return gameDto;		

	}

	public GameDto analyseGame(String gameId) throws GameIDException {
		int gameIdInt = Integer.parseInt(gameId);
		gameDao.analyseGame(gameIdInt);
		Game game = gameDao.getGameDetails(gameIdInt);
		GameDto gameDto = modelMapper.map(game,GameDto.class);
		return gameDto;		
	}

	public List<GameDto> getAllGames(String emailId) {
		Player player = null ;
		try {
			player = playerDao.getPlayer(emailId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Game> gameList = gameDao.getAllGames(player);
		return gameList.stream().map(game -> convertToDto(game))
		          .collect(Collectors.toList());
	}
	
	private GameDto convertToDto(Game game) {
	    GameDto gameDto = modelMapper.map(game, GameDto.class);
	    return gameDto;
	}
		
}