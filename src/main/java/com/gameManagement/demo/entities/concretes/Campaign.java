package com.gameManagement.demo.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="campaigns")
@Builder
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campaign_id")
    private int campaignId;

    @Column(name = "campaign_name")
    private String campaignName;

    @Column(name = "campaign_start_date")
    private LocalDate campaignStartDate;

    @Column(name = "campaign_end_date")
    private LocalDate campaignEndDate;

    @Column(name = "campaign_amount")
    private double discountAmount;
}