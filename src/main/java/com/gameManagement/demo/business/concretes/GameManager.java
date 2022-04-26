package com.gameManagement.demo.business.concretes;

import com.gameManagement.demo.business.abstracts.GameService;
import com.gameManagement.demo.business.dtos.GameListDto;
import com.gameManagement.demo.business.requests.CreateGameRequest;
import com.gameManagement.demo.business.requests.UpdateGameRequest;
import com.gameManagement.demo.core.mapping.ModelMapperService;
import com.gameManagement.demo.core.results.DataResult;
import com.gameManagement.demo.core.results.Result;
import com.gameManagement.demo.core.results.SuccessDataResult;
import com.gameManagement.demo.core.results.SuccessResult;
import com.gameManagement.demo.dataAccess.abstracts.GameDao;
import com.gameManagement.demo.entities.concretes.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameManager implements GameService {

    private GameDao gameDao;
    private ModelMapperService modelMapperService;

    @Autowired
    public GameManager(GameDao gameDao, ModelMapperService modelMapperService)
    {
        this.gameDao = gameDao;
        this.modelMapperService = modelMapperService;
    }


    @Override
    public DataResult<List<GameListDto>> getAll() {

        List<GameListDto> gameListDtos= this.gameDao.findAll()
                .stream().map(game -> this.modelMapperService.forDto().map(game,GameListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult(gameListDtos,"Data Listed");
    }

    @Override
    public DataResult<CreateGameRequest> add(CreateGameRequest createGameRequest) {

        Game game = this.modelMapperService.forRequest().map(createGameRequest,Game.class);

        this.gameDao.save(game);

        return new SuccessDataResult(createGameRequest,"Data Added");
    }

    @Override
    public DataResult<UpdateGameRequest> update(int id, UpdateGameRequest updateGameRequest) {

        Game game = this.gameDao.getById(id);

        this.gameDao.save(game);

        return new SuccessDataResult(updateGameRequest,"Data Updated");
    }

    @Override
    public Result delete(int id) {

        this.gameDao.deleteById(id);

        return new SuccessResult("Date Deleted");
    }
}
