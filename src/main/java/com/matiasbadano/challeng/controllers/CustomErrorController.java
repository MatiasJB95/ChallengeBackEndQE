package com.matiasbadano.challeng.controllers;

import com.matiasbadano.challeng.config.AlumnoNotFoundException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class CustomErrorController implements ErrorController {



    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "500";
            }else if(statusCode == HttpStatus.FORBIDDEN.value()){
                return "403";
            }
        }
        return "error";
    }
    @ExceptionHandler(AlumnoNotFoundException.class)
    public ResponseEntity<String> handleAlumnoNotFoundException(AlumnoNotFoundException ex) {
        String mensaje = ex.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
    }
    }
