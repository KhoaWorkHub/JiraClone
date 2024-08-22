package com.khoa.managementsystem.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.khoa.managementsystem.model.Issue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserResponse {

    private Long id;

    private String fullName;

    private String email;

    private String password;

    private int projectSize; // dòng này để mở thêm nhiều project cho user

    private List<Issue> assignedIssues = new ArrayList<>();
}
