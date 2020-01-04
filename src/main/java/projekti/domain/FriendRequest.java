/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.domain;

import javax.persistence.Entity;
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
public class FriendRequest extends AbstractPersistable<Long> {
    @OneToOne
    private Account requester;
    
    @OneToOne
    private Account requestee;
}
