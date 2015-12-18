package com.joeycurry.welcome;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener,
        ViewPager.OnPageChangeListener {

    // 定义ViewPager对象
    private ViewPager viewPager;

    // 定义ViewPager适配器
    private ViewPagerAdapter vpAdapter;

    // 定义一个ArrayList来存放View
    private ArrayList<View> views;

    // 引导图片资源
    private static final int[] pics = { R.mipmap.guide1, R.mipmap.guide2,
            R.mipmap.guide3, R.mipmap.guide4 };

    // 底部小点的图片
    private ImageView[] points;

    // 记录当前选中位置
    private int currentIndex;

    private Button btn_login,btn_reg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // 初始化组件
        initView();
        // 初始化数据
        initData();
    }

    private void initView() {
        // 实例化ArrayList对象
        views = new ArrayList<View>();
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_reg = (Button) findViewById(R.id.btn_reg);
        // 实例化ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        // 实例化ViewPager适配器
        vpAdapter = new ViewPagerAdapter(views);
    }

    private void initData() {
        // 定义一个布局并设置参数
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        // 初始化引导图片列表
        for (int i = 0; i < pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setImageResource(pics[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);//设置全屏幕填充
            views.add(iv);
        }
        // 设置数据
        //前面的views中没有数据 在前面的循环中才插入数据 而此时vpAdapter中已经有数据说明
        //初始化adapter的时候 参数传递是传引用
        viewPager.setAdapter(vpAdapter);
        // 设置监听
        viewPager.setOnPageChangeListener(this);
        //选择viewpaper的切换动画
//        viewPager.setPageTransformer(true,new DepthPageTransformer());
        viewPager.setPageTransformer(true,new ZoomOutPageTransformer());
        // 初始化底部小点
        initPoint();
    }

    /**
     * 初始化底部小点
     */
    private void initPoint() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        points = new ImageView[pics.length];

        // 循环取得小点图片
        for (int i = 0; i < pics.length; i++) {
            // 得到一个LinearLayout下面的每一个子元素
            points[i] = (ImageView) linearLayout.getChildAt(i);
            // 默认都设为灰色
            points[i].setEnabled(true);
            // 给每个小点设置监听
            points[i].setOnClickListener(this);
            // 设置位置tag，方便取出与当前位置对应
            points[i].setTag(i);
        }

        // 设置当面默认的位置
        currentIndex = 0;
        // 设置为白色，即选中状态
        points[currentIndex].setEnabled(false);
    }


    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }

    /**
     * 设置当前页面的位置
     */
    private void setCurView(int position) {
        // 排除异常情况
        if (position < 0 || position >= pics.length) {
            return;
        }
        viewPager.setCurrentItem(position);
    }

    /**
     * 设置当前的小点的位置
     */
    private void setCurDot(int positon) {
        // 排除异常情况
        if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
            return;
        }
        points[positon].setEnabled(false);
        points[currentIndex].setEnabled(true);

        currentIndex = positon;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 设置底部小点选中状态
        setCurDot(position);
        if (position == (pics.length - 1)){
            btn_login.setVisibility(View.VISIBLE);
            btn_reg.setVisibility(View.VISIBLE);
        } else {
            btn_login.setVisibility(View.GONE);
            btn_reg.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
