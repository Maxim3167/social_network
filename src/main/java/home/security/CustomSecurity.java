package home.security;

import home.model.Comment;
import home.model.Post;
import home.repository.CommentRepository;
import home.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("customSecurity")
public class CustomSecurity {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public boolean isAuthorOfPost(String login,int postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("No post with id " + postId));
        return post.getAuthor().getLogin().equals(login);
    }

    public boolean isAuthorOfComment(String login,int commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("No comment with id " + commentId));
        return comment.getAuthor().getLogin().equals(login);
    }
}
