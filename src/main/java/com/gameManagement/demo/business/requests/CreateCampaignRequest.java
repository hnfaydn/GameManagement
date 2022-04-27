package com.gameManagement.demo.business.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCampaignRequest {

    private String campaignName;

    private LocalDate campaignStartDate;

    private LocalDate campaignEndDate;

    @Min(0)
    private double discountAmount;
}
