package com.example.firstapp.ui.user_stats;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserStatsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UserStatsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the records page.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}