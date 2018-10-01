package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.model.Category;
import pl.coderslab.model.Products;
import pl.coderslab.repository.CategoryRepository;
import pl.coderslab.repository.ProductsRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController {

    private final ProductsRepository productsRepository;
    private final CategoryRepository categoryRepository;

    public ProductsController(CategoryRepository categoryRepository, ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
        this.categoryRepository = categoryRepository;
    }

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @RequestMapping("/delete")
//    public String deleteBook(Model model, @RequestParam String id, @RequestParam(required = false) String confirm) {
    public String deleteProduct(@RequestParam Long id) {
//        if (confirm == null) {
//            model.addAttribute("book", bookDao.findBookById(Long.parseLong(id)));
//            return "book/confirmDel";
//        } else {
        productsRepository.delete(id);
        return "redirect:/products/list";
//        }
    }

    @RequestMapping("/list")
    public String showProduct(Model model, @RequestParam(required = false, defaultValue = "10") Integer limit,
                              @RequestParam(required = false, defaultValue = "0") Integer offset) {
        model.addAttribute("products", productsRepository.getProductsList(limit, offset));
        return "products/productList";
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model, @RequestParam(required = false) Long id) {
        if (id != null) {
            model.addAttribute("product", productsRepository.findOne(id));
        } else {
            Products product = new Products();
            model.addAttribute("product", product);
        }
        return "products/addProduct";
    }

    @PostMapping("/addProduct")
    public String saveProduct(@Valid @ModelAttribute("product") Products product, BindingResult result) {
        if (result.hasErrors()) {
            return "products/addProduct";
        } else {
            productsRepository.save(product);
        }
        return "redirect:/products/list";
    }

    @GetMapping("/productDetails")
    public String showProductDetails(Model model, @RequestParam Long id) {
        model.addAttribute("product", productsRepository.findOne(id));
        return "products/productDetails";
    }
}
