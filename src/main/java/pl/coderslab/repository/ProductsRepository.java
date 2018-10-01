package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.coderslab.model.Category;
import pl.coderslab.model.Products;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Products,Long> {

    List<Products> findAllByName (String name);

    List<Products> findAllByCategory (String name);

    @Query(value = "SELECT * FROM products ORDER BY id LIMIT ?1 OFFSET ?2", nativeQuery = true)
    public List<Products> getProductsList (@Param("limit")Integer limit, @Param("offset")Integer offset);
}
