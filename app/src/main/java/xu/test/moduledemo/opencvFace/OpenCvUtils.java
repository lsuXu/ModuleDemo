package xu.test.moduledemo.opencvFace;

import android.content.Context;
import android.util.Log;

import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import xu.test.moduledemo.R;

/**
 * Created by 12852 on 2017/12/15.
 */

public class OpenCvUtils {

    private static String TAG = "OpenCvUtils";
    private static OpenCvUtils openCvUtils;

    //获取openCvUtils实例
    public static OpenCvUtils getInstance(Context context){
        if(openCvUtils == null)
            openCvUtils = new OpenCvUtils(context.getApplicationContext());
        return openCvUtils;
    }

    private OpenCvUtils(Context context){
    }


    /**
     * orb 计算特征值，返回两张图片是否相似
     * 传入两张灰度图
     * 根据匹配出来的列表中，先筛选，再取平均值
     */
    public boolean getCompareResultByORB3(Mat queryImg, Mat trainImg){
        try{
            if(queryImg != null && trainImg != null && !queryImg.empty() && !trainImg.empty()) {

                MatOfKeyPoint queryImgOfKeyPoints = new MatOfKeyPoint();
                MatOfKeyPoint trainImOfKeyPoints = new MatOfKeyPoint();
                Mat queryImgDescriptors = new Mat();
                Mat trainImgDescriptors = new Mat();

                MatOfDMatch matches = new MatOfDMatch();

                Imgproc.equalizeHist(queryImg, queryImg);
                Imgproc.equalizeHist(trainImg, trainImg);

                FeatureDetector detector_train = FeatureDetector.create(FeatureDetector.ORB);
                DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.ORB);

                detector_train.detect(queryImg, queryImgOfKeyPoints);
                extractor.compute(queryImg, queryImgOfKeyPoints, queryImgDescriptors);
                detector_train.detect(trainImg, trainImOfKeyPoints);
                extractor.compute(trainImg, trainImOfKeyPoints, trainImgDescriptors);

                DescriptorMatcher descriptormatcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
                descriptormatcher.match(queryImgDescriptors, trainImgDescriptors, matches);
                List<DMatch> matchsList = matches.toList();

                if (matchsList.size() > 0) {
                    Collections.sort(matchsList, new Comparator<DMatch>() {

                        @Override
                        public int compare(DMatch o1, DMatch o2) {
                            float i = o1.distance - o2.distance;
                            return (int) i;
                        }
                    });
                    int min_num = 65;  // 要筛选的，指定的经验值
                    float max_value = 52.245f;  // 最大的平均值
                    int count = 0;
                    int num = 0;
                    // 先筛选
                    for(DMatch tmp : matchsList){
                        if(tmp.distance <= 2*matchsList.get(0).distance || tmp.distance <= min_num ){
                            count++;
                            num += tmp.distance;
                        }
                    }
                    Log.i("mat", "getCompareResultByORB3：" + num*1.0/count + "");
                    if(num*1.0/count <= max_value){
                        // 识别为同一个人
                        return true;
                    }

                }

            }
        } catch (Exception e){
            Log.i("mat", "getCompareResultByORB3：" + e.toString() + "");
            try{
                String tmpImgPath = CommonUtil.getFilePath(Constant.PHOTO, Constant.ORIGINAL);
                if (tmpImgPath != null){
                    //删除本地 tmp 图片
                    File file = new File(tmpImgPath);
                    if(file.exists()){
                        file.delete();
                    }
                }
            } catch (Exception e2){
//                System.out.println(e2.toString());
                Log.i("mat", "getCompareResultByORB3：" + e2.toString() + "");
            }
        }
        // 其他情况当做不是同一个人处理
        return false;
    }

}
