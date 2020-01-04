/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.domain;

import projekti.domain.Account;
import projekti.domain.Comment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author sami
 */
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Post extends AbstractPersistable<Long> {
    @OneToOne
    private Account wall;
    
    @ManyToOne
    private Account poster;
    
    private LocalDateTime date = LocalDateTime.now();
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();
    
    @ManyToMany
    private List<Account> likedBy = new ArrayList<>();
    
    private String message;
}
