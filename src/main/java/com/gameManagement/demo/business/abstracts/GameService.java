package com.gameManagement.demo.business.abstracts;

import com.gameManagement.demo.business.dtos.GameDto;
import com.gameManagement.demo.business.dtos.GameListDto;
import com.gameManagement.demo.business.requests.CreateGameRequest;
import com.gameManagement.demo.business.requests.UpdateGameRequest;
import com.gameManagement.demo.core.businessException.BusinessException;
import com.gameManagement.demo.core.results.DataResult;
import com.gameManagement.demo.core.results.Result;
import com.gameManagement.demo.entities.concretes.Campaign;
import com.gameManagement.demo.entities.concretes.Game;

import java.util.List;

public interface GameService {

    DataResult<List<GameListDto>> getAll();

    DataResult<CreateGameRequest> add(CreateGameRequest createGameRequest) throws BusinessException;

    DataResult<UpdateGameRequest> update(int id, UpdateGameRequest updateGameRequest) throws BusinessException;

    Result delete(int id) throws BusinessException;

    Game getGameById(int gameId) throws BusinessException;
}
