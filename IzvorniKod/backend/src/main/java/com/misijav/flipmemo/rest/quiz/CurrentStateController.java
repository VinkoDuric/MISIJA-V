package com.misijav.flipmemo.rest.quiz;

import com.misijav.flipmemo.model.Account;
import com.misijav.flipmemo.model.LearningMode;
import com.misijav.flipmemo.service.CurrentStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/current-state")
public class CurrentStateController {

    @Autowired
    private CurrentStateService currentStateService;

    // set learning mode
    @PostMapping("/learning-mode")
    public ResponseEntity<?> POST(@AuthenticationPrincipal UserDetails userDetails,
                                  @RequestBody LearningMode learningMode) {
        Account user = (Account) userDetails;
        currentStateService.updateLearningMode(user.getId(), learningMode);
        return ResponseEntity.ok().body("Learning mode updated successfully.");
    }

    /*
    @PostMapping("/{dict_id}")
    public ResponseEntity<?> initDictPots(@PathVariable(value = "dict_id") Long dictId,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        Account user = (Account) userDetails;
        currentStateService.initializeLearningStatesForUser(user, dictId);
        return ResponseEntity.ok().build();
    }
     */
}
