package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.model.Meal;
import pl.coderslab.model.User;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal,Long> {

    List<Meal> findAllByUser(User user);
}
