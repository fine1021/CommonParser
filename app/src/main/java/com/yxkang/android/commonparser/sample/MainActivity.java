package com.yxkang.android.commonparser.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yxkang.android.commonparser.Converter;
import com.yxkang.android.commonparser.converter.XmlConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ExecutorService mExecutor = Executors.newCachedThreadPool();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(value = R.id.button)
    void xml() {
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                Converter converter = new XmlConverter();
                converter.setLogger(new XmlParserLogger());
                UserResponse userResponse = converter.convert(UserResponse.class, XmlConstant.DATA2);
                if (userResponse != null) {
                    Log.i(TAG, "xml: " + userResponse.toString());
                }

                ListSize listSize = converter.convert(ListSize.class, XmlConstant.DATA3);
                Log.i(TAG, "xml: " + listSize.toString());
            }
        });
    }
}
