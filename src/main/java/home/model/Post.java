package home.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//название поста, содержимое поста, автор поста (объект класса MyUser), дата создания, список комментариев
@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String content;

    @NonNull
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JsonIgnore
    private MyUser author;

    @NonNull
    private LocalDate date;

    @JsonIgnore
    @OneToMany(mappedBy = "post",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<Comment> commentList = new ArrayList<>();

    @PreRemove
    public void preRemove(){
        getCommentList().forEach(comment->comment.setPost(null));
    }
}
