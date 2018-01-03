package xu.test.moduledemo.sqlitedb.tools;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Created by 12852 on 2017/12/18.
 */

public class Util {

    public static void saveLogFile(String s){
        File parent_path = Environment.getExternalStorageDirectory();
        File f = new File(parent_path.getAbsoluteFile(),"testFileLog.txt");

        try {
            FileWriter fileWriter = new FileWriter(f,true);
            for(int i = 0; i < s.length();i++) {
                fileWriter.write(s.charAt(i));
            }
            fileWriter.write("\r\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
