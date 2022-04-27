package com.gameManagement.demo.business.concretes;

import com.gameManagement.demo.business.abstracts.CampaignService;
import com.gameManagement.demo.business.abstracts.GameService;
import com.gameManagement.demo.business.dtos.GameListDto;
import com.gameManagement.demo.business.requests.CreateGameRequest;
import com.gameManagement.demo.business.requests.UpdateGameRequest;
import com.gameManagement.demo.core.businessException.BusinessException;
import com.gameManagement.demo.core.mapping.ModelMapperService;
import com.gameManagement.demo.core.results.DataResult;
import com.gameManagement.demo.core.results.Result;
import com.gameManagement.demo.core.results.SuccessDataResult;
import com.gameManagement.demo.core.results.SuccessResult;
import com.gameManagement.demo.dataAccess.abstracts.GameDao;
import com.gameManagement.demo.entities.concretes.Campaign;
import com.gameManagement.demo.entities.concretes.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameManager implements GameService {

    private GameDao gameDao;
    private ModelMapperService modelMapperService;
    private CampaignService campaignService;

    @Autowired
    public GameManager(GameDao gameDao, ModelMapperService modelMapperService, CampaignService campaignService)
    {
        this.gameDao = gameDao;
        this.modelMapperService = modelMapperService;
        this.campaignService = campaignService;
    }

    @Override
    public DataResult<List<GameListDto>> getAll() {

        List<GameListDto> gameListDtos= this.gameDao.findAll()
                .stream().map(game -> this.modelMapperService.forDto().map(game,GameListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult(gameListDtos,"Data Listed");
    }

    @Override
    public DataResult<CreateGameRequest> add(CreateGameRequest createGameRequest) throws BusinessException {

        Game game = this.modelMapperService.forRequest().map(createGameRequest,Game.class);

        checkIfGameAlreadyExists(createGameRequest.getGameName());
        checkIfGamePriceIsValid(createGameRequest.getGamePrice());
        checkIfCampaignNullOrEmpty(game,createGameRequest.getCampaignIds());

        game.setGameId(0);

        this.gameDao.save(game);

        return new SuccessDataResult(createGameRequest,"Data Added");
    }

    @Override
    public DataResult<UpdateGameRequest> update(int id, UpdateGameRequest updateGameRequest) throws BusinessException {

        checkIfGameIdExists(id);

        Game game = this.gameDao.getById(id);

        checkIfGameAlreadyExists(updateGameRequest.getGameName());
        checkIfGamePriceIsValid(updateGameRequest.getGamePrice());
        checkIfCampaignNullOrEmpty(game,updateGameRequest.getCampaignIds());

        gameUpdateOperations(game,updateGameRequest);

        this.gameDao.save(game);

        return new SuccessDataResult(updateGameRequest,"Data Updated");
    }

    private void gameUpdateOperations(Game game, UpdateGameRequest updateGameRequest) {
        game.setGameName(updateGameRequest.getGameName());
        game.setGamePrice(updateGameRequest.getGamePrice());
        game.setGameProviderCompany(updateGameRequest.getGameProviderCompany());
    }

    @Override
    public Result delete(int id) throws BusinessException {

        checkIfGameIdExists(id);

        this.gameDao.deleteById(id);

        return new SuccessResult("Date Deleted");
    }

    @Override
    public Game getGameById(int gameId) throws BusinessException {

        checkIfGameIdExists(gameId);

        return this.gameDao.getById(gameId);
    }

    private void checkIfGamePriceIsValid(double gamePrice) throws BusinessException {
        if(gamePrice<0){
            throw new BusinessException("Following game price is not valid: "+gamePrice);
        }
    }

    private void checkIfCampaignNullOrEmpty(Game game, List<Integer> campaignIds) throws BusinessException {

        List<Campaign> tempCampaignList = new ArrayList<>();

        if(campaignIds == null || campaignIds.isEmpty()){
            game.setCampaigns(tempCampaignList);
        }else{

            for (Integer id: campaignIds) {

                tempCampaignList.add(this.campaignService.getCampaignById(id));
            }

            game.setCampaigns(tempCampaignList);
        }
    }

    private void checkIfGameAlreadyExists(String gameName) throws BusinessException {
        if(this.gameDao.existsGameByGameName(gameName)){
            throw new BusinessException("Following game is already exists: "+gameName);
        }
    }

    private void checkIfGameIdExists(int id) throws BusinessException {
        if(!this.gameDao.existsGameByGameId(id)){
            throw new BusinessException("There is no game with following id: "+id);
        }
    }

}
