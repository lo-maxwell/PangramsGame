package com.example.firstapp.ui.home;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
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
import java.util.Random;

import static java.lang.Integer.parseInt;

//minor issue: game time increments even when app is not in focus (as long as it is not killed)
//TODO: Change background music
//TODO: App icon, Credits page, Night Theme, Settings explanations
//TODO: Remove ad setting

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private static ArrayList<String> saveFileStrings = new ArrayList<String>();
    private static ArrayList<String> preLoadFileStrings = new ArrayList<String>();
    private static ArrayList<String> userStatsFileStrings = new ArrayList<String>();
    private static ArrayList<String> userAchievementFileStrings = new ArrayList<String>();
    private static ArrayList<String> settingsFileStrings = new ArrayList<String>();
    private static ArrayList<String> possibleWords = new ArrayList<String>();
    private static ArrayList<String> submittedWords = new ArrayList<String>();
    private static ArrayList<Button> currentAchievements = new ArrayList<Button>();
    private static ArrayList<String> newLettersStrings = new ArrayList<String>();
    private static int score = 0;
    private static int maxScore = 0;
    private static int enableCheat = 0;
    private View rootView;
    private Button submitButton;
    private Button textButton1;
    private Button textButton2;
    private Button textButton3;
    private Button textButton4;
    private Button textButton5;
    private Button textButton6;
    private Button textButton7;
    private TextView answerBox;
    private TextView messageBox;
    private ScrollView scoreTrackerScrollView;
    private TextView scoreTracker;
    private Button newGameButton;
    private Button eraseButton;
    private Button shuffleButton;
    private TextView wordList;
    private ScrollView wordListScrollView;
    private Button defaultAchievementButton;
    private Button defaultAchievementButton2;
    private Button defaultAchievementButton3;
    private Button defaultAchievementButton4;
    private Button defaultAchievementButton5;
    private Button pangramAchievementButton;
    private Button wordProgress10AchievementButton;
    private Button wordProgress25AchievementButton;
    private Button wordProgress50AchievementButton;
    private Button longWord8AchievementButton;
    private Button longWord10AchievementButton;
    private Button longWord12AchievementButton;
    private Button longWord15AchievementButton;
    private Button speedDemon1AchievementButton;
    private Button speedDemon2AchievementButton;
    private Button playtimeAchievementButton;
    private ProgressBar loadingBar;
    private int speedDemonCounter = 0;
    public static Handler playtimeHandler;
    private Handler speedDemonHandler;
    private static ObjectAnimator defaultAchievementButtonAnimation;
    private Context mContext;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //readSaveFile(HomeFragment.this.getActivity());
        System.out.println("onCreate was called.");
        mContext = getActivity();

    }

    public void onDestroy() {
        super.onDestroy();
        System.out.println("Fragment was destroyed.");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        submitButton = root.findViewById(R.id.Submit_Button_Home);
        textButton1 = root.findViewById(R.id.Add_Text_Button_1_Home);
        textButton2 = root.findViewById(R.id.Add_Text_Button_2_Home);
        textButton3 = root.findViewById(R.id.Add_Text_Button_3_Home);
        textButton4 = root.findViewById(R.id.Add_Text_Button_4_Home);
        textButton5 = root.findViewById(R.id.Add_Text_Button_5_Home);
        textButton6 = root.findViewById(R.id.Add_Text_Button_6_Home);
        textButton7 = root.findViewById(R.id.Add_Text_Button_7_Home);
        answerBox = root.findViewById(R.id.Answer_Box_Home);
        messageBox = root.findViewById(R.id.Message_Box_Home);
        scoreTracker = root.findViewById(R.id.Score_Tracker_Home);
        scoreTrackerScrollView = root.findViewById(R.id.Score_Tracker_Scroll_View_Home);
        newGameButton = root.findViewById(R.id.New_Game_Button_Home);
        eraseButton = root.findViewById(R.id.Erase_Button_Home);
        shuffleButton = root.findViewById(R.id.Shuffle_Button_Home);
        wordList = root.findViewById(R.id.Word_List_Home);
        wordListScrollView = root.findViewById(R.id.Word_List_Scroll_View_Home);
        defaultAchievementButton = root.findViewById(R.id.Default_Achievement_Button_Home);
        defaultAchievementButton.setVisibility(View.INVISIBLE);
        defaultAchievementButton2 = root.findViewById(R.id.Default_Achievement_Button_Home_2);
        defaultAchievementButton2.setVisibility(View.INVISIBLE);
        defaultAchievementButton3 = root.findViewById(R.id.Default_Achievement_Button_Home_3);
        defaultAchievementButton3.setVisibility(View.INVISIBLE);
        defaultAchievementButton4 = root.findViewById(R.id.Default_Achievement_Button_Home_4);
        defaultAchievementButton4.setVisibility(View.INVISIBLE);
        defaultAchievementButton5 = root.findViewById(R.id.Default_Achievement_Button_Home_5);
        defaultAchievementButton5.setVisibility(View.INVISIBLE);
        pangramAchievementButton = root.findViewById(R.id.Achievement_Button_Pangram_Home);
        pangramAchievementButton.setVisibility(View.INVISIBLE);
        wordProgress10AchievementButton = root.findViewById(R.id.Achievement_Button_Word_Progress_10_Home);
        wordProgress10AchievementButton.setVisibility(View.INVISIBLE);
        wordProgress25AchievementButton = root.findViewById(R.id.Achievement_Button_Word_Progress_25_Home);
        wordProgress25AchievementButton.setVisibility(View.INVISIBLE);
        wordProgress50AchievementButton = root.findViewById(R.id.Achievement_Button_Word_Progress_50_Home);
        wordProgress50AchievementButton.setVisibility(View.INVISIBLE);
        longWord8AchievementButton = root.findViewById(R.id.Achievement_Button_Longest_Word_7_Home);
        longWord8AchievementButton.setVisibility(View.INVISIBLE);
        longWord10AchievementButton = root.findViewById(R.id.Achievement_Button_Longest_Word_9_Home);
        longWord10AchievementButton.setVisibility(View.INVISIBLE);
        longWord12AchievementButton = root.findViewById(R.id.Achievement_Button_Longest_Word_11_Home);
        longWord12AchievementButton.setVisibility(View.INVISIBLE);
        longWord15AchievementButton = root.findViewById(R.id.Achievement_Button_Longest_Word_13_Home);
        longWord15AchievementButton.setVisibility(View.INVISIBLE);
        speedDemon1AchievementButton = root.findViewById(R.id.Achievement_Button_Speed_Demon_1_Home);
        speedDemon1AchievementButton.setVisibility(View.INVISIBLE);
        speedDemon2AchievementButton = root.findViewById(R.id.Achievement_Button_Speed_Demon_2_Home);
        speedDemon2AchievementButton.setVisibility(View.INVISIBLE);
        playtimeAchievementButton = root.findViewById(R.id.Achievement_Button_Playtime_Home);
        playtimeAchievementButton.setVisibility(View.INVISIBLE);
        defaultAchievementButtonAnimation = ObjectAnimator.ofFloat(defaultAchievementButton, "translationX", 1);
        loadingBar = root.findViewById(R.id.Loading_Bar_Home);
        loadingBar.setVisibility(View.GONE);
        loadingBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.loadingBar), android.graphics.PorterDuff.Mode.MULTIPLY);

        //Submit_Button_Home
        submitButton.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                if (enableCheat % 2 == 1) {
                    enableCheat += 1;
                    if (enableCheat == 20) {
                        for (int i = 0; i < possibleWords.size(); i++) {
                            wordList.append(possibleWords.get(i) + ", ");
                        }
                        wordList.append("\n");
                        enableCheat = -10000000;
                    }
                } else {
                    enableCheat = 0;
                }

                if (answerBox.getText().toString().equals(getString(R.string.AnswerBox)) || answerBox.getText().toString().equals("Enter a word first!") || answerBox.getText().toString().equals("")) {
                    answerBox.setText("Enter a word first!");
                } else {
                    String answer = answerBox.getText().toString().toUpperCase();
                    messageBox.setTextColor(ContextCompat.getColor(HomeFragment.this.getActivity(), R.color.red));
                    animateTextViewColors(messageBox, 0);
                    if (submittedWords.contains(answer)) {
                        answerBox.setText(getString(R.string.AnswerBox));
                        messageBox.setText("Word already submitted!");

                    } else if (answer.length() >= 3 && !(answer.contains((textButton4).getText().toString()))) {
                        answerBox.setText(getString(R.string.AnswerBox));
                        messageBox.setText("Word must contain the center letter (" + (textButton4).getText().toString() + ")");
                    } else if (!(answer.length() >= 3) && (answer.contains((textButton4).getText().toString()))) {
                        answerBox.setText(getString(R.string.AnswerBox));
                        messageBox.setText("Word must have at least 3 letters");
                    } else if (!(answer.length() >= 3) && !(answer.contains((textButton4).getText().toString()))) {
                        answerBox.setText(getString(R.string.AnswerBox));
                        messageBox.setText("Word must have at least 3 letters and contain the center letter (" + (textButton4).getText().toString() + ")");
                    } else if (!(possibleWords.contains(answer))) {
                        System.out.println("Word " + answer + " not in possible words");
                        answerBox.setText(getString(R.string.AnswerBox));
                        messageBox.setText("Not a valid word!");
                    } else if (answer.length() >= 3 && answer.contains((textButton4).getText().toString())) {
                        messageBox.setTextColor(ContextCompat.getColor(HomeFragment.this.getActivity(), R.color.green));
                        animateTextViewColors(messageBox, 0);
                        // find the amount we need to scroll.  This works by
                        // asking the TextView's internal layout for the position
                        // of the final line and then subtracting the TextView's height
                        //final int scrollAmount = wordList.getLayout().getLineTop(wordList.getLineCount()) - wordList.getHeight();
                        // if there is no need to scroll, scrollAmount will be <=0
                        wordListScrollView.smoothScrollTo(0, wordList.getHeight());
                        answerBox.setText(getString(R.string.AnswerBox));
                        boolean containsAll = true;
                        final Button[] tempButtons = {
                                textButton1, textButton2, textButton3, textButton4, textButton5, textButton6, textButton7
                        };
                        for (int i = 0; i < 7; i++) {
                            if (!(answer.contains(tempButtons[i].getText().toString()))) {
                                containsAll = false;
                                break;
                            }
                        }
                        int tempScore = answer.length();
                        if (containsAll) {
                            tempScore *= 2;
                            messageBox.setText("Pangram! +" + tempScore);
                            wordList.append(answer + " (+" + tempScore + ")\n");
                            //Increment pangrams counter
                            userStatsFileStrings.set(4, Integer.toString(Integer.parseInt(userStatsFileStrings.get(4)) + 1));
                            System.out.println("Updating pangrams submitted counter : " + userStatsFileStrings.get(4));
                            //Check if new longest pangram
                            if (answer.length() >= userStatsFileStrings.get(5).length()) {
                                userStatsFileStrings.set(5, answer);
                                System.out.println("Updating longest word: " + userStatsFileStrings.get(5));
                            }
                            //First pangram achievement if it does not already exist
                            if (!(userAchievementFileStrings.contains("FIRST PANGRAM!"))) {
                                ArrayList<String> tempArrayList = new ArrayList<String>();
                                tempArrayList.add("Achievement_Button_Pangram_Home");
                                tempArrayList.add(Integer.toString(R.id.Achievement_Button_Pangram_Home));
                                addAchievementToScreen(tempArrayList);
                                userAchievementFileStrings.add("FIRST PANGRAM!");
                                writeSave(userAchievementFileStrings, HomeFragment.this.getActivity(), "achievementFile.txt");
                            }

                        } else {
                            messageBox.setText("+" + tempScore);
                            wordList.append(answer + " (+" + tempScore + ")\n");
                        }
                        score += tempScore;
                        scoreTracker.setText("Score: " + score + "/" + maxScore);
                        if (score == maxScore) {
                            scoreTracker.append("\nPerfect!");
                        }
                        saveFileStrings.set(8, Integer.toString(score));
                        submittedWords.add(answer);
                        saveFileStrings.add(answer);
                        writeSave(saveFileStrings, HomeFragment.this.getActivity(), "saveFile.txt");

                        //Increment words submitted counter
                        userStatsFileStrings.set(0, Integer.toString(Integer.parseInt(userStatsFileStrings.get(0)) + 1));
                        System.out.println("Updating words submitted counter : " + userStatsFileStrings.get(0));
                        //Words submitted achievement if it does not already exist
                        if (!(userAchievementFileStrings.contains("10 WORDS!")) && Integer.parseInt(userStatsFileStrings.get(0)) == 10) {
                            ArrayList<String> tempArrayList = new ArrayList<String>();
                            tempArrayList.add("Achievement_Button_Word_Progress_10_Home");
                            tempArrayList.add(Integer.toString(R.id.Achievement_Button_Word_Progress_10_Home));
                            addAchievementToScreen(tempArrayList);
                            userAchievementFileStrings.add("10 WORDS!");
                            writeSave(userAchievementFileStrings, HomeFragment.this.getActivity(), "achievementFile.txt");
                        }
                        else if (!(userAchievementFileStrings.contains("25 WORDS!")) && Integer.parseInt(userStatsFileStrings.get(0)) == 25) {
                            ArrayList<String> tempArrayList = new ArrayList<String>();
                            tempArrayList.add("Achievement_Button_Word_Progress_25_Home");
                            tempArrayList.add(Integer.toString(R.id.Achievement_Button_Word_Progress_25_Home));
                            addAchievementToScreen(tempArrayList);
                            userAchievementFileStrings.add("25 WORDS!");
                            writeSave(userAchievementFileStrings, HomeFragment.this.getActivity(), "achievementFile.txt");
                        }
                        else if (!(userAchievementFileStrings.contains("50 WORDS!")) && Integer.parseInt(userStatsFileStrings.get(0)) == 50) {
                            ArrayList<String> tempArrayList = new ArrayList<String>();
                            tempArrayList.add("Achievement_Button_Word_Progress_50_Home");
                            tempArrayList.add(Integer.toString(R.id.Achievement_Button_Word_Progress_50_Home));
                            addAchievementToScreen(tempArrayList);
                            userAchievementFileStrings.add("50 WORDS!");
                            writeSave(userAchievementFileStrings, HomeFragment.this.getActivity(), "achievementFile.txt");
                        }

                        //Update longest word
                        if (answer.length() >= userStatsFileStrings.get(1).length()) {
                            userStatsFileStrings.set(1, answer);
                            System.out.println("Updating longest word: " + userStatsFileStrings.get(1));
                        }
                        //Long word achievement if it does not already exist
                        if (!(userAchievementFileStrings.contains("7 LETTER WORD!")) && answer.length() >= 7) {
                            ArrayList<String> tempArrayList = new ArrayList<String>();
                            tempArrayList.add("Achievement_Button_Longest_Word_7_Home");
                            tempArrayList.add(Integer.toString(R.id.Achievement_Button_Longest_Word_7_Home));
                            addAchievementToScreen(tempArrayList);
                            userAchievementFileStrings.add("7 LETTER WORD!");
                            writeSave(userAchievementFileStrings, HomeFragment.this.getActivity(), "achievementFile.txt");
                        }
                        if (!(userAchievementFileStrings.contains("9 LETTER WORD!")) && answer.length() >= 9) {
                            ArrayList<String> tempArrayList = new ArrayList<String>();
                            tempArrayList.add("Achievement_Button_Longest_Word_9_Home");
                            tempArrayList.add(Integer.toString(R.id.Achievement_Button_Longest_Word_9_Home));
                            addAchievementToScreen(tempArrayList);
                            userAchievementFileStrings.add("9 LETTER WORD!");
                            writeSave(userAchievementFileStrings, HomeFragment.this.getActivity(), "achievementFile.txt");
                        }
                        if (!(userAchievementFileStrings.contains("11 LETTER WORD!")) && answer.length() >= 11) {
                            ArrayList<String> tempArrayList = new ArrayList<String>();
                            tempArrayList.add("Achievement_Button_Longest_Word_12_Home");
                            tempArrayList.add(Integer.toString(R.id.Achievement_Button_Longest_Word_11_Home));
                            addAchievementToScreen(tempArrayList);
                            userAchievementFileStrings.add("11 LETTER WORD!");
                            writeSave(userAchievementFileStrings, HomeFragment.this.getActivity(), "achievementFile.txt");
                        }
                        if (!(userAchievementFileStrings.contains("13 LETTER WORD!")) && answer.length() >= 13) {
                            ArrayList<String> tempArrayList = new ArrayList<String>();
                            tempArrayList.add("Achievement_Button_Longest_Word_13_Home");
                            tempArrayList.add(Integer.toString(R.id.Achievement_Button_Longest_Word_13_Home));
                            addAchievementToScreen(tempArrayList);
                            userAchievementFileStrings.add("13 LETTER WORD!");
                            writeSave(userAchievementFileStrings, HomeFragment.this.getActivity(), "achievementFile.txt");
                        }

                        //Increment total points
                        userStatsFileStrings.set(2, Integer.toString(Integer.parseInt(userStatsFileStrings.get(2)) + tempScore));
                        System.out.println("Updating total points: " + userStatsFileStrings.get(2));
                        //Increment highest score
                        if (score >= Integer.parseInt(userStatsFileStrings.get(3))) {
                            userStatsFileStrings.set(3, Integer.toString(score));
                            System.out.println("Updating highest score: " + userStatsFileStrings.get(3));
                        }

                        updateSpeedDemonCounter();

                        writeSave(userStatsFileStrings, HomeFragment.this.getActivity(), "userStatsFile.txt");
                    }
                }
            }
        });

        //Add_Text_Button (1-7)
        textButton1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempButtonText = textButton1.getText().toString();
                enableCheat = 0;
                if (answerBox.getText().toString().equals(getString(R.string.AnswerBox)) || answerBox.getText().toString().equals("Enter a word first!")) {
                    answerBox.setText(getString(R.string.EmptyText));
                }
                if (answerBox.getText().toString().length() < 20) {
                    answerBox.append(tempButtonText);
                }
            }
        });
        textButton2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempButtonText = textButton2.getText().toString();
                enableCheat = 0;
                if (answerBox.getText().toString().equals(getString(R.string.AnswerBox)) || answerBox.getText().toString().equals("Enter a word first!")) {
                    answerBox.setText(getString(R.string.EmptyText));
                }
                if (answerBox.getText().toString().length() < 20) {
                    answerBox.append(tempButtonText);
                }
            }
        });
        textButton3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempButtonText = textButton3.getText().toString();
                enableCheat = 0;
                if (answerBox.getText().toString().equals(getString(R.string.AnswerBox)) || answerBox.getText().toString().equals("Enter a word first!")) {
                    answerBox.setText(getString(R.string.EmptyText));
                }
                if (answerBox.getText().toString().length() < 20) {
                    answerBox.append(tempButtonText);
                }
            }
        });
        textButton4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempButtonText = textButton4.getText().toString();
                enableCheat = 0;
                if (answerBox.getText().toString().equals(getString(R.string.AnswerBox)) || answerBox.getText().toString().equals("Enter a word first!")) {
                    answerBox.setText(getString(R.string.EmptyText));
                }
                if (answerBox.getText().toString().length() < 20) {
                    answerBox.append(tempButtonText);
                }
            }
        });
        textButton5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempButtonText = textButton5.getText().toString();
                enableCheat = 0;
                if (answerBox.getText().toString().equals(getString(R.string.AnswerBox)) || answerBox.getText().toString().equals("Enter a word first!")) {
                    answerBox.setText(getString(R.string.EmptyText));
                }
                if (answerBox.getText().toString().length() < 20) {
                    answerBox.append(tempButtonText);
                }
            }
        });
        textButton6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempButtonText = textButton6.getText().toString();
                enableCheat = 0;
                if (answerBox.getText().toString().equals(getString(R.string.AnswerBox)) || answerBox.getText().toString().equals("Enter a word first!")) {
                    answerBox.setText(getString(R.string.EmptyText));
                }
                if (answerBox.getText().toString().length() < 20) {
                    answerBox.append(tempButtonText);
                }
            }
        });
        textButton7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //final Button tempButton = (Button) rootView.findViewById(view.getId());
                //String tempButtonText = tempButton.getText().toString();
                String tempButtonText = textButton7.getText().toString();
                enableCheat = 0;
                if (answerBox.getText().toString().equals(getString(R.string.AnswerBox)) || answerBox.getText().toString().equals("Enter a word first!")) {
                    answerBox.setText(getString(R.string.EmptyText));
                }
                if (answerBox.getText().toString().length() < 20) {
                    answerBox.append(tempButtonText);
                }
            }
        });

        //Erase_Button_Home
        eraseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!answerBox.getText().toString().equals(getString(R.string.AnswerBox)) && !answerBox.getText().toString().equals("Enter a word first!")) {
                    if (answerBox.getText().toString().length() >= 1) {
                        answerBox.setText(answerBox.getText().toString().substring(0, answerBox.getText().toString().length() - 1));
                    }
                } else {
                    if (enableCheat % 2 == 0) {
                        enableCheat += 1;
                    } else {
                        enableCheat = 0;
                    }
                    answerBox.setText("");
                }
            }
        });

        //Shuffle_Button_Home
        shuffleButton.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {

                Random rand = new Random();
                ArrayList<Integer> randomIntArray = new ArrayList<Integer>();
                randomIntArray.add(4);
                while (randomIntArray.size() <= 6) {
                    int randInt = rand.nextInt(7) + 1;
                    if (!randomIntArray.contains(randInt)) {
                        if (randomIntArray.size() < 4) {
                            randomIntArray.add(0, randInt);
                        } else if (randomIntArray.size() >= 4) {
                            randomIntArray.add(4, randInt);
                        }
                    }
                }
                System.out.println(randomIntArray);
                String[] tempStringArray = {"", "", "", "", "", "", ""};
                for (int i = 0; i < 7; i++) {
                    tempStringArray[i] = saveFileStrings.get(randomIntArray.get(i));
                }
                for (int i = 0; i < 7; i++) {
                    saveFileStrings.set(i + 1, tempStringArray[i]);
                }
                writeSave(saveFileStrings, HomeFragment.this.getActivity(), "saveFile.txt");

                final Button[] tempButtons = {textButton1, textButton2, textButton3, textButton4, textButton5, textButton6, textButton7};
                String[] usedLetters = {"", "", "", "", "", "", ""};
                for (int i = 0; i < 7; i++) {
                    usedLetters[i] = saveFileStrings.get(i + 1);
                    tempButtons[i].setText(usedLetters[i]);
                }

                if (!currentAchievements.contains(defaultAchievementButton)) {
                    System.out.println("Adding achievement button 1 to screen");
                    ArrayList<String> tempArrayList = new ArrayList<String>();
                    tempArrayList.add("Default_Achievement_Button_Home");
                    tempArrayList.add(Integer.toString(R.id.Default_Achievement_Button_Home));
                    addAchievementToScreen(tempArrayList);
                } else if (!currentAchievements.contains(defaultAchievementButton2)) {
                    System.out.println("Adding achievement button 2 to screen");
                    ArrayList<String> tempArrayList = new ArrayList<String>();
                    tempArrayList.add("Default_Achievement_Button_Home_2");
                    tempArrayList.add(Integer.toString(R.id.Default_Achievement_Button_Home_2));
                    addAchievementToScreen(tempArrayList);
                } else if (!currentAchievements.contains(defaultAchievementButton3)) {
                    System.out.println("Adding achievement button 3 to screen");
                    ArrayList<String> tempArrayList = new ArrayList<String>();
                    tempArrayList.add("Default_Achievement_Button_Home_3");
                    tempArrayList.add(Integer.toString(R.id.Default_Achievement_Button_Home_3));
                    addAchievementToScreen(tempArrayList);
                } else if (!currentAchievements.contains(defaultAchievementButton4)) {
                    System.out.println("Adding achievement button 4 to screen");
                    ArrayList<String> tempArrayList = new ArrayList<String>();
                    tempArrayList.add("Default_Achievement_Button_Home_4");
                    tempArrayList.add(Integer.toString(R.id.Default_Achievement_Button_Home_4));
                    addAchievementToScreen(tempArrayList);
                } else if (!currentAchievements.contains(defaultAchievementButton5)) {
                    System.out.println("Adding achievement button 5 to screen");
                    ArrayList<String> tempArrayList = new ArrayList<String>();
                    tempArrayList.add("Default_Achievement_Button_Home_5");
                    tempArrayList.add(Integer.toString(R.id.Default_Achievement_Button_Home_5));
                    addAchievementToScreen(tempArrayList);
                }

                System.out.println(defaultAchievementButtonAnimation);
            }
        });

        //New_Game_Button_Home
        newGameButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setLettersToBlank();
                new Thread(new Runnable() {
                    public void run() {
                        loadingBar.post(new Runnable() {
                            public void run() {
                                loadingBar.setVisibility(View.VISIBLE);
                                loadingBar.invalidate();
                            }
                        });
                    }
                }).start();
                new Thread(new Runnable() {
                    public void run() {
                        writeToFile("NEWPLAYER == FALSE\n", HomeFragment.this.getActivity(), "saveFile.txt");
                        pickRandomLetters();
                        answerBox.post(new Runnable() {
                            public void run() {
                                answerBox.setText(getString(R.string.AnswerBox));
                            }
                        });
                        writeSave(saveFileStrings, HomeFragment.this.getActivity(), "saveFile.txt");
                        loadingBar.post(new Runnable() {
                            public void run() {
                                loadingBar.setVisibility(View.GONE);
                            }
                        });

                        setLetters();
                    }
                }).start();
            }
        });

        setLettersToBlank();

        readSaveFile(HomeFragment.this.getActivity());

        startPlaytimeTimer();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("onStop called");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("onResume called");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("onActivityCreated called");
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            //mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
            System.out.println("onActivityCreated changed mCurCheckPosition");
        }
    }

    private void readSaveFile(Context context) {
        BufferedReader reader = null;
        try {
            System.out.println("Opening saveFile");
            File file = new File(context.getFilesDir(), "saveFile.txt");
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Created new file");
            }
            FileInputStream fis = context.openFileInput("saveFile.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            reader = new BufferedReader(inputStreamReader);
            // do reading, usually loop until end of file reading
            System.out.println("Opened saveFile");
            String mLine;
            saveFileStrings.clear();
            while ((mLine = reader.readLine()) != null) {
                //process line
                String data = mLine.toUpperCase();
                System.out.println("Savefile: " + data);
                saveFileStrings.add(data);
            }
            System.out.println(saveFileStrings);
            boolean saveFileError = false;
            try {
                if (saveFileStrings.size() <= 8) {
                    saveFileError = true;
                }
                if (!saveFileStrings.get(0).equals("NEWPLAYER == FALSE")) {
                    saveFileError = true;
                    System.out.println("First string is not newplayer false");
                }
                for (int i = 1; i < 8; i++) {
                    if (!(saveFileStrings.get(i).length() == 1)) {
                        saveFileError = true;
                        System.out.println(i + " string is not a letter");
                    }
                }
                if (!saveFileStrings.get(8).matches("-?\\d+(\\.\\d+)?")) {
                    saveFileError = true;
                    System.out.println("9th string is not a number");
                }
                for (int i = 9; i < saveFileStrings.size(); i++) {
                    char[] chars = saveFileStrings.get(i).toCharArray();
                    for (char c : chars) {
                        if (!Character.isLetter(c)) {
                            saveFileError = true;
                            System.out.println(i + "th string is not a word");
                        }
                    }
                }

            } catch (IndexOutOfBoundsException e) {
                System.out.println("saveFileError =" + saveFileError);
            }
            reader.close();

            //preLoadFile
            try {
                System.out.println("Opening preLoadFile");
                file = new File(context.getFilesDir(), "preLoadFile.txt");
                if (!file.exists()) {
                    file.createNewFile();
                    System.out.println("Created new preLoadFile (This should never occur besides the first run after a new patch)");
                }
                fis = context.openFileInput("preLoadFile.txt");
                inputStreamReader = new InputStreamReader(fis);
                reader = new BufferedReader(inputStreamReader);
                // do reading, usually loop until end of file reading
                System.out.println("Opened preLoadFile");
                mLine = "";
                preLoadFileStrings.clear();
                while ((mLine = reader.readLine()) != null) {
                    //process line
                    String data = mLine.toUpperCase();
                    System.out.println("Preload: " + data);
                    preLoadFileStrings.add(data);
                }
                reader.close();
            } catch (IOException e) {
                //log the exception
                System.out.println("An exception occurred while reading preLoadFile");
            }

            //userStatsFile
            try {
                System.out.println("Opening userStatsFile");
                file = new File(context.getFilesDir(), "userStatsFile.txt");
                if (!file.exists()) {
                    file.createNewFile();
                    System.out.println("Created new userStatsFile (This should never occur besides the first run after a new patch)");
                    System.out.println("Filling with default values...");
                    //Words submitted (0), Longest word (none), Total points (0), Best score (0), Total Pangrams (0), Longest Pangram (none), Time Played (0)
                    String tempString = "0\nN/A\n0\n0\n0\nN/A\n0";
                    writeToFile(tempString, context, "userStatsFile.txt");
                }
                fis = context.openFileInput("userStatsFile.txt");
                inputStreamReader = new InputStreamReader(fis);
                reader = new BufferedReader(inputStreamReader);
                // do reading, usually loop until end of file reading
                System.out.println("Opened userStatsFile");
                mLine = "";
                userStatsFileStrings.clear();
                while ((mLine = reader.readLine()) != null) {
                    //process line
                    String data = mLine.toUpperCase();
                    System.out.println("UserStatsFile: " + data);
                    userStatsFileStrings.add(data);
                }
                for (int i = 0; i < 7; i++) {
                    try {
                        System.out.println(userStatsFileStrings.get(i));
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Missing data! ");
                        System.out.println("Filling in missing data.");
                        if (i == 1 || i == 5)
                            userStatsFileStrings.add(i, "N/A");
                        else
                            userStatsFileStrings.add(i, "0");
                    }
                }
                reader.close();
            } catch (IOException e) {
                //log the exception
                System.out.println("An exception occurred while reading userStatsFile");
            }

            //achievementFile
            try {
                System.out.println("Opening achievementFile");
                file = new File(context.getFilesDir(), "achievementFile.txt");
                if (!file.exists()) {
                    file.createNewFile();
                    System.out.println("Created new achievementFile (This should never occur besides the first run after a new patch)");
                    System.out.println("Creating with no achievements.");
                    writeToFile("", context, "achievementFile.txt");
                }
                fis = context.openFileInput("achievementFile.txt");
                inputStreamReader = new InputStreamReader(fis);
                reader = new BufferedReader(inputStreamReader);
                // do reading, usually loop until end of file reading
                System.out.println("Opened achievementFile");
                mLine = "";
                userAchievementFileStrings.clear();
                while ((mLine = reader.readLine()) != null) {
                    //process line
                    String data = mLine.toUpperCase();
                    System.out.println("achievementFile: " + data);
                    userAchievementFileStrings.add(data);
                }
                reader.close();
            } catch (IOException e) {
                //log the exception
                System.out.println("An exception occurred while reading achievementFile");
            }

            //settingsFile
            try {
                System.out.println("Opening settingsFile");
                file = new File(context.getFilesDir(), "settingsFile.txt");
                if (!file.exists()) {
                    file.createNewFile();
                    System.out.println("Created new settingsFile (This should never occur besides the first run after a new patch)");
                    System.out.println("Creating with default settings.");
                    writeToFile("", context, "settingsFile.txt");
                }
                fis = context.openFileInput("settingsFile.txt");
                inputStreamReader = new InputStreamReader(fis);
                reader = new BufferedReader(inputStreamReader);
                // do reading, usually loop until end of file reading
                System.out.println("Opened settingsFile");
                mLine = "";
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
                MainMenuNavigation.setMusicVolume(MainMenuNavigation.backgroundMusic, Integer.parseInt(settingsFileStrings.get(0)));
                reader.close();
            } catch (IOException e) {
                //log the exception
                System.out.println("An exception occurred while reading settingstFile");
            }

            //Update UI to reflect savefile
            if (saveFileError) {
                saveFileStrings.clear();
                saveFileStrings.add("NEWPLAYER == FALSE");
                saveFileStrings.add("A");
                saveFileStrings.add("B");
                saveFileStrings.add("C");
                saveFileStrings.add("D");
                saveFileStrings.add("E");
                saveFileStrings.add("F");
                saveFileStrings.add("G");
                saveFileStrings.add("0");
                //pickRandomLetters(null);
                //setLetters();

                new Thread(new Runnable() {
                    public void run() {
                        loadingBar.post(new Runnable() {
                            public void run() {
                                loadingBar.setVisibility(View.VISIBLE);
                                loadingBar.invalidate();
                            }
                        });
                    }
                }).start();
                new Thread(new Runnable() {
                    public void run() {
                        writeToFile("NEWPLAYER == FALSE\n", HomeFragment.this.getActivity(), "saveFile.txt");
                        pickRandomLetters();
                        answerBox.post(new Runnable() {
                            public void run() {
                                answerBox.setText(getString(R.string.AnswerBox));
                            }
                        });
                        writeSave(saveFileStrings, HomeFragment.this.getActivity(), "saveFile.txt");
                        loadingBar.post(new Runnable() {
                            public void run() {
                                loadingBar.setVisibility(View.GONE);
                            }
                        });

                        setLetters();
                    }
                }).start();
                System.out.println("saveFileError = TRUE");
            } else {

                new Thread(new Runnable() {
                    public void run() {
                        loadingBar.post(new Runnable() {
                            public void run() {
                                loadingBar.setVisibility(View.VISIBLE);
                                loadingBar.invalidate();
                            }
                        });
                    }
                }).start();
                new Thread(new Runnable() {
                    public void run() {
                        writeToFile("NEWPLAYER == FALSE\n", HomeFragment.this.getActivity(), "saveFile.txt");
                        pickLetters();
                        answerBox.post(new Runnable() {
                            public void run() {
                                answerBox.setText(getString(R.string.AnswerBox));
                            }
                        });
                        writeSave(saveFileStrings, HomeFragment.this.getActivity(), "saveFile.txt");
                        loadingBar.post(new Runnable() {
                            public void run() {
                                loadingBar.setVisibility(View.GONE);
                            }
                        });

                        setLetters();
                    }
                }).start();
                System.out.println("saveFileError = FALSE");
            }
//            writeSave(saveFileStrings, context, "saveFile.txt");
//            writeSave(userStatsFileStrings, context, "userStatsFile.txt");
//            writeSave(userAchievementFileStrings, context, "achievementFile.txt");
//            writeSave(settingsFileStrings, context, "settingsFile.txt");

        } catch (IOException e) {
            //log the exception
            System.out.println("An exception occurred while reading saveFile");
        }
    }

    private void writeSave(ArrayList<String> stringArrayList, Context context, String filename) {
        switch (filename) {
            case "saveFile.txt":
                try {
                    writeToFile("", context, filename);
                    String tempString = "NEWPLAYER == FALSE\n";
                    for (int i = 1; i < 8; i++) {
                        tempString += stringArrayList.get(i) + "\n";
                    }
                    tempString += score + "\n";
                    for (int i = 9; i < saveFileStrings.size(); i++) {
                        tempString += saveFileStrings.get(i) + "\n";
                    }
                    appendToFile(tempString, context, filename);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Error while saving saveFileStrings.");
                }
                break;
            case "userStatsFile.txt":
                try {
                    writeToFile("", context, filename);
                    String tempString = "";
                    for (int i = 0; i < userStatsFileStrings.size(); i++) {
                        tempString += userStatsFileStrings.get(i) + "\n";
                    }
                    appendToFile(tempString, context, filename);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Error while saving userStatsFileStrings.");
                }
                break;
            case "achievementFile.txt":
                try {
                    writeToFile("", context, filename);
                    String tempString = "";
                    for (int i = 0; i < userAchievementFileStrings.size(); i++) {
                        tempString += userAchievementFileStrings.get(i) + "\n";
                    }
                    appendToFile(tempString, context, filename);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Error while saving achievementFileStrings.");
                }
                break;
            case "settingsFile.txt":
                try {
                    writeToFile("", context, filename);
                    String tempString = "";
                    for (int i = 0; i < settingsFileStrings.size(); i++) {
                        tempString += settingsFileStrings.get(i) + "\n";
                    }
                    appendToFile(tempString, context, filename);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Error while saving settingsFileStrings.");
                }
                break;
            default:
                System.out.println("WriteSave is not defined for " + filename + ". Did you mean to use writeToFile?");
                break;
        }
    }

    private void appendToFile(String data, Context context, String filename) {
        File file = new File(mContext.getFilesDir(), filename);
        System.out.println(file);
        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Created new file");
            }
            //FileOutputStream fos = context.openFileOutput(filename, Context.MODE_APPEND);
            FileOutputStream fos = mContext.openFileOutput(filename, Context.MODE_APPEND);
            OutputStreamWriter bw = new OutputStreamWriter(fos);
            bw.append(data);
            bw.flush();
            bw.close();
            System.out.println("Appended to file: " + fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(String data, Context context, String filename) {
        File file = new File(mContext.getFilesDir(), filename);
        System.out.println(file);
        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Created new file");
            }
            FileOutputStream fos = mContext.openFileOutput(filename, Context.MODE_PRIVATE);
            OutputStreamWriter bw = new OutputStreamWriter(fos);
            bw.append(data);
            bw.flush();
            bw.close();
            System.out.println("Wrote to file: " + fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void animateTextViewColors(TextView textView, Integer colorTo) {

        final Property<TextView, Integer> property = new Property<TextView, Integer>(int.class, "textColor") {
            @Override
            public Integer get(TextView object) {
                return object.getCurrentTextColor();
            }

            @Override
            public void set(TextView object, Integer value) {
                object.setTextColor(value);
            }
        };


        final ObjectAnimator animator = ObjectAnimator.ofInt(textView, property, colorTo);
        //Higher = longer duration of animation
        animator.setDuration(4533L);
        animator.setEvaluator(new ArgbEvaluator());
        //Higher = more gradual fade of animation
        animator.setInterpolator(new DecelerateInterpolator((float) 0.5));
        animator.start();
    }

    public static boolean contains(final String[] array, final String v) {
        boolean result = false;
        for (String i : array) {
            if (i.equals(v)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private void pickLetters() {
        final Button[] tempButtons = {textButton1, textButton2, textButton3, textButton4, textButton5, textButton6, textButton7};

        //Selects the saved 7 letters
        final String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String[] usedLetters = {"", "", "", "", "", "", ""};
        for (int i = 0; i < 7; i++) {
            usedLetters[i] = saveFileStrings.get(i + 1);
        }

        String tempString = "";
        for (String usedLetter : usedLetters) tempString += usedLetter + "\n";
        appendToFile(tempString, HomeFragment.this.getActivity(), "saveFile.txt");

        possibleWords.clear();
        submittedWords.clear();

        try {
            for (int i = 9; i < saveFileStrings.size(); i++) {
                submittedWords.add(saveFileStrings.get(i));
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No words found");
        }

        try {
            score = parseInt(saveFileStrings.get(8));
        } catch (IndexOutOfBoundsException e) {
            score = 0;
            saveFileStrings.add(8, "0");
            System.out.println("Could not find score");
        }

        for (int j = 0; j < submittedWords.size(); j++) {
            boolean containsAll = true;
            for (int i = 0; i < 7; i++) {
                if (!(submittedWords.get(j).contains(tempButtons[i].getText().toString()))) {
                    containsAll = false;
                }
            }
            if (containsAll) {
                wordList.append(submittedWords.get(j) + " (+" + 2 * submittedWords.get(j).length() + ")\n");
            } else {
                wordList.append(submittedWords.get(j) + " (+" + submittedWords.get(j).length() + ")\n");
            }
        }

        maxScore = 0;

        if (preLoadFileStrings.size() != 0) {
            for (int i = 1; i < preLoadFileStrings.size(); i++) {
                possibleWords.add(preLoadFileStrings.get(i));
            }
            maxScore = parseInt(preLoadFileStrings.get(0));
        } else {
            //Should never run unless there is a file error
            System.out.println("There was a file error in preLoadFile. Creating new possiblewordlist.");
            BufferedReader reader = null;
            try {
                System.out.println("Beginning 4");
                String filename;
                try {
                    if (Boolean.parseBoolean(settingsFileStrings.get(1))) {
                        filename = "scrabbleWordList.txt";
                    } else {
                        filename = "commonWordList.txt";
                    }
                } catch (NullPointerException e) {
                    filename = "scrabbleWordList.txt";
                }
                reader = new BufferedReader(
                        new InputStreamReader(getActivity().getAssets().open(filename)));
                // do reading, usually loop until end of file reading
                String mLine;
                while ((mLine = reader.readLine()) != null) {
                    //process line
                    String data = mLine.toUpperCase();
                    boolean isPossibleWord = true;
                    for (int i = 0; i < 26; i++) {
                        if (!(data.contains(usedLetters[3])) || (data.length() <= 2) || !(contains(usedLetters, alphabet[i])) && data.contains(alphabet[i])) {
                            isPossibleWord = false;
                        }
                    }
                    if (isPossibleWord) {
                        possibleWords.add(data);

                        boolean containsAll = true;
                        for (int i = 0; i < 7; i++) {
                            if (!(data.contains(tempButtons[i].getText().toString()))) {
                                containsAll = false;
                                break;
                            }
                        }
                        if (containsAll) {
                            maxScore += 2 * data.length();
                        } else {
                            maxScore += data.length();
                        }

                    }

                }
                writeToFile(Integer.toString(maxScore) + "\n", HomeFragment.this.getActivity(), "preLoadFile.txt");
                tempString = "";
                for (int i = 0; i < possibleWords.size(); i++) {
                    tempString += possibleWords.get(i) + "\n";
                }
                appendToFile(tempString, HomeFragment.this.getActivity(), "preLoadFile.txt");
                System.out.println(possibleWords);
            } catch (IOException e) {
                //log the exception
                System.out.println("An exception occurred! 1");
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        System.out.println("An exception occurred! 2");
                    }
                }
            }
        }

        newLettersStrings.clear();
        for (int i = 1; i < 8; i++) {
            newLettersStrings.add(saveFileStrings.get(i));
        }
        newLettersStrings.add(Integer.toString(score));
        newLettersStrings.add(Integer.toString(maxScore));
        System.out.println("pickLetters called");
    }

    private void pickRandomLetters() {
        //Picks a random set of 7 unique letters - not guaranteed to have possible words (like 7 consonants)
        final String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String[] usedLetters = {"", "", "", "", "", "", ""};
        Random rand = new Random();

        //From a list of all words with exactly 7 different letters, pick a random one to make the set of buttons
        BufferedReader reader = null;
        ArrayList<String> pangramList = new ArrayList<String>();
        try {
            System.out.println("Beginning 1");
            String filename;
            try {
                if (Boolean.parseBoolean(settingsFileStrings.get(1))) {
                    filename = "scrabblePangramList.txt";
                } else {
                    filename = "commonPangramList.txt";
                }
            } catch (NullPointerException e) {
                filename = "scrabblePangramList.txt";
            }
            reader = new BufferedReader(
                    new InputStreamReader(getActivity().getAssets().open(filename)));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                String data = mLine.toUpperCase();
                pangramList.add(data);
            }
            System.out.println(pangramList);
            int randInt = rand.nextInt(pangramList.size());
            String pangramWord = pangramList.get(randInt).toUpperCase();
            System.out.println("PangramWord = " + pangramWord);
            int filledLetters = 0;
            while (true) {
                randInt = rand.nextInt(pangramWord.length());
                while (!(contains(usedLetters, pangramWord.substring(randInt, randInt + 1)))) {
                    System.out.println("Letter " + pangramWord.substring(randInt, randInt + 1) + " added to buttons");
                    usedLetters[filledLetters] = pangramWord.substring(randInt, randInt + 1);
                    saveFileStrings.set(filledLetters + 1, usedLetters[filledLetters]);
                    filledLetters += 1;
                }
                if (filledLetters == 7) {
                    break;
                }
            }
            System.out.println("Made it");

        } catch (IOException e) {
            //log the exception
            System.out.println("An exception occurred! 1");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("An exception occurred! 2");
                }
            }
        }

        for (int i = 0; i < usedLetters.length; i++)
            System.out.println(usedLetters[i]);

        possibleWords.clear();
        submittedWords.clear();

        try {
            for (int i = saveFileStrings.size() - 1; i > 8; i--) {
                saveFileStrings.remove(i);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No words found");
        }

        score = 0;
        maxScore = 0;

        reader = null;
        try {
            System.out.println("Beginning 4");
            String filename;
            try {
                if (Boolean.parseBoolean(settingsFileStrings.get(1))) {
                    filename = "scrabbleWordList.txt";
                } else {
                    filename = "commonWordList.txt";
                }
            } catch (NullPointerException e) {
                filename = "scrabbleWordList.txt";
            }
            reader = new BufferedReader(
                    new InputStreamReader(getActivity().getAssets().open(filename)));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                String data = mLine.toUpperCase();
                boolean isPossibleWord = true;
                for (int i = 0; i < 26; i++) {
                    if (!(data.contains(usedLetters[3])) || (data.length() <= 2) || !(contains(usedLetters, alphabet[i])) && data.contains(alphabet[i])) {
                        isPossibleWord = false;
                    }
                }
                if (isPossibleWord) {
                    possibleWords.add(data);
                    boolean containsAll = true;
                    for (int i = 0; i < 7; i++) {
                        if (!(data.contains(usedLetters[i]))) {
                            containsAll = false;
                            break;
                        }
                    }
                    if (containsAll) {
                        maxScore += 2 * data.length();
                    } else {
                        maxScore += data.length();
                    }
                }
            }
            writeToFile(Integer.toString(maxScore) + "\n", HomeFragment.this.getActivity(), "preLoadFile.txt");
            String tempString = "";
            for (int i = 0; i < possibleWords.size(); i++) {
                tempString += possibleWords.get(i) + "\n";
            }
            appendToFile(tempString, HomeFragment.this.getActivity(), "preLoadFile.txt");
            System.out.println(possibleWords);
        } catch (IOException e) {
            //log the exception
            System.out.println("An exception occurred! 1");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("An exception occurred! 2");
                }
            }
        }

        newLettersStrings.clear();
        for (int i = 1; i < 8; i++) {
            newLettersStrings.add(saveFileStrings.get(i));
        }
        newLettersStrings.add(Integer.toString(score));
        newLettersStrings.add(Integer.toString(maxScore));
        System.out.println("pickRandomLetters called");
    }

    private void setLettersToBlank() {
        Button[] tempButtons = {textButton1, textButton2, textButton3, textButton4, textButton5, textButton6, textButton7};
        for (int i = 0; i < 7; i++) {
            tempButtons[i].setText("");
            tempButtons[i].setEnabled(false);
        }
        eraseButton.setEnabled(false);
        shuffleButton.setEnabled(false);
        submitButton.setEnabled(false);
        newGameButton.setEnabled(false);
    }

    private void setLetters() {
        textButton1.post(new Runnable() {
            public void run() {
                textButton1.setText(newLettersStrings.get(0));
                textButton1.setEnabled(true);
            }
        });

        textButton2.post(new Runnable() {
            public void run() {
                textButton2.setText(newLettersStrings.get(1));
                textButton2.setEnabled(true);
            }
        });

        textButton3.post(new Runnable() {
            public void run() {
                textButton3.setText(newLettersStrings.get(2));
                textButton3.setEnabled(true);
            }
        });

        textButton4.post(new Runnable() {
            public void run() {
                textButton4.setText(newLettersStrings.get(3));
                textButton4.setEnabled(true);
            }
        });

        textButton5.post(new Runnable() {
            public void run() {
                textButton5.setText(newLettersStrings.get(4));
                textButton5.setEnabled(true);
            }
        });

        textButton6.post(new Runnable() {
            public void run() {
                textButton6.setText(newLettersStrings.get(5));
                textButton6.setEnabled(true);
            }
        });

        textButton7.post(new Runnable() {
            public void run() {
                textButton7.setText(newLettersStrings.get(6));
                textButton7.setEnabled(true);
            }
        });

        eraseButton.post(new Runnable() {
            public void run() {
                eraseButton.setEnabled(true);
            }
        });

        shuffleButton.post(new Runnable() {
            public void run() {
                shuffleButton.setEnabled(true);
            }
        });

        submitButton.post(new Runnable() {
            public void run() {
                submitButton.setEnabled(true);
            }
        });

        newGameButton.post(new Runnable() {
            public void run() {
                newGameButton.setEnabled(true);
            }
        });

        wordList.post(new Runnable() {
            public void run() {
                wordList.setText(getString(R.string.WordList) + "\n");
                final Button[] tempButtons = {textButton1, textButton2, textButton3, textButton4, textButton5, textButton6, textButton7};
                for (int j = 0; j < submittedWords.size(); j++) {
                    boolean containsAll = true;
                    for (int i = 0; i < 7; i++) {
                        if (!(submittedWords.get(j).contains(tempButtons[i].getText().toString()))) {
                            containsAll = false;
                        }
                    }
                    if (containsAll) {
                        wordList.append(submittedWords.get(j) + " (+" + 2 * submittedWords.get(j).length() + ")\n");
                    } else {
                        wordList.append(submittedWords.get(j) + " (+" + submittedWords.get(j).length() + ")\n");
                    }
                }
            }
        });

        score = Integer.parseInt(newLettersStrings.get(7));
        maxScore = Integer.parseInt(newLettersStrings.get(8));
        scoreTracker.post(new Runnable() {
            public void run() {
                scoreTracker.setText("Score: " + score + "/" + maxScore);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void addAchievementToScreen(ArrayList<String> achievementInformation) {
        final Button achievementButton;
        switch(achievementInformation.get(0)) {
            case "Default_Achievement_Button_Home":
                achievementButton = defaultAchievementButton;
                break;
            case "Default_Achievement_Button_Home_2":
                achievementButton = defaultAchievementButton2;
                break;
            case "Default_Achievement_Button_Home_3":
                achievementButton = defaultAchievementButton3;
                break;
            case "Default_Achievement_Button_Home_4":
                achievementButton = defaultAchievementButton4;
                break;
            case "Default_Achievement_Button_Home_5":
                achievementButton = defaultAchievementButton5;
                break;
            case "Achievement_Button_Pangram_Home":
                achievementButton = pangramAchievementButton;
                break;
            case "Achievement_Button_Word_Progress_10_Home":
                achievementButton = wordProgress10AchievementButton;
                break;
            case "Achievement_Button_Word_Progress_25_Home":
                achievementButton = wordProgress25AchievementButton;
                break;
            case "Achievement_Button_Word_Progress_50_Home":
                achievementButton = wordProgress50AchievementButton;
                break;
            case "Achievement_Button_Longest_Word_7_Home":
                achievementButton = longWord8AchievementButton;
                break;
            case "Achievement_Button_Longest_Word_9_Home":
                achievementButton = longWord10AchievementButton;
                break;
            case "Achievement_Button_Longest_Word_11_Home":
                achievementButton = longWord12AchievementButton;
                break;
            case "Achievement_Button_Longest_Word_13_Home":
                achievementButton = longWord15AchievementButton;
                break;
            case "Achievement_Button_Speed_Demon_1_Home":
                achievementButton = speedDemon1AchievementButton;
                break;
            case "Achievement_Button_Speed_Demon_2_Home":
                achievementButton = speedDemon2AchievementButton;
                break;
            case "Achievement_Button_Playtime_Home":
                achievementButton = playtimeAchievementButton;
                break;
            default:
                achievementButton = defaultAchievementButton;
                break;
        }
        achievementButton.setVisibility(View.VISIBLE);
        achievementButton.setAlpha(1.0f);
        achievementButton.bringToFront();

        //If not the first achievement, set Y to be lower
        float end = scoreTrackerScrollView.getY() + getPixelsFromDp(20 + 40 * currentAchievements.size());
        achievementButton.setX(scoreTrackerScrollView.getX());
        achievementButton.setY(end);

        //Always run
        if (!currentAchievements.contains(achievementButton)) {
            currentAchievements.add(achievementButton);
        }
        achievementButton.animate().alpha(0).setDuration(10000).withStartAction(new Runnable() {
            @Override
            public void run() {
                System.out.println("Fade animation started!");
                achievementButton.setVisibility(View.VISIBLE);
            }
        }).withEndAction(new Runnable() {
            @Override
            public void run() {
                System.out.println("Fade animation finished!");
                try {
                    currentAchievements.remove(achievementButton);
                    updateAchievementLocations();
                } catch (NullPointerException e) {
                    System.out.println("achievement was already removed from arraylist");
                }
                achievementButton.setVisibility(View.GONE);
            }
        });

        achievementButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Animation canceled early while fading!");
                try {
                    currentAchievements.remove(achievementButton);
                    updateAchievementLocations();
                } catch (NullPointerException e) {
                    System.out.println("achievement was already removed from arraylist");
                }
                achievementButton.setVisibility(View.GONE);
            }
        });
    }

    private void updateAchievementLocations() {
        //Runs whenever a fade animation finishes or an achievement is clicked so that it is removed.
        //Creates new translation animations for all remaining achievements.
        System.out.println("Updating achievement button locations!");
        System.out.println("Sizeof currentAchievements: " + currentAchievements.size());
        for (int i = 0; i < currentAchievements.size(); i++) {
            final Button achievementButton = currentAchievements.get(i);
            float end = scoreTrackerScrollView.getY();
            end += getPixelsFromDp(20 + 40 * i);
            System.out.println(end);
            final ObjectAnimator achievementButtonAnimation = ObjectAnimator.ofFloat(achievementButton, "y", end);
            System.out.println("default animation: " + defaultAchievementButtonAnimation);
            achievementButtonAnimation.setDuration(1000);
            achievementButtonAnimation.start();
        }
    }

    public float getPixelsFromDp(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        float pixels = (int) (dp * scale + 0.5f);
        return pixels;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void updateSpeedDemonCounter() {
        speedDemonCounter += 1;
        System.out.println("speedDemon is now: " + speedDemonCounter);
        if (speedDemonCounter == 10 && !(userAchievementFileStrings.contains("QUICK AS LIGHTNING!"))) {
            ArrayList<String> tempArrayList = new ArrayList<String>();
            tempArrayList.add("Achievement_Button_Speed_Demon_1_Home");
            tempArrayList.add(Integer.toString(R.id.Achievement_Button_Speed_Demon_1_Home));
            addAchievementToScreen(tempArrayList);
            userAchievementFileStrings.add("QUICK AS LIGHTNING!");
            writeSave(userAchievementFileStrings, HomeFragment.this.getActivity(), "achievementFile.txt");
        }

        if (speedDemonCounter == 20 && !(userAchievementFileStrings.contains("SPEED DEMON!"))) {
            ArrayList<String> tempArrayList = new ArrayList<String>();
            tempArrayList.add("Achievement_Button_Speed_Demon_2_Home");
            tempArrayList.add(Integer.toString(R.id.Achievement_Button_Speed_Demon_2_Home));
            addAchievementToScreen(tempArrayList);
            userAchievementFileStrings.add("SPEED DEMON!");
            writeSave(userAchievementFileStrings, HomeFragment.this.getActivity(), "achievementFile.txt");
        }

        try {
            speedDemonHandler.removeCallbacksAndMessages(null);
        } catch (Exception e) {
            System.out.println("No runnable to remove.");
        }
        speedDemonHandler = new Handler();
        speedDemonHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                speedDemonCounter = 0;
                System.out.println("speedDemon was reset to 0.");
            }
        }, 10000);
    }

    private void startPlaytimeTimer(){
        if (playtimeHandler == null) {
            System.out.println("time: playtimeHandler isNull");
            playtimeHandler = new Handler();
            playtimeHandler.postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                public void run() {
                    try {
                        userStatsFileStrings.set(6, Integer.toString(Integer.parseInt((userStatsFileStrings.get(6))) + 1));
                        System.out.println("time: " + userStatsFileStrings.get(6));
                    } catch (NumberFormatException e) {
                        userStatsFileStrings.set(6, "0");
                        System.out.println("time: " + userStatsFileStrings.get(6));
                    }
                    if (!(userAchievementFileStrings.contains("1 HOUR PLAYED!")) && Integer.parseInt(userStatsFileStrings.get(6)) >= 3600) {
                        ArrayList<String> tempArrayList = new ArrayList<String>();
                        tempArrayList.add("Achievement_Button_Playtime_Home");
                        tempArrayList.add(Integer.toString(R.id.Achievement_Button_Playtime_Home));
                        addAchievementToScreen(tempArrayList);
                        userAchievementFileStrings.add("1 HOUR PLAYED!");
                        writeSave(userAchievementFileStrings, HomeFragment.this.getActivity(), "achievementFile.txt");
                    }
                    writeSave(userStatsFileStrings, HomeFragment.this.getContext(), "userStatsFile.txt");
                    playtimeHandler.postDelayed(this, 1000);
                }
            },1000);
            System.out.println("time: playtimeHandler started");
        }
        else {
            playtimeHandler.removeCallbacksAndMessages(null);
            playtimeHandler = null;
            startPlaytimeTimer();
        }
    }


}