package com.example.petfriend.Activity.Newspeed;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.petfriend.Activity.Login.IdpwActivity;
import com.example.petfriend.Activity.Login.LoginActivity;
import com.example.petfriend.Activity.Login.Signup_MemberActivity;
import com.example.petfriend.Activity.MainActivity;
import com.example.petfriend.Model.MemberInfo;
import com.example.petfriend.Model.Upload_newspeed_info;
import com.example.petfriend.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddnewspeedActivity extends AppCompatActivity {
    private static final String TAG = "upload_newspeed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newspeed_uplode);

        ImageButton button_back = (ImageButton) findViewById(R.id.imageButton_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddnewspeedActivity.this, newspeedActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button bt_ok = (Button)findViewById(R.id.button_upload);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newspeed_info();
            }
        });
        FloatingActionButton ft_bt_image = (FloatingActionButton) findViewById(R.id.ft_bt_image);
        ft_bt_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        FloatingActionButton ft_bt_video = (FloatingActionButton) findViewById(R.id.ft_bt_video);
        ft_bt_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void newspeed_info() {
        String title = ((EditText)findViewById(R.id.editText_newspeed_upload_title)).getText().toString();
        String content = ((EditText)findViewById(R.id.editText_newspeed_upload_content)).getText().toString();




        if(title.length() > 0 && content.length() >0 ){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            if(user != null) {
                Upload_newspeed_info newspeed_info = new Upload_newspeed_info(title, content,user.getUid());
                db.collection("newspeed").add(newspeed_info)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                startToast("게시글이 등록되었습니다.");
                                Intent intent = new Intent(AddnewspeedActivity.this, newspeedActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                                startToast("게시글 등록이 취소되었습니다.");
                            }
                        });
            }
        }else{
            startToast("빈칸 없이 채워주세요.");
        }

    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
