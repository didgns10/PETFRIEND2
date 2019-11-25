package com.example.petfriend.Activity.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.petfriend.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SingupActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // 돌아가기 화면
        ImageButton button_back = (ImageButton) findViewById(R.id.imageButton_back);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button bt_signup = (Button) findViewById(R.id.button_signup);
        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    private void signup() {
        String email = ((EditText)findViewById(R.id.editText_signup_id)).getText().toString();
        String password = ((EditText)findViewById(R.id.editText_signup_pw)).getText().toString();
        String re_password = ((EditText)findViewById(R.id.editText_signup_repw)).getText().toString();



        FirebaseUser user = mAuth.getCurrentUser();


        if(email.length() > 0 && password.length() > 0 && re_password.length()>0 ){
            if(password.equals(re_password)){
                final RelativeLayout loaderlayout = findViewById(R.id.loaderlayout);
                loaderlayout.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                loaderlayout.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    startToast("이메일 등록이 완료되었습니다.");
                                    //성공했을때 UI로직
                                    Intent intent = new Intent(SignupActivity.this, Signup_MemberActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    if(task.getException() != null) {
                                        startToast("이메일 또는 비밀번호 형식에 맞게 넣어주세요.");
                                    }
                                }

                                // ...
                            }
                        });

            }else{
                startToast("비밀번호가 일치하지 않습니다.");
            }
        }else{
            startToast("빈칸 없이 채워주세요.");
        }

    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
