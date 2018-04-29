package com.example.imran.vollyexample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.imran.vollyexample.R;
import com.example.imran.vollyexample.adapter.custom.CustomUserAdapter;
import com.example.imran.vollyexample.adapter.custom.base.ItemClickListener;
import com.example.imran.vollyexample.model.CustomUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Imran on 4/29/2018.
 */

public class UserInfoActivity extends AppCompatActivity implements ItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();

    }

    private void initView() {
        RecyclerView recyclerViewUser = getRecyclerView();
        recyclerViewUser.setAdapter(new CustomUserAdapter());
        recyclerViewUser.setLayoutManager(new LinearLayoutManager(this));
        getAdapter().setItemClickListener(this);
        Intent intent = getIntent();
        ArrayList<CustomUser> customUsers = new ArrayList<>();
        customUsers = intent.getParcelableArrayListExtra("UserList");
        getAdapter().addItems(customUsers);
    }


    private RecyclerView getRecyclerView() {
        return findViewById(R.id.recyclerViewUser);
    }

    private CustomUserAdapter getAdapter() {
        return (CustomUserAdapter) getRecyclerView().getAdapter();
    }

    @Override
    public void onItemClick(View view, Object item) {

        switch (view.getId()) {
            case R.id.checkBox:
                Toast.makeText(this, "checked", Toast.LENGTH_LONG).show();
            case R.id.layoutParent:
                if (item != null) {
                    //startActivity(MessageActivity.getStartIntent(this, item.getNeedName()));
                }
                break;
        }
    }

    private void getIntentValue() {

    }
}
