/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import projekti.domain.Account;
import projekti.domain.AccountRepository;
import projekti.domain.ImageFile;
import projekti.domain.ImageFileRepository;
import projekti.domain.Post;
import projekti.domain.PostRepository;

/**
 *
 * @author sami
 */
@Controller
public class LikeController {
    
    @Autowired
    private AccountRepository accountRepo;
    
    @Autowired
    private PostRepository postRepo;
    
    @Autowired
    private ImageFileRepository imageRepo;
    
    @GetMapping("/like/post/{postId}")
    public String like(@PathVariable Long postId, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account user = accountRepo.findByUsername(auth.getName());
        
        Post post = postRepo.getOne(postId);
        
        String referer = request.getHeader("Referer");
        
        if (post.getLikedBy().contains(user) || !post.getWall().getFriends().contains(user) && !post.getWall().getId().equals(user.getId())) {
            return "redirect:"+ referer;
        }
        
        post.getLikedBy().add(user);
        
        postRepo.save(post);
        
        return "redirect:"+ referer;
    }
    
    @GetMapping("/unlike/post/{postId}")
    public String unlike(@PathVariable Long postId, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account user = accountRepo.findByUsername(auth.getName());
        
        Post post = postRepo.getOne(postId);
        
        post.getLikedBy().remove(user);
        
        postRepo.save(post);
        
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
    
    @GetMapping("/like/image/{postId}")
    public String likeImage(@PathVariable Long postId, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account user = accountRepo.findByUsername(auth.getName());
        
        ImageFile image = imageRepo.getOne(postId);
        
        String referer = request.getHeader("Referer");
        
        if (image.getLikedBy().contains(user) || !image.getOwner().getFriends().contains(user) && !image.getOwner().getId().equals(user.getId())) {
            return "redirect:"+ referer;
        }
        
        image.getLikedBy().add(user);
        
        imageRepo.save(image);
        
        return "redirect:"+ referer;
    }
    
    @GetMapping("/unlike/image/{postId}")
    public String unlikeImage(@PathVariable Long postId, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account user = accountRepo.findByUsername(auth.getName());
        
        ImageFile image = imageRepo.getOne(postId);
        
        image.getLikedBy().remove(user);
        
        imageRepo.save(image);
        
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
}
