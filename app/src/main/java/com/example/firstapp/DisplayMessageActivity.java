package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Property;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import java.io.File;

public class DisplayMessageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.Display_Box);
        textView.setText(message);
    }

    public void deleteSave(View view) {
        Context context = getApplicationContext();
        String filename = "saveFile.txt";
        File file = new File(context.getFilesDir(), filename);
        System.out.println("Clearing save file");
        if (file.exists())
            System.out.println("File deleted: " + file.delete());
        else
            System.out.println("Could not delete file: File does not exist");
        TextView messageBox = findViewById(R.id.Display_Box);
        messageBox.setTextColor(ContextCompat.getColor(this, R.color.red));
        messageBox.setText("Deleted save file!");
    }
}
