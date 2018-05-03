package com.eliassilva.bakingapp.utilities;

import android.net.Uri;
import android.util.Log;

import com.eliassilva.bakingapp.Ingredient;
import com.eliassilva.bakingapp.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Elias on 24/04/2018.
 */
public class NetworkUtils {
    private static final String RECIPES_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private static String getResponse() {
        HttpURLConnection urlConnection = null;
        URL url = null;
        try {
            url = new URL(RECIPES_URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : null;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Cannot create url: " + e);
        } catch (IOException e) {
            throw new RuntimeException("Error making HTTP request: " + e);
        } finally {
            urlConnection.disconnect();
        }
    }

    public static List<Recipe> extractRecipesFromJson() {
        final String RECIPE_NAME = "name";
        final String RECIPE_STEPS = "steps";
        final String RECIPE_INGREDIENTS = "ingredients";
        final String INGREDIENT_NAME = "ingredient";
        final String INGREDIENT_MEASURE = "measure";
        final String INGREDIENT_QUANTITY = "quantity";
        List<Recipe> recipesList = new ArrayList<>();

        String response = getResponse();
        try {
            JSONArray recipes = new JSONArray(response);
            for (int i = 0; i < recipes.length(); i++) {
                JSONObject recipe = recipes.getJSONObject(i);
                String name = recipe.optString(RECIPE_NAME);
                JSONArray steps = recipe.getJSONArray(RECIPE_STEPS);
                JSONArray ingredients = recipe.getJSONArray(RECIPE_INGREDIENTS);
                List<Ingredient> ingredientsList = new ArrayList<>();
                for (int j = 0; j < ingredients.length(); j++) {
                    JSONObject ingredient = ingredients.getJSONObject(j);
                    int ingredientQuantity = ingredient.optInt(INGREDIENT_QUANTITY);
                    String ingredientMeasure = ingredient.optString(INGREDIENT_MEASURE);
                    String ingredientName = ingredient.optString(INGREDIENT_NAME);
                    ingredientsList.add(new Ingredient(ingredientName, ingredientMeasure, ingredientQuantity));
                }
                int numberOfSteps = steps.length();
                recipesList.add(new Recipe(name, numberOfSteps, ingredientsList));
            }
        } catch (JSONException e) {
            throw new RuntimeException("Error trying to create JSON object: " + e);
        }
        return recipesList;
    }
}
