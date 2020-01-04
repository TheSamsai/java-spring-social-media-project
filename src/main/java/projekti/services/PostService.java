/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.services;

import projekti.domain.Account;
import projekti.domain.Account;
import projekti.domain.Post;
import projekti.domain.Post;
import projekti.domain.PostRepository;
import projekti.domain.PostRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author sami
 */
@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepository;
    
    public Post findById(Long id) {
        return postRepository.getOne(id);
    }
    
    public List<Post> findByWall(Account wallOwner) {
        return postRepository.findByWall(wallOwner);
    }
    
    public List<Post> findByWall(Account wallOwner, Pageable pageable) {
        return postRepository.findByWall(wallOwner, pageable);
    }
    
    public void postToWall(Account wallOwner, Post post) {
        post.setWall(wallOwner);
        postRepository.save(post);
    }
}
