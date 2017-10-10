package com.zcl.hxqh.liangqingmanagement.constants;

/**
 * Created by apple on 15/10/8.
 * 静态常量类
 */
public class Constants {

    /**
     * 基础接口*
     */

    /**
     * 默认 外网*
     */
    public static final String HTTP_API_IP = "http://218.25.141.113:9080";
    //本地
//    public static final String HTTP_API_IP = "http://192.168.101.164:7001";
    /**
     * 内网*
     */
    public static final String HTTPZHENGSHI_API_IP = "http://10.1.88.101:9080";

    //登陆URL
    public static final String SIGN_IN_URL = "/maximo/mobile/system/login";

    //粮情检查单webservice上传接口
    public static final String lqwebserviceURL = "/meaweb/services/N_GRAINS";

    //扦样单webservice上传接口
    public static final String qywebserviceURL = "/meaweb/services/N_SAMPLE";

    //货运预报webservice上传接口
    public static final String carwebserviceURL = "/meaweb/services/N_CAR";

    //扦样单webservice获取车辆信息接口
    public static final String qywebservice1URL = "/meaweb/services/N_SAMPLE1";

    //缺陷工单,快速上报webservice新增接口
    public static final String WorkOrderwebserviceURL = "/meaweb/services/MOBILEROUTES";

    //登录webservice新增接口
    public static final String LoginwebserviceURL = "/meaweb/services/MOBILELOGIN_";

    //移动设备查询
    public static final String MobileassteURL = "/meaweb/services/MOBILEASSTE";

    //工具设备
    public static final String MOBILETOOLSURL = "/meaweb/services/MOBILETOOLS";

    //通用接口查询
    public static final String BASE_URL = "/maximo/mobile/common/api";


    //生成物资编码接口
    public static final String ITEM_GENERATE_URL = "/maximo/mobile/itemreq/createCode";
    //发送工作流接口

    public static final String START_FLOW_URL = "/maximo/mobile/wf/start";
    //审批工作流接口
    public static final String APPROVAL_FLOW_URL = "/maximo/mobile/wf/approve";


    /**
     * 图片*
     */
    public static final String WORK_FLOW_URL = "/meaweb/services/WFSERVICE";

    /**
     * ------------------数据库表名配置－－开始*
     */

    //收件箱appid
    public static final String WFASSIGNMENT_APPID = "INBOX";

    //收件箱的表名
    public static final String WFASSIGNMENT_NAME = "WFASSIGNMENT";

    //仓储粮情标准单的appid
    public static final String N_GRAINJC_APPID = "N_GRAINJC";

    //仓储粮情标准单的表名
    public static final String N_GRAINJC_NAME = "N_GRAINJC";


    //扦样单的appid
    public static final String N_SAMPLE_APPID = "N_SAMPLE";

    //扦样单的表名
    public static final String N_SAMPLE_NAME = "N_SAMPLE";

    //送检编号的appid
    public static final String N_QCLSAMP_APPID = "N_SAMPLE";
    //送检编号的表名
    public static final String N_QCLSAMP_NAME = "N_QCLSAMP";

    //货运预报的appid
    public static final String N_CAR_APPID = "N_CAR";
    //货运预报的表名
    public static final String N_CAR_NAME = "N_CAR";

    public static final String N_CARLINE_NAME = "N_CARLINE";
    //仓储作业计划的表名
    public static final String N_TASKPLAN = "N_TASKPLAN";

    //扦样单的appid
    public static final String ALNDOMAIN_APPID = "ALNDOMAIN";

    //扦样单的表名
    public static final String ALNDOMAIN_NAME = "ALNDOMAIN";

    //任务编号的表名
    public static final String N_QCTASKLINE_NAME = "N_QCTASKLINE";
    //车辆作业的表名
    public static final String N_CARTASK_NAME = "N_CARTASK";
    //车皮跟踪的表名
    public static final String N_WAGONS_NAME = "N_WAGONS";

    //车辆作业的appid
    public static final String N_STOREINFO_APPID = "N_STOREINFO";
    //车皮跟踪的表名
    public static final String N_STOREINFO_NAME = "N_STOREINFO";

    //考勤管理的appid
    public static final String N_WTLINE_APPID = "N_WTLINE";
    //考勤管理的表名
    public static final String N_WTLINE_NAME = "N_WTLINE";

    //人员查询的appid
    public static final String PERSON_APPID = "PERSON";
    //人员查询的表名
    public static final String PERSON_NAME = "PERSON";
    //用工记录的appid
    public static final String WTLABORVIEW_APPID = "WTLABORVIEW";
    //用工记录的表名
    public static final String WTLABORVIEW_NAME = "WTLABORVIEW";
    //缺陷工单的appid
    public static final String WORKORDER_APPID = "WOCM";
    //缺陷工单的表名
    public static final String WORKORDER_NAME = "WORKORDER";
    //设备的appid
    public static final String ASSET_APPID = "ASSET";
    //设备的表名
    public static final String ASSET_NAME = "ASSET";

    //用工验收的appid
    public static final String N_LABAPYS_APPID = "N_LABAPYYS";
    //用工验收的表名
    public static final String N_LABAPYS_NAME = "N_LABAPY";
    //验收标准与评分
    public static final String N_LABAPYRULE_NAME = "N_LABAPYRULE";

    /**
     * 附件appid
     **/
    public static final String DOCLINKS_APPID = "DOCLINKS";
    /**
     * 附件表名
     **/
    public static final String DOCLINKS_NAME = "DOCLINKS";

    /**
     * 移动设备借用appid
     **/
    public static final String N_MOVEASSE_APPID = "N_MOVEASSE";
    /**
     * 移动设备归还appid
     **/
    public static final String N_MOVEASSE1_APPID = "N_MOVEASSE1";
    /**
     * 移动设备查询appid
     **/
    public static final String N_MOVEASSE2_APPID = "N_MOVEASSE2";
    /**
     * 工具设备借用appid
     **/
    public static final String N_MOVEASSE3_APPID = "N_MOVEASSE3";
    /**
     * 工具设备归还appid
     **/
    public static final String N_MOVEASSE4_APPID = "N_MOVEASSE4";
    /**
     * 工具设备查询appid
     **/
    public static final String N_MOVEASSE5_APPID = "N_MOVEASSE5";
    /**
     * 调车作业指令单appid
     **/
    public static final String N_INSTRUCS_APPID = "N_INSTRUCS";

    /**
     * 移动设备表名
     **/
    public static final String INVUSE_NAME = "INVUSE";
    /**
     * 实际领用
     **/
    public static final String INVUSELINE_NAME = "INVUSELINE";
    /**
     * 计划领用
     **/
    public static final String N_PRODUCTIONPLANS_NAME = "N_PRODUCTIONPLANS";

    public static final String N_TASKASSET_NAME = "N_TASKASSET";

    /**
     * 调车作业指令单表名
     **/
    public static final String N_INSTRUCSTASK_NAME = "N_INSTRUCSTASK";
    /**
     * 调车作业指令单行表名
     **/
    public static final String N_INSTRUCTIONS_NAME = "N_INSTRUCTIONS";

    /**
     * 风雨雪三查-appid
     **/
    public static final String N_GRAINJC_APP = "N_GRAINJC";
    /**
     * 风雨雪三查表名
     **/
    public static final String N_ROSTC_NAME = "N_ROSTC";
    /**
     * 出入仓告知记录表名
     **/
    public static final String N_FOODJOB_NAME = "N_FOODJOB";


    /**
     * 用户登录表识--开始*
     */
    public static final String LOGINSUCCESS = "USER-S-101"; //登录成功

    public static final String CHANGEIMEI = "USER-S-104"; //登录成功,检测到用户更换手机登录

    public static final String USERNAMEERROR = "USER-E-100";//用户名密码错误

    public static final String GETDATASUCCESS = "GLOBAL-S-0";//获取数据成功


    /**
     * 入库管理的发放与接收*
     */
    public static final String RECEIPT = "RECEIPT";//接收
    public static final String RETURN = "RETURN";//退货


    /**
     * 选项值
     **/
    public static final String AREA = "AREA";//区域
    public static final String FRVIEW = "FRVIEW";//处理意见

    public static final String SMJBCL = "SMJBCL";//虫类
    public static final String CHLY = "CHLY";//害虫来源
    public static final String CHVIEW = "CHVIEW";//虫害处理意见
    public static final String CFXN = "CFXN";//仓房性能
    public static final String N_TYPES = "N_TYPES";//扦样类型
    public static final String WORKTYPE = "WORKTYPE1";//作业性质
    public static final String GRAINJCAREA = "GRAINJCAREA";//面积
    public static final String JLZJ = "JLZJ";//状况
    public static final String THINGS = "THINGS";//处理结果
    public static final String JLSTATUS = "JLSTATUS";//结露面积
    public static final String JLVIEW = "JLVIEW";//结露处理意见

    public static final String CFLY = "CFLY";//生霉位置
    public static final String SMCD = "SMCD";//生霉程度
    public static final String XZYN = "XZYN";//是否熏蒸
    public static final String MEDTYPE = "MEDTYPE";//施药方法
    public static final String CLOSETYPE = "CLOSETYPE";//密闭方法
    public static final String MOUSETYPE = "MOUSETYPE";//鼠害种类
    public static final String MOUSEAREA = "MOUSEAREA";//鼠害区域
    public static final String SHJYFZ = "SHJYFZ";//鼠害处理意见
    public static final String SHWLFZ = "SHWLFZ";//治理措施
    public static final String CROPSTYPE = "CROPSTYPE";//粮食品种
    public static final String CONTENT = "CONTENT";//作业内容
    public static final String GRAINSWS = "GRAINSWS";//一般，良好，较差
    public static final String MHQVIEW = "MHQVIEW";//灭火器问题描述
    public static final String THINGSS = "THINGSS";//灭火器处理结果
    public static final String XFSVIEW = "XFSVIEW";//消防栓问题描述
    public static final String PDXVIEW = "PDXVIEW";//配电箱问题描述
    public static final String GBJVIEW = "GBJVIEW";//刮板机问题描述
    public static final String ZLVIEW = "DGFJVIEW";//轴流风机问题描述
    public static final String DTVIEW = "DTVIEW";//电梯问题描述
    public static final String LIGHTVIEW = "LIGHTVIEW";//照明问题描述
    public static final String CVIEW = "CVIEW";//窗户问题描述
    public static final String XCWSVIEW = "XCVIEW";//现场卫生问题描述
    public static final String CNWSVIEW = "XCVIEW";//仓内卫生问题描述
    public static final String TLWSVIEW = "TLWSVIEW";//通廊卫生问题描述
    public static final String DGWSVIEW = "DGWSVIEW";//地坑卫生问题描述
    public static final String CFLYVIEW = "CFLYVIEW";//仓房漏雨问题描述
    public static final String DGFJVIEW = "DGFJVIEW";//仓房漏雨问题描述
    public static final String LXFJVIEW = "LXFJVIEW";//离心风机问题描述

    /**风雨雪三查**/
    public static final String POSITIONING = "POSITIONING";//发生部位
    public static final String TAKEACTIONING = "TAKEACTIONING";//采取措施

}
