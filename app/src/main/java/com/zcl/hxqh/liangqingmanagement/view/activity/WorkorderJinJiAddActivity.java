package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.Utils;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.adapter.ImageLoadAdapter;
import com.zcl.hxqh.liangqingmanagement.constants.Constants;
import com.zcl.hxqh.liangqingmanagement.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.liangqingmanagement.until.AccountUtils;
import com.zcl.hxqh.liangqingmanagement.until.CreateFile;
import com.zcl.hxqh.liangqingmanagement.until.ImageCompressUtils;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.view.widght.AbstractSpinerAdapter;
import com.zcl.hxqh.liangqingmanagement.view.widght.GlideImageLoader;
import com.zcl.hxqh.liangqingmanagement.view.widght.SpinerPopWindow;
import com.zcl.hxqh.liangqingmanagement.webserviceclient.AndroidClientService;

import org.json.JSONException;
import org.json.JSONObject;
import org.kobjects.base64.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * 紧急工单提交
 */
public class WorkorderJinJiAddActivity extends BaseActivity implements ImageLoadAdapter.OnRecyclerViewItemClickListener {
    private static String TAG = "WorkorderJinJiAddActivity";

    public static final int REQUEST_CODE_PREVIEW = 101;

    private ImageLoadAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 9;               //允许选择图片最大数

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
    private ImageButton submitBtn;


    //区域
    private TextView n_regionText;

    //缺陷描述
    private EditText descriptionText;
    //照相
    private ImageView cameraImageView;

    //下拉列表
    private SpinerPopWindow mSpinerPopWindow;


    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;

    protected FlippingLoadingDialog mLoadingDialog;

    private List<String> nameList = new ArrayList<String>();

    private File takeImageFile;

    private ImagePicker imagePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workorderjinji_add);
        geiIntentData();
        findViewById();
        initView();
        initWidget();

        initImagePicker();


        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();

    }

    private void geiIntentData() {
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        submitBtn = (ImageButton) findViewById(R.id.sbmittext_id);

        n_regionText = (TextView) findViewById(R.id.n_region_text);
        descriptionText = (EditText) findViewById(R.id.workorder_description);
        cameraImageView = (ImageView) findViewById(R.id.photo_image);


    }


    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.kssb_text);
        submitBtn.setVisibility(View.VISIBLE);
        submitBtn.setOnClickListener(submitBtnBtnOnClickListener);
        n_regionText.setOnClickListener(n_regionTextOnClickListener);

        cameraImageView.setOnClickListener(cameraImageViewOnClickListener);

        //设置数据源
        setSpiner();

    }

    private void setSpiner() {

        String[] names = getResources().getStringArray(R.array.area_titles);
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

            n_regionText.setText(value);
        }
    }


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    private View.OnClickListener cameraImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            imagePicker.takePicture(WorkorderJinJiAddActivity.this, ImagePicker.REQUEST_CODE_TAKE);
        }
    };




    private View.OnClickListener submitBtnBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            submitNormalDialog();
        }
    };


    //区域
    private View.OnClickListener n_regionTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            mSpinerPopWindow.setWidth(n_regionText.getWidth());
            mSpinerPopWindow.showAsDropDown(n_regionText);


        }
    };


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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);

                selImageList.clear();
                selImageList.addAll(images);
                adapter.setImages(selImageList);
            }
        }

        //如果是裁剪，因为裁剪指定了存储的Uri，所以返回的data一定为null
        if (resultCode == RESULT_OK && requestCode == imagePicker.REQUEST_CODE_TAKE) {
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


    /**
     * 提交数据*
     */
    private void submitNormalDialog() {

        final NormalDialog dialog = new NormalDialog(WorkorderJinJiAddActivity.this);
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
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String n_region = n_regionText.getText().toString();
                String description = descriptionText.getText().toString();
                String reviseresult = AndroidClientService.addKsWorkOrder(WorkorderJinJiAddActivity.this, n_region, description, AccountUtils.getloginUserName(WorkorderJinJiAddActivity.this));
                return reviseresult;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                showResults(s);


            }
        }.execute();


    }


    /**
     * 解析新增成功返回值
     **/
    private void showResults(String s) {
        try {
            JSONObject json = new JSONObject(s);
            String success = json.getString("success");
            String msg = json.getString("msg");
            if (success.equals("1") && !msg.equals("") && adapter.getImages().size() != 0) {
                mLoadingDialog.setText("正在上传图片...");
                for (ImageItem imageItem : adapter.getImages()) {
                    int i = 0;
                    i++;
                    startAsyncTask(imageItem.path, msg, i);
                }

            } else {
                mLoadingDialog.dismiss();
                MessageUtils.showMiddleToast(WorkorderJinJiAddActivity.this, msg);
            }
        } catch (JSONException e) {
            mLoadingDialog.dismiss();
            MessageUtils.showMiddleToast(WorkorderJinJiAddActivity.this, "新增失败");
            e.printStackTrace();
        }

    }


    /**
     * 提交图片
     **/
    private void startAsyncTask(String fileName, final String ownid, final int i) {
        Log.i(TAG, "fileName=" + fileName + ",ownid=" + ownid + ",i=" + i);
        File f = new File(fileName);

        ImageCompressUtils.from(WorkorderJinJiAddActivity.this)
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
                                    String reviseresult = AndroidClientService.connectWebService(WorkorderJinJiAddActivity.this,
                                            finalname, finalUpdataInfo, Constants.WORKORDER_NAME, ownid);
                                    return reviseresult;
                                }

                                @Override
                                protected void onPostExecute(String workResult) {
                                    super.onPostExecute(workResult);
                                    if (i == adapter.getItemCount()) {
                                        MessageUtils.showMiddleToast(WorkorderJinJiAddActivity.this, "图片上传成功");
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
                        MessageUtils.showMiddleToast(WorkorderJinJiAddActivity.this, "图片上传失败");
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
