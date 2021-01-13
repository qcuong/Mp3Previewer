package com.quoccuong.mp3previewereviewer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MutableLiveData<String> filePathLiveData;

    public MainViewModel() {
        filePathLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<String> getFilePathLiveData() {
        return filePathLiveData;
    }

    public void setFilePath(String filePath) {
        this.filePathLiveData.setValue(filePath);
    }
}
