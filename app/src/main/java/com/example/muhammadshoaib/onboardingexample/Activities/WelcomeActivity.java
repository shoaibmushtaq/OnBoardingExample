package com.example.muhammadshoaib.onboardingexample.Activities;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.muhammadshoaib.onboardingexample.Adapters.MPagerAdapter;
import com.example.muhammadshoaib.onboardingexample.SharedPreference.PrefernceManager;
import com.example.muhammadshoaib.onboardingexample.R;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mPager;
    private int[] layouts = {R.layout.first_slide,R.layout.second_slide,R.layout.third_slide};
    private MPagerAdapter mPagerAdapter;
    private LinearLayout DotsLayout;
    private ImageView[] dots;
    private Button bnNext,bnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //to check if the user has opened the app before or not
        if (new PrefernceManager(this).checkPreference()){

            CallHomeActivity();
        }


        //if the api level is 19 or above, this will aply transparent notification bar
        if (Build.VERSION.SDK_INT >= 19){

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        else {

            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_welcome);

        //initializing
        mPager=(ViewPager) findViewById(R.id.viewPager);
        mPagerAdapter=new MPagerAdapter(layouts,this);
        mPager.setAdapter(mPagerAdapter);
        DotsLayout=(LinearLayout) findViewById(R.id.dotsLayout);
        bnNext=findViewById(R.id.btnNext);
        bnSkip=findViewById(R.id.btnSkip);

        //setting click listeners on buttons
        bnSkip.setOnClickListener(this);
        bnNext.setOnClickListener(this);

        //this method will create page indicators for view pager
        createDots(0);

        //listener for view pager
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {

                //this method will create page indictor
                createDots(position);

                //if the current page in the viewpager is the last one , this code will change the text of "next" to "start" and hide the "skip" button
                if(position == layouts.length -1 ){

                    bnNext.setText("Start");
                    bnSkip.setVisibility(View.INVISIBLE);

                }
                else {

                    bnNext.setText("Next");
                    bnSkip.setVisibility(View.VISIBLE);


                }


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }


//this method gets the current position in viewpager and set the indicators
    private void createDots(int current_position){


        if (DotsLayout!=null){

            DotsLayout.removeAllViews();

            dots=new ImageView[layouts.length];

            for (int i=0; i<layouts.length; i++)
            {
                dots[i] = new ImageView(this);

                //this condition will be true varriable i is equal to current position and change the dot icon color into active
                if(i == current_position){

                    dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dots));
                }

                //otherwise it will change the dot color into inactive
                else {

                    dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.default_dots));
                }

                //creates an instance of linear layout parameters instance and pass height and width into arguments
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

                //sets the margins of linear layout
                params.setMargins(4,0,4,0);

                //it will generate dot icons into linear layout
                DotsLayout.addView(dots[i],params);
            }

        }





    }


    //onclick listeners for skip and next buttons
    @Override
    public void onClick(View view) {

        //checking which button is clicked
        switch (view.getId()){

            //it will load next page on click
            case R.id.btnNext:
                LoadNextSlide();
                break;

            //it will launch main activity and write the preference that next time main activity will launch directly
            case R.id.btnSkip:
                CallHomeActivity();
                new PrefernceManager(this).writePreference();
                break;
        }

    }

    //this method will launch main activity
    private void CallHomeActivity()
    {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    //this method will get current slide in view pager and check if it is the last slide or not
    private void LoadNextSlide(){

        int next_slide= mPager.getCurrentItem() + 1;

        //if the current slide is not last slide it will add the next slide
        if(next_slide < layouts.length){

            mPager.setCurrentItem(next_slide);

        }

        // otherwise if it is the last slide it will call main activity and write the preference
        else {

            CallHomeActivity();
            new PrefernceManager(this).writePreference();
        }
    }
}
