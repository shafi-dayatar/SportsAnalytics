package com.sportstar.controller;

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
import com.sportstar.model.dto.PlayerDto;
import com.sportstar.model.entities.Player;
import com.sportstar.responses.ApiResponse;
import com.sportstar.service.AuthenticateService;
import com.sportstar.service.GameService;
import com.sportstar.service.PlayerService;

@RestController
public class PlayerController {
public static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private AuthenticateService authenticateService;
	
	@RequestMapping(value = "/player", method = RequestMethod.POST)
	public ResponseEntity<?> createPlayer(@RequestBody PlayerDto playerDto){
	
		playerService.createPlayer(playerDto);		
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(playerDto), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/player", method = RequestMethod.GET)
	public ResponseEntity<?> getPlayer(@RequestParam String emailId){
		
		if(!authenticateService.authenticate(emailId)) {
			ApiResponse response = new ApiResponse();
			return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
		}		
		ApiResponse apiResponse = null;
		PlayerDto playerDto = playerService.getPlayer(emailId);
		apiResponse = new ApiResponse(playerDto);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
	
}
