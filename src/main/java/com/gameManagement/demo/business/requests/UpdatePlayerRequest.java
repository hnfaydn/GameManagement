package com.gameManagement.demo.business.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePlayerRequest {

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private Long nationalityId;

    @Nullable
    private List<Integer> gameIds;
}
