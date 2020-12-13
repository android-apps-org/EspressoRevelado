package com.jdemaagd.espressorevelado.taskdetail;

import android.os.Bundle;

import com.example.android.architecture.blueprints.todoapp.Injection;
import com.jdemaagd.espressorevelado.R;
import com.jdemaagd.espressorevelado.util.ActivityUtils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Displays task details screen
 */
public class TaskDetailActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_ID = "TASK_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.taskdetail_act);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        String taskId = getIntent().getStringExtra(EXTRA_TASK_ID);

        TaskDetailFragment taskDetailFragment = (TaskDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (taskDetailFragment == null) {
            taskDetailFragment = TaskDetailFragment.newInstance(taskId);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    taskDetailFragment, R.id.contentFrame);
        }

        // Create the presenter
        new TaskDetailPresenter(
                taskId,
                Injection.provideTasksRepository(getApplicationContext()),
                taskDetailFragment);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}