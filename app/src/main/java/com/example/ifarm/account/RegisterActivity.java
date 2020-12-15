package com.example.ifarm.account;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ifarm.R;
import com.example.ifarm.utils.AnimationUtil;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    public Uri imageUri;
    public ProgressDialog mDialog;
    public String name_, pass_, email_, username_, location_;
    private EditText name, email, password, location, username;
    private CircleImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imageUri = null;

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        location = findViewById(R.id.location);
        username = findViewById(R.id.username);

        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Please wait..");
        mDialog.setIndeterminate(true);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);

        Button register = findViewById(R.id.button);

        profile_image = findViewById(R.id.profile_image);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null) {
                    username_ = username.getText().toString();
                    name_ = name.getText().toString();
                    email_ = email.getText().toString();
                    pass_ = password.getText().toString();
                    location_ = location.getText().toString();

                    mDialog.show();

                    if (TextUtils.isEmpty(username_)) {
                        AnimationUtil.shakeView(username, RegisterActivity.this);
                        mDialog.dismiss();

                    } else if (username_.length() < 5) {

                        Toast.makeText(RegisterActivity.this, "Username should be more than 5 characters", Toast.LENGTH_SHORT).show();
                        AnimationUtil.shakeView(username, RegisterActivity.this);
                        mDialog.dismiss();

                    } else if (!username_.matches("[a-zA-Z._]*")) {

                        Toast.makeText(RegisterActivity.this, "No numbers or special character than period and underscore allowed", Toast.LENGTH_SHORT).show();
                        AnimationUtil.shakeView(username, RegisterActivity.this);
                        mDialog.dismiss();

                    }

                    if (TextUtils.isEmpty(name_) && !name_.matches("[a-zA-Z ]*")) {

                        Toast.makeText(RegisterActivity.this, "Invalid name", Toast.LENGTH_SHORT).show();
                        AnimationUtil.shakeView(name, RegisterActivity.this);
                        mDialog.dismiss();

                    }
                    if (TextUtils.isEmpty(email_)) {

                        AnimationUtil.shakeView(email, RegisterActivity.this);
                        mDialog.dismiss();

                    }
                    if (TextUtils.isEmpty(pass_)) {

                        AnimationUtil.shakeView(password, RegisterActivity.this);
                        mDialog.dismiss();

                    }

                    if (TextUtils.isEmpty(location_)) {

                        AnimationUtil.shakeView(location, RegisterActivity.this);
                        mDialog.dismiss();

                    }

                    if (!TextUtils.isEmpty(name_) || !TextUtils.isEmpty(email_) ||
                            !TextUtils.isEmpty(pass_) || !TextUtils.isEmpty(username_) || !TextUtils.isEmpty(location_)) {
                        //TODO: check if the account exists, then call this function
                        registerUser();

                    } else {

                        AnimationUtil.shakeView(username, RegisterActivity.this);
                        AnimationUtil.shakeView(name, RegisterActivity.this);
                        AnimationUtil.shakeView(email, RegisterActivity.this);
                        AnimationUtil.shakeView(password, RegisterActivity.this);
                        AnimationUtil.shakeView(location, RegisterActivity.this);
                        mDialog.dismiss();

                    }

                } else {
                    AnimationUtil.shakeView(profile_image, RegisterActivity.this);
                    Toast.makeText(RegisterActivity.this, "We recommend you to set a profile picture", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }
            }
        });
    }

    private void registerUser() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                imageUri = data.getData();
                // start crop activity
                UCrop.Options options = new UCrop.Options();
                options.setCompressionFormat(Bitmap.CompressFormat.PNG);
                options.setCompressionQuality(100);
                options.setShowCropGrid(true);

                UCrop.of(imageUri, Uri.fromFile(new File(getCacheDir(), "ESA_user_profile_picture.png")))
                        .withAspectRatio(1, 1)
                        .withOptions(options)
                        .start(this);

            }
        }
        if (requestCode == UCrop.REQUEST_CROP) {
            if (resultCode == RESULT_OK) {
                imageUri = UCrop.getOutput(data);
                profile_image.setImageURI(imageUri);

            } else if (resultCode == UCrop.RESULT_ERROR) {
                Log.e("Error", "Crop error:" + UCrop.getError(data).getMessage());
            }
        }
    }

    public void setProfilepic(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), PICK_IMAGE);
    }

    public void onLogin(View view) {
        onBackPressed();
    }

    public void openPolicy(View view) {
        String url = "https://google.com";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void openTerms(View view) {
        String url = "https://google.com";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
