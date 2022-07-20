package home.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    private String login;
    @NonNull
    private String password;

    @CollectionTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_id"))
    @ElementCollection(targetClass = Roles.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @NonNull
    private List<Roles> rolesList = new ArrayList<>();

    @OneToMany(mappedBy = "author",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<Post> postList = new ArrayList<>();
}
