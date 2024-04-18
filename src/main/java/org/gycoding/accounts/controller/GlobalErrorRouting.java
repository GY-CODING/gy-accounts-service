package org.gycoding.accounts.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.gycoding.accounts.model.entities.ServerStatus;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@ControllerAdvice
public class GlobalErrorRouting {
    @ExceptionHandler(Exception.class)
    public String handleError(HttpServletRequest request, Model model) {
        // Get error status.
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        int statusCode = status != null ? Integer.valueOf(status.toString()) : -1;
        model.addAttribute("errorStatus", statusCode);

        // Get error message.
        Object errorMsg = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        String errorMessage = errorMsg != null ? errorMsg.toString() : "Unknown";
        model.addAttribute("errorMessage", errorMessage);

        // Get additional information.
        // You may want to customize this based on your application's needs
        String additionalInfo = "Additional information here...";
        model.addAttribute("additionalInfo", additionalInfo);

        // Store error information in local storage for display in the HTML page
        // This is just an example, you should adjust it based on your requirements
        HttpSession session = request.getSession();
        session.setAttribute("errorStatus", statusCode);
        session.setAttribute("errorMessage", errorMessage);
        session.setAttribute("additionalInfo", additionalInfo);

        try {
            return new String(Files.readAllBytes(Paths.get(new ClassPathResource("static/error.html").getURI())));
        } catch (IOException e) {
            e.printStackTrace();
            return ServerStatus.NOT_FOUND.toString();
        }
    }
}
