package com.kw.one.source;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kw.arch.model.IRetrofitDataSource;
import com.kw.one.arch.retrofit.WebService;
import com.kw.one.source.bean.Calendar;

import retrofit2.Call;

/**
 * @author Kang Wei
 * @date 2019/11/18
 */
public class CalendarSource extends IRetrofitDataSource<String, Calendar> {
    @NonNull
    @Override
    public Call<Calendar> getCall(@Nullable String date) {
        return WebService.getInstance().getCalendar(date);
    }
}
