package com.gameManagement.demo.business.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePlayerRequest {

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private Long nationalityId;

    private List<Integer> gameIds;

}
