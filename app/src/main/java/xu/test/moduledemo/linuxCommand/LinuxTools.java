package xu.test.moduledemo.linuxCommand;


import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by 12852 on 2018/2/23.
 */

public class LinuxTools {
    public static String [] getList(String commandLine){
        String [] commandSp = commandLine.split(" ");
        return commandSp;
    }

    public static String exec(String directoryPath,String [] commandSp) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(commandSp);
        File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + directoryPath);
        if(!directory.exists())
            return "Directory path is not exist:" + directory.getAbsolutePath();
        pb.directory(directory);

        Process process = pb.start();

        BufferedReader brE = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String result = "";
        String line ;
        while ((line = br.readLine())!= null){
//            Log.i("testLinuxCommand",line);
            result = result + "\n\r" + line;
        }
        result = result + "\n\r error:";

        String lineError ;
        while ((lineError = brE.readLine())!= null){
            //            Log.i("testLinuxCommand",line);
            result = result + "\n\r" + lineError;
        }
        return result;
    }
}
