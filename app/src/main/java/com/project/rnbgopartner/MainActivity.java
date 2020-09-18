package com.project.rnbgopartner;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{


    private CardView mcardnewregister;
    private CardView mcardlogout;
    private CardView mcardlistedrooms;

    private TextView tv_username;
    Handler mainHandler;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("RNBGO PARTNER");

        mcardnewregister=(CardView)findViewById(R.id.card_register_room);
        mcardlistedrooms=(CardView)findViewById(R.id.card_view_registred);
        mcardlogout=(CardView)findViewById(R.id.card_logout);
        tv_username=(TextView)findViewById(R.id.username);
        mainHandler = new Handler(Looper.getMainLooper());
        get_username();

        check_permissions();













        mcardnewregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RoomRegisterActivity.class));
            }
        });
        mcardlistedrooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ListedRoomsActivity.class));
            }
        });

        mcardlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));

            }
        });


        // Binding MainActivity.java with
        // activity_main.xml file


        // Find the WebView by its unique ID






    }

    private void check_permissions() {


        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };


        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void get_username() {

        final String mphonenumber;


        mphonenumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        Call<PartnerInfo> username_call=RetrofitClient.getInstance().getApi().get_username(mphonenumber);
        username_call.enqueue(new Callback<PartnerInfo>() {
            @Override
            public void onResponse(Call<PartnerInfo> call, final Response<PartnerInfo> response) {


                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            tv_username.setText(response.body().getPartnerName());

                        }
                    });














            }

            @Override
            public void onFailure(Call<PartnerInfo> call, Throwable t) {

            }
        });






    }




}
