package com.misijav.flipmemo.rest.quiz;

import com.misijav.flipmemo.model.Account;
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

    @GetMapping("/{dictId}")
    public ResponseEntity<?> GET(@PathVariable Long dictId, @AuthenticationPrincipal UserDetails userDetails,
                                 @RequestBody GetQuizQuestionRequest request) {
        Account user = (Account) userDetails;
        GetQuizQuestionResponse getQuizQuestionResponse = quizService.getQuizQuestion(dictId, user.getId(), request);
        return ResponseEntity.ok(getQuizQuestionResponse);
    }

    @PostMapping("/{wordId}/{type}")
    public ResponseEntity<?> POST(@PathVariable Long wordId, @PathVariable String type,
                                  @AuthenticationPrincipal UserDetails userDetails,
                                  @RequestBody CheckQuizAnswerRequest answer) {

        Account user = (Account) userDetails;
        boolean isCorrect = quizService.checkAnswer(user.getId(), wordId, type, answer);
        return ResponseEntity.ok(isCorrect);
    }
}
