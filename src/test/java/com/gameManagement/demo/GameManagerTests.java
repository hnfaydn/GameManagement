package com.gameManagement.demo;

import com.gameManagement.demo.business.abstracts.CampaignService;
import com.gameManagement.demo.business.concretes.GameManager;
import com.gameManagement.demo.business.requests.CreateGameRequest;
import com.gameManagement.demo.core.businessException.BusinessException;
import com.gameManagement.demo.core.mapping.ModelMapperManager;
import com.gameManagement.demo.dataAccess.abstracts.GameDao;
import com.gameManagement.demo.entities.concretes.Campaign;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameManagerTests {

    GameManager gameManager;

    @Mock
    GameDao gameDao;

    @Mock
    CampaignService campaignService;

    @AfterAll
    public void finish() {

    }

    @BeforeAll
    public void initialize() {

    }

    @AfterEach
    public void cleanup() {

    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        gameManager = new GameManager(gameDao, new ModelMapperManager(new ModelMapper()),campaignService);
    }

    @Test
    public void game_add_successfully_with_null_campaign() throws BusinessException {

        CreateGameRequest createGameRequest = new CreateGameRequest("Warface","Crytek",100, null);

        boolean actual = gameManager.add(createGameRequest).isSuccess();

        Assertions.assertTrue(actual);
    }

    @Test
    public void game_add_successfully_without_null_campaign() throws BusinessException {

        List<Integer> campaignIds = new ArrayList<>();
        campaignIds.add(1);
        campaignIds.add(2);
        campaignIds.add(3);

        CreateGameRequest createGameRequest = new CreateGameRequest("Warface","Crytek",100, campaignIds);

        for (Integer id:campaignIds) {
            given(campaignService.getCampaignById(id)).willReturn(Campaign.builder().build());
        }

        boolean actual = gameManager.add(createGameRequest).isSuccess();

        Assertions.assertTrue(actual);
    }

    @Test
    public void game_add_successfully_with_non_existing_name() throws BusinessException {

        List<Integer> campaignIds = new ArrayList<>();
        campaignIds.add(1);
        campaignIds.add(2);
        campaignIds.add(3);

        CreateGameRequest createGameRequest = new CreateGameRequest("Warface","Crytek",100, campaignIds);

        for (Integer id:campaignIds) {
            given(campaignService.getCampaignById(id)).willReturn(Campaign.builder().build());
        }

        given(gameDao.existsGameByGameName(createGameRequest.getGameName())).willReturn(false);

        boolean actual = gameManager.add(createGameRequest).isSuccess();

        Assertions.assertTrue(actual);
    }

    @Test
    public void game_add_not_successfully_with_existing_name() throws BusinessException {

        List<Integer> campaignIds = new ArrayList<>();
        campaignIds.add(1);
        campaignIds.add(2);
        campaignIds.add(3);

        CreateGameRequest createGameRequest = new CreateGameRequest("Warface","Crytek",100, campaignIds);

        for (Integer id:campaignIds) {
            given(campaignService.getCampaignById(id)).willReturn(Campaign.builder().build());
        }

        given(gameDao.existsGameByGameName(createGameRequest.getGameName())).willReturn(true);

        Executable executable = () -> gameManager.add(createGameRequest);

        Assertions.assertThrows(BusinessException.class,executable);
    }

    @Test
    public void game_add_successfully_with_price_more_than_or_equal_zero() throws BusinessException {

        List<Integer> campaignIds = new ArrayList<>();
        campaignIds.add(1);
        campaignIds.add(2);
        campaignIds.add(3);

        CreateGameRequest createGameRequest = new CreateGameRequest("Warface","Crytek",100, campaignIds);

        for (Integer id:campaignIds) {
            given(campaignService.getCampaignById(id)).willReturn(Campaign.builder().build());
        }

        given(gameDao.existsGameByGameName(createGameRequest.getGameName())).willReturn(false);

        boolean actual= gameManager.add(createGameRequest).isSuccess();

        Assertions.assertTrue(true);
    }

    @Test
    public void game_add_not_successfully_with_price_less_than_zero() throws BusinessException {

        List<Integer> campaignIds = new ArrayList<>();
        campaignIds.add(1);
        campaignIds.add(2);
        campaignIds.add(3);

        CreateGameRequest createGameRequest = new CreateGameRequest("Warface","Crytek",-100, campaignIds);

        for (Integer id:campaignIds) {
            given(campaignService.getCampaignById(id)).willReturn(Campaign.builder().build());
        }

        given(gameDao.existsGameByGameName(createGameRequest.getGameName())).willReturn(false);

        Executable executable = () -> gameManager.add(createGameRequest);

        Assertions.assertThrows(BusinessException.class,executable);
    }

    @Test
    public void game_delete_successfully_with_existing_id() throws BusinessException {

        given(gameDao.existsGameByGameId(1)).willReturn(true);

        boolean actual = gameManager.delete(1).isSuccess();

        Assertions.assertTrue(actual);
    }

    @Test
    public void game_delete_not_successfully_without_existing_id() throws BusinessException {

        given(gameDao.existsGameByGameId(1)).willReturn(false);

        Executable executable = ()->gameManager.delete(1);

        Assertions.assertThrows(BusinessException.class,executable);
    }
}



