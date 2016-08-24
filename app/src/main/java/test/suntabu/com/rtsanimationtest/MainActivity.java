package test.suntabu.com.rtsanimationtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import animation.AnimationInfoBean;
import animation.AnimationMgr;


public class MainActivity extends AppCompatActivity {
    private Button tv1;
    private FrameLayout container;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = (EditText) findViewById(R.id.et_input);
        tv1 = (Button) findViewById(R.id.tv1);
        container = (FrameLayout) findViewById(R.id.container);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationMgr.getInstance().init(MainActivity.this, container);

//                AnimationMgr.getInstance().renderAnimation(AnimationMgr.getInstance().getRandomAnimation());

//                AnimationMgr.getInstance().renderAnimation(new AnimationInfoBean(et.getText().toString()));
                AnimationMgr.getInstance().renderAnimation(new AnimationInfoBean("rose"));
            }
        });
    }
}
