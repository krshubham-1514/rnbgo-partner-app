package com.project.rnbgopartner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RoomRegisterActivity extends AppCompatActivity {

    public EditText et_room_onwer_name;
    public EditText et_room_onwer_contact_number;
    public Button btn_room_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_register);

        et_room_onwer_name=(EditText)findViewById(R.id.room_owner_name);
        et_room_onwer_contact_number=(EditText)findViewById(R.id.room_owner_contact_number);
        btn_room_register=(Button)findViewById(R.id.btn_register_room);

        btn_room_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_room();
            }
        });
    }

    private void register_room() {

        String room_owner_name,room_owner_contact_number;

        room_owner_name=et_room_onwer_name.getText().toString();
        room_owner_contact_number=et_room_onwer_contact_number.getText().toString();



        Call<ResponseBody> call=RetrofitClient.getInstance().getApi().register_room(room_owner_name,room_owner_contact_number);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {



                try {
                    Log.i("register_room",response.body().string());
                    startActivity(new Intent(RoomRegisterActivity.this,RegisterSuccessActivity.class));
                    finish();

                }
                catch (IOException e)
                {
                    Toast.makeText(RoomRegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }




            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(RoomRegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

}
