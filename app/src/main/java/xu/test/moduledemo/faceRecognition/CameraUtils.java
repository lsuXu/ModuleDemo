package xu.test.moduledemo.faceRecognition;

import android.hardware.Camera;
import android.util.Log;

/**
 * Created by 12852 on 2018/1/2.
 */

public class CameraUtils implements Camera.PreviewCallback{
    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Log.i("hhh","data: " + data.length);
    }
}
