package com.example.question_service.QuestionController;


import com.example.question_service.Service.QuestionService;
import com.example.question_service.model.Question;

import com.example.question_service.model.QuestionWrapper;
import com.example.question_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("getAllQuestion")
    public ResponseEntity<List<Question>> getAllQuestion(){
        return questionService.getAllQuestion();
    }

    @GetMapping("catagory/{CAT}")
    public ResponseEntity<List<Question>> getQuestionByCatagory(@PathVariable String CAT){
        return questionService.getQuestionByCatagory(CAT);
    }

    @PostMapping("/addQuestion")
    public ResponseEntity<String>addQuestions(@RequestBody Question question){
           return questionService.addQuestion(question);
    }

    @GetMapping("generateQuiz")
    public ResponseEntity<List<Integer>> generateQuiz(@RequestParam String Category, @RequestParam Integer nmuQ){
        return questionService.getQuestionforQuiz(Category,nmuQ);
    }

    @PostMapping("getQuestion")
    public ResponseEntity<List<QuestionWrapper>> getQuestionFromid(@RequestBody List<Integer> questionId){
        return questionService.getQuestionFromId(questionId);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses)
    {
        return questionService.getScore(responses);
    }

}
