package tn.esprit.projetPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import tn.esprit.projetPI.models.WSmessage;

@Controller
public class WsNotificationController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/application")
    @SendTo("/all/WSmessage")
    public WSmessage send(final WSmessage WSmessage) throws Exception {
        // Log message to verify it is received
        System.out.println("Received WSmessage: " + WSmessage.getText());
        return WSmessage;
    }
    @MessageMapping("/broadcast")
    public void broadcastMessage(@Payload WSmessage WSmessage) {
        // Log message to verify it is received
        System.out.println("Broadcasting WSmessage: " + WSmessage.getText());

        // Broadcasting the message to all connected users
        simpMessagingTemplate.convertAndSend("/all/WSmessage", WSmessage);
    }

}
