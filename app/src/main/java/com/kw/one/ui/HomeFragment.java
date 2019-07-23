package com.kw.one.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.kw.one.R;
import com.kw.one.databinding.FragmentHomeBinding;
import com.kw.one.viewmodel.HomeViewModel;

/**
 * @author Kang Wei
 * @date 2019/7/23
 */
public class HomeFragment extends BaseFragment<HomeViewModel, FragmentHomeBinding> {
    private String mCity = "天津";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.confirm.setOnClickListener(v -> {
            mCity = mBinding.editQuery.getText().toString();
            mViewModel.setCity(mCity);
        });
        mBinding.reload.setOnClickListener(v -> mViewModel.reloadWeather());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.mCurWeather.observe(this, curWeather -> {
            String text = curWeather != null ? curWeather.data.address + ":" + curWeather.data.weather : "null";
            mBinding.weather.setText(text);
        });
        mViewModel.setCity(mCity);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Nullable
    @Override
    protected HomeViewModel getViewModel() {
        return ViewModelProviders.of(this).get(HomeViewModel.class);
    }
}
