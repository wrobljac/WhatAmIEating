package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.coderslab.model.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

//    @Query("SELECT c FROM Category c LIMIT :amount  :startsWith")
    @Query(value = "SELECT * FROM category ORDER BY id LIMIT ?1 OFFSET ?2", nativeQuery = true)
    public List<Category> getCategoryList (@Param("limit")Integer limit, @Param("offset")Integer offset);
}
