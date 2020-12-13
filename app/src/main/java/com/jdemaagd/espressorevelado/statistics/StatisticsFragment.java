package com.jdemaagd.espressorevelado.statistics;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jdemaagd.espressorevelado.R;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Main UI for the statistics screen
 */
public class StatisticsFragment extends Fragment implements StatisticsContract.View {

    private TextView mStatisticsTV;
    private TextView seekBarTextView;
    private SeekBar seekBar;
    private String progressText;

    private StatisticsContract.Presenter mPresenter;

    public static StatisticsFragment newInstance() {
        return new StatisticsFragment();
    }

    @Override
    public void setPresenter(@NonNull StatisticsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.statistics_frag, container, false);
        mStatisticsTV = root.findViewById(R.id.statistics);
        seekBarTextView = root.findViewById(R.id.seekBarTextView);
        seekBar = root.findViewById(R.id.simpleSeekBar);
        progressText = getString(R.string.statisticsProgress);
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                                                  boolean fromUser) {
                        seekBarTextView.setText(String.format(progressText + " %d", seekBar.getProgress()));
                    }
                }
        );

        seekBar.setMax(50);
        seekBar.setMin(0);
        seekBar.setProgress(0);

        seekBarTextView.setText(String.format(progressText + " %d", 0));
        showDialogWithDelay();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setProgressIndicator(boolean active) {
        if (active) {
            mStatisticsTV.setText(getString(R.string.loading));
        } else {
            mStatisticsTV.setText("");
        }
    }

    @Override
    public void showStatistics(int numberOfIncompleteTasks, int numberOfCompletedTasks) {
        if (numberOfCompletedTasks == 0 && numberOfIncompleteTasks == 0) {
            mStatisticsTV.setText(getResources().getString(R.string.statistics_no_tasks));
        } else {
            String displayString = getResources().getString(R.string.statistics_active_tasks) + " "
                    + numberOfIncompleteTasks + "\n" + getResources().getString(
                    R.string.statistics_completed_tasks) + " " + numberOfCompletedTasks;
            mStatisticsTV.setText(displayString);
        }
    }

    @Override
    public void showLoadingStatisticsError() {
        mStatisticsTV.setText(getResources().getString(R.string.statistics_error));
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    private void showDialogWithDelay(){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Alert dialog")
                .setMessage("Dismiss me silently.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing - test sample
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing - test sample
                    }
                })
                .show();
    }
}