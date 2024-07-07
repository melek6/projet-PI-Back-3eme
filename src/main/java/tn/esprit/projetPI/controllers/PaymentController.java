package tn.esprit.projetPI.controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.projetPI.models.ChargeResponse;
import tn.esprit.projetPI.services.StripeService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/charge")
    public ResponseEntity<String> chargeCard(
            @RequestParam String token,
            @RequestParam double amount) {
        try {
            Charge charge = stripeService.chargeCreditCard(token, amount);
            return ResponseEntity.ok("Charge successful: " + charge.getId());
        } catch (StripeException e) {
            return ResponseEntity.status(500).body("Charge failed: " + e.getMessage());
        }
    }
}
