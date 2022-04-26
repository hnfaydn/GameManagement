package com.gameManagement.demo.business.abstracts;

import com.gameManagement.demo.business.dtos.PlayerDto;
import com.gameManagement.demo.business.dtos.PlayerListDto;
import com.gameManagement.demo.business.requests.CreatePlayerRequest;
import com.gameManagement.demo.business.requests.UpdatePlayerRequest;
import com.gameManagement.demo.core.results.DataResult;
import com.gameManagement.demo.core.results.Result;
import com.gameManagement.demo.entities.concretes.Campaign;
import com.gameManagement.demo.entities.concretes.Game;
import com.gameManagement.demo.entities.concretes.Player;

import java.util.List;

public interface PlayerService {

    DataResult<List<PlayerListDto>> getAll();

    DataResult<CreatePlayerRequest> add(CreatePlayerRequest createPlayerRequest);

    DataResult<UpdatePlayerRequest> update(int id, UpdatePlayerRequest updatePlayerRequest);

    Result delete(int id);
}

