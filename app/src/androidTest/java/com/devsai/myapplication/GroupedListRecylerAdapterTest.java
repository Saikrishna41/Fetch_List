package com.devsai.myapplication;

import androidx.test.core.app.ActivityScenario;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4ClassRunner.class)
public class GroupedListRecylerAdapterTest {


    @Test
    public void test_visibility_listIdHeader() {
        ActivityScenario activityScenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.single_listId)).check(matches(isDisplayed()));

    }

    @Test
    public void test_visibility_itemsInList() {
        ActivityScenario activityScenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.general_Id)).check(matches(isDisplayed()));
        onView(withId(R.id.general_listId)).check(matches(isDisplayed()));

    }




}