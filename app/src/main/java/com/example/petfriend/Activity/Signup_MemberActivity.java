package com.example.petfriend.Activity;

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

import com.example.petfriend.Model.MemberInfo;
import com.example.petfriend.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

public class Signup_MemberActivity extends AppCompatActivity {
    private static final String TAG = "memberinfo";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_member);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // 돌아가기 화면
        ImageButton button_back = (ImageButton) findViewById(R.id.imageButton_back);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup_MemberActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button bt_signup = (Button) findViewById(R.id.button_info);
        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberinfo();
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.


    }

    private void memberinfo() {
        String phone_num = ((EditText)findViewById(R.id.editText_signup_member_phone)).getText().toString();
        String nickname = ((EditText)findViewById(R.id.editText_signup_member_nick)).getText().toString();
        String address = ((EditText)findViewById(R.id.editText_signup_member_address)).getText().toString();




        if(address.length() > 0 && nickname.length() >0 && phone_num.length() >9){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            if(user != null) {
                MemberInfo memberInfo = new MemberInfo(nickname, phone_num, address);
                db.collection("users").document(user.getUid()).set(memberInfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                startToast("회원정보 등록을 완료하였습니다.");
                                Intent intent = new Intent(Signup_MemberActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                                startToast("회원정보 등록을 실패하였습니다.");
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