/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author sami
 */
@Entity
@AllArgsConstructor @NoArgsConstructor
public class Account extends AbstractPersistable<Long>{
    private String username;
    
    private String password;
    
    private String name;
    
    private String profile;
    
    @OneToOne
    private ImageFile profilePicture;
    
    @ManyToMany
    private List<Account> friends = new ArrayList<>();
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getProfile() {
        return profile;
    }
    
    public void setProfile(String profile) {
        this.profile = profile;
    }
    
    
    public ImageFile getProfilePicture() {
        return profilePicture;
    }
    
    public void setProfilePicture(ImageFile img) {
        this.profilePicture = img;
    }
    
    public List<Account> getFriends() {
        return friends;
    }
}
