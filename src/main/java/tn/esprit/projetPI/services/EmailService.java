package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.dto.ProjectDTO;
import tn.esprit.projetPI.models.Project;

import java.time.format.DateTimeFormatter;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("devp01694@gmail.com");

        mailSender.send(message);
    }
        public void sendDeadlineReminderEmail(String toEmail, ProjectDTO project) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Project Deadline Reminder");
        message.setText(buildEmailContent(project));
        mailSender.send(message);
    }

    private String buildEmailContent(ProjectDTO project) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return String.format("Hello,\n\nThis is a reminder that the project \"%s\" is nearing its deadline.\n\n" +
                        "Project Details:\n" +
                        "Title: %s\n" +
                        "Description: %s\n" +
                        "Category: %s\n" +
                        "Skills Required: %s\n" +
                        "Deadline: %s\n" +
                        "Budget: %.2f\n" +
                        "Number of Propositions: %d\n\n" +
                        "Please make sure to complete all necessary tasks before the deadline.\n\nThank you!",
                project.getTitle(),
                project.getTitle(),
                project.getDescription(),
                project.getCategory(),
                project.getSkillsRequired(),
                project.getDeadline().format(formatter),
                project.getBudget(),
                project.getNbPropositions());
    }

    public void sendPropositionStatusEmail(String toEmail, String status) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Proposition Status Update");
        message.setText("Your proposition has been " + status + ".");
        mailSender.send(message);
    }

}



