package com.khoa.managementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    @Column(nullable = false)
    private String email;
    private String password;
    private int projectSize; // dòng này để mở thêm nhiều project cho user
    @JsonIgnore
    @OneToMany(mappedBy = "assigned", cascade = CascadeType.ALL)
    private List<Issue> assignedIssues = new ArrayList<>();




}
