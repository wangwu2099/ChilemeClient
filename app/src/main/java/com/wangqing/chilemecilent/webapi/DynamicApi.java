package com.wangqing.chilemecilent.webapi;

import com.wangqing.chilemecilent.object.ao.CommonResult;
import com.wangqing.chilemecilent.object.dto.DynamicDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DynamicApi {
    @GET("api/v1/dynamic")
    Call<CommonResult<List<DynamicDto>>> getDynamicList(@Query("userId") Integer userId, @Query("access_token") String accessToken);
}
