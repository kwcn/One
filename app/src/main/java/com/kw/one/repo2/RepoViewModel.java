package com.kw.one.repo2;

import androidx.lifecycle.ViewModel;

/**
 * @author Kang Wei
 * @date 2019/10/28
 */
public abstract class RepoViewModel extends ViewModel {
    private Repository mRepository;

    public RepoViewModel() {
        mRepository = Repository.getInstance();
    }
}
