package com.biastomo.crew.kuyliah;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class EditTugas extends AppCompatActivity {
    private TextView mTimeDisplay;
    private EditText mPickTime;
    private int mHour;
    private int mMinute;
    static final int TIME_DIALOG_ID = 0;
    //tgl
    private TextView mDateDisplay;
    private EditText mPickDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID = 1;

    //database
    protected Cursor cursor;
    DataHelper dbHelper;
    Button ton1, ton2;
    EditText text1, text2, text3, text4;

    TimePicker myTimePicker;
    Button buttonstartSetDialog;
    TextView textAlarmPrompt;

    TimePickerDialog timePickerDialog;
    final static int RQS_1 = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_tugas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // capture our View elements
        mTimeDisplay = (TextView) findViewById(R.id.editwaktu);
        mPickTime = (EditText) findViewById(R.id.editwaktu);
// add a click listener to the button
        mPickTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openTimePickerDialog(false);
                //showDialog(TIME_DIALOG_ID);
            }
        });
// get the current time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

// display the current date
        updateDisplay();
//tgl

        // capture our View elements
        mDateDisplay = (TextView) findViewById(R.id.edittgltugas);
        mPickDate = (EditText) findViewById(R.id.edittgltugas);
// add a click listener to the button
        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
// get the current date
        //final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
// display the current date (this method is below)
        updateDisplay2();
//database

        dbHelper = new DataHelper(this);
        text1 = (EditText) findViewById(R.id.editnamatugas);
        text2 = (EditText) findViewById(R.id.edittgltugas);
        text3 = (EditText) findViewById(R.id.editwaktu);
        text4 = (EditText) findViewById(R.id.editdesktugas);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tugas WHERE namatugas = '" +
                getIntent().getStringExtra("namatugas") + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            text1.setText(cursor.getString(0).toString());
            text2.setText(cursor.getString(1).toString());
            text3.setText(cursor.getString(2).toString());
            text4.setText(cursor.getString(3).toString());

        }

        ton1 = (Button) findViewById(R.id.savetugas);
        ton2 = (Button) findViewById(R.id.batal);

        ton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // TODO Auto-generated method stub
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                if (text1.getText().toString().trim().equals("")) {
                    text1.setError("Isi Nama Tugasmu");

                } else {
                    db.execSQL("REPLACE into tugas (namatugas, tgl, waktu, deskripsi) values('" +
                            text1.getText().toString() + "','" +
                            text2.getText().toString() + "','" +
                            text3.getText().toString() + "','" +
                            text4.getText().toString() + "')");
                    Toast.makeText(getApplicationContext(), "Berhasil Simpan", Toast.LENGTH_LONG).show();
                    Tugas.ma.RefreshList();
                    finish();
                }
            }
        });
        ton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }
//alarm
        private void openTimePickerDialog(boolean is24r) {
            Calendar calendar = Calendar.getInstance();

            timePickerDialog = new TimePickerDialog(EditTugas.this,
                    mTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), true);
            timePickerDialog.setTitle("Atur Waktu");

            timePickerDialog.show();

        }



    private void updateDisplay() {
        mTimeDisplay.setText(
                new StringBuilder()
                        .append(pad(mHour)).append(":")
                        .append(pad(mMinute)));
    }
    private void updateDisplay2() {
        mDateDisplay.setText(
                new StringBuilder()
// Month is 0 based so add 1

                        .append(mDay).append("-")
                        .append(mMonth + 1).append("-")
                        .append(mYear).append(" "));
    }
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
    // the callback received when the user "sets" the time in the dialog
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int
                        minute) {
                    mHour = hourOfDay;
                    mMinute = minute;
                    updateDisplay();
                    int day = mDay;
                    int month = mMonth;
                    int year = mYear;
                    Calendar calNow = Calendar.getInstance();
                    Calendar calSet = (Calendar) calNow.clone();
                    calSet.set(year, month, day);
                    calSet.set(Calendar.HOUR_OF_DAY, mHour);
                    calSet.set(Calendar.MINUTE, mMinute);
                    calSet.set(Calendar.SECOND, 0);
                    calSet.set(Calendar.MILLISECOND, 0);

                    setAlarm(calSet);
                }
            };


    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth)
                {
                    mDay = dayOfMonth;
                    mMonth = monthOfYear;
                    mYear = year;
                    updateDisplay2();

                }

            };

    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id) {
            case 0: TIME_DIALOG_ID:
            return new TimePickerDialog(this,
                    mTimeSetListener, mHour, mMinute, false);
            case 1: DATE_DIALOG_ID:
            return new DatePickerDialog(this,
                    mDateSetListener,
                    mYear, mMonth, mDay);
        }
        return null;
    }


    private void setAlarm(Calendar targetCal) {

        Intent intent = new Intent(getBaseContext(), TugasReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                pendingIntent);


    }


    //database
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}


