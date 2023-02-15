package com.anand.withu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.anand.withu.Models.Users;
import com.anand.withu.databinding.ActivitySignUpBinding;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUp extends AppCompatActivity {
    private static int SPLASH_SCREEN=1000;
    private CircleImageView ppic;
    private DatabaseReference dbsref;
    private Uri imgUri;
    String Myuri ="";
    private StorageReference strgref;
    private StorageTask uploadtask;
    private FirebaseAuth auth;
    ActivitySignUpBinding binding;
    FirebaseDatabase database;
    ProgressDialog prgsialog;
   AwesomeValidation awv;
    boolean isEmailValid, isPasswordValid, isPasswordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        setContentView(binding.getRoot());


        binding.pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (binding.pass.getRight() - binding.pass.getCompoundDrawables()[RIGHT].getBounds().width())) {
                        int selection = binding.pass.getSelectionEnd();
                        if (isPasswordVisible) {
                            // set drawable image
                            binding.pass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eyeoff, 0);
                            // hide Password
                            binding.pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            isPasswordVisible = false;
                        } else  {
                            // set drawable image
                            binding.pass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eyeon, 0);
                            // show Password
                            binding.pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            isPasswordVisible = true;
                        }
                        binding.pass.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        binding.cnfpass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (binding.cnfpass.getRight() - binding.cnfpass.getCompoundDrawables()[RIGHT].getBounds().width())) {
                        int selection = binding.cnfpass.getSelectionEnd();
                        if (isPasswordVisible) {
                            // set drawable image
                            binding.cnfpass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eyeoff, 0);
                            // hide Password
                            binding.cnfpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            isPasswordVisible = false;
                        } else  {
                            // set drawable image
                            binding.cnfpass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eyeon, 0);
                            // show Password
                            binding.cnfpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            isPasswordVisible = true;
                        }
                        binding.cnfpass.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });




        dbsref = FirebaseDatabase.getInstance().getReference().child("User");
        auth = FirebaseAuth.getInstance();
        strgref = FirebaseStorage.getInstance().getReference().child("Profile Pic");
        database = FirebaseDatabase.getInstance();
        prgsialog = new ProgressDialog(SignUp.this);
        binding.fnm.requestFocus();

        binding.pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        binding.pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        binding.fnm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    if(binding.fnm.getText().toString().length()==0)
                    binding.fnm.setError("Inavlid Name");
                    else
                    {
                        binding.fnm.setError(null);
                    }
                }

            }
        });


        binding.cnfpass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus)
                {
                    if(binding.cnfpass.getText().toString().length()<8 &&!isValidPassword(binding.cnfpass.getText().toString()) || (binding.cnfpass.getText().toString()!=binding.pass.getText().toString())){
                        binding.cnfpass.setError("Not Valid");
                    }else{
                        binding.cnfpass.setError(null);
                    }
                }

            }
        });

        binding.lnm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    if(binding.lnm.getText().toString().length()==0)
                        binding.lnm.setError("Inavlid Name");
                    else
                    {
                        binding.lnm.setError(null);
                    }
                    //Toast.makeText(SignUp.this, "Focused", Toast.LENGTH_SHORT).show();
                }

            }
        });



        binding.email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus)
                {
                    if (isEmailValid(binding.email.getText().toString()))
                    {
                        binding.email.setError(null);
                    }
                    else
                    {
                        binding.email.setError("Invalid Email");
                    }
                }

            }
        });

        binding.pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus)
                {
                    if(binding.pass.getText().toString().length()<8 &&!isValidPassword(binding.pass.getText().toString())){
                        binding.pass.setError("Not Valid");
                    }else{
                        binding.pass.setError(null);
                    }
                }
            }
        });



        prgsialog.setTitle("Creating Account");
        prgsialog.setMessage("Creating Your Account");



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen



        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if((binding.fnm.getText().toString().length()>0) && (binding.lnm.getText().toString().length()>0) && (binding.email.getText().toString().length()>0) && (binding.pass.getText().toString().length()>0) && (binding.cnfpass.getText().toString().length()>0))
                    {
                        prgsialog.show();
                        auth.createUserWithEmailAndPassword(binding.email.getText().toString(),binding.pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                prgsialog.dismiss();
                                if(task.isSuccessful())
                                {
                                    Users user = new Users(binding.fnm.getText().toString() ,binding.lnm.getText().toString(), binding.email.getText().toString() , binding.pass.getText().toString());
                                    String id = task.getResult().getUser().getUid();
                                    database.getReference().child("Users").child(id).setValue(user);
                                    Toast.makeText(SignUp.this ,"User Created Successfully" , Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUp.this, SignIn.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(SignUp.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(SignUp.this, "All Fields Are Mandatory", Toast.LENGTH_LONG).show();
                    }



                }
        });

    }

    private void profileimage()
    {
        binding.ppicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public void opensignin (View view)
    {
        Signin();
    }
    private void Signin ()
    {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}