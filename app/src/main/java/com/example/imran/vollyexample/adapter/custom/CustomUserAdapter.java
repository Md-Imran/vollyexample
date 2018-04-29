package com.example.imran.vollyexample.adapter.custom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.imran.vollyexample.R;
import com.example.imran.vollyexample.adapter.custom.base.BaseAdapter;
import com.example.imran.vollyexample.adapter.custom.base.BaseViewHolder;
import com.example.imran.vollyexample.model.CustomUser;
import com.example.imran.vollyexample.utils.Glider;

/**
 * Created by Imran on 4/29/2018.
 */

public class CustomUserAdapter extends BaseAdapter<CustomUser> {
    @Override
    public boolean isEqual(CustomUser left, CustomUser right) {
        return left.getId().equals(right.getId());
    }

    @Override
    public BaseViewHolder newViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_custom_user, parent, false);

        return new CustomUserViewHolder(view);
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return 0;
    }

    @Override
    protected CustomUser getObjForPosition(int position) {
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<CustomUser> holder, int position) {
        CustomUser user = getItem(position);
        if (user == null) return;
        CustomUserViewHolder customUserViewHolder = (CustomUserViewHolder) holder;
        customUserViewHolder.bind(user);

    }

    public class CustomUserViewHolder extends BaseViewHolder<CustomUser> implements View.OnClickListener {

        private TextView tvFirstName, tvLastName;
        private ImageView imageViewUserAvatar;
        private CheckBox checkBox;
        private RelativeLayout layoutParent;

        public CustomUserViewHolder(View itemView) {
            super(itemView);

            tvFirstName = (TextView) itemView.findViewById(R.id.tvFirstName);
            tvLastName = (TextView) itemView.findViewById(R.id.tvLastName);
            imageViewUserAvatar = (ImageView) itemView.findViewById(R.id.imageViewUserAvatar);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            layoutParent = (RelativeLayout) itemView.findViewById(R.id.layoutParent);
            checkBox.setOnClickListener(this);

        }

        @Override
        public void bind(CustomUser item) {
            CustomUser customUser = (CustomUser) item;
            tvFirstName.setText(customUser.getFirst_name());
            tvLastName.setText(customUser.getLast_name());
            Glider.loadUserAvatar(customUser.getAvatar(), imageViewUserAvatar);
            //checkBox.setChecked();

        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getItem(getAdapterPosition()));
        }
    }
}
