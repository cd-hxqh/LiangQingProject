package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.animation.BaseAnimatorSet;
import com.zcl.hxqh.liangqingmanagement.AppManager;
import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.view.fragment.CclqjcdFragment;
import com.zcl.hxqh.liangqingmanagement.view.fragment.HyybFragment;
import com.zcl.hxqh.liangqingmanagement.view.fragment.N_cartaskFragment;
import com.zcl.hxqh.liangqingmanagement.view.fragment.NavigationDrawerFragment;
import com.zcl.hxqh.liangqingmanagement.view.fragment.PersonFragment;
import com.zcl.hxqh.liangqingmanagement.view.fragment.QydFragment;
import com.zcl.hxqh.liangqingmanagement.view.fragment.WilineFragment;
import com.zcl.hxqh.liangqingmanagement.view.fragment.WorkOrderFragment;

/**
 * MainActivity
 */
public class MainActivity extends BaseActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String TAG = "MainActivity";
    //    private SpinnerAdapter mSpinnerAdapter;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ViewGroup mDrawerLayout;
    private View mActionbarCustom;
    /**
     * 标题*
     */
    private TextView title;

    /**
     * 新建
     **/
    private ImageView addImage;

    /**
     * 仓储粮情检查单*
     */
    private CclqjcdFragment cclqjcdFragment;

    /**
     * 扦样单*
     */
    private QydFragment qydFragment;
    /**
     * 货运预报单*
     */
    private HyybFragment hyybFragment;
    /**
     * 考勤管理*
     */
    private WilineFragment wilinefragment;
    /**
     * 人员查询*
     */
    private PersonFragment personFragment;

    /**
     * 车辆查询
     **/
    private N_cartaskFragment n_cartaskFragment;
    /**
     * 消缺工单
     **/
    private WorkOrderFragment workOrderFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private String[] mFavoriteTabTitles;
    private String[] mFavoriteTabPaths;
    private String[] mMainTitles;

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;


    protected FlippingLoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        initView();

    }

    @Override
    protected void findViewById() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        title = (TextView) findViewById(R.id.title_id);
        addImage = (ImageView) findViewById(R.id.title_add);
        mDrawerLayout = (ViewGroup) findViewById(R.id.drawer_layout);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.left_drawer);
        mTitle = getTitle();

        mMainTitles = getResources().getStringArray(R.array.drawer_tab_titles);


        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.left_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    protected void initView() {
        addImage.setOnClickListener(addImageOnClickListener);
        if (mSelectPos == 3 ) {
            addImage.setImageResource(R.drawable.ic_dk);
        }
    }


    private View.OnClickListener addImageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (mSelectPos) {
                case 0://跳转到粮情检查单新建
                    Intent intent = new Intent(MainActivity.this, N_grainjcAddActivity.class);
                    startActivityForResult(intent, 0);

                    break;
                case 1://跳转到扦样单新建
                    Intent intent1 = new Intent(MainActivity.this, N_sampleAddActivity.class);
                    startActivityForResult(intent1, 0);
                    break;
                case 2://跳转到货运预报新建
                    Intent intent2 = new Intent(MainActivity.this, N_carAddActivity.class);
                    startActivityForResult(intent2, 0);
                    break;
                case 3://跳转到考勤信息新建
                    Intent intent3 = new Intent(MainActivity.this, Nfc_Activity.class);
                    intent3.putExtra("type", "wiline");
                    startActivityForResult(intent3, 0);
                    break;
            }
        }
    };


    int mSelectPos = 0;

    @Override
    public void onNavigationDrawerItemSelected(final int position) {
        mSelectPos = position;

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        switch (position) {
            case 0: //仓储粮情检查单
                if (cclqjcdFragment == null) {
                    cclqjcdFragment = new CclqjcdFragment();
                    Bundle bundle = new Bundle();
                    cclqjcdFragment.setArguments(bundle);
                }
                fragmentTransaction.replace(R.id.container, cclqjcdFragment).commit();
                break;
            case 1: //扦样单
                if (qydFragment == null) {
                    qydFragment = new QydFragment();
                    Bundle bundle = new Bundle();
                    qydFragment.setArguments(bundle);
                }
                fragmentTransaction.replace(R.id.container, qydFragment).commit();
                break;
            case 2: //货运预报
                if (hyybFragment == null) {
                    hyybFragment = new HyybFragment();
                    Bundle bundle = new Bundle();
                    hyybFragment.setArguments(bundle);
                }
                fragmentTransaction.replace(R.id.container, hyybFragment).commit();
                break;
            case 3: //考勤管理
                addImage.setImageResource(R.drawable.ic_dk);
                if (wilinefragment == null) {
                    wilinefragment = new WilineFragment();
                    Bundle bundle = new Bundle();
                    wilinefragment.setArguments(bundle);
                }
                fragmentTransaction.replace(R.id.container, wilinefragment).commit();
                break;
            case 4: //人员查询
                if (personFragment == null) {
                    personFragment = new PersonFragment();
                    Bundle bundle = new Bundle();
                    personFragment.setArguments(bundle);
                }
                fragmentTransaction.replace(R.id.container, personFragment).commit();
                break;
            case 5: //车辆查询
                if (n_cartaskFragment == null) {
                    n_cartaskFragment = new N_cartaskFragment();
                    Bundle bundle = new Bundle();
                    n_cartaskFragment.setArguments(bundle);
                }
                fragmentTransaction.replace(R.id.container, n_cartaskFragment).commit();
                break;
            case 6: //消缺工单
                if (workOrderFragment == null) {
                    workOrderFragment = new WorkOrderFragment();
                    Bundle bundle = new Bundle();
                    workOrderFragment.setArguments(bundle);
                }
                fragmentTransaction.replace(R.id.container, workOrderFragment).commit();
                break;
        }

    }


    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        mTitle = mMainTitles[mSelectPos];
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("");
        title.setText(mTitle);
        if (mSelectPos == 0 || mSelectPos == 1 || mSelectPos == 2) {
            addImage.setVisibility(View.VISIBLE);
            addImage.setImageResource(R.drawable.ic_add);
        } else if (mSelectPos == 3 ) {
            addImage.setVisibility(View.VISIBLE);
            addImage.setImageResource(R.drawable.ic_dk);
        }else if (mSelectPos == 4 ) {
            addImage.setVisibility(View.GONE);
        } else if (mSelectPos == 5) {
            addImage.setVisibility(View.GONE);
        }else if (mSelectPos == 6) {
            addImage.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {

            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }


    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
            return;
        }

        if ((System.currentTimeMillis() - exitTime) > 2000) {
            MessageUtils.showMiddleToast(this, getString(R.string.exit));
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.AppExit(MainActivity.this);
        }
    }


}
