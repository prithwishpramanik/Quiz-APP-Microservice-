package com.example.Quiz_Service.Service;

import com.example.Quiz_Service.DAO.QuizeDao;

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

//    @Autowired
//    QuestionDao questionDAo;


    public ResponseEntity<String> CreateQuize(String category, int numQ, String title) {
//        Quiz quiz = new Quiz();
//        quiz.setTitle(title);
//        List<Question> questions = questionDAo.getrandomQuestionsBycategory(category,numQ) ;
//        quiz.setQuestions(questions);
//        quizeDao.save(quiz);
        return new  ResponseEntity<>("success",HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(Integer id) {

//        Optional<Quiz> quize = quizeDao.findById(id);
//        List<Question> questionFromDB = quize.get().getQuestions();
        List<QuestionWrapper> quetionForUser = new ArrayList<>();
//        for(Question q : questionFromDB){
//            QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
//            quetionForUser.add(qw);
//        }

        return new ResponseEntity<>(quetionForUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
//        Optional<Quiz> quize = quizeDao.findById(id);
//        List<Question>questions = quize.get().getQuestions();
        int right=0;
//        int i = 0;
//        System.out.println("responses: "+ responses);
//        for (Response response :responses){
//            System.out.println("questions.get(i).getRightAnswer() : " + questions.get(i).getRightAnswer());
//            System.out.println("response.getResponse(): "+response.getResponse() );
//            if (questions.get(i).getRightAnswer().equals(response.getResponse())) {
//
//                right++;
//            }
//            i++;
//        }

        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
