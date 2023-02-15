package com.anand.withu;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Random;

public class FragCreate extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.frag_create,container,false);
        EditText getcode = (EditText) view.findViewById(R.id.codegen);

        Random random = new Random();

        String id = String.format("%04d", random.nextInt(10000));

        getcode.setText(id);

        getcode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode >=0 && keyCode <=255)
                {
                    getcode.setFocusable(false);
                    return false;
                }
                else
                {
                    getcode.setFocusable(true);
                    return true;
                }
            }
        });
        return view;
    }

}
