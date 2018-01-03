package xu.test.moduledemo.faceRecognition;

import android.Manifest;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import xu.test.moduledemo.R;

/**
 * Created by 12852 on 2018/1/2.
 */

public class FaceRecognitionActivity extends AppCompatActivity{

    @BindView(R.id.openCamera)
    Button openCamera;

    Camera camera ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.face_activity);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        camera = Camera.open();
        camera.setOneShotPreviewCallback(new CameraUtils());
    }
}
