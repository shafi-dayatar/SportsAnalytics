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
	
	@RequestMapping(value = "/player", method = RequestMethod.POST)
	public ResponseEntity<?> createPlayer(@RequestBody Player playerDto){
	
		try{playerService.createPlayer(playerDto);		
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(playerDto), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(e.getMessage()), HttpStatus.BAD_REQUEST);

		}
	}
	
	@RequestMapping(value = "/player", method = RequestMethod.GET)
	public ResponseEntity<?> getPlayer(@RequestParam String emailId){
		
		Player player = playerService.getPlayer(emailId);
		if(player == null) {
			ApiResponse response = new ApiResponse();
			response.setMessage("Player Does Not Exists");
			return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
		}		
		ApiResponse apiResponse = null;
		Player playerDto = playerService.getPlayer(emailId);
		apiResponse = new ApiResponse(playerDto);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> authenticate(@RequestBody Player player){
		
		if(playerService.authenticate(player.getEmailid(), player.getPassword())) {
			ApiResponse response = new ApiResponse("Login Failed");
			return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
		} else {		
			ApiResponse apiResponse = new ApiResponse(player,"Success");
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		}
	}
	
}
