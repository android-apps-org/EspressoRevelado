package com.jdemaagd.espressorevelado.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.android.architecture.blueprints.todoapp.Injection;
import com.google.android.material.navigation.NavigationView;

import com.jdemaagd.espressorevelado.R;
import com.jdemaagd.espressorevelado.settings.SettingsActivity;
import com.jdemaagd.espressorevelado.statistics.StatisticsActivity;
import com.jdemaagd.espressorevelado.util.ActivityUtils;
import com.jdemaagd.espressorevelado.util.EspressoIdlingResource;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.test.espresso.IdlingResource;

public class TasksActivity extends AppCompatActivity {

    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    private DrawerLayout mDrawerLayout;

    private TasksPresenter mTasksPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_act);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = findViewById(R.id.nav_view);

        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        TasksFragment tasksFragment =
                (TasksFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (tasksFragment == null) {
            tasksFragment = TasksFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), tasksFragment, R.id.contentFrame);
        }

        mTasksPresenter = new TasksPresenter(
                Injection.provideTasksRepository(getApplicationContext()), tasksFragment);

        if (savedInstanceState != null) {
            TasksFilterType currentFiltering =
                    (TasksFilterType) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
            mTasksPresenter.setFiltering(currentFiltering);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CURRENT_FILTERING_KEY, mTasksPresenter.getFiltering());

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // open navigation drawer when home icon is selected from toolbar
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.list_navigation_menu_item:
                            // Do nothing, we're already on that screen
                            break;
                        case R.id.statistics_navigation_menu_item:
                            Intent statisticsIntent =
                                    new Intent(TasksActivity.this, StatisticsActivity.class);
                            startActivity(statisticsIntent);
                            break;
                        case R.id.settings_navigation_menu_item:
                            Intent aboutIntent =
                                    new Intent(TasksActivity.this, SettingsActivity.class);
                            startActivity(aboutIntent);
                        default:
                            break;
                    }

                    // close navigation drawer when an item is selected
                    menuItem.setChecked(true);
                    mDrawerLayout.closeDrawers();

                    return true;
                });
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}