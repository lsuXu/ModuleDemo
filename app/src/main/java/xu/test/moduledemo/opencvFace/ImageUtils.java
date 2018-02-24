package xu.test.moduledemo.opencvFace;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by sg on 2017/5/20.
 */

public final class ImageUtils {

    /**
     * 特征对比
     *
     * @return 相似度
     */
    public static double comparePic(Mat mat) {
        try {
            Mat hist0 = new Mat();
            Mat hist1 = new Mat();

            MatOfFloat ranges = new MatOfFloat(0f, 256f);
            MatOfInt histSize = new MatOfInt(25);

            //读入本地保存用于对比的灰度图片
            Mat local_img = Imgcodecs.imread(CommonUtil.getFilePath(Constant.PHOTO, Constant.ORIGINAL), Imgcodecs.IMREAD_GRAYSCALE);

            //直方图均衡化
//            Imgproc.equalizeHist(mat,mat);

            Imgproc.calcHist(Arrays.asList(local_img), new MatOfInt(0), new Mat(),
                    hist0, histSize, ranges);
            Imgproc.calcHist(Arrays.asList(mat), new MatOfInt(0), new Mat(),
                    hist1, histSize, ranges);


            double target = Imgproc.compareHist(hist0, hist1,
//                    Imgproc.CV_COMP_CORREL);//越大越相似
                    Imgproc.CV_COMP_BHATTACHARYYA);//越小越相似
            return target;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    /**
     * orb 计算特征值，返回两张图片是否相似
     * 传入两张灰度图
     * 根据匹配出来的列表中，先筛选，再取平均值
     */
    public static boolean getCompareResultByORB3(Mat queryImg, Mat trainImg){
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
                    if(num*1.0/count <= max_value){
                        // 识别为同一个人
                        return true;
                    }

                }

            }
        } catch (Exception e){
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
            }
        }
        // 其他情况当做不是同一个人处理
        return false;
    }
}