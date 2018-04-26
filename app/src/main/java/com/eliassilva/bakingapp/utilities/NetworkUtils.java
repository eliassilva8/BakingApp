package com.eliassilva.bakingapp.utilities;

import android.net.Uri;
import android.util.Log;

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
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.next());
            }
            return response.toString();
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
        List<Recipe> recipesList = new ArrayList<>();

        String response = getResponse();
        Log.d("Response: ", response);
        try {
            JSONArray recipes = new JSONArray(response);
            for (int i = 0; i < recipes.length(); i++) {
                JSONObject recipe = recipes.getJSONObject(i);
                String name = recipe.optString(RECIPE_NAME);
                JSONArray steps = recipe.getJSONArray(RECIPE_STEPS);
                int numberOfSteps = steps.length();
                recipesList.add(new Recipe(name, numberOfSteps));
            }
        } catch (JSONException e) {
            throw new RuntimeException("Error trying to create JSON object: " + e);
        }
        return recipesList;
    }
}
