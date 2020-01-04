package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import projekti.domain.Account;
import projekti.domain.AccountRepository;

@Controller
public class DefaultController {
    
    @Autowired
    private AccountRepository accountRepo;
    
    @ModelAttribute("user")
    public Account getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account user = accountRepo.findByUsername(auth.getName());
        
        return user;
    }
    
    @Secured("USER")
    @GetMapping("/")
    public String helloWorld(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        model.addAttribute("message", auth.getName());
        return "index";
    }
}
