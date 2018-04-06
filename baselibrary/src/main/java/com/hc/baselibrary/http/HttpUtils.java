package com.hc.baselibrary.http;

import android.content.Context;
import android.util.ArrayMap;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static com.hc.baselibrary.http.EngineCallBack.DEFAULT_CALL_BACK;

/**
 * Email: 2185134304@qq.com
 * Created by JackChen 2018/4/6 12:21
 * Version 1.0
 * Params:
 * Description:  自己的一套实现网络请求
*/

public class HttpUtils{


    // 上下文
    private Context mContext ;
    // 直接带参数，最好是链式调用
    private String mUrl ;
    // 请求方式
    private int mType = GET_TYPE;

    private static final int GET_TYPE = 0x0011 ;

    private static final int POST_TYPE = 0x0011 ;


    private Map<String , Object> mParams ;

    private HttpUtils(Context context){
        this.mContext = context ;
        mParams = new HashMap<>() ;
    }


    /**
     * 这里最好不要用单例，否则内存泄露，因为所有的设计模式都有自己的特定场景
     */
    public static HttpUtils with(Context context){
        return new HttpUtils(context) ;
    }


    /**
     * 添加 url 路径
     */
    public HttpUtils url(String url){
        this.mUrl = url ;
        return this ;
    }


    /**
     * 请求的方式 -->  post
     * 这里为了链式调用，只需要返回this就ok
     */
    public HttpUtils post(){
        mType = POST_TYPE ;
        return this ;
    }


    /**
     * 请求的方式 -->  get
     * 这里为了链式调用，只需要返回this就ok
     */
    public HttpUtils get(){
        mType = GET_TYPE ;
        return this ;
    }


    /**
     * 一次添加一个参数
     */
    public HttpUtils addParam(String key , Object value){
        mParams.put(key , value) ;
        return this ;
    }


    /**
     * 一次添加多个参数
     */
    public HttpUtils addParams(Map<String , Object> params){
        mParams.putAll(params) ;
        return this ;
    }


    /**
     * 添加回调 , 最终去执行
     */
    public void execute(EngineCallBack callBack){
        if (callBack == null){
            callBack = DEFAULT_CALL_BACK ;
        }

        // 判断执行方法
        if (mType == POST_TYPE){
            post(mUrl , mParams , callBack);
        }

        if (mType == GET_TYPE){
            get(mUrl , mParams , callBack);
        }
    }

    public void execute(){
        execute(null);
    }

    // 默认是 OKHttpEngine
    private static IHttpEngine mHttpEngine = new OkHttpEngine() ;


    /**
     * 这个是在 BaseApplication中初始化引擎的
     */
    public static void init(IHttpEngine httpEngine){
        mHttpEngine = httpEngine ;
    }


    /**
     * 这个是 我们每次可以自带引擎：
     * 比如每次去new Volley()、new OkHttp()、new Retrofit()等不同的引擎
     */
    public static void exchange(IHttpEngine httpEngine){

    }



    private void get(String url, Map<String, Object> params, EngineCallBack callBack) {
        mHttpEngine.get(mContext , url , params , callBack);
    }


    private void post(String url, Map<String, Object> params, EngineCallBack callBack) {
        mHttpEngine.post(mContext , url , params , callBack);
    }


    /**
     * 拼接参数
     */
    public static String jointParams(String url, Map<String, Object> params) {
        if (params == null || params.size() <= 0) {
            return url;
        }

        StringBuffer stringBuffer = new StringBuffer(url);
        if (!url.contains("?")) {
            stringBuffer.append("?");
        } else {
            if (!url.endsWith("?")) {
                stringBuffer.append("&");
            }
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
        }

        stringBuffer.deleteCharAt(stringBuffer.length() - 1);

        return stringBuffer.toString();
    }

    /**
     * 解析一个类上面的class信息
     */
    public static Class<?> analysisClazzInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }
}
