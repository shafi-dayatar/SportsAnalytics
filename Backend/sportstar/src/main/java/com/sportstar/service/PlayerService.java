package com.sportstar.service;

import com.sportstar.model.dto.PlayerDto;

public interface PlayerService {

	PlayerDto getPlayer(String emailId);

	void createPlayer(PlayerDto playerDto);

}
