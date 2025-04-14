package com.example.Quiz_Service.DAO;

import com.example.Quiz_Service.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizeDao extends JpaRepository<Quiz,Integer> {
}
