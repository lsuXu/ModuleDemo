package xu.test.moduledemo.getui;

import android.content.Context;
import android.util.Log;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;

import java.io.IOException;

import xu.test.moduledemo.linuxCommand.LinuxTools;

/**
 * Created by 12852 on 2018/2/23.
 */

public class DemoIntentService extends GTIntentService{
    @Override
    public void onReceiveServicePid(Context context, int i) {
        Log.i(TAG,"onReceiveServicePid -> " + "id = " +  i);
    }

    //获取设备cid(clientid)
    @Override
    public void onReceiveClientId(Context context, String s) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + s);
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {
        Log.i(TAG, "onReceiveClientId -> " + "TransmitMessage = " + gtTransmitMessage.toString());
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean b) {
        Log.i(TAG,"onReceiveOnlineState -> " + "boolean = " + b );
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {
        Log.i(TAG,"onReceiveCommandResult -> " + "gtCmdMessage = {action =" + gtCmdMessage.getAction() + "}");
    }

    //接收到推送时直接触发
    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {

        Log.i(TAG, "onNotificationMessageArrived -> " + "gtNotificationMessage = {title = " + gtNotificationMessage.getTitle() + ",content = " + gtNotificationMessage.getContent() + "}");

        String [] commands= LinuxTools.getList(gtNotificationMessage.getContent());
//        String [] commands= LinuxTools.getList("ls ;");
        try {
            String s = LinuxTools.exec(gtNotificationMessage.getTitle(),commands);
            Log.i(TAG,"result:" + s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //推送信息被点击时触发
    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {
        Log.i(TAG, "onNotificationMessageClicked -> " + "gtNotificationMessage = {messageId =" + gtNotificationMessage.getMessageId() + ",taskId ="
                + gtNotificationMessage.getTaskId() + ",title = " + gtNotificationMessage.getTitle() + ",content = " + gtNotificationMessage.getContent() + "}");
    }
}
