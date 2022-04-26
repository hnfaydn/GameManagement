package com.gameManagement.demo.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignListDto {

    private int campaignId;

    private String campaignName;

    private LocalDate campaignStartDate;

    private LocalDate campaignEndDate;

    private double discountAmount;

}
