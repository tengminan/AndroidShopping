package com.example.androidshopping.home.fragment;


import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidshopping.R;
import com.example.androidshopping.base.BaseFragment;
import com.example.androidshopping.home.adapter.HomeFragmentAdapter;
import com.example.androidshopping.home.bean.ResultBeanData;
import com.example.androidshopping.utils.Constants;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * 作用：主页面的Fragment
 */
public class HomeFragment extends BaseFragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private RecyclerView rvHome;
    private ImageView ib_top;
    private TextView tv_search_home;
    private TextView tv_message_home;
    private HomeFragmentAdapter adapter;
    /**
     * 返回的数据
     */
    private ResultBeanData.ResultBean resultBean;
    //初始化OkHttpClient(客户端)
    private OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    public View initView() {
        Log.e(TAG, "主页视图被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        rvHome = (RecyclerView) view.findViewById(R.id.rv_home);
        ib_top = (ImageView) view.findViewById(R.id.ib_top);
        tv_search_home = (TextView) view.findViewById(R.id.tv_search_home);
        tv_message_home = (TextView) view.findViewById(R.id.tv_message_home);

        //设置点击事件
        initListener();
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG, "主页数据被初始化了");
        //联网请求主页的数据
        getDataFromNet();
    }

    //Get请求
    private void getDataFromNet() {
        String url = Constants.HOME_URL;
        Request request = new Request.Builder().header("Accept-Language","zh-CN")
                .url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            /**
             * 当请求失败的时候回调
             * @param call
             * @param e
             */
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "首页请求失败==" + e.getMessage());
            }

            /**
             * 当联网成功的时候回调
             * @param response 请求成功的数据
             */
            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                Log.e(TAG, "首页请求成功==" + response);
//                System.out.println("************"+response.body().string());
                //todo
                   //解析数据
                processData(response.body().string());
            }
        });
    }

    private void processData(String response) {
        Gson gson = new Gson();
//        ResponseBody responseBody = response.body();
//        String response_string=gson.toJson(response.toString(),ResultBeanData.class);
        ResultBeanData resultBeanData = gson.fromJson(response, ResultBeanData.class);
        resultBean = resultBeanData.getResult();
        if (resultBean != null) {
            //有数据
            //设置适配器
            adapter = new HomeFragmentAdapter(mContext, resultBean);
            rvHome.setAdapter(adapter);
            GridLayoutManager manager = new GridLayoutManager(mContext, 1);
            //设置跨度大小(item的个数)监听
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position <= 3) {
                        //隐藏(初试隐藏)
                        ib_top.setVisibility(View.GONE);
                    } else {
                        //显示
                        ib_top.setVisibility(View.VISIBLE);
                    }
                    //只能返回1
                    return 1;
                }
            });
            //设置布局管理者
            rvHome.setLayoutManager(manager);

        } else {
            //没有数据
        }
        Log.e(TAG, "解析成功==" + resultBean.getHot_info().get(0).getName());
    }

    private void initListener() {
        //置顶的监听
        ib_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回到顶部
                rvHome.scrollToPosition(0);
            }
        });

        //搜素的监听
        tv_search_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
            }
        });

        //消息的监听
        tv_message_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "进入消息中心", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
