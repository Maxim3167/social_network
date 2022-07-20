package home.controller;

import home.model.MyUser;
import home.model.Post;
import home.repository.PostRepository;
import home.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class PostController {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @PostMapping("/create/{content}/{authorId}")
    public void createPost(@PathVariable String content,@PathVariable int authorId){
        MyUser author = userRepository.findById(authorId).get();
        Post post = new Post(content, author, LocalDate.now());
        post.setAuthor(author);
        postRepository.save(post);
    }

    @PutMapping("/updatePost/{content}/id/{id}")
    public void updatePost(@PathVariable String content,@PathVariable int id){
        Post post = postRepository.findById(id).orElseThrow(()-> new RuntimeException());
        post.setContent(content);
        postRepository.save(post);
    }

    @DeleteMapping("/deletePost/id/{id}")
    public void deletePost(@PathVariable int id){
        Post post = postRepository.findById(id).orElseThrow(()-> new RuntimeException());
        postRepository.delete(post);
    }

    @GetMapping("/open/getAllPostsByDate/{date}")
    public List<Post> getPostsByDate(@PathVariable String date){
        LocalDate realDate = LocalDate.parse(date);
        return postRepository.findAll().stream().filter(post -> post.getDate().equals(realDate)).collect(Collectors.toList());
    }
}
