package com.project.rnbgopartner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {

    Button btn_registe_partner;
    EditText et_partner_name;
    EditText et_partner_mobile_number;
    EditText et_partner_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_partner_name=(EditText)findViewById(R.id.et_partner_name);
        et_partner_mobile_number=(EditText)findViewById(R.id.et_partner_mobile);
        et_partner_password=(EditText)findViewById(R.id.et_partner_password);
        btn_registe_partner=(Button)findViewById(R.id.btn_register_partner);

        getSupportActionBar().setTitle("Register");



        
        
        
        btn_registe_partner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_partner();
            }
        });







    }

    private void register_partner() {



        final String parnter_name,partner_mobile_number,partner_password;

        parnter_name=et_partner_name.getText().toString();
        partner_mobile_number="+91"+et_partner_mobile_number.getText().toString();
        partner_password=et_partner_password.getText().toString();

        //function to sigin using the phone number


        Call<ResponseBody> call=RetrofitClient.getInstance().getApi().create_user(parnter_name,partner_mobile_number,partner_password);



        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {



                try {
                    Log.i("check",response.body().string());

                    Intent intent = new Intent(RegisterActivity.this, VerifyPhoneActivity.class);
                    intent.putExtra("mobile", partner_mobile_number);
                    startActivity(intent);
                }
                catch (IOException e)
                {
                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }




            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });




    }








}





