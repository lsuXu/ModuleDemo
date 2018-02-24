package xu.test.moduledemo.opencvFace;

import android.util.ArrayMap;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by sg on 2017/6/1.
 */

public class Constant {

    public static final String TAG = "ModuleDemo";
    public static final String PHOTO = "photos";//拍照存储目录
    public static final String LOG = "log";//log目录

    public static final String ORIGINAL = "original.jpg";//对比的原图

    //---关于权限申请RequestCode
    public static final int REQUEST_PERMISSION_BLUETOOTH = 0x01;//蓝牙权限申请
    public static final int REQUEST_PERMISSION_READ_PHONE = 0x02;//读取手机状态
    public static final int REQUEST_PERMISSION_CAMERA = 0x03;//Camera
    public static final int REQUEST_PERMISSION_WRITE_STORAGE = 0x04;//写sd卡
    public static final int REQUEST_PERMISSION_GPS = 0x05;//gps定位
    public static final int REQUEST_PERMISSION_READ_STORAGE = 0x06;//读sd卡

}
