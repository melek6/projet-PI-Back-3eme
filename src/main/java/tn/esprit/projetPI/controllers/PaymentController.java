package tn.esprit.projetPI.controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.projetPI.models.ChargeRequest;
import tn.esprit.projetPI.models.ChargeResponse;
import tn.esprit.projetPI.services.StripeService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/charge")
    public ResponseEntity<ChargeResponse> chargeCard(@RequestBody ChargeRequest chargeRequest) {
        try {
            Charge charge = stripeService.chargeCreditCard(chargeRequest.getToken(), chargeRequest.getAmount());
            ChargeResponse chargeResponse = new ChargeResponse(
                    charge.getId(),
                    charge.getStatus(),
                    charge.getAmount(),
                    charge.getCurrency(),
                    charge.getDescription()
            );
            return ResponseEntity.ok(chargeResponse);
        } catch (StripeException e) {
            return ResponseEntity.status(500).body(new ChargeResponse(null, "failed", null, null, e.getMessage()));
        }
    }
}
