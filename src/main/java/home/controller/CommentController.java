package home.controller;

import home.model.Comment;
import home.model.MyUser;
import home.model.Post;
import home.repository.CommentRepository;
import home.repository.PostRepository;
import home.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private UserRepository userRepository;

    @PostMapping("/create/{text}/{postId}/{authorId}")
    public void createComment(@PathVariable String text,@PathVariable int postId,@PathVariable int authorId){
        MyUser author = userRepository.findById(authorId).orElseThrow(RuntimeException::new);
        Post post = postRepository.findById(postId).orElseThrow(RuntimeException::new);
        Comment comment = new Comment(text, author, LocalDate.now(), 0);
        comment.setPost(post);
        commentRepository.save(comment);
    }

    @PostMapping("/likeComment/{id}")
    public void likeComment(@PathVariable int id){
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException());
        comment.setNumberOfLikes(comment.getNumberOfLikes() + 1);
        commentRepository.save(comment);
    }

    @DeleteMapping("/deleteComment/id/{id}")
    public void deleteComment(@PathVariable int id){
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException());
        commentRepository.delete(comment);
    }
}
