package com.jdemaagd.espressorevelado.taskdetail;

import android.graphics.Bitmap;

import com.jdemaagd.espressorevelado.BasePresenter;
import com.jdemaagd.espressorevelado.BaseView;

/**
 * This specifies the contract between the view and the presenter
 */
public interface TaskDetailContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showMissingTask();

        void hideTitle();

        void showTitle(String title);

        void showImage(Bitmap bitmap);

        void hideImage();

        void hideDescription();

        void showDescription(String description);

        void showCompletionStatus(boolean complete);

        void showEditTask(String taskId);

        void showTaskDeleted();

        void showTaskMarkedComplete();

        void showTaskMarkedActive();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void editTask();

        void deleteTask();

        void completeTask();

        void activateTask();
    }
}