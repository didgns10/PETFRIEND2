package com.example.petfriend.Activity.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.petfriend.Activity.MainActivity;
import com.example.petfriend.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "SingupActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        Button button_login = (Button) findViewById(R.id.button_login);
        Button button_signup = (Button) findViewById(R.id.button_signup);
        Button button_idpw = (Button) findViewById(R.id.button_idpw);

        button_login.setOnClickListener(new View.OnClickListener() { //로그인버튼을 클릭을 할경우
            @Override
            public void onClick(View view) {
                login();
            }

        });
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent1);
            }
        });
        button_idpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(LoginActivity.this, IdpwActivity.class);
                startActivity(intent2);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    private void login() {
        String email = ((EditText)findViewById(R.id.editText_login_id)).getText().toString();
        String password = ((EditText)findViewById(R.id.editText_login_pw)).getText().toString();

        if(email.length() > 0 && password.length() >0){
            final RelativeLayout loaderlayout = findViewById(R.id.loaderlayout);
            loaderlayout.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            loaderlayout.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startToast("로그인이 되었습니다.");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                if(task.getException() != null) {
                                    startToast("이메일 또는 비밀번호를 확인해주세요.");
                                }
                            }

                            // ...
                        }
                    });
        }else{
            startToast("이메일 또는 비밀번호를 입력해주세요.");
        }

    }
    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}

