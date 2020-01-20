package virtus.lab.workshops.KotlinWorkshops.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import virtus.lab.workshops.KotlinWorkshops.model.jpa.Role;
import virtus.lab.workshops.KotlinWorkshops.model.jpa.User;
import virtus.lab.workshops.KotlinWorkshops.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> maybeUser = userRepository.findByEmail(email);
        if(!maybeUser.isPresent()) {
            throw new UsernameNotFoundException("Not found user with email: " + email);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        User user = maybeUser.get();
        for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}
