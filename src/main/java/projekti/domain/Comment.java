/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
public class Comment extends AbstractPersistable<Long> {
    @ManyToOne
    private Post post;
    
    @ManyToOne
    private ImageFile image;
    
    @ManyToOne
    private Account poster;
    
    private String message;
    
    private LocalDateTime date = LocalDateTime.now();
}
