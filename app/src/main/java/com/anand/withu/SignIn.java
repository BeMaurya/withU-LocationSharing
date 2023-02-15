package com.anand.withu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.anand.withu.databinding.ActivitySignInBinding;
import com.anand.withu.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    private static int SPLASH_SCREEN = 1000;
    ActivitySignInBinding binding;
    ProgressDialog prgsialog;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        prgsialog = new ProgressDialog(SignIn.this);
        prgsialog.setTitle("Login");
        prgsialog.setMessage("Login To Your Account");

        binding.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prgsialog.show();
                auth.signInWithEmailAndPassword(binding.usrNm.getEditText().getText().toString(),binding.password.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        prgsialog.dismiss();

                        if(task.isSuccessful())
                        {
                            Intent intent = new Intent(SignIn.this,UserLocationMainActivity.class);
                            startActivity(intent);

                        }
                        else 
                        {
                            Toast.makeText(SignIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen




    }

    public void opensignup (View view)
    {
        SignUp();
    }

    private void SignUp ()
    {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}