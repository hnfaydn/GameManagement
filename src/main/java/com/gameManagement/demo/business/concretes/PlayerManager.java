package com.gameManagement.demo.business.concretes;

import com.gameManagement.demo.business.abstracts.PlayerService;
import com.gameManagement.demo.business.dtos.PlayerListDto;
import com.gameManagement.demo.business.requests.CreatePlayerRequest;
import com.gameManagement.demo.business.requests.UpdatePlayerRequest;
import com.gameManagement.demo.core.mapping.ModelMapperService;
import com.gameManagement.demo.core.results.DataResult;
import com.gameManagement.demo.core.results.Result;
import com.gameManagement.demo.core.results.SuccessDataResult;
import com.gameManagement.demo.core.results.SuccessResult;
import com.gameManagement.demo.dataAccess.abstracts.PlayerDao;
import com.gameManagement.demo.entities.concretes.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerManager implements PlayerService {

    private PlayerDao playerDao;
    private ModelMapperService modelMapperService;

    @Autowired
    public PlayerManager(PlayerDao playerDao, ModelMapperService modelMapperService) {
        this.playerDao = playerDao;
        this.modelMapperService = modelMapperService;
    }


    @Override
    public DataResult<List<PlayerListDto>> getAll() {

        List<PlayerListDto> playerListDtos = this.playerDao.findAll()
                .stream().map(player -> this.modelMapperService.forDto().map(player,PlayerListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult(playerListDtos,"Data Listed");
    }

    @Override
    public DataResult<CreatePlayerRequest> add(CreatePlayerRequest createPlayerRequest) {

        Player player = this.modelMapperService.forRequest().map(createPlayerRequest,Player.class);

        this.playerDao.save(player);

        return new SuccessDataResult(createPlayerRequest,"Data Added");

    }

    @Override
    public DataResult<UpdatePlayerRequest> update(int id, UpdatePlayerRequest updatePlayerRequest) {
        return null;
    }

    @Override
    public Result delete(int id) {

        this.playerDao.deleteById(id);

        return new SuccessResult("Date Deleted");
    }
}
