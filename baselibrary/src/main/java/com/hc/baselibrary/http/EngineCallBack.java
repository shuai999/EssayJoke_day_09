package com.hc.baselibrary.http;

/**
 * Email: 2185134304@qq.com
 * Created by JackChen 2018/4/6 12:33
 * Version 1.0
 * Params:
 * Description:   我们自己的网络引擎的回调
*/
public interface EngineCallBack {

    /**
     * 网络请求成功
     */
    public void onSuccess(String result) ;

    /**
     * 网络请求失败
     */
    public void onError(Exception e) ;


    /**
     * 默认的，里边什么也不干
     */
    public final EngineCallBack DEFAULT_CALL_BACK = new EngineCallBack() {
        @Override
        public void onSuccess(String result) {

        }

        @Override
        public void onError(Exception e) {

        }
    } ;
}
