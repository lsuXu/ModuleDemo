package xu.test.moduledemo.opencvFace;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.BatteryManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Log;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by sg on 2017/6/1.
 */

public final class CommonUtil {

    /**
     * 判断集合是否为空
     *
     * @param collection
     * @return
     */
    public static boolean collectionNotEmpty(Collection collection) {
        if (collection != null && !collection.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 获取文件的字节数组
     *
     * @param file
     * @return
     */
    public static byte[] getBytes(File file) {
        byte[] data = null;
        try {
            InputStream in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getApplicationContext().getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = context.getApplicationContext().getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    /**
     * 屏幕密度（像素比例 0.75 / 1.0 / 1.5 / 2.0 / 2.5 / 3.0）
     *
     * @param context
     * @return
     */
    public static float getScreenDensity(Context context) {
        DisplayMetrics displayMetrics = context.getApplicationContext().getResources().getDisplayMetrics();
        return displayMetrics.density;
    }

    /**
     * 将数据写入文件中
     * @param fileName 文件名
     * @return
     */
    public static String saveDataToFile(String fileName,String errorLog){
        //获取Log目录下的具体日期文件夹下的文件路径
        String filePath = CommonUtil.getFilePath(CommonUtil.getDateDirectory(Constant.LOG),fileName);

        File file = new File(filePath);

            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(file,true);
                //写入数据到文件
                fileWriter.write(errorLog);
                fileWriter.write("\r\n");
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        Log.i("matService","写入文件结束");
        return fileName;
    }

    /**
     * // 屏幕密度DPI（每寸像素 120 / 160 / 240 / 320 / 480 /640）
     *
     * @param context
     * @return
     */
    public static int getScreenDensityDpi(Context context) {
        DisplayMetrics displayMetrics = context.getApplicationContext().getResources().getDisplayMetrics();
        return displayMetrics.densityDpi;
    }

    public static String getDeniedWords(int requestCode) {
        String value = null;
        switch (requestCode) {
            case Constant.REQUEST_PERMISSION_BLUETOOTH:
                break;
            case Constant.REQUEST_PERMISSION_CAMERA:
                value = "请打开相机权限";
                break;
            case Constant.REQUEST_PERMISSION_READ_PHONE:
                value = "请打开读取手机状态权限";
                break;
            case Constant.REQUEST_PERMISSION_GPS:
                value = "请打开读定位权限";
                break;
            case Constant.REQUEST_PERMISSION_READ_STORAGE:
            case Constant.REQUEST_PERMISSION_WRITE_STORAGE:
                value = "请打开读写sd卡权限";
                break;
            default:
                value = "请打开应用所需权限";
                break;
        }
        return value;
    }


    /**
     * 字符串转换为16进制字符串
     *
     * @param s
     * @return
     */
    public static String stringToHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    public static String padLeft(String s, int length) {
        byte[] bs = new byte[length];
        byte[] ss = s.getBytes();
        Arrays.fill(bs, (byte) (48 & 0xff));
        System.arraycopy(ss, 0, bs, length - ss.length, ss.length);
        return new String(bs);
    }

    /**
     * byte数组转换为十六进制字符串
     *
     * @param b
     * @return
     */
    public static String bytesToHexString(byte[] b) {
        if (b.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < b.length; i++) {
            int value = b[i] & 0xFF;
            String hv = Integer.toHexString(value);
            if (hv.length() < 2) {
                sb.append(0);
            }

            sb.append(hv);
        }
        return sb.toString();
    }

    /**
     * 十六进制数转成普通字符串
     *
     * @param s
     * @return
     */
    public static String hexToString(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
                        i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }


    /**
     * 判断sd卡是否存在
     *
     * @return
     */
    public static boolean sdCardExit() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    /**
     * 返回某个目录下某个文件的绝对路径
     *
     * @param directory
     * @param fileName
     * @return
     */
    public static String getFilePath(String directory, String fileName) {
        if (sdCardExit()) {
            File root = new File(Environment.getExternalStorageDirectory(), Constant.TAG);
            if (!root.exists() || !root.isDirectory()) {
                root.mkdirs();
            }
            if (TextUtils.isEmpty(fileName)) {
                File dir = new File(root, directory);
                if (!dir.exists() || !dir.isDirectory()) {
                    dir.mkdirs();
                }
                return dir.getAbsolutePath();
            } else {
                if (TextUtils.equals(Constant.TAG, directory)) {
                    return String.format("%s%s%s", root.getAbsolutePath(), File.separator, fileName);
                }
                File dir = new File(root, directory);
                if (!dir.exists() || !dir.isDirectory()) {
                    dir.mkdirs();
                }
                return String.format("%s%s%s", dir.getAbsolutePath(), File.separator, fileName);
            }
        }
        return null;
    }
    /**
     * 写文件到本地目录
     * @param dir 文件相对路径，相对于VS目录
     * @param filename 文件名
     * @param content   需要写入的内容
     * @param isAdd 文件存在，是否允许追加写入
     */
    public static void writeString2SDCard(String dir, String filename, String content, boolean isAdd) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            PrintWriter printWriter = null;
            try {
                File file = new File(getFilePath(dir, filename));
                printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, isAdd), "UTF-8"));
                printWriter.write(content);
                printWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (printWriter != null) {
                    printWriter.close();
                }
            }
        } else {
        }
    }

    /**
     * 文件重命名
     *
     * @param filePath
     * @param newFilePath
     * @return
     */
    public static boolean renameFileName(String filePath, String newFilePath) {
        File oldFile = new File(filePath);
        File newFile = new File(newFilePath);
        if (!oldFile.exists()) {
            return false;//重命名文件不存在
        }
        if (newFile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
            newFile.delete();
        oldFile.renameTo(newFile);
        return true;
    }

    /**
     * 删除文件
     *
     * @param filePath
     */
    public static void deleteFiles(String filePath) {
        File file = new File(filePath);
        if (file.isDirectory()) {
            for (File file1 : file.listFiles()) {
                deleteFiles(file1.getAbsolutePath());
            }
        } else {
            file.delete();
        }
    }

    /**
     * 删除昨天之前的文件
     *
     * @param filePath
     */
    public static void deleteOldFiles(String filePath) {
        File file = new File(filePath);
        String format = "yyyyMMdd";
        SimpleDateFormat formatPattern = new SimpleDateFormat(format);
        Date now = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(now);
        calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
        Date yesterday = calendar.getTime(); //这个时间就是日期往后推一天的结果
        String today =  formatPattern.format(now);
        String yesterdayStr = formatPattern.format(yesterday);
        if (file.exists() && !file.getName().contains(today) && !file.getName().contains(yesterdayStr)) {
            if (file.isDirectory() && file.listFiles() != null) {
                for (File file1 : file.listFiles()) {
                    deleteOldFiles(file1.getAbsolutePath());
                }
                file.delete();
            } else {
                file.delete();
            }
        }
    }


    /**
     * 获取NavigationBar的高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getApplicationContext().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }


    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
    public static boolean isCharging(Context context) {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent intent = context.getApplicationContext().registerReceiver(null, ifilter);
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        //判断是否是充电状态
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        return isCharging;
    }
    /**
     * 根据当前日期得到文件夹路径
     *
     * @return String
     */
    public static String getDateDirectory(String directory){
        String format = "yyyyMMdd";
        SimpleDateFormat formatPattern = new SimpleDateFormat(format);
        return directory + "/"+ formatPattern.format(new Date());
    }

    /**
     * 方法描述：判断某一Service是否正在运行
     *
     * @param context     上下文
     * @param serviceName Service的全路径： 包名 + service的类名
     * @return true 表示正在运行，false 表示没有运行
     */
    public static boolean isServiceRunning(Context context, String serviceName) {
        ActivityManager am = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = am.getRunningServices(200);
        if (runningServiceInfos.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningServiceInfo serviceInfo : runningServiceInfos) {
            if (serviceInfo.service.getClassName().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }

    public static void powerAction(Context mContext,ComponentName componentName) {
        Intent intent = new Intent();
        intent.setComponent(componentName);
        mContext.getApplicationContext().startService(intent);
    }


}
