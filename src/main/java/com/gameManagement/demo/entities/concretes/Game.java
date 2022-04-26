package com.gameManagement.demo.entities.concretes;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@Entity
@NoArgsConstructor
@Data
@Table(name = "games")
@Builder
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private int gameId;

    @Column(name = "game_name")
    private String gameName;

    @Column(name = "game_provider_company")
    private String gameProviderCompany;

    @Column(name = "game_price")
    private double gamePrice;

    @OneToMany
    private List<Campaign> campaigns;
}
