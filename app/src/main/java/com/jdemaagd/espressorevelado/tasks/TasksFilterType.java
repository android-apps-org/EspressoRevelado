package com.jdemaagd.espressorevelado.tasks;

/**
 * Used with the filter spinner in tasks list
 */
public enum TasksFilterType {
    /**
     * Do not filter tasks.
     */
    ALL_TASKS,

    /**
     * Filters only the active (not completed yet) tasks
     */
    ACTIVE_TASKS,

    /**
     * Filters only the completed tasks
     */
    COMPLETED_TASKS
}