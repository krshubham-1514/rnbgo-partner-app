package com.project.rnbgopartner;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListedRoomsActivity extends AppCompatActivity {


    ArrayList<String> room_id,room_owner_name,room_owner_contact_number;
    ListView listview_listedrooms;
    Handler mainhandler;
    CustomAdapter customAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listed_rooms);

        room_id=new ArrayList<String>();
        room_owner_name=new ArrayList<String>();
        room_owner_contact_number=new ArrayList<String>();



        listview_listedrooms=(ListView)findViewById(R.id.listview_listedrooms);
        mainhandler = new Handler(Looper.getMainLooper());
        customAdapter=new CustomAdapter();








        getSupportActionBar().setTitle("Listed Rooms");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //function to get all the listed rooms
        get_listed_room_info();
    }


    class CustomAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return room_id.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=getLayoutInflater().inflate(R.layout.layout_listed_rooms,null);


            TextView tv_room_id=(TextView)convertView.findViewById(R.id.tv_room_id);
            TextView tv_room_owner_name=(TextView)convertView.findViewById(R.id.tv_room_owner_name);
            TextView tv_room_owner_contact_number=(TextView)convertView.findViewById(R.id.tv_room_owner_contact_number);


            tv_room_id.setText(room_id.get(position));
            tv_room_owner_name.setText(room_owner_name.get(position));
            tv_room_owner_contact_number.setText(room_owner_name.get(position));

            return convertView;


        }
    }
    private void get_listed_room_info() {

        String partner_uid="123";
        //partner_uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        Call<List<RoomInfo>> call_get_listed_rooms=RetrofitClient.getInstance().getApi().get_listed_rooms(partner_uid);
        call_get_listed_rooms.enqueue(new Callback<List<RoomInfo>>() {
            @Override
            public void onResponse(Call<List<RoomInfo>> call, Response<List<RoomInfo>> response) {



                Log.i("hello_world","asdfsadfsdf");




              if(response.isSuccessful())
              {








              }
              else
              {

                  Log.i("hello","null value is coming");

              }


                mainhandler.post(new Runnable() {
                    @Override
                    public void run() {
                       // listview_listedrooms.setAdapter(customAdapter);

                    }
                });
            }

            @Override
            public void onFailure(Call<List<RoomInfo>> call, Throwable t) {

            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
