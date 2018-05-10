package com.eliassilva.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.eliassilva.bakingapp.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by Elias on 09/05/2018.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UITest {
    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class, false, true);

    @Test
    public void clickOnRecipeItem() {
        onView(withId(R.id.recipes_list_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(allOf(withId(R.id.ingredient_name_tv), isDisplayed()));
        onView(allOf(withId(R.id.ingredient_quantity_tv), isDisplayed()));
        onView(allOf(withId(R.id.ingredient_measure_tv), isDisplayed()));
        onView(withId(R.id.steps_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(allOf(withId(R.id.step_description_tv), isDisplayed()));
        onView(allOf(withId(R.id.step_video), isDisplayed()));
    }
}
