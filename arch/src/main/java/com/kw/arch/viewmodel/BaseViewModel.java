package com.kw.arch.viewmodel;

import androidx.lifecycle.ViewModel;

import com.kw.arch.annotation.SourceAnnotate;
import com.kw.arch.model.base.Repository;

/**
 * @author Kang Wei
 * @date 2019/10/29
 */
public abstract class BaseViewModel extends ViewModel {
    protected Repository mRepository;
    public BaseViewModel(){
        mRepository = Repository.getInstance();
        SourceAnnotate.initSources(this,mRepository);
    }
}
