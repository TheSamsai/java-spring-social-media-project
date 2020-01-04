/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author sami
 */
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class ImageFile extends AbstractPersistable<Long> {
    @ManyToOne
    private Account owner;
    
    private String description;
    
    private LocalDateTime date = LocalDateTime.now();
    
    @ManyToMany
    private List<Account> likedBy = new ArrayList<>();
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "image")
    private List<Comment> comments = new ArrayList<>();
    
    private String contentType;
    
    private Long size;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;
}