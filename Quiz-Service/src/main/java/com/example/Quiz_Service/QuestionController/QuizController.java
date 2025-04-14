package com.example.Quiz_Service.QuestionController;

import com.example.Quiz_Service.Service.QuizeService;
import com.example.Quiz_Service.model.QuestionWrapper;
import com.example.Quiz_Service.model.QuizeDTO;
import com.example.Quiz_Service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizeService quizeService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizeDTO quizedto){

        return quizeService.CreateQuize(quizedto.getCategory(),quizedto.getNumQ(),quizedto.getTitle());

    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuestions(@PathVariable Integer id){
        return   quizeService.getQuiz(id);

    }

    @PostMapping("sumbit/{id}")
    public ResponseEntity<Integer> SubmitQuiz(@PathVariable Integer id,@RequestBody List<Response>responses){
        System.out.println("responsesPP: "+responses);
        return  quizeService.calculateResult(id,responses);
    }
}
