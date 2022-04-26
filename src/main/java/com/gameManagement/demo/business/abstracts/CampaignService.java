package com.gameManagement.demo.business.abstracts;

import com.gameManagement.demo.business.dtos.CampaignDto;
import com.gameManagement.demo.business.dtos.CampaignListDto;
import com.gameManagement.demo.business.requests.CreateCampaignRequest;
import com.gameManagement.demo.business.requests.UpdateCampaignRequest;
import com.gameManagement.demo.core.businessException.BusinessException;
import com.gameManagement.demo.core.results.DataResult;
import com.gameManagement.demo.core.results.Result;
import com.gameManagement.demo.entities.concretes.Campaign;
import com.gameManagement.demo.entities.concretes.Player;

import java.util.List;

public interface CampaignService {

    DataResult<List<CampaignListDto>> getAll();

    DataResult<CreateCampaignRequest> add(CreateCampaignRequest createCampaignRequest) throws BusinessException;

    DataResult<UpdateCampaignRequest> update(int id,UpdateCampaignRequest updateCampaignRequest) throws BusinessException;

    Result delete(int id) throws BusinessException;
}
