package com.misijav.flipmemo.rest.quiz;

import com.misijav.flipmemo.model.Account;
import com.misijav.flipmemo.model.LearningMode;
import com.misijav.flipmemo.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quiz")
public class QuizController {
    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);
    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/{dictId}")
    public ResponseEntity<?> GET(@PathVariable Long dictId, @AuthenticationPrincipal UserDetails userDetails,
                                 @RequestBody LearningMode learningMode) {
        Account user = (Account) userDetails;
        logger.info("Received new quiz question request from user {} with {} learning mode.", user.getId(), learningMode);
        GetQuizQuestionResponse getQuizQuestionResponse = quizService.getQuizQuestion(dictId, user.getId(), learningMode);
        logger.info("Returning new quiz question for user {}.", user.getId());
        return ResponseEntity.ok(getQuizQuestionResponse);
    }

    @PostMapping("/{wordId}")
    public ResponseEntity<?> POST(@PathVariable Long wordId, @AuthenticationPrincipal UserDetails userDetails,
                                  @RequestBody CheckQuizAnswerRequest answer) {

        Account user = (Account) userDetails;
        logger.info("Received request to evaluate answer from user {}.", user.getId());
        int evaluation = quizService.checkAnswer(user.getId(), wordId, answer);
        if (answer.learningMode().equals(LearningMode.ORIGINAL_AUDIO)) {
            return ResponseEntity.ok(evaluation);
        } else {
            boolean isCorrect;
            isCorrect = evaluation == 10;
            return ResponseEntity.ok(isCorrect);
        }
    }
}
