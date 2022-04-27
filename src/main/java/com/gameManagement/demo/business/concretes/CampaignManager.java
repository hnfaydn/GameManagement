package com.gameManagement.demo.business.concretes;

import com.gameManagement.demo.business.abstracts.CampaignService;
import com.gameManagement.demo.business.dtos.CampaignListDto;
import com.gameManagement.demo.business.requests.CreateCampaignRequest;
import com.gameManagement.demo.business.requests.UpdateCampaignRequest;
import com.gameManagement.demo.core.businessException.BusinessException;
import com.gameManagement.demo.core.mapping.ModelMapperService;
import com.gameManagement.demo.core.results.DataResult;
import com.gameManagement.demo.core.results.Result;
import com.gameManagement.demo.core.results.SuccessDataResult;
import com.gameManagement.demo.core.results.SuccessResult;
import com.gameManagement.demo.dataAccess.abstracts.CampaignDao;
import com.gameManagement.demo.entities.concretes.Campaign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampaignManager implements CampaignService {
    private CampaignDao campaignDao;
    private ModelMapperService modelMapperService;

    @Autowired
    public CampaignManager(CampaignDao campaignDao, ModelMapperService modelMapperService) {
        this.campaignDao = campaignDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<CampaignListDto>> getAll() {

        List<CampaignListDto> campaignListDtos = this.campaignDao.findAll()
                .stream().map(campaign -> this.modelMapperService.forDto().map(campaign,CampaignListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult(campaignListDtos,"Data Listed");
    }

    @Override
    public DataResult<CreateCampaignRequest> add(CreateCampaignRequest createCampaignRequest) throws BusinessException {

        checkIfCampaignAlreadyExists(createCampaignRequest.getCampaignName());
        checkIfCampaignDatesAreCorrect(createCampaignRequest.getCampaignStartDate(),createCampaignRequest.getCampaignEndDate());
        checkIfCampaignAmountCorrect(createCampaignRequest.getDiscountAmount());

        Campaign campaign = this.modelMapperService.forRequest().map(createCampaignRequest,Campaign.class);

        this.campaignDao.save(campaign);

        return new SuccessDataResult(createCampaignRequest,"Data Added");
    }

    @Override
    public DataResult<UpdateCampaignRequest> update(int id, UpdateCampaignRequest updateCampaignRequest) throws BusinessException {

        checkIfCampaignIdExists(id);
        checkIfCampaignAlreadyExists(updateCampaignRequest.getCampaignName());
        checkIfCampaignDatesAreCorrect(updateCampaignRequest.getCampaignStartDate(),updateCampaignRequest.getCampaignEndDate());
        checkIfCampaignAmountCorrect(updateCampaignRequest.getDiscountAmount());

        Campaign campaign = this.campaignDao.getById(id);

        campaignUpdateOperations(campaign,updateCampaignRequest);

        this.campaignDao.save(campaign);

        return new SuccessDataResult(updateCampaignRequest,"Data Updated");
    }

    @Override
    public Result delete(int id) throws BusinessException {

        checkIfCampaignIdExists(id);

        this.campaignDao.deleteById(id);

        return new SuccessResult("Date Deleted");
    }

    @Override
    public Campaign getCampaignById(int campaignId) throws BusinessException {

        checkIfCampaignIdExists(campaignId);

        return this.campaignDao.getById(campaignId);
    }

    private void checkIfCampaignIdExists(int id) throws BusinessException {
        if (!this.campaignDao.existsCampaignByCampaignId(id)){
            throw new BusinessException("There is no campaign with following id: "+id);
        }
    }

    private void checkIfCampaignAmountCorrect(double discountAmount) throws BusinessException {
        if (discountAmount<0){
            throw new BusinessException("Campaign amount can not less than zero");
        }
    }

    private void checkIfCampaignDatesAreCorrect(LocalDate campaignStartDate, LocalDate campaignEndDate) throws BusinessException {

        if (campaignStartDate.isBefore(campaignEndDate)){
            throw new BusinessException("Campaign end date can not before start date");

        }
    }

    private void checkIfCampaignAlreadyExists(String campaignName) throws BusinessException {
        if(this.campaignDao.existsCampaignByCampaignName(campaignName)){
            throw new BusinessException("This campaign is already exists:"+ campaignName );
        }
    }

    private void campaignUpdateOperations(Campaign campaign, UpdateCampaignRequest updateCampaignRequest) {

        campaign.setCampaignName(updateCampaignRequest.getCampaignName());
        campaign.setCampaignStartDate(updateCampaignRequest.getCampaignStartDate());
        campaign.setCampaignEndDate(updateCampaignRequest.getCampaignEndDate());
        campaign.setDiscountAmount(updateCampaignRequest.getDiscountAmount());
    }
}
