package com.example.UserRecipe.controller;

import com.example.UserRecipe.domain.RecipeAddForm;
import com.example.UserRecipe.domain.RecipeAssignForm;
import com.example.UserRecipe.service.RecipeService;
import com.example.UserRecipe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RecipeController {
    private final RecipeService recipeService;
    private final UserService userService;
    @Autowired
    public RecipeController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }
    @RequestMapping("/recipes/add")
    public ModelAndView itemAddPage() {
        return new ModelAndView("addRecipe", "recipeForm", new RecipeAddForm());
    }
    @RequestMapping(value = "/recipes", method = RequestMethod.POST)
    public String handleItemAdd(
            @Valid @ModelAttribute("recipeForm")  RecipeAddForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "addRecipe";
        recipeService.addRecipe(form);
        return "redirect:/recipes";
    }
    @RequestMapping("/recipes")
    public ModelAndView getRecipesPage() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("recipes", recipeService.getRecipes());
        model.put("usernames", userService.getUsernames());
        model.put("assignForm", new RecipeAssignForm());
        return new ModelAndView("recipes", model);
    }

    @RequestMapping(value = "/recipes/{id}", method = RequestMethod.DELETE)
    public String handleRecipeDelete(@PathVariable Long id) {
        recipeService.deleteRecipeById(id);
        return "redirect:/recipes";
    }
    @RequestMapping(value = "/recipes/{id}", method = RequestMethod.GET)
    public ModelAndView getRecipesPage(@PathVariable Long id) {

        return new ModelAndView("recipePage" ,"recipes", recipeService.getRecipeById(id));
    }
    @RequestMapping(value = "/recipes/{id}", method = RequestMethod.PUT)
    public String handleItemAssign(@ModelAttribute("user") RecipeAssignForm form, @PathVariable("id") long id) {
        recipeService.assignRecipe(form.getUsername(), id);
        return "redirect:/recipes";
    }
}