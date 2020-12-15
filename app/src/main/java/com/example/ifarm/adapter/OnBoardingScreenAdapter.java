package com.example.ifarm.adapter;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.example.ifarm.R;
import com.example.ifarm.model.TipsModel;

import java.util.ArrayList;

public class OnBoardingScreenAdapter extends PagerAdapter {

    private Context mContext;
    ArrayList<TipsModel> onBoardItems = new ArrayList<>();


    public OnBoardingScreenAdapter(Context mContext, ArrayList<TipsModel> items) {
        this.mContext = mContext;
        this.onBoardItems = items;
    }

    @Override
    public int getCount() {
        return onBoardItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.onboardingscreen_item,
                container, false);

        TipsModel item = onBoardItems.get(position);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_onboard);
        imageView.setImageResource(item.getImageID());

        TextView tv_title = (TextView) itemView.findViewById(R.id.tv_header);
        tv_title.setText(item.getTitle());

        TextView tv_content = (TextView) itemView.findViewById(R.id.tv_desc);
        tv_content.setMovementMethod(new ScrollingMovementMethod());
        tv_content.setText(item.getDescription());

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
