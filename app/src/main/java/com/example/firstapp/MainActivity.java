package com.example.firstapp;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Property;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
//Not currently in use.

//camelCase = variable
//UpperCase = class or instance
//Upper_Underscore = widget or view
//ALL_CAPS = constant

//Sort words by alphabet (after implementing storing list of words to check from) and scroll to most recently added word
//Button to sort by alphabet or by most recent
//Implement score tracker/level system
//Common word list seems to be missing some common ish words.
//Create better word list, remove some words
//Add start screen/menu, add multiple pages
//Create progress bar for score completion
//Create loading bar
//Get an icon
//Create space for google ad banner, implement ads
//Create settings page with sound, word list, ad options
//Achievement list?
//Sounds


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private static ArrayList<String> saveFileStrings = new ArrayList<String>();
    public static ArrayList<String> possibleWords = new ArrayList<String>();
    public static ArrayList<String> submittedWords = new ArrayList<String>();
    public static int score = 0;
    public static int maxScore = 0;
    private static int enableCheat = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readSaveFile(getApplicationContext());

    }

    public void readSaveFile (Context context) {
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
                System.out.println(data);
                saveFileStrings.add(data);
            }

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
                    if (!(saveFileStrings.get(i).length() == 1)){
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
                        if(!Character.isLetter(c)) {
                            saveFileError = true;
                            System.out.println(i + "th string is not a word");
                        }
                    }
                }

            } catch (IndexOutOfBoundsException e){
                System.out.println("saveFileError =" + saveFileError);
            }

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
                setRandomLetters(null);
                writeSave(saveFileStrings, getApplicationContext(), "saveFile.txt");
                System.out.println("saveFileError = TRUE");
            }
            else {
                setLetters(null, saveFileStrings);
                writeSave(saveFileStrings, getApplicationContext(), "saveFile.txt");
                System.out.println("saveFileError = FALSE");
            }

            System.out.println("Stored to string");
            System.out.println(saveFileStrings);
            reader.close();
        } catch (IOException e) {
            //log the exception
            System.out.println("An exception occurred while reading saveFile");
        }
    }

    private void writeSave(ArrayList<String> stringArrayList, Context context, String filename) {
        try {
            writeToFile("", context, filename);
            appendToFile("NEWPLAYER == FALSE\n", context, filename);
            for (int i = 1; i < 8; i++) {
                appendToFile(stringArrayList.get(i) + "\n", context, filename);
            }
            appendToFile(score + "\n", context, filename);
            for (int i = 9; i < saveFileStrings.size(); i++) {
                appendToFile(saveFileStrings.get(i) + "\n", context, filename);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error while saving.");
        }
    }

    private void appendToFile(String data, Context context, String filename) {
        File file = new File(context.getFilesDir(), filename);
        System.out.println("Appending to file");
        System.out.println(file);
        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Created new file");
            }
            FileOutputStream fos = openFileOutput(filename, Context.MODE_APPEND);
            System.out.println(fos);
            OutputStreamWriter bw = new OutputStreamWriter(fos);
            bw.append(data);
            bw.flush();
            bw.close();
            System.out.println("Appended to file");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Appended " + data);
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
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
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

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void animateTextViewColors(TextView textView, Integer colorTo) {

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
        animator.setInterpolator(new DecelerateInterpolator((float)0.5));
        animator.start();
    }

    public static boolean contains(final String[] array, final String v) {
        boolean result = false;
        for(String i : array){
            if(i.equals(v)){
                result = true;
                break;
            }
        }
        return result;
    }

    public void setLetters(View view, ArrayList<String> stringArrayList) {
        final Button[] tempButtons = {
                (Button) findViewById(R.id.Add_Text_Button_1),
                (Button) findViewById(R.id.Add_Text_Button_2),
                (Button) findViewById(R.id.Add_Text_Button_3),
                (Button) findViewById(R.id.Add_Text_Button_4),
                (Button) findViewById(R.id.Add_Text_Button_5),
                (Button) findViewById(R.id.Add_Text_Button_6),
                (Button) findViewById(R.id.Add_Text_Button_7)
        };

        //Selects the saved 7 letters
        final String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String[] usedLetters = {"","","","","","",""};
        for (int i = 0; i < 7; i++) {
            usedLetters[i] = stringArrayList.get(i + 1);
            tempButtons[i].setText(usedLetters[i]);
        }

        for (int i = 0; i < usedLetters.length; i++)
            appendToFile(usedLetters[i] + "\n", getApplicationContext(), "saveFile.txt");

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


        boolean containsAll = true;
        ScrollView wordListScrollView = findViewById(R.id.Word_List_Scroll_View);
        TextView wordList = findViewById(R.id.Word_List);
        for (int j = 0; j < submittedWords.size(); j++) {
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
        TextView scoreTracker = findViewById(R.id.Score_Tracker);

        BufferedReader reader = null;
        try {
            System.out.println("Beginning 4");
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("scrabbleWordList.txt")));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                String data = mLine.toUpperCase();
                boolean isPossibleWord = true;
                for (int i = 0; i < 26; i++) {
                    if (!(data.contains(usedLetters[3])) || (data.length() <= 2) || !(contains(usedLetters, alphabet[i])) && data.contains(alphabet[i]) ) {
                        isPossibleWord = false;
                    }
                }
                if (isPossibleWord) {
                    possibleWords.add(data);

                    containsAll = true;
                    for (int i = 0; i < 7; i++) {
                        if (!(data.contains(tempButtons[i].getText().toString()))) {
                            containsAll = false;
                            break;
                        }
                    }
                    if (containsAll) {
                        maxScore += 2*data.length();
                    }
                    else {
                        maxScore += data.length();
                    }

                }

            }
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
        scoreTracker.setText("Score: " + score + "/" + maxScore);
    }

    public void setRandomLetters(View view) {
        final Button[] tempButtons = {
                (Button) findViewById(R.id.Add_Text_Button_1),
                (Button) findViewById(R.id.Add_Text_Button_2),
                (Button) findViewById(R.id.Add_Text_Button_3),
                (Button) findViewById(R.id.Add_Text_Button_4),
                (Button) findViewById(R.id.Add_Text_Button_5),
                (Button) findViewById(R.id.Add_Text_Button_6),
                (Button) findViewById(R.id.Add_Text_Button_7)
        };

        //Picks a random set of 7 unique letters - not guaranteed to have possible words (like 7 consonants)
        final String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String[] usedLetters = {"","","","","","",""};
        Random rand = new Random();

        //From a list of all words with exactly 7 different letters, pick a random one to make the set of buttons
        BufferedReader reader = null;
        ArrayList<String> pangramList = new ArrayList<String>();
        try {
            System.out.println("Beginning 1");
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("pangramLongList.txt")));

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
            while(true) {
                randInt = rand.nextInt(pangramWord.length());
                while(!(contains(usedLetters, pangramWord.substring(randInt, randInt+1)))) {
                    System.out.println("Letter " + pangramWord.substring(randInt, randInt+1) + " added to buttons");
                    usedLetters[filledLetters] = pangramWord.substring(randInt, randInt+1);
                    tempButtons[filledLetters].setText(usedLetters[filledLetters]);
                    saveFileStrings.set(filledLetters+1, usedLetters[filledLetters]);
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

        TextView wordList = findViewById(R.id.Word_List);
        wordList.setText(R.string.WordList);

        try {
            for (int i = saveFileStrings.size()-1; i > 8; i--) {
                saveFileStrings.remove(i);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No words found");
        }

        score = 0;
        maxScore = 0;
        TextView scoreTracker = findViewById(R.id.Score_Tracker);

        reader = null;
        try {
            System.out.println("Beginning 4");
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("scrabbleWordList.txt")));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                String data = mLine.toUpperCase();
                boolean isPossibleWord = true;
                for (int i = 0; i < 26; i++) {
                    if (!(data.contains(usedLetters[3])) || (data.length() <= 2) || !(contains(usedLetters, alphabet[i])) && data.contains(alphabet[i]) ) {
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
                        maxScore += 2*data.length();
                    }
                    else {
                        maxScore += data.length();
                    }
                }
            }
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
        scoreTracker.setText("Score: " + score + "/" + maxScore);
    }

    /** Called when the user taps the Send button, currently obsolete */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        if (message.equals("")) {
            TextView answerBox = findViewById(R.id.Answer_Box);
            answerBox.setText("Enter a word first!");
        }
        else {
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }
    }

    public void submitWord(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        TextView answerBox = findViewById(R.id.Answer_Box);
        TextView messageBox = findViewById(R.id.Message_Box);
        if (enableCheat%2 == 1) {
            enableCheat += 1;
            if (enableCheat == 20) {
                TextView wordList = findViewById(R.id.Word_List);
                for (int i = 0; i < possibleWords.size(); i++) {
                    wordList.append(possibleWords.get(i) + ", ");
                }
                wordList.append("\n");
                enableCheat = -10000000;
            }
        }
        else {
            enableCheat = 0;
        }

        if (answerBox.getText().toString().equals(getString(R.string.AnswerBox)) || answerBox.getText().toString().equals("Enter a word first!") || answerBox.getText().toString().equals("")) {
            answerBox.setText("Enter a word first!");
        }
        else {
            String answer = answerBox.getText().toString().toUpperCase();
            messageBox.setTextColor(ContextCompat.getColor(this, R.color.red));
            animateTextViewColors(messageBox, 0);
            if (submittedWords.contains(answer)) {
                answerBox.setText(getString(R.string.AnswerBox));
                messageBox.setText("Word already submitted!");

            }
            else if (answer.length() >= 3 && !(answer.contains(((Button) findViewById(R.id.Add_Text_Button_4)).getText().toString()))){
                answerBox.setText(getString(R.string.AnswerBox));
                messageBox.setText("Word must contain the center letter (" + ((Button) findViewById(R.id.Add_Text_Button_4)).getText().toString() + ")");
            }
            else if (!(answer.length() >= 3) && (answer.contains(((Button) findViewById(R.id.Add_Text_Button_4)).getText().toString()))){
                answerBox.setText(getString(R.string.AnswerBox));
                messageBox.setText("Word must have at least 3 letters");
            }
            else if (!(answer.length() >= 3) && !(answer.contains(((Button) findViewById(R.id.Add_Text_Button_4)).getText().toString()))){
                answerBox.setText(getString(R.string.AnswerBox));
                messageBox.setText("Word must have at least 3 letters and contain the center letter (" + ((Button) findViewById(R.id.Add_Text_Button_4)).getText().toString() + ")");
            }
            else if (!(possibleWords.contains(answer))) {
                System.out.println("Word " + answer + " not in possible words");
                answerBox.setText(getString(R.string.AnswerBox));
                messageBox.setText("Not a valid word!");
            }
            else if (answer.length() >= 3 && answer.contains(((Button) findViewById(R.id.Add_Text_Button_4)).getText().toString())) {
                ScrollView wordListScrollView = findViewById(R.id.Word_List_Scroll_View);
                TextView wordList = findViewById(R.id.Word_List);
                TextView scoreTracker = findViewById(R.id.Score_Tracker);
                messageBox.setTextColor(ContextCompat.getColor(this, R.color.green));
                animateTextViewColors(messageBox,0);
                // find the amount we need to scroll.  This works by
                // asking the TextView's internal layout for the position
                // of the final line and then subtracting the TextView's height
                //final int scrollAmount = wordList.getLayout().getLineTop(wordList.getLineCount()) - wordList.getHeight();
                // if there is no need to scroll, scrollAmount will be <=0
                wordListScrollView.smoothScrollTo(0, wordList.getHeight());
                answerBox.setText(getString(R.string.AnswerBox));
                boolean containsAll = true;
                final Button[] tempButtons = {
                        (Button) findViewById(R.id.Add_Text_Button_1),
                        (Button) findViewById(R.id.Add_Text_Button_2),
                        (Button) findViewById(R.id.Add_Text_Button_3),
                        (Button) findViewById(R.id.Add_Text_Button_4),
                        (Button) findViewById(R.id.Add_Text_Button_5),
                        (Button) findViewById(R.id.Add_Text_Button_6),
                        (Button) findViewById(R.id.Add_Text_Button_7)
                };
                for (int i = 0; i < 7; i++) {
                    if (!(answer.contains(tempButtons[i].getText().toString()))) {
                        containsAll = false;
                        break;
                    }
                }
                if (containsAll) {
                    messageBox.setText("Pangram! +" + 2*answer.length());
                    wordList.append(answer + " (+" + 2*answer.length() + ")\n");
                    score += 2*answer.length();
                    scoreTracker.setText("Score: " + score + "/" + maxScore);
                    if (score == maxScore) {
                        scoreTracker.append("\nPerfect!");
                    }
                    saveFileStrings.set(8,Integer.toString(score));
                }
                else {
                    messageBox.setText("+" + answer.length());
                    wordList.append(answer + " (+" + answer.length() + ")\n");
                    score += answer.length();
                    scoreTracker.setText("Score: " + score + "/" + maxScore);
                    if (score == maxScore) {
                        scoreTracker.append("\nPerfect!");
                    }
                    saveFileStrings.set(8,Integer.toString(score));
                }
                submittedWords.add(answer);
                saveFileStrings.add(answer);
                writeSave(saveFileStrings, getApplicationContext(), "saveFile.txt");
            }
        }
    }

    public void addLetter(View view) {
        final Button tempButton = (Button) findViewById(view.getId());
        String tempButtonText = tempButton.getText().toString();
        TextView answerBox = findViewById(R.id.Answer_Box);
        enableCheat = 0;
        if (answerBox.getText().toString().equals(getString(R.string.AnswerBox)) || answerBox.getText().toString().equals("Enter a word first!")) {
            answerBox.setText(getString(R.string.EmptyText));
        }
        if (answerBox.getText().toString().length() < 20) {
            answerBox.append(tempButtonText);
        }
    }

    public void eraseAnswerBox(View view) {
        TextView answerBox = findViewById(R.id.Answer_Box);
        if (!answerBox.getText().toString().equals(getString(R.string.AnswerBox)) && !answerBox.getText().toString().equals("Enter a word first!")) {
            if (answerBox.getText().toString().length() >= 1) {
                answerBox.setText(answerBox.getText().toString().substring(0, answerBox.getText().toString().length() - 1));
            }
        }
        else {
            if (enableCheat%2 == 0) {
                enableCheat += 1;
            }
            else {
                enableCheat = 0;
            }
            answerBox.setText("");
        }
    }

    public void shuffleLetters(View view) {
        Random rand = new Random();
        ArrayList<Integer> randomIntArray = new ArrayList<Integer>();
        randomIntArray.add(4);
        while(randomIntArray.size() <= 6) {
            int randInt = rand.nextInt(7)+1;
            if (!randomIntArray.contains(randInt)) {
                if (randomIntArray.size() < 4) {
                    randomIntArray.add(0, randInt);
                }
                else if (randomIntArray.size() >= 4) {
                    randomIntArray.add(4, randInt);
                }
            }
        }
        System.out.println(randomIntArray);
        String[] tempStringArray = {"","","","","","",""};
        for (int i = 0; i < 7; i++) {
            tempStringArray[i] = saveFileStrings.get(randomIntArray.get(i));
        }
        for (int i = 0; i < 7; i++) {
            saveFileStrings.set(i+1, tempStringArray[i]);
        }
        writeSave(saveFileStrings, getApplicationContext(), "saveFile.txt");

        final Button[] tempButtons = {
                (Button) findViewById(R.id.Add_Text_Button_1),
                (Button) findViewById(R.id.Add_Text_Button_2),
                (Button) findViewById(R.id.Add_Text_Button_3),
                (Button) findViewById(R.id.Add_Text_Button_4),
                (Button) findViewById(R.id.Add_Text_Button_5),
                (Button) findViewById(R.id.Add_Text_Button_6),
                (Button) findViewById(R.id.Add_Text_Button_7)
        };
        String[] usedLetters = {"","","","","","",""};
        for (int i = 0; i < 7; i++) {
            usedLetters[i] = saveFileStrings.get(i + 1);
            tempButtons[i].setText(usedLetters[i]);
        }
    }

    public void startNewGame(View view) {
        writeToFile("NEWPLAYER == FALSE\n", getApplicationContext(), "saveFile.txt");
        setRandomLetters(null);
        writeSave(saveFileStrings, getApplicationContext(), "saveFile.txt");
    }
}
