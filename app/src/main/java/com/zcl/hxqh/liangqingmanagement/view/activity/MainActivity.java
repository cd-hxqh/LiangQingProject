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

import com.zcl.hxqh.liangqingmanagement.AppManager;
import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.view.fragment.CclqjcdFragment;
import com.zcl.hxqh.liangqingmanagement.view.fragment.HyybFragment;
import com.zcl.hxqh.liangqingmanagement.view.fragment.NavigationDrawerFragment;
import com.zcl.hxqh.liangqingmanagement.view.fragment.QydFragment;
import com.zcl.hxqh.liangqingmanagement.view.fragment.WilineFragment;

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
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private String[] mFavoriteTabTitles;
    private String[] mFavoriteTabPaths;
    private String[] mMainTitles;

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
    }


    private View.OnClickListener addImageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                switch (mSelectPos){
                    case 0://跳转到粮情检查单新建
                        Intent intent =new Intent(MainActivity.this,N_grainjcAddActivity.class);
                        startActivityForResult(intent,0);

                        break;
                    case 1://跳转到扦样单新建
                        Intent intent1 =new Intent(MainActivity.this,N_sampleAddActivity.class);
                        startActivityForResult(intent1,0);
                        break;
                    case 2://跳转到货运预报新建
                        Intent intent2 =new Intent(MainActivity.this,N_carAddActivity.class);
                        startActivityForResult(intent2,0);
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
                if (wilinefragment == null) {
                    wilinefragment = new WilineFragment();
                    Bundle bundle = new Bundle();
                    wilinefragment.setArguments(bundle);
                }
                fragmentTransaction.replace(R.id.container, wilinefragment).commit();
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
        if (mSelectPos == 0||mSelectPos==1||mSelectPos==2) {
            addImage.setVisibility(View.VISIBLE);
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
