package com.example.androidshopping.app;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.androidshopping.R;
import com.example.androidshopping.home.bean.GoodsBean;
import com.example.androidshopping.shoppingcart.utils.CartStorage;
import com.example.androidshopping.utils.Constants;

import static com.example.androidshopping.R.id.iv_good_info_image;

public class GoodsInfoActivity extends Activity implements View.OnClickListener {

    private ImageButton ibGoodInfoBack;
    private ImageButton ibGoodInfoMore;
    private ImageView ivGoodInfoImage;
    private TextView tvGoodInfoName;
    private TextView tvGoodInfoDesc;
    private TextView tvGoodInfoPrice;
    private TextView tvGoodInfoStore;
    private TextView tvGoodInfoStyle;
    private WebView wbGoodInfoMore;
    private LinearLayout llGoodsRoot;
    private TextView tvGoodInfoCallcenter;
    private TextView tvGoodInfoCollection;
    private TextView tvGoodInfoCart;
    private Button btnGoodInfoAddcart;
    private TextView tv_more_share;
    private TextView tv_more_search;
    private TextView tv_more_home;
    private Button btn_more;
    private GoodsBean goodsBean;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-11-15 10:55:30 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        //实例化控件
        tv_more_share = (TextView) findViewById(R.id.tv_more_share);
        tv_more_search = (TextView) findViewById(R.id.tv_more_search);
        tv_more_home = (TextView) findViewById(R.id.tv_more_home);
        btn_more = (Button) findViewById(R.id.btn_more);
        ibGoodInfoBack = (ImageButton) findViewById(R.id.ib_good_info_back);
        ibGoodInfoMore = (ImageButton) findViewById(R.id.ib_good_info_more);
        ivGoodInfoImage = (ImageView) findViewById(iv_good_info_image);
        tvGoodInfoName = (TextView) findViewById(R.id.tv_good_info_name);
        tvGoodInfoDesc = (TextView) findViewById(R.id.tv_good_info_desc);
        tvGoodInfoPrice = (TextView) findViewById(R.id.tv_good_info_price);
        tvGoodInfoStore = (TextView) findViewById(R.id.tv_good_info_store);
        tvGoodInfoStyle = (TextView) findViewById(R.id.tv_good_info_style);
        wbGoodInfoMore = (WebView) findViewById(R.id.wb_good_info_more);
        llGoodsRoot = (LinearLayout) findViewById(R.id.ll_goods_root);

        tvGoodInfoCallcenter = (TextView) findViewById(R.id.tv_good_info_callcenter);
        tvGoodInfoCollection = (TextView) findViewById(R.id.tv_good_info_collection);
        tvGoodInfoCart = (TextView) findViewById(R.id.tv_good_info_cart);
        btnGoodInfoAddcart = (Button) findViewById(R.id.btn_good_info_addcart);

        ibGoodInfoBack.setOnClickListener(this);
        ibGoodInfoMore.setOnClickListener(this);
        btnGoodInfoAddcart.setOnClickListener(this);

        tvGoodInfoCallcenter.setOnClickListener(this);
        tvGoodInfoCollection.setOnClickListener(this);
        tvGoodInfoCart.setOnClickListener(this);

        //设置点击事件
        tv_more_share.setOnClickListener(this);
        tv_more_search.setOnClickListener(this);
        tv_more_home.setOnClickListener(this);


    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2016-11-15 10:55:30 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == ibGoodInfoBack) {

            // Handle clicks for ibGoodInfoBack
            finish();
        } else if (v == ibGoodInfoMore) {
            // Handle clicks for ibGoodInfoMore
            Toast.makeText(this, "更多", Toast.LENGTH_SHORT).show();
        } else if (v == btnGoodInfoAddcart) {
            // Handle clicks for btnGoodInfoAddcart
            CartStorage.getInstance().addData(goodsBean);
            Toast.makeText(this, "添加到购物车成功了", Toast.LENGTH_SHORT).show();
        } else if (v == tvGoodInfoCallcenter) {
            Toast.makeText(this, "客户中心", Toast.LENGTH_SHORT).show();
        } else if (v == tvGoodInfoCollection) {
            Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
        } else if (v == tvGoodInfoCart) {
            Toast.makeText(this, "购物车", Toast.LENGTH_SHORT).show();
        } else if (v == tv_more_share) {
            Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
        } else if (v == tv_more_search) {
            Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
        } else if (v == tv_more_home) {
            Toast.makeText(this, "主页面", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        findViews();

        //(从intent中)接收数据
        goodsBean = (GoodsBean) getIntent().getSerializableExtra("goodsBean");
        if (goodsBean != null) {
//            Toast.makeText(this, "goodsBean=="+goodsBean.toString(), Toast.LENGTH_SHORT).show();
            setDataForView(goodsBean);
        }

    }

    /**
     * 设置数据
     *
     * @param goodsBean
     */
    private void setDataForView(GoodsBean goodsBean) {

        //设置图片
//        iv_good_info_image
        Glide.with(this).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).into(ivGoodInfoImage);

        //设置文本
        tvGoodInfoName.setText(goodsBean.getName());

        //设置价格
        tvGoodInfoPrice.setText("￥" + goodsBean.getCover_price());


        setWebViewData(goodsBean.getProduct_id());
    }

    private void setWebViewData(String product_id) {
        if (product_id != null) {


            //TODO
            wbGoodInfoMore.loadUrl("http://www.wecoo.com/");
            //设置支持JavaScript
            WebSettings webSettings = wbGoodInfoMore.getSettings();
            webSettings.setUseWideViewPort(true);//支持双击页面变大变小
            webSettings.setJavaScriptEnabled(true);//设置支持JavaScript
            //优先使用缓存
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            wbGoodInfoMore.setWebViewClient(new WebViewClient() {
                //TODO 自己添加的
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    //handler.cancel(); // Android默认的处理方式
                    handler.proceed();  // 接受所有网站的证书
                    //handleMessage(Message msg); // 进行其他处理
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
                    return true;
                }
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        view.loadUrl(request.getUrl().toString());
//                    }
//                    return true;
//                }
            });
        }
    }
}
