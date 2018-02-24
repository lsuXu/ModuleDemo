package xu.test.moduledemo.opencvFace;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import xu.test.moduledemo.R;

/**
 * Created by 12852 on 2018/2/23.
 */

public class OpenCvFaceComp extends AppCompatActivity{

    @BindView(R.id.btn_face_comp)
    Button btn_face_comp;


    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.opencv_face_comp);
        ButterKnife.bind(this);
        if (!OpenCVLoader.initDebug()) {
            Log.d("mat", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, this, mLoaderCallback);
        } else {
            Log.d("mat", "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }

        btn_face_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //对比源图片
                final String sourceFileName = new File(Environment.getExternalStorageDirectory(), Constant.TAG).getAbsolutePath() +"/" + Constant.ORIGINAL;
                final Mat sourceMat = Imgcodecs.imread(sourceFileName, Imgcodecs.IMREAD_GRAYSCALE);
                //对比目标图片
                File folder = new File(CommonUtil.getFilePath(Constant.PHOTO,null));
                Log.i("mat","文件数量：" + folder.list().length);

                /*for(File file :folder.listFiles()){
                    Mat targerMat = Imgcodecs.imread(file.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);
                    boolean isSimilarity = OpenCvUtils.getInstance(OpenCvFaceComp.this).getCompareResultByORB3(sourceMat,targerMat);
                    Log.i("mat","相似：" + isSimilarity + ";sourceMat = " + sourceFileName );
                }*/
                /*Observable.from(folder.listFiles())
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .map(new Func1<File, Mat>() {

                            @Override
                            public Mat call(File file) {
                                return Imgcodecs.imread(file.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);
                            }
                        })
                        .subscribe(new Action1<Mat>() {
                            @Override
                            public void call(Mat targerMat) {
                                boolean isSimilarity = OpenCvUtils.getInstance(OpenCvFaceComp.this.getApplicationContext()).getCompareResultByORB3(sourceMat,targerMat);
                                Log.i("mat","相似：" + isSimilarity + ";sourceMat = " + sourceFileName );
                            }
                        });*/
                Observable.from(folder.listFiles())
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe(new Action1<File>() {
                            @Override
                            public void call(File file) {
                                Mat targetMat =  Imgcodecs.imread(file.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);
                                boolean isSimilarity = OpenCvUtils.getInstance(OpenCvFaceComp.this.getApplicationContext()).getCompareResultByORB3(sourceMat,targetMat);
                                if(isSimilarity)
                                    Log.i("mat","相似：" + isSimilarity + ";sourceMat = " + sourceFileName  + ";    targetMat :" + file.getName());
                                else
                                    Log.e("mat","相似：" + isSimilarity + ";sourceMat = " + sourceFileName  + ";    targetMat :" + file.getName());
                            }
                        });

            }
        });
    }

}
