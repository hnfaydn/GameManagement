package com.gameManagement.demo.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto {

    private int playerId;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private Long nationalityId;

    private List<GameDto> games;
}
