package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.model.Category;
import pl.coderslab.repository.CategoryRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/category")
public class CategoryController {


    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @RequestMapping("/delete")
//    public String deleteBook(Model model, @RequestParam String id, @RequestParam(required = false) String confirm) {
    public String deleteCategory(@RequestParam Long id) {
//        if (confirm == null) {
//            model.addAttribute("book", bookDao.findBookById(Long.parseLong(id)));
//            return "book/confirmDel";
//        } else {
        categoryRepository.delete(id);
        return "redirect:/category/list";
//        }
    }

    @RequestMapping("/list")
    public String showCategory(Model model, @RequestParam(required = false, defaultValue = "10") Integer limit,
                               @RequestParam(required = false, defaultValue = "0") Integer offset) {
        model.addAttribute("categories", categoryRepository.getCategoryList(limit, offset));
        return "category/categoryList";
    }

    @GetMapping("/addCategory")
    public String addCategory(Model model, @RequestParam(required = false) Long id) {
        if (id != null) {
            model.addAttribute("category", categoryRepository.findOne(id));
        } else {
            Category category = new Category();
            model.addAttribute("category", category);
        }
        return "category/addCategory";
    }

    @PostMapping("/addCategory")
    public String saveCategory(@Valid Category category, BindingResult result) {
        if (result.hasErrors()) {
            return "category/addCategory";
        } else {
            categoryRepository.save(category);
        }
        return "redirect:/category/list";
    }

}