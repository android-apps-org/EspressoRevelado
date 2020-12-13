package com.example.android.architecture.blueprints.todoapp;

import android.content.Context;

import com.example.android.architecture.blueprints.todoapp.data.FakeTasksRemoteDataSource;
import com.jdemaagd.espressorevelado.data.source.TasksDataSource;
import com.jdemaagd.espressorevelado.data.source.TasksRepository;
import com.jdemaagd.espressorevelado.data.source.local.TasksLocalDataSource;
import com.jdemaagd.espressorevelado.data.source.local.ToDoDatabase;
import com.jdemaagd.espressorevelado.util.AppExecutors;

import androidx.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Enables injection of mock implementations for {@link TasksDataSource} at compile time
 * Useful for testing since it allows fake instance of class to isolate dependencies
 * and run a test hermetically
 */
public class Injection {

    public static TasksRepository provideTasksRepository(@NonNull Context context) {
        checkNotNull(context);
        ToDoDatabase database = ToDoDatabase.getInstance(context);
        return TasksRepository.getInstance(FakeTasksRemoteDataSource.getInstance(),
                TasksLocalDataSource.getInstance(new AppExecutors(),
                        database.taskDao()));
    }
}