package com.gameManagement.demo.business.dtos;

import com.gameManagement.demo.entities.concretes.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerListDto {

    private int playerId;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private Long nationalityId;

    private List<GameDto> games;
}
