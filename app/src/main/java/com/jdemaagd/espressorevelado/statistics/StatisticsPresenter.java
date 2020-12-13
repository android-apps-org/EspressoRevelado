package com.jdemaagd.espressorevelado.statistics;

import androidx.annotation.NonNull;

import com.jdemaagd.espressorevelado.data.Task;
import com.jdemaagd.espressorevelado.data.source.TasksDataSource;
import com.jdemaagd.espressorevelado.data.source.TasksRepository;
import com.jdemaagd.espressorevelado.util.EspressoIdlingResource;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link StatisticsFragment})
 * retrieves data and updates UI as required
 */
public class StatisticsPresenter implements StatisticsContract.Presenter {

    private final TasksRepository mTasksRepository;

    private final StatisticsContract.View mStatisticsView;

    public StatisticsPresenter(@NonNull TasksRepository tasksRepository,
                               @NonNull StatisticsContract.View statisticsView) {
        mTasksRepository = checkNotNull(tasksRepository, "tasksRepository cannot be null");
        mStatisticsView = checkNotNull(statisticsView, "StatisticsView cannot be null!");

        mStatisticsView.setPresenter(this);
    }

    @Override
    public void start() {
        loadStatistics();
    }

    private void loadStatistics() {
        mStatisticsView.setProgressIndicator(true);

        // network request might be handled in a different thread
        // make sure Espresso knows that app is busy until response is handled
        EspressoIdlingResource.increment();     // app is busy until further notice

        mTasksRepository.getTasks(new TasksDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                int activeTasks = 0;
                int completedTasks = 0;

                // callback may be called twice, once for cache and once for loading
                // data from server API, so check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement();     // set app as idle
                }

                // calculate number of active and completed tasks
                for (Task task : tasks) {
                    if (task.isCompleted()) {
                        completedTasks += 1;
                    } else {
                        activeTasks += 1;
                    }
                }

                // view may not be able to handle UI updates anymore
                if (!mStatisticsView.isActive()) {
                    return;
                }
                mStatisticsView.setProgressIndicator(false);

                mStatisticsView.showStatistics(activeTasks, completedTasks);
            }

            @Override
            public void onDataNotAvailable() {
                // view may not be able to handle UI updates anymore
                if (!mStatisticsView.isActive()) {
                    return;
                }
                mStatisticsView.showLoadingStatisticsError();
            }
        });
    }
}