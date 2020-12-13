package com.jdemaagd.espressorevelado.addedittask;

import android.graphics.Bitmap;

import com.jdemaagd.espressorevelado.BasePresenter;
import com.jdemaagd.espressorevelado.BaseView;

/**
 * This specifies the contract between the view and the presenter
 */
public interface AddEditTaskContract {

    interface View extends BaseView<Presenter> {

        void showEmptyTaskError();

        void showTasksList();

        void setTitle(String title);

        void setDescription(String description);

        void setImage(Bitmap bitmap);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void saveTask(String title, String description, byte[] image);

        void populateTask();

        boolean isDataMissing();
    }
}