package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.animation.LayoutTransition;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.api.HttpManager;
import com.zcl.hxqh.liangqingmanagement.api.HttpRequestHandler;
import com.zcl.hxqh.liangqingmanagement.api.JsonUtils;
import com.zcl.hxqh.liangqingmanagement.bean.Results;
import com.zcl.hxqh.liangqingmanagement.constants.Constants;
import com.zcl.hxqh.liangqingmanagement.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.liangqingmanagement.model.ALNDOMAIN;
import com.zcl.hxqh.liangqingmanagement.model.N_GRAINJC;
import com.zcl.hxqh.liangqingmanagement.until.AccountUtils;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.view.widght.ShareBottomDialog;
import com.zcl.hxqh.liangqingmanagement.webserviceclient.AndroidClientService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class N_grainjcAddActivity extends BaseActivity {

    private static final String TAG = "N_grainjcAddActivity";

    /**
     * 标题*
     */
    private TextView titleTextView;
    /**
     * 返回按钮
     */
    private ImageView backImageView;

    /**
     * 确定按钮
     **/
    private Button sbmitBtn;

    /**
     * ViewPager*
     */
    private ViewPager viewPager;
    /**
     * TextView*
     */
    private TextView jbxxText, lqjcText, sbssjcText, aqjcText;

    private List<View> views;// Tab页面列表
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度
    private View view1, view2, view3, view4;//各个页卡

    private ImageView imageView;

    private N_GRAINJC n_grainjc;
    /**基本信息**/
    /**
     * 标准单号
     **/
    private TextView grainsnunText;
    //布局文件
    private LinearLayout grainsnunLayout;
    //
    private View grainsnumView;
    /**
     * 描述
     **/
    private EditText descriptionText;
    /**
     * 创建时间*
     */
    private TextView reportdateText;
    /**
     * 保管员*
     */
    private TextView storemanText;

    /**
     * 责任区*
     */
    private EditText areaText;
    /**
     * 作业性质*
     */
    private TextView worktypeText;
    /**
     * 货位号
     */
    private TextView locText;
    /**
     * 仓型
     */
    private TextView storepottypeText;
    /**
     * 粮食品种
     */
    private TextView foodtypeText;
    /**
     * 粮食数量
     */
    private TextView numberinthelibraryText;
    /**
     * 水分
     */
    private TextView moistureText;
    /**
     * 状态
     */
    private TextView statusText;
    /**
     * 天气*
     */
    private EditText weatherText;
    /**
     * 温度*
     */
    private EditText temText;
    /**
     * 湿度*
     */
    private EditText humidityText;
    /**
     * 风向*
     */
    private EditText tem2Text;

    /**
     * 复核时间*
     */
    private TextView date2Text;


    //粮情检查
    /**
     * 发热
     **/
    private TextView frynTextView;

    //发热布局
    private LinearLayout frLayout;

    /**
     * 发热区域
     **/
    private TextView frareaText;
    /**
     * 发热面积
     **/
    private TextView frareaiText;
    /**
     * 发热状况
     **/
    private TextView frdepthText;
    /**
     * 发热处理意见
     **/
    private TextView frviewText;

    /**
     * 发热处理天数
     **/
    private EditText frjytsText;

    /**
     * 发热处理结果
     **/
    private TextView fld1Text;


    /**
     * 结露
     **/
    private TextView jlynTextView;

    //结露布局
    private LinearLayout sfjlLayout;
    /**
     * 结露区域
     **/
    private TextView jlareaText;
    /**
     * 结露面积
     **/
    private TextView jlareaiText;
    /**
     * 结露状况
     **/
    private TextView jldepthText;

    /**
     * 结露处理意见
     **/
    private TextView jlviewText;
    /**
     * 结露处理天数
     **/
    private EditText jljytsText;

    /**
     * 结露处理结果
     **/
    private TextView fld2Text;

    /**
     * 生霉
     **/
    private TextView smynTextView;

    //生霉布局
    private LinearLayout sfsmLayout;

    /**
     * 生霉区域
     **/
    private TextView smareaText;
    /**
     * 发霉位置
     **/
    private TextView smplaceText;
    /**
     * 生霉程度
     **/
    private TextView smcdText;
    /**
     * 生霉处理意见
     **/
    private TextView smviewText;

    /**
     * 药品名称
     **/
    private EditText smypText;
    /**
     * 药剂用量
     **/
    private EditText smjlText;
    /**
     * 生霉处理结果
     **/
    private TextView fld3Text;
    /**
     * 生霉处理天数
     **/
    private EditText smjytsText;


    /**
     * 虫害
     **/
    private TextView chynTextView;

    //虫害布局
    private LinearLayout sfhcLayout;

    /**
     * 虫害区域
     **/
    private TextView chareaText;

    /**
     * 虫害状况
     **/
    private TextView chstatusText;
    /**
     * 虫害种类
     **/
    private TextView smjbclText;
    /**
     * 虫害处理结果
     **/
    private TextView fld4Text;
    /**
     * 虫害处理意见
     **/
    private TextView chviewText;
    /**
     * 虫害处理天数
     **/
    private EditText chjytsText;
    /**
     * 药剂名称
     **/
    private EditText petyjText;
    /**
     * 药剂用量
     **/
    private EditText drugsumText;
    /**
     * 虫害来源
     **/
    private EditText mos1Text;
    /**
     * 处理时间
     **/
    private TextView mos2Text;
    /**
     * 药剂浓度
     **/
    private EditText mos3Text;
    /**
     * 粮堆体积
     **/
    private EditText foodsizeText;
    /**
     * 空间体积
     **/
    private EditText spacesizeText;
    /**
     * 合计体积
     **/
    private EditText mos5Text;

    /**
     * 施药方法
     **/
    private TextView medtypeText;
    /**
     * 施药人数
     **/
    private EditText medpeoplesText;
    /**
     * 密闭方法
     **/
    private TextView closetypeText;
    /**
     * 熏蒸开始日期
     **/
    private TextView mos8Text;
    /**
     * 熏蒸结束日期
     **/
    private TextView mos9Text;
    /**
     * 粮堆用药量
     **/
    private EditText foodmedText;
    /**
     * 空间用药量
     **/
    private EditText spacemedText;
    /**
     * 密闭时间
     **/
    private EditText mos10Text;
    /**
     * 熏蒸前粮温
     **/
    private EditText foodtemText;
    /**
     * 熏蒸后粮温
     **/
    private EditText foodtem1Text;
    /**
     * 熏蒸前发芽率
     **/
    private EditText mos11Text;
    /**
     * 熏蒸后发芽率
     **/
    private EditText mos12Text;
    /**
     * 熏蒸前害虫密度
     **/
    private EditText mos13Text;
    /**
     * 熏蒸后害虫密度
     **/
    private EditText mos14Text;
    /**
     * 药剂残留率
     **/
    private EditText mos15Text;
    /**
     * 曾否熏蒸
     **/
    private TextView xzynText;


    /**
     * 鼠害
     **/
    private TextView shynTextView;

    //鼠害布局
    private LinearLayout sfshLayout;

    /**
     * 鼠害种类
     **/
    private TextView shzlText;
    /**
     * 鼠害区域
     **/
    private TextView shareaText;
    /**
     * 鼠害处理结果
     **/
    private TextView fld5Text;
    /**
     * 鼠害处理天数
     **/
    private EditText shjytsText;
    /**
     * 鼠害处理意见
     **/
    private TextView shviewText;
    /**
     * 治理措施(物理)
     **/
    private TextView shwlfzText;
    /**
     * 防治方法
     **/
    private TextView methodTextView;
    /**
     * 鼠类
     **/
    private TextView mousetypeText;
    /**
     * 药剂名称
     **/
    private EditText mousenameText;
    /**
     * 诱饵
     **/
    private EditText baitText;
    /**
     * 盗食点数
     **/
    private EditText shhxcsText;
    /**
     * 投药点数
     **/
    private EditText shhxcs_Text;
    /**
     * 药剂计量
     **/
    private EditText doseText;
    /**
     * 投药点
     **/
    private EditText setplaceText;
    /**
     * 鼠密度
     **/
    private EditText densityText;
    /**
     * 盗食率(%)
     **/
    private EditText robpercentText;
    /**
     * 死鼠率(%)
     **/
    private EditText deathpercentText;
    /**
     * 灭鼠后密度
     **/
    private EditText afterText;


    //日常检查
    /**
     * 灭火器
     **/
    private TextView mhqynText;

    //布局文件
    private LinearLayout mhqLinearLayout;
    /**
     * 问题描述
     **/
    private TextView mhqviewText;
    /**
     * 处理结果
     **/
    private TextView fld6Text;


    /**
     * 消防栓
     **/
    private TextView xfsynText;
    //布局文件
    private LinearLayout xfsLinearLayout;
    /**
     * 问题描述
     **/
    private TextView xfsviewText;
    /**
     * 处理结果
     **/
    private TextView fld7Text;

    /**
     * 配电箱
     **/
    private TextView pdxynText;
    //布局文件
    private LinearLayout pdxLinearLayout;
    /**
     * 问题描述
     **/
    private TextView pdxviewText;
    /**
     * 处理结果
     **/
    private TextView fld8Text;

    /**
     * 刮板机
     **/
    private TextView gbjynText;
    //布局文件
    private LinearLayout gbjLinearLayout;
    /**
     * 问题描述
     **/
    private TextView gbjviewText;
    /**
     * 处理结果
     **/
    private TextView fld9Text;


    /**
     * 轴流风机
     **/
    private TextView zlfjynText;
    //布局文件
    private LinearLayout zlfjLinearLayout;
    /**
     * 问题描述
     **/
    private TextView zlviewText;
    /**
     * 处理结果
     **/
    private TextView fld10Text;


    /**
     * 电梯
     **/
    private TextView dtynText;
    //布局文件
    private LinearLayout dtLinearLayout;
    /**
     * 问题描述
     **/
    private TextView dtviewText;
    /**
     * 处理结果
     **/
    private TextView fld11Text;

    /**
     * 照明
     **/
    private TextView lightynText;

    //布局文件
    private LinearLayout lightLinearLayout;
    /**
     * 问题描述
     **/
    private TextView lightviewText;
    /**
     * 处理结果
     **/
    private TextView fld12Text;

    /**
     * 窗户
     **/
    private TextView cynText;
    //布局文件
    private LinearLayout cyLinearLayout;
    /**
     * 问题描述
     **/
    private TextView cviewText;
    /**
     * 处理结果
     **/
    private TextView fld16Text;

    /**
     * 现场卫生
     **/
    private TextView xcwsynText;
    //布局文件
    private LinearLayout xcwsLinearLayout;
    /**
     * 问题描述
     **/
    private TextView xcwsviewText;
    /**
     * 处理结果
     **/
    private TextView fld25Text;

    /**
     * 仓内卫生
     **/
    private TextView cnwsynText;
    //布局文件
    private LinearLayout cnwsLinearLayout;
    /**
     * 问题描述
     **/
    private TextView cnwsviewText;
    /**
     * 处理结果
     **/
    private TextView fld17Text;


    /**
     * 通廊卫生
     **/
    private TextView tlwsynText;
    //布局文件
    private LinearLayout tlwsLinearLayout;
    /**
     * 问题描述
     **/
    private TextView tlwsviewText;
    /**
     * 处理结果
     **/
    private TextView fld18Text;

    /**
     * 地坑卫生
     **/
    private TextView dkwsynText;
    //布局文件
    private LinearLayout dkwsLinearLayout;
    /**
     * 问题描述
     **/
    private TextView dgwsviewText;
    /**
     * 处理结果
     **/
    private TextView fld19Text;

    /**
     * 仓房漏雨
     **/
    private TextView cflyynText;
    //布局文件
    private LinearLayout cflyLinearLayout;
    /**
     * 问题描述
     **/
    private TextView cflyviewText;
    /**
     * 处理结果
     **/
    private TextView fld20Text;

    /**
     * 单管风机
     **/
    private TextView dgfjynText;
    //布局文件
    private LinearLayout dgfjLinearLayout;
    /**
     * 问题描述
     **/
    private TextView dgfjviewText;
    /**
     * 处理结果
     **/
    private TextView fld21Text;

    /**
     * 离心风机
     **/
    private TextView lxfjynText;
    //布局文件
    private LinearLayout lxfjLinearLayout;
    /**
     * 问题描述
     **/
    private TextView lxfjviewText;
    /**
     * 处理结果
     **/
    private TextView fld22Text;


    /**
     * 出入仓管理
     **/
    //粮食品种
    private TextView objText;
    //作业内容
    private TextView contentText;
    //开始时间
    private TextView fromText;
    //结束时间
    private TextView toText;
    //电源
    private CheckBox ps1Text;
    //输送机械
    private CheckBox cm1Text;
    //清杂机
    private CheckBox qc1Text;
    //除尘设备
    private CheckBox dr1Text;
    //灌包机
    private CheckBox sfm1Text;
    //登高作业平台
    private CheckBox awp1Text;
    //电瓶车
    private CheckBox bc1Text;
    //叉车
    private CheckBox fk1Text;
    //安全绳
    private CheckBox sr1Text;
    //防尘口罩
    private CheckBox dm1Text;


    /**
     * 日期选择器
     **/
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    StringBuffer sb;
    private int layoutnum;


    /**
     * 选项值
     **/
    private String[] types = null;


    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;


    protected FlippingLoadingDialog mLoadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grainjc_details);
        findViewById();
        initView();
        InitImageView();
        InitTextView();
        InitViewPager();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }


    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        sbmitBtn = (Button) findViewById(R.id.sbmit_id);

        jbxxText = (TextView) findViewById(R.id.jbxx_text);
        lqjcText = (TextView) findViewById(R.id.lqjc_text);
        sbssjcText = (TextView) findViewById(R.id.sbssjc_text);
        aqjcText = (TextView) findViewById(R.id.aqjc_text);


        viewPager = (ViewPager) this.findViewById(R.id.pager);


    }

    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(getString(R.string.cclqbzd_text_text));
        sbmitBtn.setVisibility(View.VISIBLE);
        sbmitBtn.setOnClickListener(sbmitBtnOnClickListener);
    }


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    private View.OnClickListener sbmitBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            submitNormalDialog();
        }
    };


    private void InitViewPager() {
        views = new ArrayList<View>();
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.activity_jbxx_details, null);
        view2 = inflater.inflate(R.layout.activity_lqjc_details, null);
        view3 = inflater.inflate(R.layout.activity_rcjc_all_details, null);
        view4 = inflater.inflate(R.layout.activity_aqjc_details, null);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        viewPager.setAdapter(new MyViewPagerAdapter(views));
        viewPager.setCurrentItem(0);
        initjbxx(view1);
        initlqjc(view2);
        inisbssjc(view3);
        initaqjc(view4);
        setBackground(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());


    }


    /**
     * 初始化头标
     */

    private void InitTextView() {


        jbxxText.setOnClickListener(new MyOnClickListener(0));
        lqjcText.setOnClickListener(new MyOnClickListener(1));
        sbssjcText.setOnClickListener(new MyOnClickListener(2));
        aqjcText.setOnClickListener(new MyOnClickListener(3));
    }

    /**
     * 2      * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
     * 3
     */

    private void InitImageView() {
        imageView = (ImageView) findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.line).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);// 设置动画初始位置

    }

    private class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        public void onClick(View v) {
            setBackground(index);
            viewPager.setCurrentItem(index);
        }

    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private List<View> mListViews;

        public MyViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量

        public void onPageScrollStateChanged(int arg0) {


        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {


        }

        public void onPageSelected(int arg0) {
            setBackground(arg0);

        }

    }


    /**
     * 设置背景颜色*
     */
    private void setBackground(int index) {
        if (index == 0) {
            jbxxText.setBackgroundResource(R.color.blue_1);
            lqjcText.setBackgroundResource(R.color.light_gray);
            sbssjcText.setBackgroundResource(R.color.light_gray);
            aqjcText.setBackgroundResource(R.color.light_gray);
        } else if (index == 1) {
            jbxxText.setBackgroundResource(R.color.light_gray);
            lqjcText.setBackgroundResource(R.color.blue_1);
            sbssjcText.setBackgroundResource(R.color.light_gray);
            aqjcText.setBackgroundResource(R.color.light_gray);
        } else if (index == 2) {
            jbxxText.setBackgroundResource(R.color.light_gray);
            lqjcText.setBackgroundResource(R.color.light_gray);
            sbssjcText.setBackgroundResource(R.color.blue_1);
            aqjcText.setBackgroundResource(R.color.light_gray);
        } else if (index == 3) {
            jbxxText.setBackgroundResource(R.color.light_gray);
            lqjcText.setBackgroundResource(R.color.light_gray);
            sbssjcText.setBackgroundResource(R.color.light_gray);
            aqjcText.setBackgroundResource(R.color.blue_1);
        }
    }


    /**
     * 初始基本信息*
     */
    private void initjbxx(View view) {
        grainsnunText = (TextView) view.findViewById(R.id.grainsnun_text_id);
        grainsnunLayout = (LinearLayout) view.findViewById(R.id.grainsnun_layout_id);
        grainsnumView = (View) view.findViewById(R.id.view_1_id);
        descriptionText = (EditText) view.findViewById(R.id.description_text_id);
        reportdateText = (TextView) view.findViewById(R.id.reportdate_text_id);
        storemanText = (TextView) view.findViewById(R.id.storeman_text_id);
        areaText = (EditText) view.findViewById(R.id.area_text_id);
        worktypeText = (TextView) view.findViewById(R.id.worktype_text_id);
        locText = (TextView) view.findViewById(R.id.loc_text_id);
        storepottypeText = (TextView) view.findViewById(R.id.storepottype_text_id);
        foodtypeText = (TextView) view.findViewById(R.id.foodtype_text_id);
        numberinthelibraryText = (TextView) view.findViewById(R.id.numberinthelibrary_text_id);
        moistureText = (TextView) view.findViewById(R.id.moisture_text_id);
        statusText = (TextView) view.findViewById(R.id.status_text_id);
        weatherText = (EditText) view.findViewById(R.id.weather_text_id);
        temText = (EditText) view.findViewById(R.id.tem_text_id);
        humidityText = (EditText) view.findViewById(R.id.humidity_text_id);
        tem2Text = (EditText) view.findViewById(R.id.tem2_text_id);
        date2Text = (TextView) view.findViewById(R.id.date2_text_id);

        grainsnunLayout.setVisibility(View.GONE);
        grainsnumView.setVisibility(View.GONE);

        reportdateText.setText(getCurrentTime("yyyy-MM-dd"));
        storemanText.setText(AccountUtils.getloginUserName(N_grainjcAddActivity.this));
        statusText.setText("草稿");
        setDataListener();


        worktypeText.setOnClickListener(worktypeTextOnClickListener);
        locText.setOnClickListener(locTextOnClickListener);
        date2Text.setOnClickListener(new MydateListener());
    }


    /**
     * 获取系统当前时间
     **/
    private String getCurrentTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);//设置日期格式
        String time = df.format(new Date());
        return time;
    }


    private View.OnClickListener worktypeTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getOptionsValue("作业性质", Constants.WORKTYPE, worktypeText);
        }
    };


    private View.OnClickListener locTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //跳转至选项值界面
            Intent intent = new Intent(N_grainjcAddActivity.this, ChooseActivity.class);
            intent.putExtra("HOLDER", AccountUtils.getloginUserName(N_grainjcAddActivity.this));
            startActivityForResult(intent, 0);
        }
    };


    /**
     * 初始化粮情检查*
     */
    private void initlqjc(View view) {

        //粮情检查
        frynTextView = (TextView) view.findViewById(R.id.fryn_text_id);
        frLayout = (LinearLayout) view.findViewById(R.id.sffr_layout_id);

        frareaText = (TextView) view.findViewById(R.id.frarea_text_id);
        frareaiText = (TextView) view.findViewById(R.id.frareai_text_id);
        frdepthText = (TextView) view.findViewById(R.id.frdepth_text_id);
        frviewText = (TextView) view.findViewById(R.id.frview_text_id);
        frjytsText = (EditText) view.findViewById(R.id.frjyts_text_id);
        fld1Text = (TextView) view.findViewById(R.id.fld1_text_id);


        jlynTextView = (TextView) view.findViewById(R.id.jlyn_text_id);
        sfjlLayout = (LinearLayout) view.findViewById(R.id.sfjl_layout_id);

        jlareaText = (TextView) view.findViewById(R.id.jlarea_text_id);
        jlareaiText = (TextView) view.findViewById(R.id.jlareai_text_id);
        jldepthText = (TextView) view.findViewById(R.id.jldepth_text_id);
        jlviewText = (TextView) view.findViewById(R.id.jlview_text_id);
        jljytsText = (EditText) view.findViewById(R.id.jljyts_text_id);
        fld2Text = (TextView) view.findViewById(R.id.fld2_text_id);


        smynTextView = (TextView) view.findViewById(R.id.smyn_text_id);
        sfsmLayout = (LinearLayout) view.findViewById(R.id.sfsm_layout_id);

        smareaText = (TextView) view.findViewById(R.id.smarea_text_id);
        smplaceText = (TextView) view.findViewById(R.id.smplace_text_id);
        smcdText = (TextView) view.findViewById(R.id.smcd_text_id);
        smviewText = (TextView) view.findViewById(R.id.smview_text_id);
        smypText = (EditText) view.findViewById(R.id.smyp_text_id);
        smjlText = (EditText) view.findViewById(R.id.smjl_text_id);
        fld3Text = (TextView) view.findViewById(R.id.fld3_text_id);
        smjytsText = (EditText) view.findViewById(R.id.smjyts_text_id);


        //虫害检查
        chynTextView = (TextView) view.findViewById(R.id.ch_text_id);
        sfhcLayout = (LinearLayout) view.findViewById(R.id.ch_layout_id);

        chareaText = (TextView) view.findViewById(R.id.charea_text_id);
        chstatusText = (TextView) view.findViewById(R.id.chstatus_text_id);
        smjbclText = (TextView) view.findViewById(R.id.smjbcl_text_id);
        fld4Text = (TextView) view.findViewById(R.id.fld4_text_id);
        chviewText = (TextView) view.findViewById(R.id.chview_text_id);
        chjytsText = (EditText) view.findViewById(R.id.chjyts_text_id);
        petyjText = (EditText) view.findViewById(R.id.petyj_text_id);
        drugsumText = (EditText) view.findViewById(R.id.drugsum_text_id);
        mos1Text = (EditText) view.findViewById(R.id.mos1_text_id);
        mos2Text = (TextView) view.findViewById(R.id.mos2_text_id);
        mos3Text = (EditText) view.findViewById(R.id.mos3_text_id);
        foodsizeText = (EditText) view.findViewById(R.id.foodsize_text_id);
        spacesizeText = (EditText) view.findViewById(R.id.spacesize_text_id);
        mos5Text = (EditText) view.findViewById(R.id.mos5_text_id);
        medtypeText = (TextView) view.findViewById(R.id.medtype_text_id);
        medpeoplesText = (EditText) view.findViewById(R.id.medpeoples_text_id);
        closetypeText = (TextView) view.findViewById(R.id.closetype_text_id);
        mos8Text = (TextView) view.findViewById(R.id.mos8_text_id);
        mos9Text = (TextView) view.findViewById(R.id.mos9_text_id);
        foodmedText = (EditText) view.findViewById(R.id.foodmed_text_id);
        spacemedText = (EditText) view.findViewById(R.id.spacemed_text_id);
        mos10Text = (EditText) view.findViewById(R.id.mos10_text_id);
        foodtemText = (EditText) view.findViewById(R.id.foodtem_text_id);
        foodtem1Text = (EditText) view.findViewById(R.id.foodtem1_text_id);
        mos11Text = (EditText) view.findViewById(R.id.mos11_text_id);
        mos12Text = (EditText) view.findViewById(R.id.mos12_text_id);
        mos13Text = (EditText) view.findViewById(R.id.mos13_text_id);
        mos14Text = (EditText) view.findViewById(R.id.mos14_text_id);
        mos15Text = (EditText) view.findViewById(R.id.mos15_text_id);
        xzynText = (TextView) view.findViewById(R.id.xzyn_text_id);

        //鼠害检查
        shynTextView = (TextView) view.findViewById(R.id.shyn_text_id);
        sfshLayout = (LinearLayout) view.findViewById(R.id.sfsh_layout_id);
        shzlText = (TextView) view.findViewById(R.id.shzl_text_id);
        shareaText = (TextView) view.findViewById(R.id.sharea_text_id);
        fld5Text = (TextView) view.findViewById(R.id.fld5_text_id);
        shjytsText = (EditText) view.findViewById(R.id.shjyts_text_id);
        shviewText = (TextView) view.findViewById(R.id.shview_text_id);
        shwlfzText = (TextView) view.findViewById(R.id.shwlfz_text_id);
        methodTextView = (TextView) view.findViewById(R.id.shjyfz_text_id);
        mousetypeText = (TextView) view.findViewById(R.id.mousetype_text_id);
        mousenameText = (EditText) view.findViewById(R.id.mousename_text_id);
        baitText = (EditText) view.findViewById(R.id.bait_text_id);
        shhxcsText = (EditText) view.findViewById(R.id.shhxcs_text_id);
        shhxcs_Text = (EditText) view.findViewById(R.id.shhxcs__text_id);
        doseText = (EditText) view.findViewById(R.id.dose_text_id);
        setplaceText = (EditText) view.findViewById(R.id.setplace_text_id);
        densityText = (EditText) view.findViewById(R.id.density_text_id);
        robpercentText = (EditText) view.findViewById(R.id.robpercent_text_id);
        deathpercentText = (EditText) view.findViewById(R.id.deathpercent_text_id);
        afterText = (EditText) view.findViewById(R.id.after_text_id);

        ViewGroup container = (ViewGroup) view.findViewById(R.id.container);
        LayoutTransition transition = new LayoutTransition();
        container.setLayoutTransition(transition);

        setLqjcEvent();
        showLqjc();


    }


    /**
     * 默认显示数据
     **/
    private void showLqjc() {
        frynTextView.setText("正常"); //发热选项
        jlynTextView.setText("正常"); //结露选项
        smynTextView.setText("正常"); //生霉选项
        chynTextView.setText("正常"); //虫害选项
        shynTextView.setText("正常"); //鼠害选项
        frLayout.setVisibility(View.GONE);
        sfjlLayout.setVisibility(View.GONE);
        sfsmLayout.setVisibility(View.GONE);
        sfhcLayout.setVisibility(View.GONE);
        sfshLayout.setVisibility(View.GONE);
    }


    /**
     * 粮情检查控件
     **/
    private void setLqjcEvent() {


        frynTextView.setOnClickListener(xxzOnClickListener); //发热选项
        frareaText.setOnClickListener(onClickListener); //发热区域
        frareaiText.setOnClickListener(onClickListener); //发热面积
        frdepthText.setOnClickListener(onClickListener); //发热状况
        frviewText.setOnClickListener(onClickListener); //发热处理意见
        fld1Text.setOnClickListener(onClickListener); //处理结果


        jlynTextView.setOnClickListener(xxzOnClickListener); //结露选项
        jlareaText.setOnClickListener(onClickListener); //结露区域
        jlareaiText.setOnClickListener(onClickListener); //结露面积
        jldepthText.setOnClickListener(onClickListener); //结露状况
        jlviewText.setOnClickListener(onClickListener); //结露处理意见
        fld2Text.setOnClickListener(onClickListener); //结露结果

        smynTextView.setOnClickListener(xxzOnClickListener); //生霉选项
        smareaText.setOnClickListener(onClickListener); //生霉区域
        smplaceText.setOnClickListener(onClickListener); //生霉位置
        smcdText.setOnClickListener(onClickListener); //生霉程度
        smviewText.setOnClickListener(onClickListener); //生霉处理意见
        fld3Text.setOnClickListener(onClickListener); //生霉结果

        chynTextView.setOnClickListener(xxzOnClickListener); //虫害选项
        chareaText.setOnClickListener(onClickListener); //虫害区域
        chstatusText.setOnClickListener(onClickListener); //虫害状况
        smjbclText.setOnClickListener(onClickListener); //虫害种类
        fld4Text.setOnClickListener(onClickListener); //处理结果
        chviewText.setOnClickListener(onClickListener); //虫害处理意见
        medtypeText.setOnClickListener(onClickListener); //施药方法
        closetypeText.setOnClickListener(onClickListener); //密闭方法
        xzynText.setOnClickListener(onClickListener); //曾否熏蒸


        shynTextView.setOnClickListener(xxzOnClickListener); //鼠害选项
        shzlText.setOnClickListener(onClickListener); //鼠害种类
        shareaText.setOnClickListener(onClickListener); //鼠害区域
        fld5Text.setOnClickListener(onClickListener); //处理结果
        shviewText.setOnClickListener(onClickListener); //鼠害处理意见
        shwlfzText.setOnClickListener(onClickListener); //治理措施(物理)


        mos2Text.setOnClickListener(new MydateListener());
        mos8Text.setOnClickListener(new MydateListener());
        mos9Text.setOnClickListener(new MydateListener());
    }


    private View.OnClickListener xxzOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fryn_text_id:
                    showShareBottomDialog1("选项值", getResources().getStringArray(R.array.xxz_titles), frynTextView);
                    break;
                case R.id.jlyn_text_id:
                    showShareBottomDialog1("选项值", getResources().getStringArray(R.array.xxz_titles), jlynTextView);
                    break;
                case R.id.smyn_text_id:
                    showShareBottomDialog1("选项值", getResources().getStringArray(R.array.xxz_titles), smynTextView);
                    break;
                case R.id.ch_text_id:
                    showShareBottomDialog1("选项值", getResources().getStringArray(R.array.xxz_titles), chynTextView);
                    break;
                case R.id.shyn_text_id:
                    showShareBottomDialog1("选项值", getResources().getStringArray(R.array.xxz_titles), shynTextView);
                    break;
                case R.id.mhqyn_text_id:
                    showShareBottomDialog1("选项值", getResources().getStringArray(R.array.xxz_titles), mhqynText);
                    break;
                case R.id.xfsyn_text_id:
                    showShareBottomDialog1("选项值", getResources().getStringArray(R.array.xxz_titles), xfsynText);
                    break;
                case R.id.pdxyn_text_id:
                    showShareBottomDialog1("选项值", getResources().getStringArray(R.array.xxz_titles), pdxynText);
                    break;
                case R.id.gbjyn_text_id:
                    showShareBottomDialog1("选项值", getResources().getStringArray(R.array.xxz_titles), gbjynText);
                    break;
                case R.id.zlfjyn_text_id:
                    showShareBottomDialog1("选项值", getResources().getStringArray(R.array.xxz_titles), zlfjynText);
                    break;
                case R.id.dtyn_text_id:
                    showShareBottomDialog1("选项值", getResources().getStringArray(R.array.xxz_titles), dtynText);
                    break;
                case R.id.lightyn_text_id:
                    showShareBottomDialog1("选项值", getResources().getStringArray(R.array.xxz_titles), lightynText);
                    break;
                case R.id.cyn_text_id:
                    showShareBottomDialog1("选项值", getResources().getStringArray(R.array.xxz_titles), cynText);
                    break;
                case R.id.cflyyn_text_id:
                    showShareBottomDialog1("选项值", getResources().getStringArray(R.array.xxz_titles), cflyynText);
                    break;
                case R.id.dgfjyn_text_id:
                    showShareBottomDialog1("选项值", getResources().getStringArray(R.array.xxz_titles), dgfjynText);
                    break;
                case R.id.lxfjyn_text_id:
                    showShareBottomDialog1("选项值", getResources().getStringArray(R.array.xxz_titles), lxfjynText);
                    break;

            }
        }
    };

    //
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.frarea_text_id:
                    getOptionsValue("发热区域", Constants.AREA, frareaText);
                    break;
                case R.id.frareai_text_id:
                    getOptionsValue("发热面积", Constants.GRAINJCAREA, frareaiText);
                    break;
                case R.id.frdepth_text_id:
                    getOptionsValue("发热状况", Constants.JLZJ, frdepthText);
                    break;
                case R.id.frview_text_id:
                    getOptionsValue("发热处理意见", Constants.FRVIEW, frviewText);
                    break;
                case R.id.fld1_text_id:
                    getOptionsValue("处理结果", Constants.THINGS, fld1Text);
                    break;

                case R.id.jlarea_text_id:
                    getOptionsValue("结露区域", Constants.AREA, jlareaText);
                    break;
                case R.id.jlareai_text_id:
                    getOptionsValue("结露面积", Constants.JLSTATUS, jlareaiText);
                    break;
                case R.id.jldepth_text_id:
                    getOptionsValue("结露状况", Constants.JLZJ, jldepthText);
                    break;
                case R.id.jlview_text_id:
                    getOptionsValue("结露处理意见", Constants.JLVIEW, jlviewText);
                    break;
                case R.id.fld2_text_id:
                    getOptionsValue("处理结果", Constants.THINGS, fld2Text);
                    break;

                case R.id.smarea_text_id:
                    getOptionsValue("生霉区域", Constants.AREA, smareaText);
                    break;
                case R.id.smplace_text_id:
                    getOptionsValue("生霉位置", Constants.CFLY, smplaceText);
                    break;
                case R.id.smcd_text_id:
                    getOptionsValue("生霉程度", Constants.SMCD, smcdText);
                    break;
                case R.id.smview_text_id:
                    getOptionsValue("生霉处理意见", Constants.JLVIEW, smviewText);
                    break;
                case R.id.fld3_text_id:
                    getOptionsValue("处理结果", Constants.THINGS, fld3Text);
                    break;
                case R.id.charea_text_id:
                    getOptionsValue("虫害区域", Constants.AREA, chareaText);
                    break;
                case R.id.chstatus_text_id:
                    getOptionsValue("虫害状况", Constants.JLZJ, chstatusText);
                    break;
                case R.id.smjbcl_text_id:
                    getOptionsValue("虫害种类", Constants.SMJBCL, smjbclText);
                    break;
                case R.id.fld4_text_id:
                    getOptionsValue("处理结果", Constants.THINGS, fld4Text);
                    break;
                case R.id.chview_text_id:
                    getOptionsValue("虫害处理意见", Constants.CHVIEW, chviewText);
                    break;
                case R.id.medtype_text_id:
                    getOptionsValue("施药方法", Constants.MEDTYPE, medtypeText);
                    break;
                case R.id.closetype_text_id:
                    getOptionsValue("密闭方法", Constants.CLOSETYPE, closetypeText);
                    break;
                case R.id.xzyn_text_id:
                    getOptionsValue("曾否熏蒸", Constants.XZYN, xzynText);
                    break;
                case R.id.shzl_text_id:
                    getOptionsValue("鼠害种类", Constants.MOUSETYPE, shzlText);
                    break;
                case R.id.sharea_text_id:
                    getOptionsValue("鼠害区域", Constants.MOUSEAREA, shareaText);
                    break;
                case R.id.fld5_text_id:
                    getOptionsValue("处理结果", Constants.THINGS, fld5Text);
                    break;
                case R.id.shview_text_id:
                    getOptionsValue("鼠害处理意见", Constants.SHJYFZ, shviewText);
                    break;
                case R.id.shwlfz_text_id:
                    getOptionsValue("治理措施", Constants.SHWLFZ, shwlfzText);
                    break;

                case R.id.obj_text_id:
                    getOptionsValue("粮食品种", Constants.CROPSTYPE, objText);
                    break;
                case R.id.content_text_id:
                    getOptionsValue("作业内容", Constants.CONTENT, contentText);
                    break;

                case R.id.xcwsyn_text_id:
                    getOptionsValue("现场卫生", Constants.GRAINSWS, xcwsynText);
                    break;
                case R.id.cnwsyn_text_id:
                    getOptionsValue("仓内卫生", Constants.GRAINSWS, cnwsynText);
                    break;
                case R.id.tlwsyn_text_id:
                    getOptionsValue("通廊卫生", Constants.GRAINSWS, tlwsynText);
                    break;
                case R.id.dkwsyn_text_id:
                    getOptionsValue("地坑卫生", Constants.GRAINSWS, dkwsynText);
                    break;
                case R.id.mhqview_text_id:
                    getOptionsValue("问题描述", Constants.MHQVIEW, mhqviewText);
                    break;
                case R.id.fld6_text_id:
                    getOptionsValue("处理结果", Constants.THINGSS, fld6Text);
                    break;
                case R.id.xfsview_text_id:
                    getOptionsValue("问题描述", Constants.XFSVIEW, xfsviewText);
                    break;
                case R.id.fld7_text_id:
                    getOptionsValue("处理结果", Constants.THINGSS, fld7Text);
                    break;
                case R.id.pdxview_text_id:
                    getOptionsValue("问题描述", Constants.PDXVIEW, pdxviewText);
                    break;
                case R.id.fld8_text_id:
                    getOptionsValue("处理结果", Constants.THINGSS, fld8Text);
                    break;
                case R.id.gbjview_text_id:
                    getOptionsValue("问题描述", Constants.GBJVIEW, gbjviewText);
                    break;
                case R.id.fld9_text_id:
                    getOptionsValue("处理结果", Constants.THINGSS, fld9Text);
                    break;
                case R.id.zlview_text_id:
                    getOptionsValue("问题描述", Constants.ZLVIEW, zlviewText);
                    break;
                case R.id.fld10_text_id:
                    getOptionsValue("处理结果", Constants.THINGSS, fld10Text);
                    break;
                case R.id.dtview_text_id:
                    getOptionsValue("问题描述", Constants.DTVIEW, dtviewText);
                    break;
                case R.id.fld11_text_id:
                    getOptionsValue("处理结果", Constants.THINGSS, fld11Text);
                    break;
                case R.id.lightview_text_id:
                    getOptionsValue("问题描述", Constants.LIGHTVIEW, lightviewText);
                    break;
                case R.id.fld12_text_id:
                    getOptionsValue("处理结果", Constants.THINGSS, fld12Text);
                    break;
                case R.id.cview_text_id:
                    getOptionsValue("问题描述", Constants.CVIEW, cviewText);
                    break;
                case R.id.fld16_text_id:
                    getOptionsValue("处理结果", Constants.THINGSS, fld16Text);
                    break;
                case R.id.xcwsview_text_id:
                    getOptionsValue("问题描述", Constants.XCWSVIEW, xcwsviewText);
                    break;
                case R.id.fld25_text_id:
                    getOptionsValue("处理结果", Constants.THINGSS, fld25Text);
                    break;
                case R.id.cnwsview_text_id:
                    getOptionsValue("问题描述", Constants.CNWSVIEW, cnwsviewText);
                    break;
                case R.id.fld17_text_id:
                    getOptionsValue("处理结果", Constants.THINGSS, fld17Text);
                    break;
                case R.id.tlwsview_text_id:
                    getOptionsValue("问题描述", Constants.TLWSVIEW, tlwsviewText);
                    break;
                case R.id.fld18_text_id:
                    getOptionsValue("处理结果", Constants.THINGSS, fld18Text);
                    break;
                case R.id.dgwsview_text_id:
                    getOptionsValue("问题描述", Constants.DGWSVIEW, dgwsviewText);
                    break;
                case R.id.fld19_text_id:
                    getOptionsValue("处理结果", Constants.THINGSS, fld19Text);
                    break;
                case R.id.cflyview_text_id:
                    getOptionsValue("问题描述", Constants.CFLYVIEW, cflyviewText);
                    break;
                case R.id.fld20_text_id:
                    getOptionsValue("处理结果", Constants.THINGSS, fld20Text);
                    break;
                case R.id.dgfjview_text_id:
                    getOptionsValue("问题描述", Constants.DGFJVIEW, dgfjviewText);
                    break;
                case R.id.fld21_text_id:
                    getOptionsValue("处理结果", Constants.THINGSS, fld21Text);
                    break;
                case R.id.lxfjview_text_id:
                    getOptionsValue("问题描述", Constants.LXFJVIEW, lxfjviewText);
                    break;
                case R.id.fld22_text_id:
                    getOptionsValue("处理结果", Constants.THINGSS, fld22Text);
                    break;


            }
        }
    };


    /**
     * 初始日常检查*
     */
    private void inisbssjc(View view) {
        mhqynText = (TextView) view.findViewById(R.id.mhqyn_text_id);
        mhqLinearLayout = (LinearLayout) view.findViewById(R.id.mhq_text_id);
        mhqviewText = (TextView) view.findViewById(R.id.mhqview_text_id);
        fld6Text = (TextView) view.findViewById(R.id.fld6_text_id);

        xfsynText = (TextView) view.findViewById(R.id.xfsyn_text_id);
        xfsLinearLayout = (LinearLayout) view.findViewById(R.id.xfs_text_id);
        xfsviewText = (TextView) view.findViewById(R.id.xfsview_text_id);
        fld7Text = (TextView) view.findViewById(R.id.fld7_text_id);

        pdxynText = (TextView) view.findViewById(R.id.pdxyn_text_id);
        pdxLinearLayout = (LinearLayout) view.findViewById(R.id.pdx_text_id);
        pdxviewText = (TextView) view.findViewById(R.id.pdxview_text_id);
        fld8Text = (TextView) view.findViewById(R.id.fld8_text_id);

        gbjynText = (TextView) view.findViewById(R.id.gbjyn_text_id);
        gbjLinearLayout = (LinearLayout) view.findViewById(R.id.gbj_text_id);
        gbjviewText = (TextView) view.findViewById(R.id.gbjview_text_id);
        fld9Text = (TextView) view.findViewById(R.id.fld9_text_id);

        zlfjynText = (TextView) view.findViewById(R.id.zlfjyn_text_id);
        zlfjLinearLayout = (LinearLayout) view.findViewById(R.id.zlfj_text_id);
        zlviewText = (TextView) view.findViewById(R.id.zlview_text_id);
        fld10Text = (TextView) view.findViewById(R.id.fld10_text_id);

        dtynText = (TextView) view.findViewById(R.id.dtyn_text_id);
        dtLinearLayout = (LinearLayout) view.findViewById(R.id.dt_text_id);
        dtviewText = (TextView) view.findViewById(R.id.dtview_text_id);
        fld11Text = (TextView) view.findViewById(R.id.fld11_text_id);

        lightynText = (TextView) view.findViewById(R.id.lightyn_text_id);
        lightLinearLayout = (LinearLayout) view.findViewById(R.id.light_text_id);
        lightviewText = (TextView) view.findViewById(R.id.lightview_text_id);
        fld12Text = (TextView) view.findViewById(R.id.fld12_text_id);

        cynText = (TextView) view.findViewById(R.id.cyn_text_id);
        cyLinearLayout = (LinearLayout) view.findViewById(R.id.cy_text_id);
        cviewText = (TextView) view.findViewById(R.id.cview_text_id);
        fld16Text = (TextView) view.findViewById(R.id.fld16_text_id);

        xcwsynText = (TextView) view.findViewById(R.id.xcwsyn_text_id);
        xcwsLinearLayout = (LinearLayout) view.findViewById(R.id.xcws_text_id);
        xcwsviewText = (TextView) view.findViewById(R.id.xcwsview_text_id);
        fld25Text = (TextView) view.findViewById(R.id.fld25_text_id);

        cnwsynText = (TextView) view.findViewById(R.id.cnwsyn_text_id);
        cnwsLinearLayout = (LinearLayout) view.findViewById(R.id.cnws_text_id);
        cnwsviewText = (TextView) view.findViewById(R.id.cnwsview_text_id);
        fld17Text = (TextView) view.findViewById(R.id.fld17_text_id);

        tlwsynText = (TextView) view.findViewById(R.id.tlwsyn_text_id);
        tlwsLinearLayout = (LinearLayout) view.findViewById(R.id.tlws_text_id);
        tlwsviewText = (TextView) view.findViewById(R.id.tlwsview_text_id);
        fld18Text = (TextView) view.findViewById(R.id.fld18_text_id);

        dkwsynText = (TextView) view.findViewById(R.id.dkwsyn_text_id);
        dkwsLinearLayout = (LinearLayout) view.findViewById(R.id.dkws_text_id);
        dgwsviewText = (TextView) view.findViewById(R.id.dgwsview_text_id);
        fld19Text = (TextView) view.findViewById(R.id.fld19_text_id);

        cflyynText = (TextView) view.findViewById(R.id.cflyyn_text_id);
        cflyLinearLayout = (LinearLayout) view.findViewById(R.id.cfly_text_id);
        cflyviewText = (TextView) view.findViewById(R.id.cflyview_text_id);
        fld20Text = (TextView) view.findViewById(R.id.fld20_text_id);

        dgfjynText = (TextView) view.findViewById(R.id.dgfjyn_text_id);
        dgfjLinearLayout = (LinearLayout) view.findViewById(R.id.dgfj_text_id);
        dgfjviewText = (TextView) view.findViewById(R.id.dgfjview_text_id);
        fld21Text = (TextView) view.findViewById(R.id.fld21_text_id);

        lxfjynText = (TextView) view.findViewById(R.id.lxfjyn_text_id);
        lxfjLinearLayout = (LinearLayout) view.findViewById(R.id.lxfj_text_id);
        lxfjviewText = (TextView) view.findViewById(R.id.lxfjview_text_id);
        fld22Text = (TextView) view.findViewById(R.id.fld22_text_id);

        ViewGroup container = (ViewGroup) view.findViewById(R.id.container);
        LayoutTransition transition = new LayoutTransition();
        container.setLayoutTransition(transition);

        setRcjcEvent();
        showRcjc();
    }


    private void showRcjc() {
        mhqynText.setText("正常"); //灭火器
        xfsynText.setText("正常"); //消防栓
        pdxynText.setText("正常"); //配电箱
        gbjynText.setText("正常"); //刮板机
        zlfjynText.setText("正常"); //轴流风机
        dtynText.setText("正常"); //电梯
        lightynText.setText("正常"); //照明
        cynText.setText("正常"); //窗户
        xcwsynText.setText("良好"); //现场卫生
        cnwsynText.setText("良好"); //仓内卫生
        tlwsynText.setText("良好"); //通廊卫生
        dkwsynText.setText("良好"); //地坑卫生
        cflyynText.setText("正常"); //仓房漏雨
        dgfjynText.setText("正常"); //单管风机
        lxfjynText.setText("正常"); //离心风机
        mhqLinearLayout.setVisibility(View.GONE);
        xfsLinearLayout.setVisibility(View.GONE);
        pdxLinearLayout.setVisibility(View.GONE);
        gbjLinearLayout.setVisibility(View.GONE);
        zlfjLinearLayout.setVisibility(View.GONE);
        dtLinearLayout.setVisibility(View.GONE);
        lightLinearLayout.setVisibility(View.GONE);
        cyLinearLayout.setVisibility(View.GONE);
        cflyLinearLayout.setVisibility(View.GONE);
        dgfjLinearLayout.setVisibility(View.GONE);
        lxfjLinearLayout.setVisibility(View.GONE);
    }


    private void setRcjcEvent() {
        mhqynText.setOnClickListener(xxzOnClickListener); //灭火器
        mhqviewText.setOnClickListener(onClickListener); //问题描述
        fld6Text.setOnClickListener(onClickListener); //处理结果

        xfsynText.setOnClickListener(xxzOnClickListener); //消防栓
        xfsviewText.setOnClickListener(onClickListener); //问题描述
        fld7Text.setOnClickListener(onClickListener); //处理结果

        pdxynText.setOnClickListener(xxzOnClickListener); //配电箱
        pdxviewText.setOnClickListener(onClickListener); //问题描述
        fld8Text.setOnClickListener(onClickListener); //处理结果

        gbjynText.setOnClickListener(xxzOnClickListener); //刮板机
        gbjviewText.setOnClickListener(onClickListener); //问题描述
        fld9Text.setOnClickListener(onClickListener); //处理结果

        zlfjynText.setOnClickListener(xxzOnClickListener); //轴流风机
        zlviewText.setOnClickListener(onClickListener); //问题描述
        fld10Text.setOnClickListener(onClickListener); //处理结果

        dtynText.setOnClickListener(xxzOnClickListener); //电梯
        dtviewText.setOnClickListener(onClickListener); //问题描述
        fld11Text.setOnClickListener(onClickListener); //处理结果

        lightynText.setOnClickListener(xxzOnClickListener); //照明
        lightviewText.setOnClickListener(onClickListener); //问题描述
        fld12Text.setOnClickListener(onClickListener); //处理结果

        cynText.setOnClickListener(xxzOnClickListener); //窗户
        cviewText.setOnClickListener(onClickListener); //问题描述
        fld16Text.setOnClickListener(onClickListener); //处理结果

        xcwsynText.setOnClickListener(onClickListener); //现场卫生
        xcwsviewText.setOnClickListener(onClickListener); //问题描述
        fld25Text.setOnClickListener(onClickListener); //处理结果

        cnwsynText.setOnClickListener(onClickListener); //仓内卫生
        cnwsviewText.setOnClickListener(onClickListener); //问题描述
        fld17Text.setOnClickListener(onClickListener); //处理结果

        tlwsynText.setOnClickListener(onClickListener); //通廊卫生
        tlwsviewText.setOnClickListener(onClickListener); //问题描述
        fld18Text.setOnClickListener(onClickListener); //处理结果

        dkwsynText.setOnClickListener(onClickListener); //地坑卫生
        dgwsviewText.setOnClickListener(onClickListener); //问题描述
        fld19Text.setOnClickListener(onClickListener); //处理结果

        cflyynText.setOnClickListener(xxzOnClickListener); //仓房漏雨
        cflyviewText.setOnClickListener(onClickListener); //问题描述
        fld20Text.setOnClickListener(onClickListener); //处理结果

        dgfjynText.setOnClickListener(xxzOnClickListener); //单管风机
        dgfjviewText.setOnClickListener(onClickListener); //问题描述
        fld21Text.setOnClickListener(onClickListener); //处理结果

        lxfjynText.setOnClickListener(xxzOnClickListener); //离心风机
        lxfjviewText.setOnClickListener(onClickListener); //问题描述
        fld22Text.setOnClickListener(onClickListener); //处理结果

    }


    /**
     * 初始化出入仓作业*
     */
    private void initaqjc(View view) {
        objText = (TextView) view.findViewById(R.id.obj_text_id);
        contentText = (TextView) view.findViewById(R.id.content_text_id);
        fromText = (TextView) view.findViewById(R.id.from_text_id);
        toText = (TextView) view.findViewById(R.id.to_text_id);

        ps1Text = (CheckBox) view.findViewById(R.id.ps1_text_id);
        cm1Text = (CheckBox) view.findViewById(R.id.cm1_text_id);
        qc1Text = (CheckBox) view.findViewById(R.id.qc1_text_id);
        dr1Text = (CheckBox) view.findViewById(R.id.dr1_text_id);

        sfm1Text = (CheckBox) view.findViewById(R.id.sfm1_text_id);
        awp1Text = (CheckBox) view.findViewById(R.id.awp1_text_id);
        bc1Text = (CheckBox) view.findViewById(R.id.bc1_text_id);
        fk1Text = (CheckBox) view.findViewById(R.id.fk1_text_id);
        sr1Text = (CheckBox) view.findViewById(R.id.sr1_text_id);
        dm1Text = (CheckBox) view.findViewById(R.id.dm1_text_id);

        ViewGroup container = (ViewGroup) view.findViewById(R.id.container);
        LayoutTransition transition = new LayoutTransition();
        container.setLayoutTransition(transition);


        setTimeListener();

        setCRCZYEvent();
    }

    private void setCRCZYEvent() {
        objText.setOnClickListener(onClickListener); //粮食品种
        contentText.setOnClickListener(onClickListener); //作业内容

        fromText.setOnClickListener(new MyTimeListener()); //开始时间
        toText.setOnClickListener(new MyTimeListener()); //结束时间
    }

//    private CompoundButton.OnCheckedChangeListener pdxynCheckBoxOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
//        @Override
//        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//            if (b) {
//                pdxviewLayout.setVisibility(View.VISIBLE);
//                pdxview.setVisibility(View.VISIBLE);
//            } else {
//                pdxviewLayout.setVisibility(View.GONE);
//                pdxview.setVisibility(View.GONE);
//            }
//        }
//    };
//    private CompoundButton.OnCheckedChangeListener xfxynCheckBoxOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
//        @Override
//        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//            if (b) {
//                xfxviewLayout.setVisibility(View.VISIBLE);
//                xfxview.setVisibility(View.VISIBLE);
//            } else {
//                xfxviewLayout.setVisibility(View.GONE);
//                xfxview.setVisibility(View.GONE);
//            }
//        }
//    };
//    private CompoundButton.OnCheckedChangeListener mhqynCheckBoxOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
//        @Override
//        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//            if (b) {
//                mhqviewLayout.setVisibility(View.VISIBLE);
//                mhqview.setVisibility(View.VISIBLE);
//            } else {
//                mhqviewLayout.setVisibility(View.GONE);
//                mhqview.setVisibility(View.GONE);
//            }
//        }
//    };

    /**
     * 显示基本信息*
     */
    private void showjbxx() {
        grainsnunText.setText(n_grainjc.getGRAINSNUN());
        descriptionText.setText(n_grainjc.getDESCRIPTION());
        reportdateText.setText(n_grainjc.getREPORTDATE());
        storemanText.setText(n_grainjc.getSTOREMAN());
        areaText.setText(n_grainjc.getAFTER());
        worktypeText.setText(n_grainjc.getWORKTYPE());
        locText.setText(n_grainjc.getLOC());
        storepottypeText.setText(n_grainjc.getSTOREPOTTYPE());
        foodtypeText.setText(n_grainjc.getFOODTYPE());
        numberinthelibraryText.setText(n_grainjc.getNUMBERINTHELIBRARY());
        moistureText.setText(n_grainjc.getMOISTURE());
        statusText.setText(n_grainjc.getSTATUS());
        weatherText.setText(n_grainjc.getWEATHER());
        temText.setText(n_grainjc.getTEM());
        humidityText.setText(n_grainjc.getHUMIDITY());
        tem2Text.setText(n_grainjc.getTEM2());
        date2Text.setText(n_grainjc.getDATE2());

    }

    /**
     * 显示粮情管理*
     */
    private void showlqjc() {
        frynTextView.setText(n_grainjc.getFRYN());
        if (n_grainjc.getFRYN().equals("正常")) {
            frLayout.setVisibility(View.GONE);
        } else {
            frLayout.setVisibility(View.VISIBLE);
        }

        frareaText.setText(n_grainjc.getFRAREA());
        frareaiText.setText(n_grainjc.getFRAREAI());
        frdepthText.setText(n_grainjc.getFRDEPTH());
        frviewText.setText(n_grainjc.getFRVIEW());
        frjytsText.setText(n_grainjc.getFRJYTS());
        fld1Text.setText(n_grainjc.getFLD1());

        jlynTextView.setText(n_grainjc.getJLYN());
        if (n_grainjc.getJLYN().equals("正常")) {
            sfjlLayout.setVisibility(View.GONE);
        } else {
            sfjlLayout.setVisibility(View.VISIBLE);
        }

        jlareaText.setText(n_grainjc.getJLAREA());
        jlareaiText.setText(n_grainjc.getJLAREAI());
        jldepthText.setText(n_grainjc.getJLDEPTH());
        jlviewText.setText(n_grainjc.getJLVIEW());
        jljytsText.setText(n_grainjc.getJLJYTS());
        fld2Text.setText(n_grainjc.getFLD2());


        smynTextView.setText(n_grainjc.getSMYN());
        if (n_grainjc.getSMYN().equals("正常")) {
            sfsmLayout.setVisibility(View.GONE);
        } else {
            sfsmLayout.setVisibility(View.VISIBLE);
        }
        smareaText.setText(n_grainjc.getSMAREA());
        smplaceText.setText(n_grainjc.getSMPLACE());
        smcdText.setText(n_grainjc.getSMCD());
        smviewText.setText(n_grainjc.getSMVIEW());
        smypText.setText(n_grainjc.getSMYP());
        smjlText.setText(n_grainjc.getSMJL());
        fld3Text.setText(n_grainjc.getFLD3());
        smjytsText.setText(n_grainjc.getSMJYTS());


        chynTextView.setText(n_grainjc.getCHYN());
        if (n_grainjc.getCHYN().equals("正常")) {
            sfhcLayout.setVisibility(View.GONE);
        } else {
            sfhcLayout.setVisibility(View.VISIBLE);
        }
        chareaText.setText(n_grainjc.getCHAREA());
        chstatusText.setText(n_grainjc.getCHSTATUS());
        smjbclText.setText(n_grainjc.getSMJBCL());
        fld4Text.setText(n_grainjc.getFLD4());
        chviewText.setText(n_grainjc.getCHVIEW());
        chjytsText.setText(n_grainjc.getCHJYTS());
        petyjText.setText(n_grainjc.getPETYJ());
        drugsumText.setText(n_grainjc.getDRUGSUM());
        mos1Text.setText(n_grainjc.getMOS1());
        mos2Text.setText(n_grainjc.getMOS2());
        mos3Text.setText(n_grainjc.getMOS3());
        foodsizeText.setText(n_grainjc.getFOODSIZE());
        spacesizeText.setText(n_grainjc.getSPACESIZE());
        mos5Text.setText(n_grainjc.getMOS5());
        medtypeText.setText(n_grainjc.getMEDTYPE());
        medpeoplesText.setText(n_grainjc.getMEDPEOPLES());
        closetypeText.setText(n_grainjc.getCLOSETYPE());
        mos8Text.setText(n_grainjc.getMOS8());
        mos9Text.setText(n_grainjc.getMOS9());
        foodmedText.setText(n_grainjc.getFOODMED());
        spacemedText.setText(n_grainjc.getSPACEMED());
        mos10Text.setText(n_grainjc.getMOS10());
        foodtemText.setText(n_grainjc.getFOODTEM());
        foodtem1Text.setText(n_grainjc.getFOODTEM1());
        mos11Text.setText(n_grainjc.getMOS11());
        mos12Text.setText(n_grainjc.getMOS12());
        mos13Text.setText(n_grainjc.getMOS13());
        mos14Text.setText(n_grainjc.getMOS14());
        mos15Text.setText(n_grainjc.getMOS15());
        xzynText.setText(n_grainjc.getXZYN());

        shynTextView.setText(n_grainjc.getSHYN());
        if (n_grainjc.getSHYN().equals("正常")) {
            sfshLayout.setVisibility(View.GONE);
        } else {
            sfshLayout.setVisibility(View.VISIBLE);
        }
        shzlText.setText(n_grainjc.getSHZL());
        shareaText.setText(n_grainjc.getSHAREA());
        fld5Text.setText(n_grainjc.getFLD5());
        shjytsText.setText(n_grainjc.getSHJYTS());
        shviewText.setText(n_grainjc.getSHVIEW());
        shwlfzText.setText(n_grainjc.getSHWLFZ());
        methodTextView.setText(n_grainjc.getMETHOD());
        mousetypeText.setText(n_grainjc.getMOUSETYPE());
        mousenameText.setText(n_grainjc.getMOUSENAME());
        baitText.setText(n_grainjc.getBAIT());
        shhxcsText.setText(n_grainjc.getSHHXCS());
        shhxcs_Text.setText(n_grainjc.getSHHXCS_());
        doseText.setText(n_grainjc.getDOSE());
        setplaceText.setText(n_grainjc.getSETPLACE());
        densityText.setText(n_grainjc.getDENSITY());
        robpercentText.setText(n_grainjc.getROBPERCENT());
        deathpercentText.setText(n_grainjc.getDEATHPERCENT());
        afterText.setText(n_grainjc.getAFTER());


    }


    /**
     * 显示日常检查*
     */
    private void showsbssjc() {
        mhqynText.setText(n_grainjc.getMHQYN());
        if (n_grainjc.getMHQYN().equals("正常")) {
            mhqLinearLayout.setVisibility(View.GONE);
        } else {
            mhqLinearLayout.setVisibility(View.VISIBLE);
        }

        mhqviewText.setText(n_grainjc.getMHQVIEW());
        fld6Text.setText(n_grainjc.getFLD6());

        xfsynText.setText(n_grainjc.getXFSYN());
        if (n_grainjc.getXFSYN().equals("正常")) {
            xfsLinearLayout.setVisibility(View.GONE);
        } else {
            xfsLinearLayout.setVisibility(View.VISIBLE);
        }
        xfsviewText.setText(n_grainjc.getXFSVIEW());
        fld7Text.setText(n_grainjc.getFLD7());

        pdxynText.setText(n_grainjc.getPDXYN());
        if (n_grainjc.getPDXYN().equals("正常")) {
            pdxLinearLayout.setVisibility(View.GONE);
        } else {
            pdxLinearLayout.setVisibility(View.VISIBLE);
        }
        pdxviewText.setText(n_grainjc.getPDXVIEW());
        fld8Text.setText(n_grainjc.getFLD8());

        gbjynText.setText(n_grainjc.getGBJYN());
        if (n_grainjc.getGBJYN().equals("正常")) {
            gbjLinearLayout.setVisibility(View.GONE);
        } else {
            gbjLinearLayout.setVisibility(View.VISIBLE);
        }
        gbjviewText.setText(n_grainjc.getGBJVIEW());
        fld9Text.setText(n_grainjc.getFLD9());

        zlfjynText.setText(n_grainjc.getZLFJYN());
        if (n_grainjc.getZLFJYN().equals("正常")) {
            zlfjLinearLayout.setVisibility(View.GONE);
        } else {
            zlfjLinearLayout.setVisibility(View.VISIBLE);
        }
        zlviewText.setText(n_grainjc.getZLVIEW());
        fld10Text.setText(n_grainjc.getFLD10());

        dtynText.setText(n_grainjc.getDTYN());
        if (n_grainjc.getDTYN().equals("正常")) {
            dtLinearLayout.setVisibility(View.GONE);
        } else {
            dtLinearLayout.setVisibility(View.VISIBLE);
        }
        dtviewText.setText(n_grainjc.getDTVIEW());
        fld11Text.setText(n_grainjc.getFLD11());

        lightynText.setText(n_grainjc.getLIGHTYN());
        if (n_grainjc.getLIGHTYN().equals("正常")) {
            lightLinearLayout.setVisibility(View.GONE);
        } else {
            lightLinearLayout.setVisibility(View.VISIBLE);
        }
        lightviewText.setText(n_grainjc.getLIGHTVIEW());
        fld12Text.setText(n_grainjc.getFLD12());

        cynText.setText(n_grainjc.getCYN());
        if (n_grainjc.getCYN().equals("正常")) {
            cyLinearLayout.setVisibility(View.GONE);
        } else {
            cyLinearLayout.setVisibility(View.VISIBLE);
        }
        cviewText.setText(n_grainjc.getCHVIEW());
        fld16Text.setText(n_grainjc.getFLD16());

        xcwsynText.setText(n_grainjc.getXCWSYN());
        xcwsviewText.setText(n_grainjc.getXCWSVIEW());
        fld25Text.setText(n_grainjc.getFLD25());

        cnwsynText.setText(n_grainjc.getCNWSYN());
        cnwsviewText.setText(n_grainjc.getCNWSVIEW());
        fld17Text.setText(n_grainjc.getFLD17());

        tlwsynText.setText(n_grainjc.getTLWSYN());
        tlwsviewText.setText(n_grainjc.getTLWSVIEW());
        fld18Text.setText(n_grainjc.getFLD18());

        dkwsynText.setText(n_grainjc.getDKWSYN());
        dgwsviewText.setText(n_grainjc.getDGWSVIEW());
        fld19Text.setText(n_grainjc.getFLD19());

        cflyynText.setText(n_grainjc.getCFLYYN());
        if (n_grainjc.getCFLYYN().equals("正常")) {
            cflyLinearLayout.setVisibility(View.GONE);
        } else {
            cflyLinearLayout.setVisibility(View.VISIBLE);
        }
        cflyviewText.setText(n_grainjc.getCFLYVIEW());
        fld20Text.setText(n_grainjc.getFLD20());

        dgfjynText.setText(n_grainjc.getDGFJYN());
        if (n_grainjc.getDGFJYN().equals("正常")) {
            dgfjLinearLayout.setVisibility(View.GONE);
        } else {
            dgfjLinearLayout.setVisibility(View.VISIBLE);
        }
        dgfjviewText.setText(n_grainjc.getDGFJVIEW());
        fld21Text.setText(n_grainjc.getFLD21());

        lxfjynText.setText(n_grainjc.getLXFJYN());
        if (n_grainjc.getLXFJYN().equals("正常")) {
            lxfjLinearLayout.setVisibility(View.GONE);
        } else {
            lxfjLinearLayout.setVisibility(View.VISIBLE);
        }
        lxfjviewText.setText(n_grainjc.getLXFJVIEW());
        fld22Text.setText(n_grainjc.getFLD22());

    }


    /**
     * 显示出入仓作业*
     */
    private void showcrczy() {

        objText.setText(n_grainjc.getOBJ());
        contentText.setText(n_grainjc.getCONTENT());
        fromText.setText(n_grainjc.getFROM());
        toText.setText(n_grainjc.getTO());

        if (n_grainjc.getPS1().equals("Y")) {
            ps1Text.setChecked(true);
        } else {
            ps1Text.setChecked(false);
        }
        if (n_grainjc.getCM1().equals("Y")) {
            cm1Text.setChecked(true);
        } else {
            cm1Text.setChecked(false);
        }
        if (n_grainjc.getQC1().equals("Y")) {
            qc1Text.setChecked(true);
        } else {
            qc1Text.setChecked(false);
        }
        if (n_grainjc.getDR1().equals("Y")) {
            dr1Text.setChecked(true);
        } else {
            dr1Text.setChecked(false);
        }


        if (n_grainjc.getSFM1().equals("Y")) {
            sfm1Text.setChecked(true);
        } else {
            sfm1Text.setChecked(false);
        }
        if (n_grainjc.getAWP1().equals("Y")) {
            awp1Text.setChecked(true);
        } else {
            awp1Text.setChecked(false);
        }

        if (n_grainjc.getBC1().equals("Y")) {
            bc1Text.setChecked(true);
        } else {
            bc1Text.setChecked(false);
        }

        if (n_grainjc.getFK1().equals("Y")) {
            fk1Text.setChecked(true);
        } else {
            fk1Text.setChecked(false);
        }

        if (n_grainjc.getSR1().equals("Y")) {
            sr1Text.setChecked(true);
        } else {
            sr1Text.setChecked(false);
        }

        if (n_grainjc.getDM1().equals("Y")) {
            dm1Text.setChecked(true);
        } else {
            dm1Text.setChecked(false);
        }


    }


    private CompoundButton.OnCheckedChangeListener jlynCheckBoxOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                sfjlLayout.setVisibility(View.VISIBLE);
            } else {
                sfjlLayout.setVisibility(View.GONE);
            }
        }
    };

    private CompoundButton.OnCheckedChangeListener smynCheckBoxOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                sfsmLayout.setVisibility(View.VISIBLE);
            } else {
                sfsmLayout.setVisibility(View.GONE);
            }
        }
    };
    private CompoundButton.OnCheckedChangeListener chynCheckBoxOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                sfhcLayout.setVisibility(View.VISIBLE);
            } else {
                sfhcLayout.setVisibility(View.GONE);
            }
        }
    };
    private CompoundButton.OnCheckedChangeListener shynCheckBoxOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                sfshLayout.setVisibility(View.VISIBLE);
            } else {
                sfshLayout.setVisibility(View.GONE);
            }
        }
    };


    /**
     * 设置日期选择器*
     */
    private void setDataListener() {

        final Calendar objTime = Calendar.getInstance();
        int iYear = objTime.get(Calendar.YEAR);
        int iMonth = objTime.get(Calendar.MONTH);
        int iDay = objTime.get(Calendar.DAY_OF_MONTH);
        int hour = objTime.get(Calendar.HOUR_OF_DAY);

        int minute = objTime.get(Calendar.MINUTE);


        datePickerDialog = new DatePickerDialog(this, new datelistener(), iYear, iMonth, iDay);
    }


    /**
     * 设置时间选择器
     **/
    private void setTimeListener() {
        final Calendar objTime = Calendar.getInstance();
        int hour = objTime.get(Calendar.HOUR_OF_DAY);
        int minute = objTime.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(this, new timelistener(), hour, minute, true);
    }


    /**
     * 日期选择器
     **/
    private class MydateListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            layoutnum = 0;
            sb = new StringBuffer();
            layoutnum = view.getId();
            datePickerDialog.show();
        }
    }

    /**
     * 日期选择器
     **/
    private class MyTimeListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            layoutnum = 0;
            sb = new StringBuffer();
            layoutnum = view.getId();
            timePickerDialog.show();
        }
    }

    /**
     * 日期选择器
     **/
    private class datelistener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            sb = new StringBuffer();
            Log.i("year=", year + "");
            monthOfYear = monthOfYear + 1;
            if (dayOfMonth < 10) {
                sb.append(year + "-" + monthOfYear + "-" + "0" + dayOfMonth);
            } else {
                sb.append(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
            if (layoutnum == R.id.date2_text_id) {
                date2Text.setText(sb.toString());
            } else if (layoutnum == R.id.mos2_text_id) {
                mos2Text.setText(sb.toString());
            } else if (layoutnum == R.id.mos8_text_id) {
                mos8Text.setText(sb.toString());
            } else if (layoutnum == R.id.mos9_text_id) {
                mos9Text.setText(sb.toString());
            }
        }
    }

    /**
     * 时间选择器
     **/
    private class timelistener implements TimePickerDialog.OnTimeSetListener {


        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            sb = new StringBuffer();
            if (hourOfDay < 10) {
                if (minute < 10) {
                    sb.append("0" + hourOfDay + ":0" + minute + ":" + "00");
                } else {
                    sb.append("0" + hourOfDay + ":" + minute + ":" + "00");
                }
            } else {
                if (minute < 10) {
                    sb.append(hourOfDay + ":0" + minute + ":" + "00");
                } else {
                    sb.append(hourOfDay + ":" + minute + ":" + "00");
                }
            }
            if (layoutnum == R.id.from_text_id) {
                fromText.setText(sb.toString());
            } else if (layoutnum == R.id.to_text_id) {
                toText.setText(sb.toString());
            }
        }
    }


    //获取选项值
    private void getOptionsValue(final String title, String url, final TextView textview) {
        {
            HttpManager.getData(this, HttpManager.getALNDOMAIN(url), new HttpRequestHandler<Results>() {


                @Override
                public void onSuccess(Results data) {

                }

                @Override
                public void onSuccess(Results data, int totalPages, int currentPage) {
                    ArrayList<ALNDOMAIN> item = JsonUtils.parsingALNDOMAIN(N_grainjcAddActivity.this, data.getResultlist());
                    if (item == null || item.isEmpty()) {
                        MessageUtils.showMiddleToast(N_grainjcAddActivity.this, getString(R.string.qiangyang_type_text));
                    } else {
                        types = new String[item.size()];
                        for (int i = 0; i < item.size(); i++) {
                            types[i] = item.get(i).getDESCRIPTION();
                        }
                        if (types != null && types.length != 0) {

                            showShareBottomDialog(title, types, textview);

                        } else {
                            MessageUtils.showMiddleToast(N_grainjcAddActivity.this, getString(R.string.qiangyang_type_text));
                        }

                    }

                }

                @Override
                public void onFailure(String error) {

                }
            });

        }
    }


    /**
     * 显示选项框
     **/
    private void showShareBottomDialog(String title, final String[] typesitem, final TextView textview) {

        final ShareBottomDialog dialog = new ShareBottomDialog(N_grainjcAddActivity.this, typesitem, null);


        dialog.title(title)//
                .titleTextSize_SP(14.5f)//
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                {
                    textview.setText(typesitem[position]);

                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });


    }

    /**
     * 显示选项框
     **/
    private void showShareBottomDialog1(String title, final String[] typesitem, final TextView textview) {

        final ShareBottomDialog dialog = new ShareBottomDialog(N_grainjcAddActivity.this, typesitem, null);


        dialog.title(title)//
                .titleTextSize_SP(14.5f)//
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                {
                    textview.setText(typesitem[position]);
                    if (textview == frynTextView) {
                        if (typesitem[position].equals("正常")) {
                            frLayout.setVisibility(View.GONE);
                        } else {
                            frLayout.setVisibility(View.VISIBLE);
                        }
                    } else if (textview == jlynTextView) {
                        if (typesitem[position].equals("正常")) {
                            sfjlLayout.setVisibility(View.GONE);
                        } else {
                            sfjlLayout.setVisibility(View.VISIBLE);
                        }
                    } else if (textview == smynTextView) {
                        if (typesitem[position].equals("正常")) {
                            sfsmLayout.setVisibility(View.GONE);
                        } else {
                            sfsmLayout.setVisibility(View.VISIBLE);
                        }
                    } else if (textview == chynTextView) {
                        if (typesitem[position].equals("正常")) {
                            sfhcLayout.setVisibility(View.GONE);
                        } else {
                            sfhcLayout.setVisibility(View.VISIBLE);
                        }
                    } else if (textview == shynTextView) {
                        if (typesitem[position].equals("正常")) {
                            sfshLayout.setVisibility(View.GONE);
                        } else {
                            sfshLayout.setVisibility(View.VISIBLE);
                        }
                    } else if (textview == mhqynText) {
                        if (typesitem[position].equals("正常")) {
                            mhqLinearLayout.setVisibility(View.GONE);
                        } else {
                            mhqLinearLayout.setVisibility(View.VISIBLE);
                        }
                    } else if (textview == xfsynText) {
                        if (typesitem[position].equals("正常")) {
                            xfsLinearLayout.setVisibility(View.GONE);
                        } else {
                            xfsLinearLayout.setVisibility(View.VISIBLE);
                        }
                    } else if (textview == pdxynText) {
                        if (typesitem[position].equals("正常")) {
                            pdxLinearLayout.setVisibility(View.GONE);
                        } else {
                            pdxLinearLayout.setVisibility(View.VISIBLE);
                        }
                    } else if (textview == gbjynText) {
                        if (typesitem[position].equals("正常")) {
                            gbjLinearLayout.setVisibility(View.GONE);
                        } else {
                            gbjLinearLayout.setVisibility(View.VISIBLE);
                        }
                    } else if (textview == zlfjynText) {
                        if (typesitem[position].equals("正常")) {
                            zlfjLinearLayout.setVisibility(View.GONE);
                        } else {
                            zlfjLinearLayout.setVisibility(View.VISIBLE);
                        }
                    } else if (textview == dtynText) {
                        if (typesitem[position].equals("正常")) {
                            dtLinearLayout.setVisibility(View.GONE);
                        } else {
                            dtLinearLayout.setVisibility(View.VISIBLE);
                        }
                    } else if (textview == lightynText) {
                        if (typesitem[position].equals("正常")) {
                            lightLinearLayout.setVisibility(View.GONE);
                        } else {
                            lightLinearLayout.setVisibility(View.VISIBLE);
                        }
                    } else if (textview == cynText) {
                        if (typesitem[position].equals("正常")) {
                            cyLinearLayout.setVisibility(View.GONE);
                        } else {
                            cyLinearLayout.setVisibility(View.VISIBLE);
                        }
                    } else if (textview == cflyynText) {
                        if (typesitem[position].equals("正常")) {
                            cflyLinearLayout.setVisibility(View.GONE);
                        } else {
                            cflyLinearLayout.setVisibility(View.VISIBLE);
                        }
                    } else if (textview == dgfjynText) {
                        if (typesitem[position].equals("正常")) {
                            dgfjLinearLayout.setVisibility(View.GONE);
                        } else {
                            dgfjLinearLayout.setVisibility(View.VISIBLE);
                        }
                    } else if (textview == lxfjynText) {
                        if (typesitem[position].equals("正常")) {
                            lxfjLinearLayout.setVisibility(View.GONE);
                        } else {
                            lxfjLinearLayout.setVisibility(View.VISIBLE);
                        }
                    }
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });


    }


    //提交弹出框

    /**
     * 提交数据*
     */

    private void submitNormalDialog() {

        final NormalDialog dialog = new NormalDialog(N_grainjcAddActivity.this);
        dialog.content("确定提交数据吗？")//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {


                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        getLoadingDialog("正在提交");
                        startAsyncTask();

                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });
    }


    private FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(this, msg);
        return mLoadingDialog;
    }


    /**
     * 提交数据*
     */
    private void startAsyncTask() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String reviseresult = AndroidClientService.addAndUpdateN_grainjc(N_grainjcAddActivity.this, JsonUtils.encapsulationN_GRAINJC(getN_grainjc()));
                return reviseresult;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                MessageUtils.showMiddleToast(N_grainjcAddActivity.this, s);
                if (s.equals("修改成功")) {
                    finish();
                }


            }
        }.execute();


    }


    /**
     * 封装数据
     **/
    private N_GRAINJC getN_grainjc() {
        n_grainjc = new N_GRAINJC();
        String description = descriptionText.getText().toString();
        n_grainjc.setDESCRIPTION(description);
        String reportdate = reportdateText.getText().toString();
        n_grainjc.setREPORTDATE(reportdate);

        String storeman = storemanText.getText().toString();
        n_grainjc.setREPORTBY(storeman);
        n_grainjc.setSTOREMAN(storeman);
        String area = areaText.getText().toString();
        n_grainjc.setAREA(area);
        String worktype = worktypeText.getText().toString();
        n_grainjc.setWORKTYPE(worktype);
        String loc = locText.getText().toString();
        n_grainjc.setLOC(loc);
        String storepottype = storepottypeText.getText().toString();
        n_grainjc.setSTOREPOTTYPE(storepottype);
        String foodtype = foodtypeText.getText().toString();
        n_grainjc.setFOODTYPE(foodtype);
        String numberinthelibrary = numberinthelibraryText.getText().toString();
        n_grainjc.setNUMBERINTHELIBRARY(numberinthelibrary);
        String moisture = moistureText.getText().toString();
        n_grainjc.setMOISTURE(moisture);
        String status = statusText.getText().toString();
        n_grainjc.setSTATUS(status);
        String weather = weatherText.getText().toString();
        n_grainjc.setWEATHER(weather);
        String tem = temText.getText().toString();
        n_grainjc.setTEM(tem);
        String humidity = humidityText.getText().toString();
        n_grainjc.setHUMIDITY(humidity);
        String tem2 = tem2Text.getText().toString();
        n_grainjc.setTEM2(tem2);
        String date2 = date2Text.getText().toString();
        n_grainjc.setDATE2(date2);
        String fryn = frynTextView.getText().toString();
        n_grainjc.setFRYN(fryn);
        String frarea = frareaText.getText().toString();
        n_grainjc.setFRAREA(frarea);
        String frareai = frareaiText.getText().toString();
        n_grainjc.setFRAREAI(frareai);
        String frdepth = frdepthText.getText().toString();
        n_grainjc.setFRDEPTH(frdepth);
        String frview = frviewText.getText().toString();
        n_grainjc.setFRVIEW(frview);
        String frjyts = frjytsText.getText().toString();
        n_grainjc.setFRJYTS(frjyts);
        String fld1 = fld1Text.getText().toString();
        n_grainjc.setFLD1(fld1);
        String jlyn = jlynTextView.getText().toString();
        n_grainjc.setJLYN(jlyn);
        String jlarea = jlareaText.getText().toString();
        n_grainjc.setJLAREA(jlarea);
        String jlareai = jlareaiText.getText().toString();
        n_grainjc.setJLAREAI(jlareai);
        String jldepth = jldepthText.getText().toString();
        n_grainjc.setJLDEPTH(jldepth);
        String jlview = jlviewText.getText().toString();
        n_grainjc.setJLVIEW(jlview);
        String jljyts = jljytsText.getText().toString();
        n_grainjc.setJLJYTS(jljyts);
        String fld2 = fld2Text.getText().toString();
        n_grainjc.setFLD2(fld2);
        String smyn = smynTextView.getText().toString();
        n_grainjc.setSMYN(smyn);
        String smarea = smareaText.getText().toString();
        n_grainjc.setSMAREA(smarea);
        String smplace = smplaceText.getText().toString();
        n_grainjc.setSMPLACE(smplace);
        String smcd = smcdText.getText().toString();
        n_grainjc.setSMCD(smcd);
        String smview = smviewText.getText().toString();
        n_grainjc.setSMVIEW(smview);
        String smyp = smypText.getText().toString();
        n_grainjc.setSMYP(smyp);
        String smjl = smjlText.getText().toString();
        n_grainjc.setSMJL(smjl);
        String fld3 = fld3Text.getText().toString();
        n_grainjc.setFLD3(fld3);
        String smjyts = smjytsText.getText().toString();
        n_grainjc.setSMJYTS(smjyts);
        String chyn = chynTextView.getText().toString();
        n_grainjc.setCHYN(chyn);
        String charea = chareaText.getText().toString();
        n_grainjc.setCHAREA(charea);
        String chstatus = chstatusText.getText().toString();
        n_grainjc.setCHSTATUS(chstatus);
        String smjbcl = smjbclText.getText().toString();
        n_grainjc.setSMJBCL(smjbcl);
        String fld4 = fld4Text.getText().toString();
        n_grainjc.setFLD4(fld4);
        String chview = chviewText.getText().toString();
        n_grainjc.setCHVIEW(chview);
        String chjyts = chjytsText.getText().toString();
        n_grainjc.setCHJYTS(chjyts);
        String petyj = petyjText.getText().toString();
        n_grainjc.setPETYJ(petyj);
        String drugsum = drugsumText.getText().toString();
        n_grainjc.setDRUGSUM(drugsum);
        String mos1 = mos1Text.getText().toString();
        n_grainjc.setMOS1(mos1);
        String mos2 = mos2Text.getText().toString();
        n_grainjc.setMOS2(mos2);
        String mos3 = mos3Text.getText().toString();
        n_grainjc.setMOS3(mos3);
        String foodsize = foodsizeText.getText().toString();
        n_grainjc.setFOODSIZE(foodsize);
        String spacesize = spacesizeText.getText().toString();
        n_grainjc.setSPACESIZE(spacesize);
        String mos5 = mos5Text.getText().toString();
        n_grainjc.setMOS5(mos5);
        String medtype = medtypeText.getText().toString();
        n_grainjc.setMEDTYPE(medtype);
        String medpeoples = medpeoplesText.getText().toString();
        n_grainjc.setMEDPEOPLES(medpeoples);
        String closetype = closetypeText.getText().toString();
        n_grainjc.setCLOSETYPE(closetype);
        String mos8 = mos8Text.getText().toString();
        n_grainjc.setMOS8(mos8);
        String mos9 = mos9Text.getText().toString();
        n_grainjc.setMOS9(mos9);
        String foodmed = foodmedText.getText().toString();
        n_grainjc.setFOODMED(foodmed);
        String spacemed = spacemedText.getText().toString();
        n_grainjc.setSPACEMED(spacemed);
        String mos10 = mos10Text.getText().toString();
        n_grainjc.setMOS10(mos10);
        String foodtem = foodtemText.getText().toString();
        n_grainjc.setFOODTEM(foodtem);
        String foodtem1 = foodtem1Text.getText().toString();
        n_grainjc.setFOODTEM1(foodtem1);
        String mos11 = mos11Text.getText().toString();
        n_grainjc.setMOS11(mos11);
        String mos12 = mos12Text.getText().toString();
        n_grainjc.setMOS12(mos12);
        String mos13 = mos13Text.getText().toString();
        n_grainjc.setMOS13(mos13);
        String mos14 = mos14Text.getText().toString();
        n_grainjc.setMOS14(mos14);
        String mos15 = mos15Text.getText().toString();
        n_grainjc.setMOS15(mos15);
        String xzyn = xzynText.getText().toString();
        n_grainjc.setXZYN(xzyn);
        String shyn = shynTextView.getText().toString();
        n_grainjc.setSHYN(shyn);
        String shzl = shzlText.getText().toString();
        n_grainjc.setSHZL(shzl);
        String sharea = shareaText.getText().toString();
        n_grainjc.setSHAREA(sharea);
        String fld5 = fld5Text.getText().toString();
        n_grainjc.setFLD5(fld5);
        String shjyts = shjytsText.getText().toString();
        n_grainjc.setSHJYTS(shjyts);
        String shview = shviewText.getText().toString();
        n_grainjc.setSHVIEW(shview);
        String shwlfz = shwlfzText.getText().toString();
        n_grainjc.setSHWLFZ(shwlfz);
        String method = methodTextView.getText().toString();
        n_grainjc.setMETHOD(method);
        String mousetype = mousetypeText.getText().toString();
        n_grainjc.setMOUSETYPE(mousetype);
        String mousename = mousenameText.getText().toString();
        n_grainjc.setMOUSENAME(mousename);
        String bait = baitText.getText().toString();
        n_grainjc.setBAIT(bait);
        String shhxcs = shhxcsText.getText().toString();
        n_grainjc.setSHHXCS(shhxcs);
        String shhxcs_ = shhxcs_Text.getText().toString();
        n_grainjc.setSHHXCS_(shhxcs_);
        String dose = doseText.getText().toString();
        n_grainjc.setDOSE(dose);
        String setplace = setplaceText.getText().toString();
        n_grainjc.setSETPLACE(setplace);
        String density = densityText.getText().toString();
        n_grainjc.setDENSITY(density);
        String robpercent = robpercentText.getText().toString();
        n_grainjc.setROBPERCENT(robpercent);
        String deathpercent = deathpercentText.getText().toString();
        n_grainjc.setDEATHPERCENT(deathpercent);
        String after = afterText.getText().toString();
        n_grainjc.setAFTER(after);
        String mhqyn = mhqynText.getText().toString();
        n_grainjc.setMHQYN(mhqyn);
        String mhqview = mhqviewText.getText().toString();
        n_grainjc.setMHQVIEW(mhqview);
        String fld6 = fld6Text.getText().toString();
        n_grainjc.setFLD6(fld6);
        String xfsyn = xfsynText.getText().toString();
        n_grainjc.setXFSYN(xfsyn);
        String xfsview = xfsviewText.getText().toString();
        n_grainjc.setXFSVIEW(xfsview);
        String fld7 = fld7Text.getText().toString();
        n_grainjc.setFLD7(fld7);
        String pdxyn = pdxynText.getText().toString();
        n_grainjc.setPDXYN(pdxyn);
        String pdxview = pdxviewText.getText().toString();
        n_grainjc.setPDXVIEW(pdxview);
        String fld8 = fld8Text.getText().toString();
        n_grainjc.setFLD8(fld8);
        String gbjyn = gbjynText.getText().toString();
        n_grainjc.setGBJYN(gbjyn);
        String gbjview = gbjviewText.getText().toString();
        n_grainjc.setGBJVIEW(gbjview);
        String fld9 = fld9Text.getText().toString();
        n_grainjc.setFLD9(fld9);
        String zlfjyn = zlfjynText.getText().toString();
        n_grainjc.setZLFJYN(zlfjyn);
        String zlview = zlviewText.getText().toString();
        n_grainjc.setZLVIEW(zlview);
        String fld10 = fld10Text.getText().toString();
        n_grainjc.setFLD10(fld10);
        String dtyn = dtynText.getText().toString();
        n_grainjc.setDTYN(dtyn);
        String dtview = dtviewText.getText().toString();
        n_grainjc.setDTVIEW(dtview);
        String fld11 = fld11Text.getText().toString();
        n_grainjc.setFLD11(fld11);
        String lightyn = lightynText.getText().toString();
        n_grainjc.setLIGHTYN(lightyn);
        String lightview = lightviewText.getText().toString();
        n_grainjc.setLIGHTVIEW(lightview);
        String fld12 = fld12Text.getText().toString();
        n_grainjc.setFLD12(fld12);
        String cyn = cynText.getText().toString();
        n_grainjc.setCYN(cyn);
        String cview = cviewText.getText().toString();
        n_grainjc.setCVIEW(cview);
        String fld16 = fld16Text.getText().toString();
        n_grainjc.setFLD16(fld16);
        String xcwsyn = xcwsynText.getText().toString();
        n_grainjc.setXCWSYN(xcwsyn);
        String xcwsview = xcwsviewText.getText().toString();
        n_grainjc.setXCWSVIEW(xcwsview);
        String fld25 = fld25Text.getText().toString();
        n_grainjc.setFLD25(fld25);
        String cnwsyn = cnwsynText.getText().toString();
        n_grainjc.setCNWSYN(cnwsyn);
        String cnwsview = cnwsviewText.getText().toString();
        n_grainjc.setCNWSVIEW(cnwsview);
        String fld17 = fld17Text.getText().toString();
        n_grainjc.setFLD17(fld17);
        String tlwsyn = tlwsynText.getText().toString();
        n_grainjc.setTLWSYN(tlwsyn);
        String tlwsview = tlwsviewText.getText().toString();
        n_grainjc.setTLWSVIEW(tlwsview);
        String fld18 = fld18Text.getText().toString();
        n_grainjc.setFLD18(fld18);
        String dkwsyn = dkwsynText.getText().toString();
        n_grainjc.setDKWSYN(dkwsyn);
        String dgwsview = dgwsviewText.getText().toString();
        n_grainjc.setDGWSVIEW(dgwsview);
        String fld19 = fld19Text.getText().toString();
        n_grainjc.setFLD19(fld19);
        String cflyyn = cflyynText.getText().toString();
        n_grainjc.setCFLYYN(cflyyn);
        String cflyview = cflyviewText.getText().toString();
        n_grainjc.setCFLYVIEW(cflyview);
        String fld20 = fld20Text.getText().toString();
        n_grainjc.setFLD20(fld20);
        String dgfjyn = dgfjynText.getText().toString();
        n_grainjc.setDGFJYN(dgfjyn);
        String dgfjview = dgfjviewText.getText().toString();
        n_grainjc.setDGFJVIEW(dgfjview);
        String fld21 = fld21Text.getText().toString();
        n_grainjc.setFLD21(fld21);
        String lxfjyn = lxfjynText.getText().toString();
        n_grainjc.setLXFJYN(lxfjyn);
        String lxfjview = lxfjviewText.getText().toString();
        n_grainjc.setLXFJVIEW(lxfjview);
        String fld22 = fld22Text.getText().toString();
        n_grainjc.setFLD22(fld22);
        String obj = objText.getText().toString();
        n_grainjc.setOBJ(obj);
        String content = contentText.getText().toString();
        n_grainjc.setCONTENT(content);
        String from = fromText.getText().toString();
        n_grainjc.setFROM(from);
        String to = toText.getText().toString();
        n_grainjc.setTO(to);

        boolean ps1 = ps1Text.isChecked();
        if (ps1) {
            n_grainjc.setPS1("Y");
        } else {
            n_grainjc.setPS1("N");
        }
        boolean cm1 = cm1Text.isChecked();
        if (cm1) {
            n_grainjc.setCM1("Y");
        } else {
            n_grainjc.setCM1("N");
        }
        boolean qc1 = qc1Text.isChecked();
        if (qc1) {
            n_grainjc.setQC1("Y");
        } else {
            n_grainjc.setQC1("N");
        }
        boolean dr1 = dr1Text.isChecked();
        if (dr1) {
            n_grainjc.setDR1("Y");
        } else {
            n_grainjc.setDR1("N");
        }
        boolean sfm1 = sfm1Text.isChecked();
        if (sfm1) {
            n_grainjc.setSFM1("Y");
        } else {
            n_grainjc.setSFM1("N");
        }
        boolean awp1 = awp1Text.isChecked();
        if (awp1) {
            n_grainjc.setAWP1("Y");
        } else {
            n_grainjc.setAWP1("N");
        }
        boolean bc1 = bc1Text.isChecked();
        if (bc1) {
            n_grainjc.setBC1("Y");
        } else {
            n_grainjc.setBC1("N");
        }
        boolean fk1 = fk1Text.isChecked();
        if (fk1) {
            n_grainjc.setFK1("Y");
        } else {
            n_grainjc.setFK1("N");
        }
        boolean sr1 = sr1Text.isChecked();
        if (sr1) {
            n_grainjc.setSR1("Y");
        } else {
            n_grainjc.setSR1("N");
        }
        boolean dm1 = dm1Text.isChecked();
        if (dm1) {
            n_grainjc.setDM1("Y");
        } else {
            n_grainjc.setDM1("N");
        }


        /**传字段**/
        n_grainjc.setGRAINSNUN(""); //编号
        n_grainjc.setCFVIEW("");
        n_grainjc.setCFXN("");
        n_grainjc.setCHELOGNAME("");
        n_grainjc.setCHLY("");
        n_grainjc.setDEFENDTYPE("");
        n_grainjc.setFLD23("");
        n_grainjc.setFLD24("");
        n_grainjc.setFLJVIEW("");
        n_grainjc.setFRPLACE("");
        n_grainjc.setHCMD("");
        n_grainjc.setHXJL("");
        n_grainjc.setHXTYD("");
        n_grainjc.setHXYJMC("");
        n_grainjc.setJWXVIEW("");
        n_grainjc.setMDQVIEW("");
        n_grainjc.setMOS4("");
        n_grainjc.setMOS6("");
        n_grainjc.setMOS7("");
        n_grainjc.setMYCWZ("");
        n_grainjc.setOTHERSVIEW("");
        n_grainjc.setSHJYFZ("");
        n_grainjc.setXCVIEW("");
        n_grainjc.setXFXVIEW("");
        n_grainjc.setYEM3("");
        n_grainjc.setSHMD("");
        n_grainjc.setSPJKVIEW("");
        n_grainjc.setSTORETYPE("");
        n_grainjc.setFLJYN(""); //翻浪机
        n_grainjc.setJWXYN(""); //检温线
        n_grainjc.setMDQYN(""); //麻袋墙
        n_grainjc.setMDQYN(""); //麻袋墙
        n_grainjc.setMYN(""); //门是否损害
        n_grainjc.setOTHERSYN(""); //其它
        n_grainjc.setSPJKYN(""); //监控
        n_grainjc.setXFXYN(""); //消防箱


        return n_grainjc;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1001:
                String loc = data.getExtras().getString("loc");
                locText.setText(loc);
                getLocInfo(loc, reportdateText.getText().toString());
                break;
        }
    }


    /**
     * 根据货位号与创建时间获取相关的粮情信息
     **/


    private void getLocInfo(final String loc, final String reportdate) {
        getLoadingDialog("请稍后...");

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String reviseresult = AndroidClientService.getN_GRAINS(N_grainjcAddActivity.this, loc, dataJson(reportdate));
                return reviseresult;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.i("结果=", s);
                showResult(s);
                mLoadingDialog.dismiss();


            }
        }.execute();

    }


    /**
     * 日期格式转换
     **/
    private String dataJson(String currentData) {
        String newDate = currentData.replace(".", "-");
        Log.i("newDate=", newDate);
        return newDate;
    }

    /**
     * 显示结果
     **/
    private void showResult(String info) {
        if (!info.equals("")) {
            try {
                JSONArray jsonArray = new JSONArray(info);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                String area = jsonObject.getString("AREA");  //责任区
                String foodtype = jsonObject.getString("FOODTYPE"); //粮食类型
                String numberinthelibrary = jsonObject.getString("NUMBERINTHELIBRARY"); //粮食数量
                String moisture = jsonObject.getString("MOISTURE"); //水分
                String weather = jsonObject.getString("WEATHER"); //天气
                String tem = jsonObject.getString("TEM"); //温度
                String humidity = jsonObject.getString("HUMIDITY"); //湿度
                String tem2 = jsonObject.getString("TEM2"); //风向
                String storepottype = jsonObject.getString("STOREPOTTYPE"); //类型

                areaText.setText(area);
                foodtypeText.setText(foodtype);
                numberinthelibraryText.setText(numberinthelibrary);
                moistureText.setText(moisture);
                weatherText.setText(weather);
                temText.setText(tem);
                humidityText.setText(humidity);
                tem2Text.setText(tem2);
                storepottypeText.setText(storepottype);
            } catch (JSONException e) {
                MessageUtils.showMiddleToast(N_grainjcAddActivity.this, "没有当天数据");
                e.printStackTrace();
            }
        }
    }


}