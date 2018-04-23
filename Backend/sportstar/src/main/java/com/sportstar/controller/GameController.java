package com.sportstar.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sportstar.exceptions.GameIDException;
import com.sportstar.model.dto.GameDto;
import com.sportstar.responses.ApiResponse;
import com.sportstar.service.AuthenticateService;
import com.sportstar.service.GameService;

@RestController
public class GameController {
	
    public static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private AuthenticateService authenticateService;
	
	@RequestMapping(value = "/game", method = RequestMethod.POST)
	public ResponseEntity<?> startGame(@RequestBody GameDto gameDto){
		
		if(!authenticateService.authenticate(gameDto.getEmailid())) {
			ApiResponse response = new ApiResponse();
			return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		gameService.startGame(gameDto.getEmailid());		
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(gameDto), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/game", method = RequestMethod.GET)
	public ResponseEntity<?> getGameDetailsById(@RequestParam String gameId, @RequestParam String emailId){
		
		if(!authenticateService.authenticate(emailId)) {
			ApiResponse response = new ApiResponse();
			return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		ApiResponse apiResponse = null;
		try {
			GameDto gameResponse = gameService.getGameById(gameId);
			apiResponse = new ApiResponse(gameResponse);
		} catch (GameIDException e) {
			String msg = "Incorrect GaMe ID";
			apiResponse = new ApiResponse(msg);
			LOGGER.debug(msg);
		}		
		
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/endGame", method = RequestMethod.POST)
	public ResponseEntity<?> updateEndTime(@RequestParam String gameId, @RequestParam String emailId) throws GameIDException{
		
		if(!authenticateService.authenticate(emailId)) {
			ApiResponse response = new ApiResponse();
			return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		GameDto gameResponse = gameService.updateEndTime(gameId);		
		return new ResponseEntity<ApiResponse>(new ApiResponse(gameResponse), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/analyseGame", method = RequestMethod.POST)
	public ResponseEntity<?> analyseGame(@RequestParam String gameId, @RequestParam String emailId) throws GameIDException{
		if(!authenticateService.authenticate(emailId)) {
			ApiResponse response = new ApiResponse();
			return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		GameDto gameResponse = gameService.analyseGame(gameId);		
		return new ResponseEntity<ApiResponse>(new ApiResponse(gameResponse), HttpStatus.OK);
	}	
}

