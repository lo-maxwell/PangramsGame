package com.example.firstapp.ui.share;

import android.graphics.Color;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.firstapp.R;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        final TextView textView = root.findViewById(R.id.text_share);
        shareViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

//Find the layout with the id you gave in on the xml
        ConstraintLayout rl = root.findViewById(R.id.Share_Constraint_Layout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(1000,300);
        /*params.leftMargin = rl.getWidth()/2;
        params.topMargin = rl.getHeight()/2;
        //And now you can add the buttons you need, because it's a fragment, use getActivity() as context
        Button bt = new Button(getActivity());
        //You can add LayoutParams to put the button where you want it and the just add it
        bt.setLayoutParams(params);
        rl.addView(bt);*/
        // Get existing constraints into a ConstraintSet
        ConstraintSet constraints = new ConstraintSet();
        constraints.clone(rl);
        // Define our ImageView and add it to layout
        Button button = new Button(getActivity());
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
        /*
        for (int i = 1; i <= 20; i++) {
            System.out.println("Constructing button.");
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                    400-i*10,
                    400-i*10);
            params2.leftMargin = 10*i;
            params2.topMargin = 40*i;
            Button btn = new Button(getActivity());
            btn.setId(i);
            final int id_ = btn.getId();
            btn.setText("button " + id_);
            btn.setBackgroundColor(Color.rgb(255-i*10,255-i*10,255-i*10));
            rl.addView(btn, params2);
            Button btn1 = ((Button) root.findViewById(id_));
            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),
                            "Button clicked index = " + id_, Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }*/
        return root;
    }
}