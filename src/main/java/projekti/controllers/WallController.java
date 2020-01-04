/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import java.util.ArrayList;
import projekti.domain.Post;
import projekti.domain.AccountRepository;
import projekti.domain.Account;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.domain.Comment;
import projekti.domain.CommentRepository;
import projekti.domain.FriendRequest;
import projekti.domain.FriendRequestRepository;
import projekti.domain.ImageFile;
import projekti.domain.ImageFileRepository;
import projekti.services.PostService;

/**
 *
 * @author sami
 */
@Controller
public class WallController {
    @Autowired
    private PostService postService;
    
    @Autowired
    private CommentRepository commentRepo;
    
    @Autowired
    private ImageFileRepository imageRepo;
    
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
    
    @GetMapping("/wall/{profile}")
    public String wall(Model model, @PathVariable String profile) {
        Account wallOwner = accountRepo.findByProfile(profile);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account user = accountRepo.findByUsername(auth.getName());
        
        PageRequest pageable = PageRequest.of(0, 25, Sort.by("date").descending());
        List<Post> posts = postService.findByWall(wallOwner, pageable);
        
        List<ImageFile> images = imageRepo.findByOwner(wallOwner);
        
        model.addAttribute("posts", posts);
        model.addAttribute("album", images);
        model.addAttribute("owner", wallOwner);
        
        if (user != null && wallOwner.getFriends().contains(user) || wallOwner == user) {
            model.addAttribute("friend", true);
        }
        
        return "wall";
    }
    
    @GetMapping("/friendrequest/{ownerId}")
    public String wall(@PathVariable Long ownerId) {
        Account wallOwner = accountRepo.getOne(ownerId);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account requester = accountRepo.findByUsername(auth.getName());
        
        FriendRequest request = new FriendRequest();
        request.setRequestee(wallOwner);
        request.setRequester(requester);
        
        requestRepo.save(request);
        
        return "redirect:/wall/" + wallOwner.getProfile();
    }
    
    @PostMapping("/wall/{profile}")
    public String postWall(Model model, @PathVariable String profile, @RequestParam String message) {
        Account wallOwner = accountRepo.findByProfile(profile);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        Account poster = accountRepo.findByUsername(auth.getName());
        
        if (wallOwner.getFriends().contains(poster) || wallOwner.equals(poster)) {
            Post post = new Post();
        
            post.setPoster(poster);
            post.setWall(wallOwner);
            post.setMessage(message);
        
            postService.postToWall(wallOwner, post);
        }
        
        return "redirect:/wall/" + wallOwner.getProfile();
    }
    
    @PostMapping("/comment/{profile}/{postId}")
    public String postComment(@PathVariable String profile, @PathVariable Long postId, @RequestParam String message) {
        Post post = postService.findById(postId);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account poster = accountRepo.findByUsername(auth.getName());
        
        Account owner = accountRepo.findByProfile(profile);
        
        if (!owner.getFriends().contains(poster) && !owner.getId().equals(poster.getId())) {
            return "redirect:/wall/" + profile;
        }
        
        Comment comment = new Comment();
        comment.setPoster(poster);
        comment.setPost(post);
        comment.setMessage(message);
        
        commentRepo.save(comment);
        
        return "redirect:/wall/" + profile;
    }
}
