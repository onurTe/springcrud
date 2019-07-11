package com.example.UserRecipe.service;

import com.example.UserRecipe.domain.Recipe;
import com.example.UserRecipe.domain.RecipeAddForm;

public interface RecipeService {
    void addRecipe(RecipeAddForm form);
    void updateRecipe(RecipeAddForm form);
    Iterable<Recipe> getRecipes();
    void deleteRecipeById(long id);
    Recipe getRecipeById(long id);
    Recipe assignRecipe(String username, long recipeId);
    Iterable<Recipe> findByNames(String name);
}
