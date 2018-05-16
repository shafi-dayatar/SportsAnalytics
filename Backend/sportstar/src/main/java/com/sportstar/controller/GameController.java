package com.sportstar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sportstar.exceptions.GameIDException;
import com.sportstar.model.entities.Game;
import com.sportstar.model.entities.Player;
import com.sportstar.responses.ApiResponse;
import com.sportstar.service.GameService;
import com.sportstar.service.PlayerService;

@RestController
public class GameController {
	
    public static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private PlayerService authenticateService;
	
	@GetMapping("/")
	public ResponseEntity<?> home(){
		ApiResponse response = new ApiResponse();
		response.setMessage("home");

		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/game", method = RequestMethod.POST)
	public ResponseEntity<?> startGame(@RequestBody Game gameData){
		
		Player player = authenticateService.getPlayer(gameData.getPlayer().getEmailid());
		if(player == null) {
			ApiResponse response = new ApiResponse();
			response.setMessage("Player doesnot exists, Can not start the game");
			return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		Game gameId = gameService.startGame(gameData);		
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(gameId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/game", method = RequestMethod.GET)
	public ResponseEntity<?> getGameDetailsById(@RequestParam String gameId, @RequestParam String emailId){
		
		Player player = authenticateService.getPlayer(emailId);
		if(player == null) {
			ApiResponse response = new ApiResponse();
			return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		ApiResponse apiResponse = null;
		try {
			Game gameResponse = gameService.getGameById(gameId);
			apiResponse = new ApiResponse(gameResponse);
		} catch (GameIDException e) {
			String msg = "Incorrect GaMe ID";
			apiResponse = new ApiResponse(msg);
			LOGGER.debug(msg);
		}		
		
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/endGame", method = RequestMethod.GET)
	public ResponseEntity<?> updateEndTime(@RequestParam String gameId, @RequestParam String emailId) throws GameIDException{
		
		Player player = authenticateService.getPlayer(emailId);
		if(player == null) {
			ApiResponse response = new ApiResponse();
			return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		Game gameResponse = gameService.updateEndTime(gameId);		
		return new ResponseEntity<ApiResponse>(new ApiResponse(gameResponse), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/analyseGame", method = RequestMethod.GET)
	public ResponseEntity<?> analyseGame(@RequestParam String gameId, @RequestParam String emailId) throws GameIDException{
		Player player = authenticateService.getPlayer(emailId);
		if(player == null) {
			ApiResponse response = new ApiResponse();
			return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		ApiResponse apiResponse = null;
		try {
			Game gameResponse = gameService.analyseGame(gameId);		
			return new ResponseEntity<ApiResponse>(new ApiResponse(gameResponse), HttpStatus.OK);
		}catch (GameIDException e) {
			String msg = "Incorrect GaMe ID";
			apiResponse = new ApiResponse(msg);
			LOGGER.debug(msg);
		}	
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

	}	
}

