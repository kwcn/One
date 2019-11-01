package com.kw.arch.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.kw.arch.annotation.SourceAnnotate;
import com.kw.arch.model.base.Repository;

/**
 * @author Kang Wei
 * @date 2019/10/29
 */
public abstract class BaseViewModel extends AndroidViewModel {
    protected Repository mRepository;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance();
        SourceAnnotate.initSources(this, mRepository);
    }
}
