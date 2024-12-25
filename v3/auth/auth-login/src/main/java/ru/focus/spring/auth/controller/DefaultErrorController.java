package ru.focus.spring.auth.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.focus.spring.auth.exception.AppException;

import java.time.LocalDateTime;

@Controller
public class DefaultErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(final Model model, final HttpServletRequest request) {
        final Throwable ex = getException(request);
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("message", ex.getMessage());
        if (ex.getCause() instanceof final AppException appex) {
            model.addAttribute("url", appex.getUrl());
            model.addAttribute("status", appex.getStatus());
        }
        return "error";
    }

    private Throwable getException(final HttpServletRequest request) {
        return (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
    }

}
