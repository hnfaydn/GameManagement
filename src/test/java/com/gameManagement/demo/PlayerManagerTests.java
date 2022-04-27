package com.gameManagement.demo;

import com.gameManagement.demo.business.abstracts.CampaignService;
import com.gameManagement.demo.business.abstracts.GameService;
import com.gameManagement.demo.business.concretes.GameManager;
import com.gameManagement.demo.business.concretes.PlayerManager;
import com.gameManagement.demo.business.requests.CreatePlayerRequest;
import com.gameManagement.demo.core.businessException.BusinessException;
import com.gameManagement.demo.core.mapping.ModelMapperManager;
import com.gameManagement.demo.core.mernisCheck.MernisManager;
import com.gameManagement.demo.core.mernisCheck.MernisService;
import com.gameManagement.demo.dataAccess.abstracts.GameDao;
import com.gameManagement.demo.dataAccess.abstracts.PlayerDao;
import com.gameManagement.demo.entities.concretes.Campaign;
import com.gameManagement.demo.entities.concretes.Game;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlayerManagerTests {

    PlayerManager playerManager;

    @Mock
    PlayerDao playerDao;

    @Mock
    GameService gameService;

    @Mock
    MernisService mernisService;

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
        playerManager = new PlayerManager(playerDao, new ModelMapperManager(new ModelMapper()),mernisService,gameService);
    }

    @Test
    public void player_add_successfully_with_null_game_list() throws Exception {

        CreatePlayerRequest createPlayerRequest =
                new CreatePlayerRequest("Mehmet Hanifi","Aydın", LocalDate.of(1995,02,02),Long.parseLong("12345678912"),null);

        boolean actual = playerManager.add(createPlayerRequest).isSuccess();

        Assertions.assertTrue(actual);
    }

    @Test
    public void player_add_successfully_with_out_null_game_list() throws Exception {

        List<Integer> gameIds = new ArrayList<>();
        gameIds.add(1);
        gameIds.add(2);
        gameIds.add(3);

        CreatePlayerRequest createPlayerRequest =
                new CreatePlayerRequest("Mehmet Hanifi","Aydın", LocalDate.of(1995,02,02),Long.parseLong("12345678912"),gameIds);

        for (Integer id:gameIds) {
            given(gameService.getGameById(id)).willReturn(Game.builder().build());
        }

        boolean actual = playerManager.add(createPlayerRequest).isSuccess();

        Assertions.assertTrue(actual);
    }

}
