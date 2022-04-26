package com.gameManagement.demo.business.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGameRequest {

    private String gameName;

    private String gameProviderCompany;

    private double gamePrice;

    private List<Integer> campaignIds;
}
