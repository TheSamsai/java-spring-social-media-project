/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.domain;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author sami
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByPost(Long id);
    
    public List<Comment> findByPost(Long id, Pageable pageable);
    
    public List<Comment> findByImage(Long id, Pageable pageable);
}
