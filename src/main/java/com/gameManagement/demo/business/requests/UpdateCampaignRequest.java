package com.gameManagement.demo.business.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCampaignRequest {

    private String campaignName;

    private LocalDate campaignStartDate;

    private LocalDate campaignEndDate;

    private double discountAmount;
}
