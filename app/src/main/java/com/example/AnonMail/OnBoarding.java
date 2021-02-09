package com.example.AnonMail;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.viewpager.widget.ViewPager;

public class OnBoarding extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dotsLayout;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    Button btnLetsGetStarted, btnNext, btnBack, btnSkip;
    Animation animation;
    int currPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_on_boarding);

        viewPager = findViewById(R.id.slider);
        dotsLayout = findViewById(R.id.dots);
        btnLetsGetStarted = findViewById(R.id.btnLetsGetStarted);
        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);
        btnSkip = findViewById(R.id.btnSkip);

        sliderAdapter = new SliderAdapter(this);

        viewPager.setAdapter(sliderAdapter);

        addDots(0);

        viewPager.addOnPageChangeListener(changeListner);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewPager.setCurrentItem(currPosition - 1);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewPager.setCurrentItem(currPosition + 1);
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), LogIn.class));           /**-----INTENT-----**/
                finish();
            }
        });

        btnLetsGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), LogIn.class));           /**-----INTENT-----**/
                finish();
            }
        });
    }

    private void addDots(int position) {

        dots = new TextView[3];

        dotsLayout.removeAllViews();

        for (int i =0; i<dots.length; i++) {

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));           //html code for bullet
            dots[i].setTextSize(35);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {

            dots[position].setTextColor(getResources().getColor(R.color.design_default_color_primary_dark));
        }
    }

    ViewPager.OnPageChangeListener changeListner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            currPosition = position;

            addDots(position);

            if (position == 0) {

                btnLetsGetStarted.setVisibility(View.INVISIBLE);
                DrawableCompat.setTint(btnBack.getCompoundDrawables()[0], ContextCompat.getColor(getApplicationContext(), R.color.notClickable));
                btnBack.setTextColor(getApplication().getResources().getColor(R.color.notClickable));
                btnBack.setClickable(false);
            }
            else if (position == 1) {

                btnLetsGetStarted.setVisibility(View.INVISIBLE);
                btnNext.setClickable(true);
                DrawableCompat.setTint(btnNext.getCompoundDrawables()[2], ContextCompat.getColor(getApplicationContext(), R.color.black));
                btnNext.setTextColor(getApplication().getResources().getColor(R.color.black));
                btnBack.setClickable(true);
                DrawableCompat.setTint(btnBack.getCompoundDrawables()[0], ContextCompat.getColor(getApplicationContext(), R.color.black));
                btnBack.setTextColor(getApplication().getResources().getColor(R.color.black));
                btnSkip.setClickable(true);
            }
            else {

                animation = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.bottom_animation);
                btnLetsGetStarted.setAnimation(animation);
                btnLetsGetStarted.setVisibility(View.VISIBLE);
                btnNext.setClickable(false);
                btnNext.setTextColor(getApplication().getResources().getColor(R.color.notClickable));
                DrawableCompat.setTint(btnNext.getCompoundDrawables()[2], ContextCompat.getColor(getApplicationContext(), R.color.notClickable));
                btnSkip.setClickable(false);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}