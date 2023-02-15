package com.anand.withu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Random;
import com.anand.withu.databinding.ActivityCreateGroupBinding;
import com.anand.withu.databinding.ActivitySignUpBinding;

public class CreateGroup extends AppCompatActivity {

    ActivityCreateGroupBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateGroupBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        Random random = new Random();

        String id = String.format("%04d", random.nextInt(10000));

        binding.codegen.setText(id);

        binding.codegen.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode >=0 && keyCode <=255)
                {
                    binding.codegen.setFocusable(false);
                    return false;
                }
                else
                {
                    binding.codegen.setFocusable(true);
                    return true;
                }
            }
        });



    }
}