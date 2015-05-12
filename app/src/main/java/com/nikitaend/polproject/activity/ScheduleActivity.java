package com.nikitaend.polproject.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.nikitaend.polproject.R;
import com.nikitaend.polproject.adapter.TemperatureAdapter;
import com.nikitaend.polproject.adapter.holder.TemperatureHolder;
import com.nikitaend.polproject.dialogs.EditDialog;
import com.nikitaend.polproject.dialogs.TimePickerFragment;
import com.nikitaend.polproject.view.FloatingActionButton;


public class ScheduleActivity extends Activity
        implements EditDialog.OnCompleteListener, TimePickerFragment.OnCompleteListener {

    public static TemperatureAdapter adapterCard;
    DialogFragment editFragment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_schedule);

        FloatingActionButton fabButton = new FloatingActionButton.Builder(this)
                .withDrawable(getDrawable(R.drawable.plus_icon))
                .withButtonColor(Color.WHITE)
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withMargins(0, 0, 4, 4)
                .create();

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFragment = new EditDialog();
                editFragment.show(getFragmentManager(), "editMainDialog");
            }
        });
        
        ListView cardListView = (ListView) findViewById(R.id.card_schedule_listView);
        adapterCard = new TemperatureAdapter(this, R.layout.card_schedule,
                MainActivity.temperatureHolderList);
        cardListView.setAdapter(adapterCard);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onComplete(String startTime, String endTime, Boolean dayNight) {
        
        String dayOrNight = "";
        if (dayNight) {
            dayOrNight = "AM";
        } else {
            dayOrNight = "PM";
        }

        adapterCard.mTemperatureHolderList.add(
                new TemperatureHolder(startTime, endTime, dayOrNight));
        adapterCard.notifyDataSetChanged();
    }

    @Override
    public void onComplete(boolean startEndTime, String timeHolder) {
        if (editFragment.getView() != null) {
            if (startEndTime) {
                TextView startView =
                        (TextView) editFragment.getView().findViewById(R.id.start_time_textView);
                startView.setText(timeHolder);
            } else {
                TextView endView =
                        (TextView) editFragment.getView().findViewById(R.id.end_time_textView);
                endView.setText(timeHolder);
            }
        } else if (adapterCard.dialogFragment.getView() != null) {
            if (startEndTime) {
                TextView startView =
                        (TextView) adapterCard.dialogFragment.getView()
                                .findViewById(R.id.start_time_textView);
                startView.setText(timeHolder);
            } else {
                TextView endView =
                        (TextView) adapterCard.dialogFragment.getView()
                                .findViewById(R.id.end_time_textView);
                endView.setText(timeHolder);
            }
        }
    }
}
