package com.misijav.flipmemo.rest.quiz;

import com.misijav.flipmemo.model.Account;
import com.misijav.flipmemo.model.Word;
import com.misijav.flipmemo.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quiz")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/{dictionary-id}")
    public ResponseEntity<?> GET(@PathVariable(value = "dictionary-id") Long dictId,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        // TODO
        //Account user = (Account) userDetails;
        //QuizQuestion question = quizService.getQuizQuestion(dictId, user.getId());
       // return ResponseEntity.ok(question);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{word-id}/{type}")
    public ResponseEntity<?> POST(@PathVariable(value = "word-id") Long wordId,
                                          @PathVariable String type){
                                          //@RequestBody Answer answer) {
        // TODO
        //boolean isCorrect = quizService.checkAnswer(wordId, type, answer);
        //return ResponseEntity.ok(isCorrect);
        return ResponseEntity.ok().build();
    }
}
