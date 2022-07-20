package home.controller;

import home.model.MyUser;
import home.model.Post;
import home.repository.UserRepository;
import home.security.CustomUserDetails;
import home.security.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
@RestController
@AllArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetails customUserDetails;

    @PostMapping("/open/registerUser")
    public void register(@RequestBody MyUser user){
        userRepository.save(user);
    }

    @GetMapping("/open/getAllPosts/{login}")
    public List<Post> getAllPosts(@PathVariable String login){
        MyUser user = userRepository.findUserByLogin(login);
        return user.getPostList();
    }

    @GetMapping("/user/login")
    public String login(Principal principal){
        return jwtTokenUtil.generateToken(customUserDetails.loadUserByUsername(principal.getName()));
    }
}
