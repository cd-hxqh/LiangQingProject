package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.animation.LayoutTransition;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.adapter.ImageLoadAdapter;
import com.zcl.hxqh.liangqingmanagement.api.HttpManager;
import com.zcl.hxqh.liangqingmanagement.api.HttpRequestHandler;
import com.zcl.hxqh.liangqingmanagement.bean.Results;
import com.zcl.hxqh.liangqingmanagement.constants.Constants;
import com.zcl.hxqh.liangqingmanagement.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.liangqingmanagement.model.WORKORDER;
import com.zcl.hxqh.liangqingmanagement.until.AccountUtils;
import com.zcl.hxqh.liangqingmanagement.until.DateTimeSelect;
import com.zcl.hxqh.liangqingmanagement.until.ImageCompressUtils;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.until.T;
import com.zcl.hxqh.liangqingmanagement.view.widght.AbstractSpinerAdapter;
import com.zcl.hxqh.liangqingmanagement.view.widght.GlideImageLoader;
import com.zcl.hxqh.liangqingmanagement.view.widght.ShareBottomDialog;
import com.zcl.hxqh.liangqingmanagement.view.widght.SpinerPopWindow;
import com.zcl.hxqh.liangqingmanagement.webserviceclient.AndroidClientService;

import org.json.JSONException;
import org.json.JSONObject;
import org.kobjects.base64.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * 缺陷工单详情
 */
public class WorkorderAddNewActivity extends BaseActivity implements ImageLoadAdapter.OnRecyclerViewItemClickListener {
    private static String TAG = "WorkorderAddNewActivity";

    public static final int REQUEST_CODE_PREVIEW = 101;

    private ImageLoadAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 9;               //允许选择图片最大数

    private List<String> nameList = new ArrayList<String>();

    private File takeImageFile;

    private ImagePicker imagePicker;

    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;

    /**
     * 提交按钮
     **/
    private Button submitBtn;

    private TextView sb;//设备
    private TextView sbwz;//设备位置
    private ImageView photoImg;//拍照
    private EditText description;//缺陷描述
    private TextView fxbm;//发现部门
    private TextView fxr;//发现人
    private TextView reportdate;//发现时间
    private TextView zrr;//责任人
    private TextView schedfinish;//整改期限
    private EditText n_recreq;//整改要求
    private CheckBox worktype;//是否排查
    private EditText remarkdesc;//备注

    private String ASSETNUM;//设备编号
    private String zrrId;//责任人id

    private WORKORDER workorder;


    /**界面信息**/

    /**
     * 时间选择器
     **/
    private DatePickerDialog datePickerDialog;
    StringBuffer stringBuffer;
    private int layoutnum;

    //下拉列表
    private SpinerPopWindow mSpinerPopWindow;

    //弹出框
    private ArrayList<DialogMenuItem> mMenuItems = new ArrayList<>();


    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;


    protected FlippingLoadingDialog mLoadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workorder_addnew);
        geiIntentData();
        findViewById();
        initView();
        initWidget();

        initImagePicker();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void geiIntentData() {
        workorder = (WORKORDER) getIntent().getSerializableExtra("workorder");
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        submitBtn = (Button) findViewById(R.id.sbmit_id);

        sb = (TextView) findViewById(R.id.workorder_sb);
        sbwz = (TextView) findViewById(R.id.workorder_sbwz);
        photoImg = (ImageView) findViewById(R.id.photo_image);
        description = (EditText) findViewById(R.id.workorder_description);
        fxbm = (TextView) findViewById(R.id.workorder_fxbm);
        fxr = (TextView) findViewById(R.id.workorder_fxr);
        reportdate = (TextView) findViewById(R.id.workorder_reportdate);
        zrr = (TextView) findViewById(R.id.workorder_zrr);
        schedfinish = (TextView) findViewById(R.id.workorder_schedfinish);
        n_recreq = (EditText) findViewById(R.id.workorder_n_recreq);
        worktype = (CheckBox) findViewById(R.id.workorder_worktype);
        remarkdesc = (EditText) findViewById(R.id.workorder_remarkdesc);

        ViewGroup container = (ViewGroup) findViewById(R.id.container);
        LayoutTransition transition = new LayoutTransition();
        container.setLayoutTransition(transition);
    }


    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.workorder_addnew_title);
        submitBtn.setVisibility(View.VISIBLE);
        submitBtn.setOnClickListener(submitBtnBtnOnClickListener);

        sb.setOnClickListener(sbOnClickListener);
        fxbm.setOnClickListener(fxbmOnClickListener);
        fxr.setOnClickListener(new personOnClickListener(1002));
        zrr.setOnClickListener(new personOnClickListener(1003));
        reportdate.setOnClickListener(new TimeOnClickListener(reportdate));
        schedfinish.setOnClickListener(new TimeOnClickListener(schedfinish));
        photoImg.setOnClickListener(photoOnClickListener);

        //设置数据源
        setSpiner();
    }

    private void setSpiner() {

        String[] names = getResources().getStringArray(R.array.fxbm_array);
        for (int i = 0; i < names.length; i++) {
            nameList.add(names[i]);
        }

        mSpinerPopWindow = new SpinerPopWindow(this);
        mSpinerPopWindow.refreshData(nameList, 0);
        mSpinerPopWindow.setItemListener(SpinerItemListener);
    }

    private AbstractSpinerAdapter.IOnItemSelectListener SpinerItemListener = new AbstractSpinerAdapter.IOnItemSelectListener() {
        @Override
        public void onItemClick(int pos) {
            setHero(pos);
        }
    };


    private void setHero(int pos) {
        if (pos >= 0 && pos <= nameList.size()) {
            String value = nameList.get(pos);

            fxbm.setText(value);
        }
    }

    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    private View.OnClickListener submitBtnBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            submitNormalDialog();
        }
    };

    private View.OnClickListener sbOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WorkorderAddNewActivity.this, AssetChooseActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    private View.OnClickListener fxbmOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSpinerPopWindow.setWidth(fxbm.getWidth());
            mSpinerPopWindow.showAsDropDown(fxbm);
        }
    };

    private View.OnClickListener photoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            imagePicker.takePicture(WorkorderAddNewActivity.this, ImagePicker.REQUEST_CODE_TAKE);
        }
    };

    private class personOnClickListener implements View.OnClickListener {
        int requestCode;

        private personOnClickListener(int requestCode) {
            this.requestCode = requestCode;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WorkorderAddNewActivity.this, PersonChooseActivity.class);
            startActivityForResult(intent, requestCode);
        }
    }

    private void initWidget() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        selImageList = new ArrayList<>();
        adapter = new ImageLoadAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void showResult(ArrayList<ImageItem> paths) {

        adapter.setImages(paths);

    }

    /**
     * 显示选项框
     **/
    private void showShareBottomDialog(String title, final String[] typesitem, final TextView textview) {

        final ShareBottomDialog dialog = new ShareBottomDialog(WorkorderAddNewActivity.this, getResources().getStringArray(R.array.fxbm_array), null);


        dialog.title(title)//
                .titleTextSize_SP(14.5f)//
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.showShort(WorkorderAddNewActivity.this, typesitem[position]);
                textview.setText(typesitem[position]);
                dialog.dismiss();
            }
        });
    }

    //时间选择监听
    private class TimeOnClickListener implements View.OnClickListener {
        TextView textView;

        private TimeOnClickListener(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void onClick(View view) {
            new DateTimeSelect(WorkorderAddNewActivity.this, textView).showDialog();
        }
    }

    //提交弹出框

    /**
     * 提交数据*
     */
    private void submitNormalDialog() {

        final NormalDialog dialog = new NormalDialog(WorkorderAddNewActivity.this);
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
                        getLoadingDialog("正在提交").show();
                        startAsyncTask();

                        dialog.dismiss();
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
        final String sbText = ASSETNUM;
        final String sbwzText = sbwz.getText().toString();
        final String descriptionText = description.getText().toString();
        final String fxbmText = fxbm.getText().toString();
        final String fxrText = fxr.getText().toString();
        final String reportdateText = reportdate.getText().toString();
        final String zrrText = zrrId;
        final String schedfinishText = schedfinish.getText().toString();
        final String n_recreqText = n_recreq.getText().toString();
        final String worktypeText = worktype.isChecked() ? "HD" : "CM";
        final String remarkdescText = remarkdesc.getText().toString();
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return AndroidClientService.saveQX(WorkorderAddNewActivity.this, AccountUtils.getloginUserName(WorkorderAddNewActivity.this), sbText, descriptionText,
                        fxbmText, "", fxrText, reportdateText, remarkdescText, worktypeText, zrrText, n_recreqText, schedfinishText);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.i(TAG, "s=" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.has("success") && jsonObject.getString("success").equals("1")) {
                        MessageUtils.showMiddleToast(WorkorderAddNewActivity.this, "新建成功");
                        mLoadingDialog.setText("正在上传图片...");
                        String msg = jsonObject.getString("msg");
                        for (ImageItem imageItem : adapter.getImages()) {
                            int i = 0;
                            i++;
                            startAsyncTask(imageItem.path, msg.contains(",") ? msg.replace(",", "") : msg, i);
                        }
                    } else {
                        mLoadingDialog.dismiss();
                        MessageUtils.showMiddleToast(WorkorderAddNewActivity.this, "新建失败");
                    }
                } catch (JSONException e) {
                    mLoadingDialog.dismiss();
                    MessageUtils.showMiddleToast(WorkorderAddNewActivity.this, s);
                }
            }
        }.execute();
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
//            case IMAGE_ITEM_ADD:
//                //打开选择,本次允许选择的数量
//                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
//                Intent intent = new Intent(this, ImageGridActivity.class);
//                startActivityForResult(intent, REQUEST_CODE_SELECT);
//                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra("results", ImagePicker.SERVER_IMAGE_ITEMS);
                intentPreview.putExtra("IMAGE_URL", AccountUtils.getIpAddress(this) + Constants.WORK_FLOW_URL);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1001:
                if (data != null) {
                    sb.setText(data.getStringExtra("N_MODELNUM"));
                    sbwz.setText(data.getStringExtra("N_LOCANAME"));
                    ASSETNUM = data.getStringExtra("ASSETNUM");
                }
                break;
            case ImagePicker.RESULT_CODE_BACK:
                //预览图片返回
                if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                    ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);

                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
                break;
            case RESULT_OK://如果是裁剪，因为裁剪指定了存储的Uri，所以返回的data一定为null
                if (requestCode == imagePicker.REQUEST_CODE_TAKE) {
                    //            //发送广播通知图片增加了
                    ImageItem imageItem = new ImageItem();
                    imageItem.path = imagePicker.getTakeImageFile().getAbsolutePath();
                    imagePicker.clearSelectedImages();
                    imagePicker.addSelectedImageItem(0, imageItem, true);
//
                    ArrayList<ImageItem> images = (ArrayList<ImageItem>) imagePicker.getSelectedImages();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
        }
        if (data != null) {
            switch (requestCode) {
                case 1002:
                    fxr.setText(data.getStringExtra("displayname"));
                    break;
                case 1003:
                    zrr.setText(data.getStringExtra("displayname"));
                    zrrId = data.getStringExtra("personid");
                    break;
            }
        }
    }

    /**
     * 提交图片
     **/
    private void startAsyncTask(String fileName, final String ownid, final int i) {
        Log.i(TAG, "fileName=" + fileName + ",ownid=" + ownid + ",i=" + i);
        File f = new File(fileName);

        ImageCompressUtils.from(WorkorderAddNewActivity.this)
                .load(fileName)
                .execute(new ImageCompressUtils.OnCompressListener() {
                    @Override
                    public String onSuccess(File file) {

                        try {
                            FileInputStream fis = new FileInputStream(file);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            byte[] buffer = new byte[1024];
                            int count = 0;
                            while ((count = fis.read(buffer)) >= 0) {
                                baos.write(buffer, 0, count);
                            }
                            String uploadBuffer = new String(Base64.encode(baos.toByteArray()));  //进行Base64编码
                            fis.close();


                            String name = getFileName(file.getPath());

                            final String finalUpdataInfo = uploadBuffer;
                            final String finalname = name;
                            new AsyncTask<String, String, String>() {
                                @Override
                                protected String doInBackground(String... strings) {
                                    String reviseresult = AndroidClientService.connectWebService(WorkorderAddNewActivity.this,
                                            finalname, finalUpdataInfo, Constants.WORKORDER_NAME, ownid);
                                    return reviseresult;
                                }

                                @Override
                                protected void onPostExecute(String workResult) {
                                    super.onPostExecute(workResult);
                                    Log.i(TAG, "workResult=" + workResult);
                                    if (i == adapter.getItemCount()) {
                                        MessageUtils.showMiddleToast(WorkorderAddNewActivity.this, "图片上传成功");
                                        mLoadingDialog.dismiss();
                                        finish();
                                    }

                                }
                            }.execute();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return null;
                    }

                    ;

                    @Override
                    public void onError(Throwable e) {
                        MessageUtils.showMiddleToast(WorkorderAddNewActivity.this, "图片上传失败");
                    }
                });

    }

    /**
     * 获取文件名称*
     */
    private String getFileName(String fileName) {
        File file = new File(fileName);
        String name = null;
        if (file.exists()) {
            name = file.getName();
        }
        return name;
    }

    /**
     * 初始化imagePicker
     **/
    private void initImagePicker() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

}
