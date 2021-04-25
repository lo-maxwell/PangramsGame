package com.example.firstapp.ui.user_stats;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.firstapp.R;
import com.example.firstapp.ui.home.HomeFragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class UserStatsFragment extends Fragment {
    private static ArrayList<String> userStatsFileStrings = new ArrayList<String>();
    private TextView mainTextView;

    private UserStatsViewModel userStatsViewModel;
    private int counter;
    private static Handler screenUpdater;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        userStatsViewModel =
                ViewModelProviders.of(this).get(UserStatsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user_stats, container, false);
        final TextView textView = root.findViewById(R.id.text_user_stats);
        userStatsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //Button_Delete_Save
        Button button = (Button) root.findViewById(R.id.Button_Delete_Save_User_Stats);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Context context = UserStatsFragment.this.getActivity();

                String filename = "saveFile.txt";
                File file = new File(context.getFilesDir(), filename);
                System.out.println("Clearing save file");
                if (file.exists())
                    System.out.println("File deleted: " + file.delete());
                else
                    System.out.println("Could not delete file: File does not exist");

                filename = "preLoadFile.txt";
                file = new File(context.getFilesDir(), filename);
                System.out.println("Clearing preload file");
                if (file.exists())
                    System.out.println("File deleted: " + file.delete());
                else
                    System.out.println("Could not delete file: File does not exist");

                filename = "userStatsFile.txt";
                file = new File(context.getFilesDir(), filename);
                System.out.println("Clearing userStats file");
                if (file.exists())
                    System.out.println("File deleted: " + file.delete());
                else
                    System.out.println("Could not delete file: File does not exist");

                filename = "achievementFile.txt";
                file = new File(context.getFilesDir(), filename);
                System.out.println("Clearing achievement file");
                if (file.exists())
                    System.out.println("File deleted: " + file.delete());
                else
                    System.out.println("Could not delete file: File does not exist");

                TextView messageBox = getView().findViewById(R.id.Display_Box_User_Stats);
                messageBox.setTextColor(ContextCompat.getColor(context, R.color.red));
                messageBox.setText("Deleted save file!");
                //Restarts playtimeHandler in home fragment so that time resets to 0
                if (HomeFragment.playtimeHandler != null) {
                    HomeFragment.playtimeHandler.removeCallbacksAndMessages(null);
                    HomeFragment.playtimeHandler = null;
                }
                mainTextView.setText("Total words submitted: 0\nLongest word submitted: N/A\nTotal pangrams: 0\nLongest pangram: N/A\nTotal points: 0\nBest score on a single puzzle: 0\nTotal time played: 0");
                counter = 0;
                readSaveFile(UserStatsFragment.this.getActivity());
            }
        });


        readSaveFile(UserStatsFragment.this.getActivity());
        //Update text for main achievement list
        mainTextView = (TextView) root.findViewById(R.id.Main_TextView_User_Stats);
        String tempString = "";
        tempString += "Total words submitted: " + userStatsFileStrings.get(0) + "\n";
        tempString += "Longest word submitted: " + userStatsFileStrings.get(1) + "\n";
        tempString += "Total pangrams: " + userStatsFileStrings.get(4) + "\n";
        tempString += "Longest pangram: " + userStatsFileStrings.get(5) + "\n";
        tempString += "Total points: " + userStatsFileStrings.get(2) + "\n";
        tempString += "Best score on a single puzzle: " + userStatsFileStrings.get(3) + "\n";
        tempString += "Total time played: " + convertStringToTime(userStatsFileStrings.get(6)) + "\n";
        mainTextView.setText(tempString);
        counter = 0;
        updateScreen();
        return root;
    }

    private void readSaveFile (Context context) {
        BufferedReader reader = null;
        try {
            System.out.println("USF: Opening userStatsFile");
            File file = new File(context.getFilesDir(), "userStatsFile.txt");
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("USF: Created new userStatsFile (This should never occur besides the first run after a new patch)");
                System.out.println("USF: Filling with default values...");
                //Words submitted (0), Longest word (none), Total points (0), Best score (0), Total Pangrams (0), Longest Pangram (none), Time Played (0)
                String tempString = "0\nN/A\n0\n0\n0\nN/A\n0";
                writeToFile(tempString, context, "userStatsFile.txt");
            }
            FileInputStream fis = context.openFileInput("userStatsFile.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            reader = new BufferedReader(inputStreamReader);
            System.out.println("USF: Opened userStatsFile");
            String mLine = "";
            userStatsFileStrings.clear();
            while ((mLine = reader.readLine()) != null) {
                //process line
                String data = mLine.toUpperCase();
                System.out.println("USF: UserStatsFile: " + data);
                userStatsFileStrings.add(data);
            }
            for (int i = 0; i < 7; i++) {
                try {
                    System.out.println(userStatsFileStrings.get(i));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("USF: Missing data! ");
                    System.out.println("USF: Filling in missing data.");
                    if (i == 1 || i == 5)
                        userStatsFileStrings.add(i, "N/A");
                    else
                        userStatsFileStrings.add(i, "0");
                }
            }
            writeSave(userStatsFileStrings, context, "userStatsFile.txt");
            reader.close();
        } catch (IOException e) {
            //log the exception
            System.out.println("USF: An exception occurred while reading userStatsFile");
        }
    }

    private void writeToFile(String data, Context context, String filename) {
        File file = new File(context.getFilesDir(), filename);
        System.out.println("Writing to file");
        System.out.println(file);
        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Created new file");
            }
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            System.out.println(fos);
            OutputStreamWriter bw = new OutputStreamWriter(fos);
            bw.append(data);
            bw.flush();
            bw.close();
            System.out.println("Wrote to file");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Wrote " + data);
    }

    private void appendToFile(String data, Context context, String filename) {
        File file = new File(context.getFilesDir(), filename);
        System.out.println(file);
        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Created new file");
            }
            //FileOutputStream fos = context.openFileOutput(filename, Context.MODE_APPEND);
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_APPEND);
            OutputStreamWriter bw = new OutputStreamWriter(fos);
            bw.append(data);
            bw.flush();
            bw.close();
            System.out.println("Appended to file: " + fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeSave(ArrayList<String> stringArrayList, Context context, String filename) {
        try {
            writeToFile("", context, filename);
            String tempString = "";
            for (int i = 0; i < stringArrayList.size(); i++) {
                tempString += stringArrayList.get(i) + "\n";
            }
            appendToFile(tempString, context, filename);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error while saving userStatsFileStrings.");
        }
    }

    private String convertStringToTime (String seconds) {
        int numSeconds = Integer.parseInt(seconds);
        String returnString = "";
        int inputNum = numSeconds/3600;
        returnString += inputNum;
        returnString += ":";
        numSeconds += -(inputNum*3600);
        inputNum = numSeconds/60;
        if (inputNum < 10) {
            returnString += "0" + inputNum;
        }
        else {
            returnString += inputNum;
        }
        returnString += ":";
        numSeconds += -(inputNum*60);
        inputNum = numSeconds;
        if (inputNum < 10) {
            returnString += "0" + inputNum;
        }
        else {
            returnString += inputNum;
        }
        return returnString;
    }

    private void updateScreen () {
        //Does not affect playtimeHandler in home fragment
        //playtimeHandler keeps running and updating new times, which updateScreen will read
        if(screenUpdater != null) {
            screenUpdater.removeCallbacksAndMessages(null); //null will remove callbacks for anonymous runnables
            System.out.println("Deleted callbacks to screenUpdater"); //currently never runs this
        } else System.out.println("screenUpdater is already null");
        screenUpdater = new Handler();
        final int delay = 1000; //milliseconds

        screenUpdater.postDelayed(new Runnable(){
            public void run(){
                mainTextView.post(new Runnable() {
                    public void run() {
                        String tempString = "";
                        tempString += "Total words submitted: " + userStatsFileStrings.get(0) + "\n";
                        tempString += "Longest word submitted: " + userStatsFileStrings.get(1) + "\n";
                        tempString += "Total pangrams: " + userStatsFileStrings.get(4) + "\n";
                        tempString += "Longest pangram: " + userStatsFileStrings.get(5) + "\n";
                        tempString += "Total points: " + userStatsFileStrings.get(2) + "\n";
                        tempString += "Best score on a single puzzle: " + userStatsFileStrings.get(3) + "\n";
                        tempString += "Total time played: " + convertStringToTime(Integer.toString(Integer.parseInt(userStatsFileStrings.get(6)) + counter)) + "\n";
                        mainTextView.setText(tempString);
                    }
                });
                counter ++;
                screenUpdater.postDelayed(this, delay);
            }
        }, delay);



    }
}