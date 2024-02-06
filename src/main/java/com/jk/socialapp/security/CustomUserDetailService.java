package com.jk.socialapp.security;

import com.jk.socialapp.exceptions.ResourceNotFoundException;
import com.jk.socialapp.models.User;
import com.jk.socialapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("User details service");
        User user = userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User",
                "Email "+ username ,
                0));
        return user;
    }
}
