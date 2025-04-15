package com.example.Quiz_Service.Service;

import com.example.Quiz_Service.DAO.QuizeDao;

import com.example.Quiz_Service.feign.QuizInterface;
import com.example.Quiz_Service.model.QuestionWrapper;
import com.example.Quiz_Service.model.Quiz;
import com.example.Quiz_Service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizeService {

    @Autowired
    QuizeDao quizeDao;

    @Autowired
    QuizInterface quizeinterface;

    public ResponseEntity<String> CreateQuize(String category, int numQ, String title) {

        List<Integer> questions = quizeinterface.generateQuiz(category,numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
//        List<Question> questions = questionDAo.getrandomQuestionsBycategory(category,numQ) ;
        quiz.setQuestions(questions);
        quizeDao.save(quiz);
        return new  ResponseEntity<>("success",HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(Integer id) {
        Quiz quize = quizeDao.findById(id).get();
        List<Integer> questionIds = quize.getQuestions();
        ResponseEntity<List<QuestionWrapper>> questions = quizeinterface.getQuestionFromid(questionIds);
//        for(Question q : questionFromDB){
//            QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
//            quetionForUser.add(qw);
//        }

        return questions;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {

        ResponseEntity<Integer> Score = quizeinterface.getScore(responses);

        return Score;
    }
}
