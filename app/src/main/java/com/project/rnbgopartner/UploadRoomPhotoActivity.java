package com.project.rnbgopartner;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;




import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadRoomPhotoActivity extends AppCompatActivity {

    private static final int PIC_CROP =2 ;
    Button btn_upload_photos;
    ImageView upload_photo_one,upload_photo_two,upload_photo_three,upload_photo_four,upload_photo_five;
    private Uri tempUri,tempoutputUri,uri_image1,uri_image2,uri_image3,uri_image4,uri_image5;
    int upload_photo_id=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_room_photo);

        getSupportActionBar().setTitle("Upload Photos");




        upload_photo_one=(ImageView)findViewById(R.id.upload_photo_one);
        upload_photo_two=(ImageView)findViewById(R.id.upload_photo_two);
        upload_photo_three=(ImageView)findViewById(R.id.upload_photo_three);
        upload_photo_four=(ImageView)findViewById(R.id.upload_photo_four);
        upload_photo_five=(ImageView)findViewById(R.id.upload_photo_five);
        btn_upload_photos=(Button)findViewById(R.id.btn_room_photo_upload);


        btn_upload_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload_room_images(uri_image1,uri_image2,uri_image3,uri_image4,uri_image5);
            }
        });


        upload_photo_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upload_photo_id=1;
                select_image();
            }
        });

        upload_photo_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upload_photo_id=2;
                select_image();
            }
        });
        upload_photo_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload_photo_id=3;
                select_image();
            }
        });
        upload_photo_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upload_photo_id=4;
                select_image();
            }
        });
        upload_photo_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upload_photo_id=5;
                select_image();
            }
        });

    }




    private void select_image()
    {



        final CharSequence[] items={"Camera","Gallery","Cancel"};

        AlertDialog.Builder builder=new AlertDialog.Builder(
                this
        );
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(items[which].equals("Camera"))
                {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);//zero can be replaced with any action code (called requestCode)
                }
                else if(items[which].equals("Gallery"))
                {

                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);//one can be replaced with any action code

                }
                else if(items[which].equals("Cancel"))
                {
                    dialog.dismiss();
                }
            }
        });

        builder.show();


    }



    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);







        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {

                    Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");

                    tempUri = getImageUri(getApplicationContext(), photo);
                    perform_crop(tempUri);





                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    perform_crop(selectedImage);

                }
                break;

            case 2:
                if (imageReturnedIntent != null) {
                    // get the returned data
                    Bundle extras = imageReturnedIntent.getExtras();
                    // get the cropped bitmap
                    Bitmap selectedBitmap = extras.getParcelable("data");

                    tempoutputUri=getImageUri(getApplicationContext(),selectedBitmap);
                    if(tempoutputUri!=null && upload_photo_id !=0)
                    {

                        switch (upload_photo_id)
                        {
                            case 1:
                                upload_photo_one.setImageURI(tempoutputUri);
                                uri_image1=tempoutputUri;
                                break;


                            case 2:
                                upload_photo_two.setImageURI(tempoutputUri);
                                uri_image2=tempoutputUri;
                                break;

                            case 3:
                                upload_photo_three.setImageURI(tempoutputUri);
                                uri_image3=tempoutputUri;
                                break;

                            case 4:
                                upload_photo_four.setImageURI(tempoutputUri);
                                uri_image4=tempoutputUri;
                                break;

                            case 5:
                                upload_photo_five.setImageURI(tempoutputUri);
                                uri_image5=tempoutputUri;
                                break;


                        }

                    }

                }
                break;




        }
    }

    private void perform_crop(Uri tempUri) {


            try {
                Intent cropIntent = new Intent("com.android.camera.action.CROP");
                // indicate image type and Uri
                cropIntent.setDataAndType(tempUri, "image/*");
                // set crop properties here
                cropIntent.putExtra("crop", true);
                // indicate aspect of desired crop
                cropIntent.putExtra("aspectX", 4);
                cropIntent.putExtra("aspectY", 3);
                // indicate output X and Y
                cropIntent.putExtra("outputX", 960);
                cropIntent.putExtra("outputY", 640);
                // retrieve data on return
                cropIntent.putExtra("return-data", true);
                // start the activity - we handle returning in onActivityResult
                startActivityForResult(cropIntent, PIC_CROP);
            }
            // respond to users whose devices do not support the crop action
            catch (ActivityNotFoundException anfe) {
                // display an error message
                String errorMessage = "Whoops - your device doesn't support the crop action!";
                Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }

    }


    private Uri getImageUri(Context applicationContext, Bitmap photo) {

        Long tsLong = System.currentTimeMillis()/1000;
        String image_title = tsLong.toString();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(UploadRoomPhotoActivity.this.getContentResolver(), photo, image_title, null);
        return Uri.parse(path);
    }

    private void upload_room_images(Uri image1, Uri image2, Uri image3, Uri image4, Uri image5) {



        ArrayList<Uri> arrayListImages=new ArrayList<>();
        Log.i("hello_world","success_shubham");



        arrayListImages.add(image1);
        arrayListImages.add(image2);
        arrayListImages.add(image3);
        arrayListImages.add(image4);
        arrayListImages.add(image5);



        // create list of file parts (photo, video, ...)
        List<MultipartBody.Part> parts = new ArrayList<>();




        if (arrayListImages != null) {
            // create part for file (photo, video, ...)
            for (int i = 0; i < arrayListImages.size(); i++) {
                parts.add(prepareFilePart(String.valueOf(i), arrayListImages.get(i)));
            }
        }
        Call<ResponseBody> call_upload_images=RetrofitClient.getInstance().getApi().uploadMultipleImages(
                parts


        );

        call_upload_images.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                try {
                    if (response.body() != null) {
                        Log.i("register_room_images",response.body().string());
                    }
                    else{
                        Toast.makeText(UploadRoomPhotoActivity.this, "null response", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(UploadRoomPhotoActivity.this, "upload Sucess", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(UploadRoomPhotoActivity.this, "upload_failed", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }




    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = FileUtils.getFile(this, fileUri);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getContentResolver().getType(fileUri)),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


//    private void showProgress() {
//        mProgressBar.setVisibility(View.VISIBLE);
//
//    }
//
//    private void hideProgress() {
//        mProgressBar.setVisibility(View.GONE);
//
//    }
}
