package com.example.firstapp.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.firstapp.MainMenuNavigation;
import com.example.firstapp.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

//Read settings file, preset if no existing file or update screen values to current
public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private static ArrayList<String> settingsFileStrings = new ArrayList<String>();
    private SeekBar soundSeekBar;
    private Switch wordListSwitch;
    private Switch nightModeSwitch;
    private Switch adSwitch;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        final TextView textView = root.findViewById(R.id.text_settings);
        settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        readSaveFile(SettingsFragment.this.getActivity());
        ((TextView)root.findViewById(R.id.Credits_Text_View)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView)root.findViewById(R.id.Credits_Text_View)).setText(Html.fromHtml(getResources().getString(R.string.SettingsCredits)));
        initializeViews(root);

        soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // handle progress change
                settingsFileStrings.set(0, Integer.toString(i));
                writeSave(settingsFileStrings, SettingsFragment.this.getActivity(), "settingsFile.txt");
                MainMenuNavigation.setMusicVolume(MainMenuNavigation.backgroundMusic, Integer.parseInt(settingsFileStrings.get(0)));
                System.out.println("Current volume level: " + settingsFileStrings.get(0));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //mProgressAtStartTracking = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                if(Math.abs(mProgressAtStartTracking - seekBar.getProgress()) <= SENSITIVITY){
//                    // react to thumb click
//                }
            }
        });

        wordListSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                settingsFileStrings.set(1, Boolean.toString(isChecked));
                writeSave(settingsFileStrings, SettingsFragment.this.getActivity(), "settingsFile.txt");
            }
        });

        nightModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (isChecked != Boolean.parseBoolean(settingsFileStrings.get(2))) {
                    settingsFileStrings.set(2, Boolean.toString(isChecked));
                    writeSave(settingsFileStrings, SettingsFragment.this.getActivity(), "settingsFile.txt");
                    MainMenuNavigation.setNightMode(isChecked);
                }
            }
        });

        adSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Currently not in use
                // do something, the isChecked will be
                // true if the switch is in the On position
                settingsFileStrings.set(3, Boolean.toString(isChecked));
                writeSave(settingsFileStrings, SettingsFragment.this.getActivity(), "settingsFile.txt");
            }
        });



        return root;
    }

    private void initializeViews(View root) {
        soundSeekBar = root.findViewById(R.id.Sound_SeekBar_Settings);
        wordListSwitch = root.findViewById(R.id.Word_List_Switch_Settings);
        nightModeSwitch = root.findViewById(R.id.Night_Mode_Switch_Settings);
        adSwitch = root.findViewById(R.id.Advertisements_Switch_Settings);

        soundSeekBar.post(new Runnable() {
            public void run() {
                soundSeekBar.setProgress(Integer.parseInt(settingsFileStrings.get(0)));
            }
        });

        wordListSwitch.post(new Runnable() {
            public void run() {
                wordListSwitch.setChecked(Boolean.parseBoolean(settingsFileStrings.get(1)));
                wordListSwitch.jumpDrawablesToCurrentState();
            }
        });

        nightModeSwitch.post(new Runnable() {
            public void run() {
                nightModeSwitch.setChecked(Boolean.parseBoolean(settingsFileStrings.get(2)));
                nightModeSwitch.jumpDrawablesToCurrentState();
            }
        });

        adSwitch.post(new Runnable() {
            public void run() {
                adSwitch.setChecked(Boolean.parseBoolean(settingsFileStrings.get(3)));
                adSwitch.jumpDrawablesToCurrentState();
            }
        });
    }

    private void readSaveFile(Context context) {
        BufferedReader reader = null;
        try {
            System.out.println("Opening settingsFile");
            File file = new File(context.getFilesDir(), "settingsFile.txt");
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Created new settingsFile (This should never occur besides the first run after a new patch)");
                System.out.println("Creating with default settings.");
                writeToFile("50\nTrue\nFalse\nFalse", context, "settingsFile.txt");
            }
            FileInputStream fis = context.openFileInput("settingsFile.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            reader = new BufferedReader(inputStreamReader);
            // do reading, usually loop until end of file reading
            System.out.println("Opened settingsFile");
            String mLine = "";
            settingsFileStrings.clear();
            while ((mLine = reader.readLine()) != null) {
                //process line
                String data = mLine.toUpperCase();
                System.out.println("settingsFile: " + data);
                settingsFileStrings.add(data);
            }
            //Add default if missing
            if (settingsFileStrings.size() != 4) {
                settingsFileStrings.clear();
                //Sound
                settingsFileStrings.add("50");
                //Word List
                settingsFileStrings.add("True");
                //Night Mode
                settingsFileStrings.add("False");
                //Ads
                settingsFileStrings.add("False");

            }
            writeSave(settingsFileStrings, context, "settingsFile.txt");
            reader.close();
        } catch (IOException e) {
            //log the exception
            System.out.println("An exception occurred while reading settingsFile");
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
            System.out.println("Error while saving settingsFileStrings.");
        }
    }



}