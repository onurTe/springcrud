package com.example.UserRecipe.service;

import com.example.UserRecipe.domain.Recipe;
import com.example.UserRecipe.domain.RecipeAddForm;

import java.util.List;

public interface RecipeService {
    void addRecipe(RecipeAddForm form);
    void updateRecipe(RecipeAddForm form);
    List<Recipe> findByName(String name);
    Iterable<Recipe> getRecipes();
    void deleteRecipeById(long id);
    Recipe getRecipeById(long id);
    Recipe assignRecipe(String username, long recipeId);
}
