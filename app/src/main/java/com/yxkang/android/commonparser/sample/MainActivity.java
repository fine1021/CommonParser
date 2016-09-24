package com.yxkang.android.commonparser.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yxkang.android.xmldom4j.converter.Dom4jConverter;
import com.yxkang.android.xmlparser.Converter;
import com.yxkang.android.xmlparser.Serializer;
import com.yxkang.android.xmlparser.serializer.XmlCommonSerializer;

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
                /*Serializer converter = new XmlConverter();
                converter.setLogger(new XmlParserLogger());
                UserResponse userResponse = converter.convert(UserResponse.class, XmlConstant.DATA2);
                if (userResponse != null) {
                    Log.i(TAG, "xml: " + userResponse.toString());
                }

                ListSize listSize = converter.convert(ListSize.class, XmlConstant.DATA3);
                Log.i(TAG, "xml: " + listSize.toString());*/
                Converter converter = new Dom4jConverter();
                converter.setLogger(new XmlParserTracer());
                UserResponse userResponse = converter.convert(UserResponse.class, XmlConstant.DATA2);
                if (userResponse != null) {
                    Log.i(TAG, "xml: " + userResponse.toString());
                }

                ListSize listSize = converter.convert(ListSize.class, XmlConstant.DATA3);
                Log.i(TAG, "xml: " + listSize.toString());

                Serializer serializer = new XmlCommonSerializer();
                serializer.setLogger(new XmlParserTracer());
                serializer.setCRLF("\n");
                serializer.setStandalone(null);

                if (userResponse != null) {
                    Log.i(TAG, serializer.toXmlString(userResponse));
                }
            }
        });
    }
}
