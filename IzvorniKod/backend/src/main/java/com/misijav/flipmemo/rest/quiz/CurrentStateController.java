package com.misijav.flipmemo.rest.quiz;

import com.misijav.flipmemo.model.Account;
import com.misijav.flipmemo.model.LearningMode;
import com.misijav.flipmemo.service.CurrentStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/current-state")
public class CurrentStateController {
    private static final Logger logger = LoggerFactory.getLogger(CurrentStateController.class);
    private final CurrentStateService currentStateService;

    @Autowired
    public CurrentStateController(CurrentStateService currentStateService) {
        this.currentStateService = currentStateService;
    }

    // It would be best if this could also be moved to QuizController?
    @PostMapping("/learning-mode")
    public ResponseEntity<?> POST(@AuthenticationPrincipal UserDetails userDetails,
                                  @RequestBody LearningMode learningMode) {
        Account user = (Account) userDetails;
        logger.info("Received request to modify user {} learning mode to {}.", user.getId(), learningMode);

        // Set learning mode for user
        currentStateService.updateLearningMode(user.getId(), learningMode);
        logger.info("Successfully modified learning mode for user {}.", user.getId());
        return ResponseEntity.ok().body("Learning mode updated successfully.");
    }

    /*
    It is better to do it inside QuizServiceJpa?
    @PostMapping("/initialize-pots")
    public ResponseEntity<?> initializePots(@AuthenticationPrincipal UserDetails userDetails,
                                            @RequestBody Long dictionaryId) {
        Account user = (Account) userDetails;
        logger.info("Received request to initialize user {} learning pots.", user.getId());
        currentStateService.initializePotsForUser(user.getId(), dictionaryId);
        logger.info("Successfully initialized learning pots for user {}.", user.getId());
        return ResponseEntity.ok().body("User pots initialized successfully.");
    }
     */
}
