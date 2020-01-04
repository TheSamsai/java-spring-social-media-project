/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.auth;

import projekti.domain.Account;
import projekti.domain.AccountRepository;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projekti.domain.Account;
import projekti.domain.AccountRepository;

/**
 *
 * @author sami
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    AccountRepository accountRepo;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepo.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                account.getUsername(),
                account.getPassword(),
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("USER")));
    }
    
    public Account registerNewUserAccount(Account account)  {
        System.out.println("Begin register");
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        
        Account test = accountRepo.findByUsername(account.getUsername());
        
        if (test != null) {
            return null;
        }
        
        test = accountRepo.findByProfile(account.getProfile());
        
        if (test != null) {
            return null;
        }
        
        System.out.println("Saving.");
        
        Account newAccount = accountRepo.save(account);
        
        System.out.println("Registered!");
        
        return newAccount;
    }
    
}
