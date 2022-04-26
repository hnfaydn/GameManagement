package com.gameManagement.demo.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDto {

    private int gameId;

    private String gameName;

    private String gameProviderCompany;

    private double gamePrice;

    private List<CampaignDto> campaigns;
}
