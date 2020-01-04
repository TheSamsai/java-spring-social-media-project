/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import javax.servlet.http.HttpSession;
import projekti.domain.Account;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.auth.CustomUserDetailsService;
import projekti.domain.AccountRepository;

/**
 *
 * @author sami
 */
@Controller
public class RegisterController {
    @Autowired
    private CustomUserDetailsService userService;
    
    @Autowired
    private AccountRepository accountRepo;
    
    @ModelAttribute
    private Account getAccount() {
        return new Account();
    }
    
    @GetMapping("/register")
    public String register(@ModelAttribute Account account) {
        return "register";
    }
    
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute Account account, 
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            return "register";
        }
        
        if (accountRepo.findByUsername(account.getUsername()) != null) {
            bindingResult.rejectValue("username", "error.user", "An account with this username already exists!");
            return "register";
        }
        
        if (accountRepo.findByProfile(account.getProfile()) != null) {
            bindingResult.rejectValue("profile", "error.user", "An account with this profile already exists!");
            return "register";
        }
        
        String allowedChars = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        
        for (char c : account.getProfile().toCharArray()) {
            if (!allowedChars.contains(Character.toString(c))) {
                bindingResult.rejectValue("profile", "error.user", "No special characters allowed in the profile!");
                return "register";
            }
        }
        
        System.out.println("Entering userService");
        
        account = userService.registerNewUserAccount(account);
        
        return "redirect:/register/success";
    }
    
    @GetMapping("/register/success")
    public String success() {
        return "success";
    }
}
