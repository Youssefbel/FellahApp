package com.example.ifarm;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.ifarm.adapter.OnBoardingScreenAdapter;
import com.example.ifarm.model.TipsModel;

import java.util.ArrayList;

public class tipsActivity extends AppCompatActivity {

    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;

    private ViewPager pager;
    private OnBoardingScreenAdapter mAdapter;

    private Button btn_get_started;

    int previous_pos = 0;


    ArrayList<TipsModel> onBoardItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        btn_get_started = findViewById(R.id.btn_get_started);
        pager = findViewById(R.id.pager_introduction);
        pager_indicator = findViewById(R.id.viewPagerCountDots);

        loadData();

        mAdapter = new OnBoardingScreenAdapter(this, onBoardItems);
        pager.setAdapter(mAdapter);
        pager.setCurrentItem(0);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                // Change the current position intimation
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(tipsActivity.this,
                            R.drawable.non_selected_item_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(tipsActivity.this,
                        R.drawable.selected_item_dot));


                int pos = position + 1;

                if (pos == dotsCount && previous_pos == (dotsCount - 1))
                    show_animation();
                else if (pos == (dotsCount - 1) && previous_pos == dotsCount)
                    hide_animation();

                previous_pos = pos;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btn_get_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setUiPageViewController();
    }

    // Load data into the viewpager
    public void loadData() {
        //TODO: insert here items to display
        onBoardItems.add(new TipsModel("Tomate", "pH\t :\t6 > pH > 6.5\n" +
                "\n" +
                "terre\t :\tsols légers , sablonneux , lourds ou argileux. Sableux sont préférables si une récolte précoce est souhaitée.\n" +
                "\n" +
                "Arrosage :\tEn plein air --> entre 4000 et 6000 m³/ha\n" +
                "\n" +
                "\t\tSous serre --> jusqu'à 10 000 m³/ha\n" +
                "\n" +
                "Temperature :   10°C < x < 30°C\n",
                R.drawable.tomato));

        onBoardItems.add(new TipsModel("Pomme de terre", "pH\t :\tpH > 5.5\n" +
                "\n" +
                "terre\t :\tQuelque soit les sols ,organiques ou minéraux.Les sols de texture légère et moyenne sont recommandés.\n" +
                "\n" +
                "Arrosage :\tPhase 1--> Maintenir le sol constamment et uniformément humide jusqu'à une profondeur d'au moins 10-15 cm.\n" +
                "\n" +
                "\t\tPhase 2--> Une fois tout les 3-5 jours.",
                R.drawable.potato));

        onBoardItems.add(new TipsModel("Citrouille", "\n" +
                "pH\t :\tNeutre\n" +
                "\n" +
                "terre\t :\tTerre riche avec beaucoup de compost ajouté.\n" +
                "\n" +
                "Arrosage :\tLes citrouilles sont des plantes très assoiffées et ont besoin de beaucoup d’eau. Arrosez un pouce par semaine. Arrosez abondamment, surtout pendant la nouaison.\n" +
                "\n" +
                "Temperature :   Toujours au soleil",
                R.drawable.pumpkin));

        onBoardItems.add(new TipsModel("Oignions", "pH\t :\tNeutre\n" +
                "\n" +
                "terre\t :\tRiche en terreaux / n'importe.\n" +
                "\n" +
                "Arrosage :\tArroser quand sol devient sec.\n" +
                "\n" +
                "Temperature :   -6°C  <  x  ",
                R.drawable.onion));

        onBoardItems.add(new TipsModel("Pastèque", "pH\t :\tNeutre.\n" +
                "\n" +
                "terre\t :\tSol riche avec beaucoup de compost ajouté.\n" +
                "\n" +
                "Arrosage :\tUne fois que les plantes ont mis leurs fruits, nourrissez-les deux fois, à intervalles de deux semaines, avec un engrais soluble dans l'eau.\n" +
                "\n" +
                "Temperature :   Toujours au soleil.\n",
                R.drawable.watermelon));
    }

    // Button bottomUp animation
    public void show_animation() {
        Animation show = AnimationUtils.loadAnimation(this, R.anim.slide_up_anim);

        btn_get_started.startAnimation(show);

        show.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                btn_get_started.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                btn_get_started.clearAnimation();

            }

        });


    }

    // Button Topdown animation

    public void hide_animation() {
        Animation hide = AnimationUtils.loadAnimation(this, R.anim.slide_down_anim);

        btn_get_started.startAnimation(hide);

        hide.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                btn_get_started.clearAnimation();
                btn_get_started.setVisibility(View.GONE);

            }

        });


    }

    // setup the
    private void setUiPageViewController() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(tipsActivity.this,
                    R.drawable.non_selected_item_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(6, 0, 6, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(tipsActivity.this,
                R.drawable.selected_item_dot));
    }
}
