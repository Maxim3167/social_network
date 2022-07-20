package home.repository;

import home.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MyUser,Integer> {
    MyUser findUserByLogin(String login);
}
