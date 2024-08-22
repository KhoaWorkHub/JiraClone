package com.khoa.managementsystem.repository;

import com.khoa.managementsystem.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    public List<Issue> findByProjectID(Long projectID);
}
