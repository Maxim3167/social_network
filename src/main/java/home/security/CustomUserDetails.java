package home.security;

import home.model.MyUser;
import home.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class CustomUserDetails implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser userByLogin = userRepository.findUserByLogin(username);
        return User.builder()
                .username(username)
                .password(userByLogin.getPassword())
                .roles(userByLogin.getRolesList().stream().map(roles -> roles.name()).toArray(String[]::new))
                .build();
    }
}
