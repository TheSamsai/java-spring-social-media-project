/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import projekti.domain.Account;
import projekti.domain.AccountRepository;
import projekti.domain.FriendRequest;
import projekti.domain.FriendRequestRepository;

/**
 *
 * @author sami
 */
@Controller
public class FriendController {
    
    @Autowired
    private AccountRepository accountRepo;
    
    @Autowired
    private FriendRequestRepository requestRepo;
    
    @ModelAttribute("user")
    public Account getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account user = accountRepo.findByUsername(auth.getName());
        
        return user;
    }
    
    @GetMapping("/friendrequests")
    public String listFriendRequests(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account user = accountRepo.findByUsername(auth.getName());
        
        model.addAttribute("requests", requestRepo.findByRequestee(user));
        
        return "friendrequests";
    }
    
    @GetMapping("/approve/{requestId}")
    @Transactional
    public String approveRequest(@PathVariable Long requestId) {
        FriendRequest request = requestRepo.getOne(requestId);
        
        Account requestee = request.getRequestee();
        Account requester = request.getRequester();
        
        requestee.getFriends().add(requester);
        requester.getFriends().add(requestee);
        
        accountRepo.save(requestee);
        accountRepo.save(requester);
        
        requestRepo.delete(request);
        
        return "redirect:/friendrequests";
    }
    
    @GetMapping("/deny/{requestId}")
    @Transactional
    public String denyRequest(@PathVariable Long requestId) {
        FriendRequest request = requestRepo.getOne(requestId);
        
        requestRepo.delete(request);
        
        return "redirect:/friendrequests";
    }
}
