package com.oranbyte.recipebook.controller;

import java.util.Map;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    private static final Map<Integer, String> ERROR_MESSAGES = Map.of(
            HttpStatus.NOT_FOUND.value(), "Oops! Page Not Found!",
            HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error!",
            HttpStatus.FORBIDDEN.value(), "Access Denied!",
            HttpStatus.BAD_REQUEST.value(), "Bad Request!"
    );

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute("jakarta.servlet.error.status_code");

        if (status instanceof Integer statusCode) {
            String message = ERROR_MESSAGES.getOrDefault(
                    statusCode,
                    "Oops! Something went wrong. Please try again later."
            );

            model.addAttribute("errorCode", statusCode);
            model.addAttribute("errorMessage", message);

            if (ERROR_MESSAGES.containsKey(statusCode)) {
                return "error/code-error";
            }
        }

        return "error/error";
    }
}
