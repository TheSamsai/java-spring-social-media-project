/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author sami
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    public Account findByUsername(String username);
    
    public Account findByProfile(String profile);
    
    public List<Account> findByNameContaining(String query);
}
