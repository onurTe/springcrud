package com.example.UserRecipe.service;

import com.example.UserRecipe.domain.Recipe;
import com.example.UserRecipe.domain.RecipeAddForm;
import com.example.UserRecipe.domain.User;
import com.example.UserRecipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService{
    private final RecipeRepository recipeRepository;
    private final UserService userService;
    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository,UserService userService) {
        this.recipeRepository = recipeRepository;
        this.userService = userService;
    }
    public void addRecipe(RecipeAddForm form) {
        Recipe recipe = new Recipe(form.getRecipeName(),form.getRecipeDesc(),form.getRecipeTag(),form.getRecipeImage());
        recipeRepository.save(recipe);
        System.out.println(recipeRepository.findById(recipe.getId()));
    }

    @Override
    public void updateRecipe(RecipeAddForm form) {
        Recipe recipe = new Recipe(form.getRecipeName(),form.getRecipeDesc(),form.getRecipeTag(),form.getRecipeImage());
        boolean flag = false;
        long flagID = 0;
        for(Recipe rec : recipeRepository.findAll()){
            if(rec.getName().equalsIgnoreCase(recipe.getName()) &&
                    rec.getDescription().equalsIgnoreCase(recipe.getDescription())&&
                    rec.getTag().equalsIgnoreCase(recipe.getTag()))
                    flagID = rec.getId();
                break;
        }
        recipeRepository.deleteById(flagID);
        recipeRepository.save(recipe);
    }

    @Override
    public List<Recipe> findByName(String name) {
        List<Recipe> list = new ArrayList<>();
        for(Recipe rc : recipeRepository.findAll()){
            if(rc.getName().equalsIgnoreCase(name))
                list.add(rc);
        }
        return list;
    }


    @Override
    public Iterable<Recipe> getRecipes() {
        return recipeRepository.findAll();
    }
    @Override
    public void deleteRecipeById(long id) {
        recipeRepository.deleteById(id);
    }
    @Override
    public Recipe getRecipeById(long id) {
        return recipeRepository.findById(id).get();
    }
    @Override
    public Recipe assignRecipe(String username, long recipeId) {
        User user = userService.getUserByUsername(username);
        Recipe recipe = getRecipeById(recipeId);
        Set<Recipe> recipeList = user.getRecipes();
        recipeList.add(recipe);
        user.setRecipes(recipeList);
        recipe.setUser(user);
        return recipeRepository.save(recipe);
    }


}
