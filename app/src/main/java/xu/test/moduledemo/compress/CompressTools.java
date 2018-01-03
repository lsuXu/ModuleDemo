package xu.test.moduledemo.compress;

import android.os.Environment;
import android.text.TextUtils;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by 12852 on 2017/12/26.
 */

public class CompressTools {
    public static String doTarGZ(File[] files, String destPath)
            throws IOException {
        /*
         * 定义一个TarArchiveOutputStream 对象
         */
        File tarFile = new File(destPath);
        FileOutputStream fos = new FileOutputStream(tarFile);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        TarArchiveOutputStream taos = new TarArchiveOutputStream(bos);
        byte[] buf = new byte[1024];
        for (File child : files) {
            if (child.isFile()) { // 文件
                FileInputStream fileInputStream = new FileInputStream(child);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(child.getName());
                tarArchiveEntry.setSize(child.length());
                taos.putArchiveEntry(tarArchiveEntry);
                int len;
                while ((len = bufferedInputStream.read(buf)) > 0) {
                    taos.write(buf, 0, len);
                }
                bufferedInputStream.close();
                taos.closeArchiveEntry();
                taos.flush();

                continue;
            }
        }
        //建立压缩文件输出流
        FileOutputStream gzFile = new FileOutputStream(destPath + ".gz");
        //建立gzip压缩输出流
        GZIPOutputStream gzout = new GZIPOutputStream(gzFile);
        //打开需压缩文件作为文件输入流
        File file = new File(destPath);
        FileInputStream tarin = new FileInputStream(file);
        int len;
        while ((len = tarin.read(buf)) != -1) {
            gzout.write(buf, 0, len);
        }
        gzout.close();
        gzFile.close();
        tarin.close();
        //删除tar包保留tar.gz
        //file.delete();
        return destPath+ ".gz";
    }

    public static String getFilePath(String directory, String fileName) {
            File root = new File(Environment.getExternalStorageDirectory(), "VSTEST");
            if (!root.exists() || !root.isDirectory()) {
                root.mkdir();
            }
            if (TextUtils.isEmpty(fileName)) {
                File dir = new File(root, directory);
                if (!dir.exists() || !dir.isDirectory()) {
                    dir.mkdir();
                }
                return dir.getAbsolutePath();
            } else {
                if (TextUtils.equals("VSTEST", directory)) {
                    return String.format("%s%s%s", root.getAbsolutePath(), File.separator, fileName);
                }
                File dir = new File(root, directory);
                if (!dir.exists() || !dir.isDirectory()) {
                    dir.mkdir();
                }
                return String.format("%s%s%s", dir.getAbsolutePath(), File.separator, fileName);
            }
    }
}
