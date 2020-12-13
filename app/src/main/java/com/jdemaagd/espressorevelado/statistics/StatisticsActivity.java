package com.jdemaagd.espressorevelado.statistics;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.android.architecture.blueprints.todoapp.Injection;
import com.google.android.material.navigation.NavigationView;
import com.jdemaagd.espressorevelado.R;
import com.jdemaagd.espressorevelado.settings.SettingsActivity;
import com.jdemaagd.espressorevelado.util.ActivityUtils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

/**
 * Show statistics for tasks
 */
public class StatisticsActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.statistics_act);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.statistics_title);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = findViewById(R.id.nav_view);

        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        StatisticsFragment statisticsFragment = (StatisticsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (statisticsFragment == null) {
            statisticsFragment = StatisticsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    statisticsFragment, R.id.contentFrame);
        }

        new StatisticsPresenter(
                Injection.provideTasksRepository(getApplicationContext()), statisticsFragment);
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
                            NavUtils.navigateUpFromSameTask(StatisticsActivity.this);
                            break;
                        case R.id.statistics_navigation_menu_item:
                            // Do nothing, we're already on that screen
                            break;
                        case R.id.settings_navigation_menu_item:
                            Intent aboutIntent =
                                    new Intent(StatisticsActivity.this, SettingsActivity.class);
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
}