package com.kw.one.arch;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.kw.arch.view.BaseFragment;
import com.kw.one.R;
import com.kw.one.databinding.FragmentArchBinding;

/**
 * @author Kang Wei
 * @date 2019/10/29
 */
public class ArchFragment extends BaseFragment<ArchViewModel, FragmentArchBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.mWeather.response().observe(this, rp -> {
            if (rp == null) return;
            mBinding.weather.setText(rp.data.weather);
        });
        mViewModel.mWeather.request().setValue("天津");
    }

    @Override
    protected ArchViewModel getVModel() {
        return ViewModelProviders.of(this).get(ArchViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_arch;
    }
}
