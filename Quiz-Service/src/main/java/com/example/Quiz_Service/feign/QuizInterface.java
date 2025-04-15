package com.example.Quiz_Service.feign;


import com.example.Quiz_Service.model.QuestionWrapper;
import com.example.Quiz_Service.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {

    @GetMapping("api/generateQuiz")
    public ResponseEntity<List<Integer>> generateQuiz(@RequestParam String Category, @RequestParam Integer nmuQ);

    @PostMapping("api/getQuestion")
    public ResponseEntity<List<QuestionWrapper>> getQuestionFromid(@RequestBody List<Integer> questionId);

    @PostMapping("api/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}
