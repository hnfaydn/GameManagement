package com.gameManagement.demo.business.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGameRequest {

    private String gameName;

    private String gameProviderCompany;

    @Min(0)
    private double gamePrice;

    @Nullable
    private List<Integer> campaignIds;
}
