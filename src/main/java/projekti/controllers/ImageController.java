/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import projekti.domain.Account;
import projekti.domain.AccountRepository;
import projekti.domain.Comment;
import projekti.domain.CommentRepository;
import projekti.domain.ImageFile;
import projekti.domain.ImageFileRepository;
import projekti.domain.Post;
import projekti.services.PostService;

/**
 *
 * @author sami
 */
@Controller
public class ImageController {
    @Autowired
    private AccountRepository accountRepo;
    
    @Autowired
    private ImageFileRepository imageRepo;
    
    @Autowired
    private CommentRepository commentRepo;
    
    @Autowired
    private PostService postService;
    
    @GetMapping(path = "/images/{id}/content")
    @ResponseBody
    public byte[] image(@PathVariable Long id) {
        ImageFile image = imageRepo.getOne(id);
        
        if (image == null) {
            return null;
        }
        
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(image.getContentType()));
        headers.setContentLength(image.getSize());

        return image.getContent();
    }
    
    @GetMapping("/deleteImage/{imageId}")
    public String deleteImage(@PathVariable Long imageId, HttpServletRequest request) {
        ImageFile img = imageRepo.getOne(imageId);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account user = accountRepo.findByUsername(auth.getName());
        
        for (Comment comment : img.getComments()) {
            commentRepo.delete(comment);
        }
        
        if (user.getProfilePicture() != null &&
            user.getProfilePicture().getId().equals(img.getId())) {

            user.setProfilePicture(null);
            
            accountRepo.save(user);
        }
        
        if (img.getOwner().getId().equals(user.getId())) {
            imageRepo.delete(img);
        }
        
        String referer = request.getHeader("Referer");
        
        return "redirect:"+ referer;
    }
    
    @PostMapping("/addImage")
    public String save(@RequestParam String description, @RequestParam("file") MultipartFile image) throws IOException{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account user = accountRepo.findByUsername(auth.getName());
        
        List<ImageFile> images = imageRepo.findByOwner(user);
        
        if (images.size() >= 10) {
            // Add error handling
            
            return "redirect:/wall/" + user.getProfile();
        }
        
        ImageFile fob = new ImageFile();
        
        if (image.getContentType().contains("image")) {
            fob.setContentType(image.getContentType());
            fob.setContent(image.getBytes());
            fob.setSize(image.getSize());
            fob.setOwner(user);
            fob.setDescription(description);
            
            fob = imageRepo.save(fob);
            
            System.out.println(fob.getId());
            System.out.println(fob.getContentType());
        } else {
            return "wall";
        }
        
        return "redirect:/wall/" + user.getProfile();
    }
    
    @GetMapping("/setProfilePic/{imageId}")
    public String setProfilePic(@PathVariable Long imageId, HttpServletRequest request) {
        ImageFile img = imageRepo.getOne(imageId);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account user = accountRepo.findByUsername(auth.getName());
        
        user.setProfilePicture(img);
        accountRepo.save(user);
        
        String referer = request.getHeader("Referer");
        
        return "redirect:"+ referer;
    }
    
    @PostMapping("/commentImage/{profile}/{imageId}")
    public String postComment(@PathVariable String profile, @PathVariable Long imageId, @RequestParam String message) {
        ImageFile image = imageRepo.getOne(imageId);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account poster = accountRepo.findByUsername(auth.getName());
        
        Account owner = accountRepo.findByProfile(profile);
        
        if (!owner.getFriends().contains(poster)) {
            return "redirect:/wall/" + profile;
        }
        
        Comment comment = new Comment();
        comment.setPoster(poster);
        comment.setImage(image);
        comment.setMessage(message);
        
        commentRepo.save(comment);
        
        return "redirect:/wall/" + profile;
    }
}
