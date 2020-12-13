package com.jdemaagd.espressorevelado;

import android.content.Context;

import androidx.annotation.NonNull;

import com.jdemaagd.espressorevelado.data.FakeTasksRemoteDataSource;
import com.jdemaagd.espressorevelado.data.source.TasksRepository;
import com.jdemaagd.espressorevelado.data.source.local.TasksLocalDataSource;
import com.jdemaagd.espressorevelado.data.source.local.ToDoDatabase;
import com.jdemaagd.espressorevelado.util.AppExecutors;

import static com.google.common.base.Preconditions.checkNotNull;

public class Injection {

    public static TasksRepository provideTasksRepository(@NonNull Context context) {
        checkNotNull(context);
        ToDoDatabase database = ToDoDatabase.getInstance(context);

        return TasksRepository.getInstance(FakeTasksRemoteDataSource.getInstance(),
                TasksLocalDataSource.getInstance(new AppExecutors(),
                        database.taskDao()));
    }
}