package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.model.Meal;
import pl.coderslab.model.ProductAmount;
import pl.coderslab.model.Products;
import pl.coderslab.model.User;
import pl.coderslab.repository.MealRepository;
import pl.coderslab.repository.ProductsRepository;
import pl.coderslab.service.CurrentUser;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Controller
@RequestMapping("/meal")
@SessionAttributes("meal")
public class MealController {

//    @Autowired
//    private CurrentUser currentUser;

    private final ProductsRepository productsRepository;
    private final MealRepository mealRepository;


    public MealController(ProductsRepository productsRepository, MealRepository mealRepository) {
        this.productsRepository = productsRepository;
        this.mealRepository = mealRepository;

    }

//    public User getLoggedUser(@AuthenticationPrincipal CurrentUser customUser) {
//        return customUser.getUser();
//    }

    @PostMapping("/addProduct")
    public String getProduct(@RequestParam Long productId, @RequestParam Double quantity, HttpSession session) {
        Meal meal;
        if (session.getAttribute("meal") == null) {
            meal = new Meal();
        } else {
            meal = (Meal) session.getAttribute("meal");
        }
        ProductAmount productAmount = new ProductAmount();
        productAmount.setProduct(productsRepository.findOne(productId));
        productAmount.setQuantity(quantity);
        productAmount.setMeal(meal);
        meal.getProductAmounts().add(productAmount);
        session.setAttribute("meal", meal);
        return "redirect:mealComposition";
    }

    @GetMapping("/mealComposition")
    public String showMealComposition(HttpSession session) {
        if (session.getAttribute("meal") == null) {
            return "redirect:/products/list";
        } else {
            return "meal/mealComposition";
        }

    }

    @GetMapping("/delete")
    public String deleteProductFromMeal(@RequestParam String name, HttpSession session) {
        Meal meal = (Meal) session.getAttribute("meal");
        List<ProductAmount> productAmounts = meal.getProductAmounts();
        ProductAmount productAmountToDelete = new ProductAmount();
        for (ProductAmount productAmount : productAmounts) {
            if (name.equals(productAmount.getProduct().getName())) {
                productAmountToDelete = productAmount;
            }
        }
        productAmounts.remove(productAmountToDelete);
        session.setAttribute("meal", meal);
        return "redirect:mealComposition";
    }

    @PostMapping("/saveMeal")
    public String saveMeal(HttpSession session, @RequestParam String mealName, @AuthenticationPrincipal CurrentUser customUser) {
        Meal meal = (Meal) session.getAttribute("meal");
        if (customUser == null) {
            return "/user/login";
        } else {
            meal.setName(mealName);

            meal.setUser(customUser.getUser());
            mealRepository.save(meal);
            session.removeAttribute("meal");
            return "meal/myMeals";
        }
    }

    @GetMapping("/myMeals")
    public String getMyMeals(Model model, @AuthenticationPrincipal CurrentUser customUser){
        if (customUser == null) {
            return "/user/login";
        } else {
            model.addAttribute("meals", mealRepository.findAllByUser(customUser.getUser()));
            return "meal/myMeals";
        }
    }

    @GetMapping("/mealDetails")
    public String getMealSummary(@RequestParam Long id, Model model){
        Meal meal = mealRepository.findOne(id);
        Products mealSummary = new Products();
        mealSummary.setCalories(0d);
        mealSummary.setTransFat(0d);
        mealSummary.setSaturatedFat(0d);
        mealSummary.setCholesterol(0d);
        mealSummary.setCarbohydrate(0d);
        mealSummary.setDietaryFiber(0d);
        mealSummary.setProtein(0d);
        mealSummary.setVitaminA(0d);
        mealSummary.setVitaminC(0d);
        mealSummary.setSodium(0d);
        mealSummary.setCalcium(0d);
        mealSummary.setIron(0d);
        for (ProductAmount productAmount:meal.getProductAmounts()){
            mealSummary.setCalories(mealSummary.getCalories()+productAmount.getProduct().getCalories()*(productAmount.getQuantity()/100));
            mealSummary.setTransFat(mealSummary.getTransFat()+productAmount.getProduct().getTransFat()*(productAmount.getQuantity()/100));
            mealSummary.setSaturatedFat(mealSummary.getSaturatedFat()+productAmount.getProduct().getSaturatedFat()*(productAmount.getQuantity()/100));
            mealSummary.setCholesterol(mealSummary.getCholesterol()+productAmount.getProduct().getCholesterol()*(productAmount.getQuantity()/100));
            mealSummary.setCarbohydrate(mealSummary.getCarbohydrate()+productAmount.getProduct().getCarbohydrate()*(productAmount.getQuantity()/100));
            mealSummary.setDietaryFiber(mealSummary.getDietaryFiber()+productAmount.getProduct().getDietaryFiber()*(productAmount.getQuantity()/100));
            mealSummary.setProtein(mealSummary.getProtein()+productAmount.getProduct().getProtein()*(productAmount.getQuantity()/100));
            mealSummary.setVitaminA(mealSummary.getVitaminA()+productAmount.getProduct().getVitaminA()*(productAmount.getQuantity()/100));
            mealSummary.setVitaminC(mealSummary.getVitaminC()+productAmount.getProduct().getVitaminC()*(productAmount.getQuantity()/100));
            mealSummary.setSodium(mealSummary.getSodium()+productAmount.getProduct().getSodium()*(productAmount.getQuantity()/100));
            mealSummary.setCalcium(mealSummary.getCalcium()+productAmount.getProduct().getCalcium()*(productAmount.getQuantity()/100));
            mealSummary.setIron(mealSummary.getIron()+productAmount.getProduct().getIron()*(productAmount.getQuantity()/100));
        }
        model.addAttribute("product",mealSummary);
        return "meal/mealDetails";
    }
}
