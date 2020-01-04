/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.domain.Account;
import projekti.domain.AccountRepository;

/**
 *
 * @author sami
 */
@Controller
public class SearchController {
    
    @Autowired
    AccountRepository accountRepo;
    
    @ModelAttribute("user")
    public Account getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account user = accountRepo.findByUsername(auth.getName());
        
        return user;
    }
    
    @GetMapping("/search")
    public String searchPage() {
        return "search";
    }
    
    @GetMapping("/search/query")
    public String searchPage(Model model, @RequestParam String query) {
        List<Account> results = accountRepo.findByNameContaining(query);
        
        model.addAttribute("results", results);
        
        return "search";
    }
}
