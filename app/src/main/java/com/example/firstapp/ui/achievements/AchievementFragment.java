package com.example.firstapp.ui.achievements;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.firstapp.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class AchievementFragment extends Fragment {

    private AchievementViewModel achievementViewModel;
    private static ArrayList<String> userAchievementFileStrings = new ArrayList<String>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        achievementViewModel =
                ViewModelProviders.of(this).get(AchievementViewModel.class);
        View root = inflater.inflate(R.layout.fragment_achievements, container, false);
        final TextView textView = root.findViewById(R.id.text_achievements);
        achievementViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        readSaveFile(AchievementFragment.this.getActivity());

        ImageView tempAchievementImage;
        TextView tempAchievementTitle;
        TextView tempAchievementDetails;
        if (userAchievementFileStrings.contains("FIRST PANGRAM!")) {
            tempAchievementImage = (ImageView) root.findViewById(R.id.First_Pangram_Image_Achievements);
            tempAchievementTitle = (TextView) root.findViewById(R.id.First_Pangram_Title_Text_Achievements);
            tempAchievementDetails = (TextView) root.findViewById(R.id.First_Pangram_Details_Text_Achievements);

            tempAchievementImage.setImageResource(android.R.drawable.btn_star_big_on);
            tempAchievementTitle.setTextColor(getResources().getColor(R.color.achievementTitleText));
            tempAchievementDetails.setTextColor(getResources().getColor(R.color.achievementDetailText));
        }
        if (userAchievementFileStrings.contains("10 WORDS!")) {
            tempAchievementImage = (ImageView) root.findViewById(R.id.Word_Progress_10_Image_Achievements);
            tempAchievementTitle = (TextView) root.findViewById(R.id.Word_Progress_10_Title_Text_Achievements);
            tempAchievementDetails = (TextView) root.findViewById(R.id.Word_Progress_10_Details_Text_Achievements);

            tempAchievementImage.setImageResource(android.R.drawable.btn_star_big_on);
            tempAchievementTitle.setTextColor(getResources().getColor(R.color.achievementTitleText));
            tempAchievementDetails.setTextColor(getResources().getColor(R.color.achievementDetailText));
        }
        if (userAchievementFileStrings.contains("25 WORDS!")) {
            tempAchievementImage = (ImageView) root.findViewById(R.id.Word_Progress_25_Image_Achievements);
            tempAchievementTitle = (TextView) root.findViewById(R.id.Word_Progress_25_Title_Text_Achievements);
            tempAchievementDetails = (TextView) root.findViewById(R.id.Word_Progress_25_Details_Text_Achievements);

            tempAchievementImage.setImageResource(android.R.drawable.btn_star_big_on);
            tempAchievementTitle.setTextColor(getResources().getColor(R.color.achievementTitleText));
            tempAchievementDetails.setTextColor(getResources().getColor(R.color.achievementDetailText));
        }
        if (userAchievementFileStrings.contains("50 WORDS!")) {
            tempAchievementImage = (ImageView) root.findViewById(R.id.Word_Progress_50_Image_Achievements);
            tempAchievementTitle = (TextView) root.findViewById(R.id.Word_Progress_50_Title_Text_Achievements);
            tempAchievementDetails = (TextView) root.findViewById(R.id.Word_Progress_50_Details_Text_Achievements);

            tempAchievementImage.setImageResource(android.R.drawable.btn_star_big_on);
            tempAchievementTitle.setTextColor(getResources().getColor(R.color.achievementTitleText));
            tempAchievementDetails.setTextColor(getResources().getColor(R.color.achievementDetailText));
        }
        if (userAchievementFileStrings.contains("LONG WORD!")) {
            tempAchievementImage = (ImageView) root.findViewById(R.id.Longest_Word_7_Image_Achievements);
            tempAchievementTitle = (TextView) root.findViewById(R.id.Longest_Word_7_Title_Text_Achievements);
            tempAchievementDetails = (TextView) root.findViewById(R.id.Longest_Word_7_Details_Text_Achievements);

            tempAchievementImage.setImageResource(android.R.drawable.btn_star_big_on);
            tempAchievementTitle.setTextColor(getResources().getColor(R.color.achievementTitleText));
            tempAchievementDetails.setTextColor(getResources().getColor(R.color.achievementDetailText));
        }
        if (userAchievementFileStrings.contains("VERY LONG WORD!")) {
            tempAchievementImage = (ImageView) root.findViewById(R.id.Longest_Word_9_Image_Achievements);
            tempAchievementTitle = (TextView) root.findViewById(R.id.Longest_Word_9_Title_Text_Achievements);
            tempAchievementDetails = (TextView) root.findViewById(R.id.Longest_Word_9_Details_Text_Achievements);

            tempAchievementImage.setImageResource(android.R.drawable.btn_star_big_on);
            tempAchievementTitle.setTextColor(getResources().getColor(R.color.achievementTitleText));
            tempAchievementDetails.setTextColor(getResources().getColor(R.color.achievementDetailText));
        }
        if (userAchievementFileStrings.contains("SUPER LONG WORD!")) {
            tempAchievementImage = (ImageView) root.findViewById(R.id.Longest_Word_11_Image_Achievements);
            tempAchievementTitle = (TextView) root.findViewById(R.id.Longest_Word_11_Title_Text_Achievements);
            tempAchievementDetails = (TextView) root.findViewById(R.id.Longest_Word_11_Details_Text_Achievements);

            tempAchievementImage.setImageResource(android.R.drawable.btn_star_big_on);
            tempAchievementTitle.setTextColor(getResources().getColor(R.color.achievementTitleText));
            tempAchievementDetails.setTextColor(getResources().getColor(R.color.achievementDetailText));
        }
        if (userAchievementFileStrings.contains("RIDICULOUSLY LONG WORD!")) {
            tempAchievementImage = (ImageView) root.findViewById(R.id.Longest_Word_13_Image_Achievements);
            tempAchievementTitle = (TextView) root.findViewById(R.id.Longest_Word_13_Title_Text_Achievements);
            tempAchievementDetails = (TextView) root.findViewById(R.id.Longest_Word_13_Details_Text_Achievements);

            tempAchievementImage.setImageResource(android.R.drawable.btn_star_big_on);
            tempAchievementTitle.setTextColor(getResources().getColor(R.color.achievementTitleText));
            tempAchievementDetails.setTextColor(getResources().getColor(R.color.achievementDetailText));
        }
        if (userAchievementFileStrings.contains("QUICK AS LIGHTNING!")) {
            tempAchievementImage = (ImageView) root.findViewById(R.id.Speed_Demon_1_Image_Achievements);
            tempAchievementTitle = (TextView) root.findViewById(R.id.Speed_Demon_1_Title_Text_Achievements);
            tempAchievementDetails = (TextView) root.findViewById(R.id.Speed_Demon_1_Details_Text_Achievements);

            tempAchievementImage.setImageResource(android.R.drawable.btn_star_big_on);
            tempAchievementTitle.setTextColor(getResources().getColor(R.color.achievementTitleText));
            tempAchievementDetails.setTextColor(getResources().getColor(R.color.achievementDetailText));
        }
        if (userAchievementFileStrings.contains("SPEED DEMON!")) {
            tempAchievementImage = (ImageView) root.findViewById(R.id.Speed_Demon_2_Image_Achievements);
            tempAchievementTitle = (TextView) root.findViewById(R.id.Speed_Demon_2_Title_Text_Achievements);
            tempAchievementDetails = (TextView) root.findViewById(R.id.Speed_Demon_2_Details_Text_Achievements);

            tempAchievementImage.setImageResource(android.R.drawable.btn_star_big_on);
            tempAchievementTitle.setTextColor(getResources().getColor(R.color.achievementTitleText));
            tempAchievementDetails.setTextColor(getResources().getColor(R.color.achievementDetailText));
        }
        if (userAchievementFileStrings.contains("PRETTY FUN!")) {
            tempAchievementImage = (ImageView) root.findViewById(R.id.Playtime_Image_Achievements);
            tempAchievementTitle = (TextView) root.findViewById(R.id.Playtime_Title_Text_Achievements);
            tempAchievementDetails = (TextView) root.findViewById(R.id.Playtime_Details_Text_Achievements);

            tempAchievementImage.setImageResource(android.R.drawable.btn_star_big_on);
            tempAchievementTitle.setTextColor(getResources().getColor(R.color.achievementTitleText));
            tempAchievementDetails.setTextColor(getResources().getColor(R.color.achievementDetailText));
        }
        return root;
    }

    private void readSaveFile (Context context) {
        try {
            BufferedReader reader = null;
            File file = new File(context.getFilesDir(), "achievementFile.txt");
            if (!file.exists()) {
                file.createNewFile();
                writeToFile("", context, "achievementFile.txt");
            }
            FileInputStream fis = context.openFileInput("achievementFile.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            reader = new BufferedReader(inputStreamReader);
            // do reading, usually loop until end of file reading
            String mLine = "";
            userAchievementFileStrings.clear();
            while ((mLine = reader.readLine()) != null) {
                //process line
                String data = mLine.toUpperCase();
                userAchievementFileStrings.add(data);
            }
            writeSave(userAchievementFileStrings, context, "achievementFile.txt");
            reader.close();
        } catch (IOException e) {
            //log the exception
            System.out.println("An exception occurred while reading achievementFile");
        }
    }

    private void writeToFile(String data, Context context, String filename) {
        File file = new File(context.getFilesDir(), filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            OutputStreamWriter bw = new OutputStreamWriter(fos);
            bw.append(data);
            bw.flush();
            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendToFile(String data, Context context, String filename) {
        File file = new File(context.getFilesDir(), filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            //FileOutputStream fos = context.openFileOutput(filename, Context.MODE_APPEND);
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_APPEND);
            OutputStreamWriter bw = new OutputStreamWriter(fos);
            bw.append(data);
            bw.flush();
            bw.close();
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
            System.out.println("Error while saving achievementFileStrings.");
        }
    }

}