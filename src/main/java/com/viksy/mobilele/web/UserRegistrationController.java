package com.viksy.mobilele.web;

import com.viksy.mobilele.model.binding.UserRegistrationBindingModel;
import com.viksy.mobilele.model.entity.UserEntity;
import com.viksy.mobilele.model.entity.VerificationTokenEntity;
import com.viksy.mobilele.model.service.UserRegistrationServiceModel;
import com.viksy.mobilele.service.UserService;
import com.viksy.mobilele.service.VerificationTokenService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.time.Instant;

@Controller
public class UserRegistrationController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final VerificationTokenService verificationTokenService;

    public UserRegistrationController(UserService userService, ModelMapper modelMapper, VerificationTokenService verificationTokenService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.verificationTokenService = verificationTokenService;
    }

    @ModelAttribute("userRegistrationBindingModel")
    public UserRegistrationBindingModel userRegistrationBindingModel() {
        return new UserRegistrationBindingModel();
    }

    @GetMapping("/users/register")
    public String registerUser() {
        return "auth-register";
    }

    @PostMapping("/users/register")
    public String register(@Valid UserRegistrationBindingModel userRegistrationBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) throws MessagingException {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationBindingModel", bindingResult);

            return "redirect:/users/register";
        }

        UserRegistrationServiceModel serviceModel = modelMapper.map(userRegistrationBindingModel, UserRegistrationServiceModel.class);

        if(!userService.isUsernameFree(serviceModel.getUsername())) {
            redirectAttributes.addFlashAttribute("userModel", userRegistrationBindingModel);
            redirectAttributes.addFlashAttribute("usernameOccupied", true);

            return "redirect:/users/register";
        } else {
            if(!userRegistrationBindingModel.getPassword().equals(userRegistrationBindingModel.getConfirmPassword())) {
                redirectAttributes.addFlashAttribute("userModel", userRegistrationBindingModel);
                redirectAttributes.addFlashAttribute("passwordsNotMatch", true);

                return "redirect:/users/register";
            }

            userService.registerUser(serviceModel);
        }

        redirectAttributes.addFlashAttribute("emailSent", true);

        return "redirect:/users/login";
    }

    @GetMapping("/activation")
    public String activation(@RequestParam("token") String token, Model model) {
        VerificationTokenEntity verificationTokenEntity = verificationTokenService.findByToken(token);

        String message = userService.activateAccount(verificationTokenEntity);

        model.addAttribute("message", message);

        return "activation";
    }
}
