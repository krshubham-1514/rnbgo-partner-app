package com.project.rnbgopartner;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DNSActivity extends AppCompatActivity {

    public EditText et_domain_name;
    public Button btn_ip_address;
    public TextView tv_ip_address;
    public Handler  mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dns);


        getSupportActionBar().setTitle("CN DNS APP");

        et_domain_name=(EditText)findViewById(R.id.domain_name);
        btn_ip_address=(Button)findViewById(R.id.btn_ip_address);
        tv_ip_address=(TextView)findViewById(R.id.ip_address);
        mainHandler=new Handler();
        btn_ip_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_ip_address();
            }
        });





    }

    private void get_ip_address() {

        String domain_name=et_domain_name.getText().toString();

        Log.i("domain_name",domain_name);

        Call<IpInfo> ipaddress_call=RetrofitClient.getInstance().getApi().get_ipaddress(domain_name);

        ipaddress_call.enqueue(new Callback<IpInfo>() {
            @Override
            public void onResponse(Call<IpInfo> call, final Response<IpInfo> response) {


                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_ip_address.setText(response.body().getIpAddress());

                    }
                });

            }

            @Override
            public void onFailure(Call<IpInfo> call, Throwable t) {

            }
        });
    }
}
