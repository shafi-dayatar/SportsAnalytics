package com.sportstar.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.sportstar.model.dto.PlayerDto;
import com.sportstar.model.entities.Player;
import com.sportstar.repositories.PlayerDAO;

public class PlayerServiceImpl implements PlayerService {
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PlayerDAO playerDao;
	
	@Override
	public PlayerDto getPlayer(String emailId) {
		Player player = null;
		try {
			player = playerDao.getPlayer(emailId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertToDto(player);
	}

	
	@Override
	public void createPlayer(PlayerDto playerDto) {
		Player player = convertToEntity(playerDto);
		playerDao.createPlayer(player);		
	}
	private PlayerDto convertToDto(Player player) {
		PlayerDto playerDto = modelMapper.map(player, PlayerDto.class);
	    return playerDto;
	}
	private Player convertToEntity(PlayerDto playerDto) {
		Player player = modelMapper.map(playerDto, Player.class);
	    return player;
	}
		
}
