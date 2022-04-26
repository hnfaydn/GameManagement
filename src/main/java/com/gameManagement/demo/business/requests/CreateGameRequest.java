package com.gameManagement.demo.business.requests;

import com.gameManagement.demo.entities.concretes.Campaign;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGameRequest {

    private String gameName;

    private String gameProviderCompany;

    private double gamePrice;

    private List<Integer> campaignIds;

}
