package com.gameManagement.demo.business.concretes;

import com.gameManagement.demo.business.abstracts.GameService;
import com.gameManagement.demo.business.abstracts.PlayerService;
import com.gameManagement.demo.business.dtos.PlayerListDto;
import com.gameManagement.demo.business.requests.CreatePlayerRequest;
import com.gameManagement.demo.business.requests.UpdatePlayerRequest;
import com.gameManagement.demo.core.businessException.BusinessException;
import com.gameManagement.demo.core.mapping.ModelMapperService;
import com.gameManagement.demo.core.mernisCheck.MernisService;
import com.gameManagement.demo.core.results.DataResult;
import com.gameManagement.demo.core.results.Result;
import com.gameManagement.demo.core.results.SuccessDataResult;
import com.gameManagement.demo.core.results.SuccessResult;
import com.gameManagement.demo.dataAccess.abstracts.PlayerDao;
import com.gameManagement.demo.entities.concretes.Game;
import com.gameManagement.demo.entities.concretes.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerManager implements PlayerService {

    private PlayerDao playerDao;
    private ModelMapperService modelMapperService;
    private MernisService mernisService;
    private GameService gameService;

    @Autowired
    public PlayerManager(PlayerDao playerDao, ModelMapperService modelMapperService, MernisService mernisService, GameService gameService) {
        this.playerDao = playerDao;
        this.modelMapperService = modelMapperService;
        this.mernisService = mernisService;
        this.gameService = gameService;
    }

    @Override
    public DataResult<List<PlayerListDto>> getAll() {

        List<PlayerListDto> playerListDtos = this.playerDao.findAll()
                .stream().map(player -> this.modelMapperService.forDto().map(player,PlayerListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult(playerListDtos,"Data Listed");
    }

    @Override
    public DataResult<CreatePlayerRequest> add(CreatePlayerRequest createPlayerRequest) throws Exception {

        checkIfPlayerExistsWithNationalityId(createPlayerRequest.getNationalityId());
        checkIfMernisServiceValid(createPlayerRequest.getNationalityId(),
                                    createPlayerRequest.getFirstName(),
                                    createPlayerRequest.getLastName(),
                                    createPlayerRequest.getDateOfBirth().getYear());

        Player player = this.modelMapperService.forRequest().map(createPlayerRequest,Player.class);

        checkIfGameNullOrEmpty(player,createPlayerRequest.getGameIds());

        player.setPlayerId(0);

        this.playerDao.save(player);

        return new SuccessDataResult(createPlayerRequest,"Data Added");

    }

    @Override
    public DataResult<UpdatePlayerRequest> update(int id, UpdatePlayerRequest updatePlayerRequest) throws Exception {

        checkIfPLayerIdExists(id);

        Player player = this.playerDao.getById(id);

        checkIfMernisServiceValid(updatePlayerRequest.getNationalityId(),
                updatePlayerRequest.getFirstName(),
                updatePlayerRequest.getLastName(),
                updatePlayerRequest.getDateOfBirth().getYear());

        playerUpdateOperations(player,updatePlayerRequest);
        checkIfGameNullOrEmpty(player,updatePlayerRequest.getGameIds());

        return new SuccessDataResult(updatePlayerRequest,"Data Updated");
    }

    @Override
    public Result delete(int id) throws BusinessException {

        checkIfPLayerIdExists(id);

        this.playerDao.deleteById(id);

        return new SuccessResult("Date Deleted");
    }

    private void checkIfPLayerIdExists(int id) throws BusinessException {
        if (this.playerDao.existsPlayerByPlayerId(id)){
            throw new BusinessException("There is no player with following id: "+id);
        }
    }

    private void playerUpdateOperations(Player player, UpdatePlayerRequest updatePlayerRequest) {
        player.setNationalityId(updatePlayerRequest.getNationalityId());
        player.setFirstName(updatePlayerRequest.getFirstName());
        player.setLastName(updatePlayerRequest.getLastName());
        player.setDateOfBirth(updatePlayerRequest.getDateOfBirth());
    }

    private void checkIfMernisServiceValid(Long nationalityId, String firstName, String lastName, int year) throws Exception {
        if(!this.mernisService.checkIfPlayerIsValid(nationalityId, firstName, lastName, year).isSuccess())
        {
            throw new BusinessException("Player informations are not valid");
        }
    }

    private void checkIfGameNullOrEmpty(Player player, List<Integer> gameIds) throws BusinessException {

        List<Game> tempGameList = new ArrayList<>();

        if(gameIds == null || gameIds.isEmpty()){
            player.setGames(tempGameList);
        }else{

            for (Integer id: gameIds) {

                tempGameList.add(this.gameService.getGameById(id));
            }

            player.setGames(tempGameList);
        }
    }

    private void checkIfPlayerExistsWithNationalityId(Long nationalityId) throws BusinessException {
        if (this.playerDao.existsPlayerByNationalityId(nationalityId)){
            throw new BusinessException("There is player exists with following nationality id: "+nationalityId);
        }
    }
}
