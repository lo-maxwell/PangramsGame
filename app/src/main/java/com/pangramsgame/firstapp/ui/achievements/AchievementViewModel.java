package com.pangramsgame.firstapp.ui.achievements;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AchievementViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AchievementViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the achievements page!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}