package com.gameManagement.demo;

import com.gameManagement.demo.business.concretes.CampaignManager;
import com.gameManagement.demo.business.dtos.CampaignListDto;
import com.gameManagement.demo.business.requests.CreateCampaignRequest;
import com.gameManagement.demo.core.businessException.BusinessException;
import com.gameManagement.demo.core.mapping.ModelMapperManager;
import com.gameManagement.demo.dataAccess.abstracts.CampaignDao;
import com.gameManagement.demo.entities.concretes.Campaign;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CampaignManagerTests {

    CampaignManager campaignManager;

    @Mock
    CampaignDao campaignDao;

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
        campaignManager = new CampaignManager(campaignDao, new ModelMapperManager(new ModelMapper()));
    }

    @Test
    public void campaign_add_successfully() throws BusinessException {

        CreateCampaignRequest createCampaignRequest =
                new CreateCampaignRequest("%10 discount", LocalDate.of(2022, 4, 30), LocalDate.of(2022, 4, 10), 10);

        given(campaignDao.existsCampaignByCampaignName(createCampaignRequest.getCampaignName())).willReturn(false);

        boolean actual = campaignManager.add(createCampaignRequest).isSuccess();

        Assertions.assertTrue(actual);
    }

    @Test
    public void campaign_add_with_same_name() {

        CreateCampaignRequest createCampaignRequest =
                new CreateCampaignRequest("%10 discount", LocalDate.of(2022, 4, 30), LocalDate.of(2022, 4, 10), 10);

        given(campaignDao.existsCampaignByCampaignName(createCampaignRequest.getCampaignName())).willReturn(true);

        Executable executable = () -> campaignManager.add(createCampaignRequest);

        Assertions.assertThrows(BusinessException.class, executable);
    }

    @Test
    public void campaign_delete_with_existing_id() throws BusinessException {

        given(campaignDao.existsCampaignByCampaignId(1)).willReturn(true);

        boolean actual = campaignManager.delete(1).isSuccess();

        Assertions.assertTrue(actual);
    }

    @Test
    public void campaign_delete_without_existing_id() throws BusinessException {

        given(campaignDao.existsCampaignByCampaignId(1)).willReturn(false);

        Executable executable = () -> campaignManager.delete(1);

        Assertions.assertThrows(BusinessException.class,executable);
    }

}