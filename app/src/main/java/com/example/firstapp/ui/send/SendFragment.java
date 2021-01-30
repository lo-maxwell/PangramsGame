package com.example.firstapp.ui.send;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.firstapp.R;

public class SendFragment extends Fragment {
    private static Button tempButton;
    private static Button tempButton2;
    private View rootView;

    private SendViewModel sendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        rootView = inflater.inflate(R.layout.fragment_send, container, false);
        final TextView textView = root.findViewById(R.id.Text_Send);
        sendViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        tempButton = root.findViewById(R.id.Fade_Button_Send);
        tempButton2 = root.findViewById(R.id.Fade_Button_2_Send);
        tempButton.setAlpha(0);
        tempButton.animate().alpha(1.0f).setDuration(10000).start();

        tempButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (tempButton.getAlpha() == 1) {
                    tempButton.setAlpha(0);
                    tempButton.animate().alpha(1.0f).setDuration(3000).start();
                    tempButton2.setVisibility(View.GONE);
                    //addButton();
                }
                else {
                    tempButton.setAlpha(1.0f);
                    tempButton.setText(tempButton.getText().toString() + "!");
                    tempButton.animate().cancel();
                    tempButton2.setVisibility(View.VISIBLE);
                }
            }
        });

        tempButton2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tempButton2.setText(tempButton2.getText().toString() + "/\\");
                float end = tempButton2.getTranslationX() + 100;
                ObjectAnimator animation = ObjectAnimator.ofFloat(tempButton2, "translationX", end);
                animation.setDuration(2000);
                animation.start();
            }
        });

        /*
        //Find the layout with the id you gave in on the xml
        ConstraintLayout rl = rootView.findViewById(R.id.Send_Constraint_Layout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100,300);
        params.leftMargin = 80;
        params.topMargin = 200;
        //And now you can add the buttons you need, because it's a fragment, use getActivity() as context
        Button bt = new Button(getActivity());
        //You can add LayoutParams to put the button where you want it and the just add it
        bt.setLayoutParams(params);
        rl.addView(bt);

        for (int i = 1; i <= 20; i++) {
            System.out.println("Constructing button.");
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            Button btn = new Button(getActivity());
            btn.setId(i);
            final int id_ = btn.getId();
            btn.setText("button " + id_);
            btn.setBackgroundColor(Color.rgb(70, 80, 90));
            rl.addView(btn, params2);
            Button btn1 = ((Button) rootView.findViewById(id_));
            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),
                            "Button clicked index = " + id_, Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }
         */
        tempAddButton(inflater, container, savedInstanceState);

        /*ConstraintLayout rl = root.findViewById(R.id.Send_Constraint_Layout);
        // Get existing constraints into a ConstraintSet
        ConstraintSet constraints = new ConstraintSet();
        constraints.clone(rl);
        // Define our ImageView and add it to layout
        Button button = new Button(root.getContext());
        button.setId(View.generateViewId());
        button.setText("Test Button!");
        rl.addView(button);
        // Now constrain the ImageView so it is centered on the screen.
        // There is also a "center" method that can be used here.
        constraints.constrainWidth(button.getId(), ConstraintSet.WRAP_CONTENT);
        constraints.constrainHeight(button.getId(), ConstraintSet.WRAP_CONTENT);
        constraints.center(button.getId(), ConstraintSet.PARENT_ID, ConstraintSet.LEFT,
                100, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0, 0.5f);
        constraints.center(button.getId(), ConstraintSet.PARENT_ID, ConstraintSet.TOP,
                100, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0, 0.5f);
        constraints.applyTo(rl);

         */
        return root;
    }


    public void tempAddButton(@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        ConstraintLayout rl = root.findViewById(R.id.Send_Constraint_Layout);
        // Get existing constraints into a ConstraintSet
        ConstraintSet constraints = new ConstraintSet();
        constraints.clone(rl);
        // Define our ImageView and add it to layout
        Button button = new Button(root.getContext());
        button.setId(View.generateViewId());
        button.setText("Test Button!");
        rl.addView(button);
        // Now constrain the ImageView so it is centered on the screen.
        // There is also a "center" method that can be used here.
        constraints.constrainWidth(button.getId(), ConstraintSet.WRAP_CONTENT);
        constraints.constrainHeight(button.getId(), ConstraintSet.WRAP_CONTENT);
        constraints.center(button.getId(), ConstraintSet.PARENT_ID, ConstraintSet.LEFT,
                100, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0, 0.5f);
        constraints.center(button.getId(), ConstraintSet.PARENT_ID, ConstraintSet.TOP,
                100, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0, 0.5f);
        constraints.applyTo(rl);
    }

    public ObjectAnimator addButton() {
        //if (curAchievementsOnScreen == 0) {
        ConstraintLayout cl = rootView.findViewById(R.id.Send_Constraint_Layout);
        // Get existing constraints into a ConstraintSet
        ConstraintSet constraints = new ConstraintSet();
        // Define our ImageView and add it to layout
        Button button = new Button(getActivity());
        int id = View.generateViewId();
        button.setId(id);
        System.out.println("Generated id: " + id);
        button.setText("Test Button!");
        cl.addView(button);
        constraints.clone(cl);
        System.out.println("Cloned constraintSet");
        // Now constrain the ImageView so it is centered on the screen.
        // There is also a "center" method that can be used here.
        constraints.constrainWidth(button.getId(), ConstraintSet.WRAP_CONTENT);
        constraints.constrainHeight(button.getId(), ConstraintSet.WRAP_CONTENT);
        constraints.center(button.getId(), ConstraintSet.PARENT_ID, ConstraintSet.LEFT,
                0, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0, 0.5f);
        constraints.center(button.getId(), ConstraintSet.PARENT_ID, ConstraintSet.TOP,
                0, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0, 0.5f);
        constraints.applyTo(cl);

        final Button achievementButton = new Button(SendFragment.this.getActivity());
        //achievementButton.setLayoutParams(rootView.findViewById(R.id.testButton).getLayoutParams());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if(achievementButton.getParent() != null) {
            ((ViewGroup)achievementButton.getParent()).removeView(achievementButton); // <- fix
        }
        cl.addView(achievementButton, lp);
        achievementButton.setText("Test Button");
        achievementButton.bringToFront();
        achievementButton.setVisibility(View.VISIBLE);
        float end = achievementButton.getTranslationY() - 100;

        final ObjectAnimator achievementButtonAnimation = ObjectAnimator.ofFloat(achievementButton, "translationY", end);

        achievementButtonAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                System.out.println("Animation started!");
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                System.out.println("Animation ended!");
                achievementButton.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

                System.out.println("Animation canceled!");
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                System.out.println("Animation repeated!");
            }
        });

        achievementButtonAnimation.setDuration(2000);
        achievementButtonAnimation.start();

        achievementButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                view.setVisibility(View.GONE);
            }
        });
            /*achievementButton.animate().alpha(0).setDuration(2000).withEndAction(new Runnable() {
                @Override
                public void run() {
                    theView.setVisibility(View.GONE);
                }
            });*/


        //}
        return achievementButtonAnimation;
    }


}