package com.kw.arch.view;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.kw.arch.R;

import static com.kw.arch.view.GLoading.STATUS_LOADING;
import static com.kw.arch.view.GLoading.STATUS_LOAD_FAILED;
import static com.kw.arch.view.GLoading.STATUS_LOAD_SUCCESS;

/**
 * @author Kang Wei
 * @date 2019/11/11
 */
public class DefaultGLoadingAdapter implements GLoading.Adapter {
    @Override
    public View getView(GLoading.Holder holder, View convertView, int status) {
        switch (status) {
            case STATUS_LOADING:
                return LayoutInflater.from(holder.getContext()).inflate(R.layout.loading_progress_bar, holder.getWrapper(), false);
            case STATUS_LOAD_SUCCESS:
                FrameLayout hideView = new FrameLayout(holder.getContext());
                hideView.setVisibility(View.GONE);
                return hideView;
            case STATUS_LOAD_FAILED:
                View failedView =
                        LayoutInflater.from(holder.getContext()).inflate(R.layout.fail_loading_page, holder.getWrapper(), false);
                failedView.findViewById(R.id.retry).setOnClickListener(v -> {
                    GLoading.RetryLoad retryTask = holder.getRetryTask();
                    if (retryTask != null) {
                        holder.getRetryTask().retry(STATUS_LOAD_FAILED);
                    }
                });
                return failedView;
            default:
                return null;
        }
    }
}
