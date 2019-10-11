package com.project.rnbgopartner;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class RegisterSuccessActivity extends AppCompatActivity {


    ImageView mImgCheck;
    Button btn_register_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);

        btn_register_next=(Button)findViewById(R.id.btn_register_next);
        mImgCheck = (ImageView) findViewById(R.id.imageView);
        ((Animatable) mImgCheck.getDrawable()).start();

        btn_register_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterSuccessActivity.this,RoomRegisterActivity.class));
                finish();
            }
        });
    }
}
