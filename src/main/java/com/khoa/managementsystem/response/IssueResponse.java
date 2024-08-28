package com.khoa.managementsystem.response;

import com.khoa.managementsystem.model.Project;
import com.khoa.managementsystem.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueResponse {

    private Long id;

    private String title;

    private String description;

    private String status;

    private Long projectID;// dòng này để lấy duy nhất projectId
    // fk mà ko cần fetch hết cả object khi gọi projectId

    private String priority;

    private LocalDate dueDate;

    private List<String> tags = new ArrayList<>();

    private User assignee;

    private Project project;

}
