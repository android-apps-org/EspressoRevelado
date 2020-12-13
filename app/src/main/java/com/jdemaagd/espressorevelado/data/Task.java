package com.jdemaagd.espressorevelado.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Immutable model class for a Task
 */
@Entity(tableName = "tasks")
public final class Task {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "entryid")
    private final String mId;

    @Nullable
    @ColumnInfo(name = "title")
    private final String mTitle;

    @Nullable
    @ColumnInfo(name = "description")
    private final String mDescription;

    @Nullable
    @ColumnInfo(name = "image")
    private final byte[] mImage;

    @ColumnInfo(name = "completed")
    private final boolean mCompleted;

    /**
     * Use this constructor to create a new active Task
     *
     * @param title       title of task
     * @param description description of task
     */
    @Ignore
    public Task(@Nullable String title, @Nullable String description, @Nullable byte[] image) {
        this(title, description, UUID.randomUUID().toString(), image, false);
    }

    /**
     * Create an active Task if Task already has an id (copy of another Task)
     *
     * @param title       title of the task
     * @param description description of the task
     * @param id          id of the task
     */
    @Ignore
    public Task(@Nullable String title, @Nullable String description, @NonNull String id, @Nullable byte[] image) {
        this(title, description, id, image, false);
    }

    /**
     * Create a new completed Task
     *
     * @param title       title of the task
     * @param description description of the task
     * @param completed   true if the task is completed, false if it's active
     */
    @Ignore
    public Task(@Nullable String title, @Nullable String description, @Nullable byte[] image, boolean completed) {
        this(title, description, UUID.randomUUID().toString(), image, completed);
    }

    /**
     * Specify a completed Task if Task already has an id (copy of another Task)
     *
     * @param title       title of the task
     * @param description description of the task
     * @param id          id of the task
     * @param completed   true if the task is completed, false if it is active
     */
    public Task(@Nullable String title, @Nullable String description,
                @NonNull String id, @Nullable byte[] image, boolean completed) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mCompleted = completed;
        mImage = image;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getTitleForList() {
        if (!Strings.isNullOrEmpty(mTitle)) {
            return mTitle;
        } else {
            return mDescription;
        }
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    @Nullable
    public Bitmap getImageBitmap() {
        if (mImage != null) {
            //turn byte[] to bitmap
            return BitmapFactory.decodeByteArray(mImage, 0, mImage.length);
        }
        return null;
    }

    @Nullable
    public byte[] getImage() {
        if (mImage != null) {
            return mImage;
        }
        return null;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public boolean isActive() {
        return !mCompleted;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(mTitle) &&
               Strings.isNullOrEmpty(mDescription);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equal(mId, task.mId) &&
               Objects.equal(mTitle, task.mTitle) &&
               Objects.equal(mDescription, task.mDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mTitle, mDescription);
    }

    @Override
    public String toString() {
        return "Task with title " + mTitle;
    }
}