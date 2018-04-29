package com.example.imran.vollyexample.adapter.standard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.imran.vollyexample.R;
import com.example.imran.vollyexample.model.UserList;
import com.example.imran.vollyexample.utils.Glider;

import java.util.List;

/**
 * Created by Imran on 4/29/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.CustomRecyclerView> {

    private List<UserList.UserDataList> itemList;
    private Context mContext;

    public UserAdapter(Context context,List<UserList.UserDataList> itemList) {
        this.itemList = itemList;
        this.mContext=context;

    }

    @Override
    public CustomRecyclerView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, null);
        CustomRecyclerView rcv = new CustomRecyclerView(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(CustomRecyclerView holder, int position) {

        Glider.init(mContext);
        UserList.UserDataList myData = itemList.get(position);
        holder.txtLabel.setText(myData.first_name + " " + myData.last_name);
        Glider.loadUserAvatar(myData.avatar,holder.avatar);
        //holder.avatar.set(myData.avatar, mImageLoader);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class CustomRecyclerView extends RecyclerView.ViewHolder {

        TextView txtLabel;
        ImageView avatar;

        CustomRecyclerView(View itemView) {
            super(itemView);
            txtLabel = itemView.findViewById(R.id.txtLabel);
            avatar = itemView.findViewById(R.id.imgNetwork);
        }
    }
}
