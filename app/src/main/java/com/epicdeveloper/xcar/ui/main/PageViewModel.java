package com.epicdeveloper.xcar.ui.main;

import static androidx.lifecycle.Transformations.map;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private MutableLiveData<Integer> mIndex2 = new MutableLiveData<>();
    private LiveData<String> mText = map(mIndex, input -> "Hello world from section: " );
    private LiveData<String> mText1 = map(mIndex2, input -> "Hello world from section: " );

    public void setIndex(int index) {
        mIndex.setValue(index);
        mIndex2.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }

}