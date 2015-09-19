package com.basilarray.futurebounce;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        // prepare intent which is triggered if the
        //Intent intent = new Intent(this, MyService.class);
        //startService(intent);
/*
// notification is selected
Intent intent = new Intent(this, MainActivity.class); // Intent intent = new Intent(this, NotificationReceiver.class);
// use System.currentTimeMillis() to have a unique ID for the pending intent
PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
Notification n  = new Notification.Builder(this)
        .setContentTitle("Kitty fountain cleaning")
        .setContentText("Replaced the kitty water and will need to do so again")
        .setSmallIcon(R.drawable.ic_stat_name)
        .setContentIntent(pIntent)
        .setAutoCancel(true)
        .addAction(R.drawable.ic_stat_name, "Later", pIntent)
        .addAction(0, "Done", pIntent)
        .addAction(0, "More", pIntent).build();
NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
notificationManager.notify(0, n);
*/
        //Remind("TestTitle", "TestText");

        Thread threadInit = new Thread(initializeScreen);
        threadInit.start();
    }

    public static class Word {
        public String word;
        public int value = 0;
        public boolean fullyMade = true;

        public Word(String inWord) {
            word = inWord;
        }
    }

    public ArrayList<Word> lWords = new ArrayList<Word>();

    Runnable initializeScreen = new Runnable() {
        @Override
        public void run() {
            //ListView mListView = (ListView) findViewById(R.id.sampleListView);
            //TextView mTextView = new TextView(mContext);
            //TextView mTextView = (TextView) findViewById(R.id.section_label);
            //mTextView.setText("Newly Added");
            //mListView.addView(mTextView);
            String[] allwords = ("aa,aah,aahed,aahing,aahs").split(",");
            for (Word w : lWords)
                lWords.add(w);
        }
    };

    public void FindMatches(View v) {
        EditText mEditText = (EditText) findViewById(R.id.editText);
        if (mEditText.getText().length() < 2)
            mEditText.setText("aha");
        String letters = mEditText.getText().toString().toLowerCase();

        long totTime = System.currentTimeMillis();
        //int needMark = 1; // letters.length();


        Log.d("tmp", "Read in " + (System.currentTimeMillis() - totTime));
        Log.d("tmp", "Count " + lWords.size());
        totTime = System.currentTimeMillis();

        for (Word w : lWords) {
            String tmpWord = w.word;
            String tmpLetters = letters;
            int mostMark = tmpLetters.indexOf(" ");
            for (int i = 0, n = tmpWord.length(); i < n; i++) {
                int len = letters.length();
                int idx = tmpLetters.indexOf(tmpWord.charAt(i));
                if (idx == -1) {
                    w.value -= 3;
                    w.fullyMade = false;
                } else {
                    w.value += letters.length() - idx;
                    tmpLetters = tmpLetters.substring(0, idx) + " " + tmpLetters.substring(idx + 1, len);
                }
            }
            Log.d("tmp", tmpWord + "|" + tmpLetters + "|" + w.value + "," + w.fullyMade);
            if (w.word.equals("haughtinesses"))
                break;
            //if (w.fullyMade) break;
        }

        Log.d("tmp", "Read in " + (System.currentTimeMillis() - totTime));
        TextView mTextResults1 = (TextView) findViewById(R.id.textResults1);
        mTextResults1.setText("Newly Added");

    }

    public void Remind(String title, String text) {
        Log.d("tmp", "Alarm Created Begin");
        //Toast.makeText(this, "Alarm Created Begin", Toast.LENGTH_LONG).show();
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        alarmIntent.putExtra("title", title);
        alarmIntent.putExtra("text", text);

        //PendingIntent pendingIntent = PendingIntent.GetBroadcast(Forms.Context, 0, alarmIntent, PendingIntentFlags.UpdateCurrent);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //AlarmManager alarmManager = (AlarmManager) Forms.Context.GetSystemService(Context.AlarmService);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + (10 * 1000), pendingIntent); // 30 seconds
//        try {
//            Log.d("tmp", "BEFORE pendingIntent");
//            pendingIntent.send();
//            Log.d("tmp", "AFTER pendingIntent");
//        }catch (Exception e){
//            Log.d("tmp", "Alarm exception:"+e.getMessage());
//        }

        Log.d("tmp", "Alarm Created End");
        //Toast.makeText(this, "Alarm Created End", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public boolean onTouchEvent(MotionEvent event) {

        // get pointer index from the event object
        int pointerIndex = event.getActionIndex();

        // get pointer ID
        int pointerId = event.getPointerId(pointerIndex);

        // get masked (not specific to a pointer) action
        int maskedAction = event.getActionMasked();

        switch (maskedAction) {

            case MotionEvent.ACTION_DOWN: {
                Log.d("tmp", "Hello World");
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN: {
                Log.d("tmp", "Hello World");
                break;
            }
            case MotionEvent.ACTION_MOVE: { // a pointer was moved
                // TODO use data
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL: {
                // TODO use data
                break;
            }
        }

        //invalidate();

        return true;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
