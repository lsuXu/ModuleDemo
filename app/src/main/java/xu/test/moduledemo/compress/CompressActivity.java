package xu.test.moduledemo.compress;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xu.test.moduledemo.R;

/**
 * Created by 12852 on 2017/12/26.
 */

public class CompressActivity extends AppCompatActivity {

    Button compressBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compress_activity);
        ButterKnife.bind(this);
        compressBtn = (Button) findViewById(R.id.compressBtn);
        compressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filePath = CompressTools.getFilePath("pictures","20171226");
                File file = new File(filePath);
                File [] files = null;
                if(file.isDirectory()){
                    files = file.listFiles();
                }
                try {
                    CompressTools.doTarGZ(files,CompressTools.getFilePath("pictures","compress.zip"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
