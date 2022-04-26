package com.gameManagement.demo.dataAccess.abstracts;

import com.gameManagement.demo.entities.concretes.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignDao extends JpaRepository<Campaign,Integer> {

    boolean existsCampaignByCampaignName(String campaignName);

    boolean existsCampaignByCampaignId(int campaignId);
}
