package home.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

//текст комментария, владелец комментария (объект класса MyUser), дата создания, количество лайков
@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    private String text;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @NonNull
    private MyUser author;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JsonIgnore
    private Post post;
    @NonNull
    private LocalDate date;
    @NonNull
    private int numberOfLikes;


}

