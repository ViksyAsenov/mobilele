package com.viksy.mobilele.web.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Controller
public class GlobalExceptionHandler implements ErrorController {
    @ExceptionHandler({ObjectNotFoundException.class})
    public ModelAndView handle(ObjectNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());

        return modelAndView;
    }

    @RequestMapping("/error")
    public String handleGeneralError(Model model) {
        model.addAttribute("message", "Oops...Something went wrong!");

        return "error";
    }
}
