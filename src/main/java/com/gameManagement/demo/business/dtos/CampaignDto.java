package com.gameManagement.demo.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignDto {

    private int campaignId;

    private String campaignName;

    private LocalDate campaignStartDate;

    private LocalDate campaignEndDate;

    private double discountAmount;
}
