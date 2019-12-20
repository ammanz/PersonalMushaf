package com.android.personalmushaf;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.android.personalmushaf.quranactivitystrategies.MadaniQuranActivityStrategy;
import com.android.personalmushaf.quranactivitystrategies.Naskh13QuranActivityStrategy;
import com.android.personalmushaf.quranactivitystrategies.QuranActivityStrategy;
import com.android.personalmushaf.quranpage.QuranPageAdapter;


public class QuranActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private int pageNumber;
    private int receivedPageNumber;
    private int pagesTurned;

    private int currentOrientation;

    private ViewPager2 pager;

    private ViewPager2.OnPageChangeCallback singlePageChangeCallback;
    private ViewPager2.OnPageChangeCallback dualPageChangeCallback;


    private QuranPageAdapter pagerAdapter;

    boolean isSmoothVolumeKeyNavigation;
    boolean isForceDualPage;
    private QuranActivityStrategy strategy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran);
        
        isSmoothVolumeKeyNavigation = QuranSettings.getInstance().getIsSmoothKeyNavigation(this);
        isForceDualPage = QuranSettings.getInstance().getIsForceDualPage(this);

        setupActionbar();

        Intent activityThatCalled = getIntent();

        receivedPageNumber = activityThatCalled.getIntExtra("new page number", 2);

        setPageNumberAndPagesTurned(savedInstanceState);

        currentOrientation = getScreenRotation();

        setStrategy();

        setupInitialPager();

        setOnSystemUiVisibilityChangeListener();

        hideSystemUI();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentPage", pageNumber);
        outState.putInt("pagesTurned", pagesTurned);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            destroyPager();
            currentOrientation = getScreenRotation();
            setupSinglePager();

        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            destroyPager();
            currentOrientation = getScreenRotation();
            setupDualPager();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        destroyPager();
        finish();
    }



    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (isLandscape(currentOrientation) && action == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_VOLUME_DOWN:
                    if (getScreenRotation() == Surface.ROTATION_90)
                        flipPageBackward(2);
                    else
                        flipPageForward(2);

                    vibrate(v, 100);
                    return true;

                case KeyEvent.KEYCODE_VOLUME_UP:
                    if (getScreenRotation() == Surface.ROTATION_90)
                        flipPageForward(2);
                    else
                        flipPageBackward(2);

                    vibrate(v, 100);
                    return true;
            }
        }

        return super.dispatchKeyEvent(event);
    }

    private void setupActionbar() {
        toolbar = findViewById(R.id.toolbar);
        ViewGroup.LayoutParams params = toolbar.getLayoutParams();
        params.height = params.height + getStatusBarHeight();
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_black_24dp);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.hide();
    }




    private void setPageNumberAndPagesTurned(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            pageNumber = receivedPageNumber;
            pagesTurned = 0;
        } else {
            pagesTurned = savedInstanceState.getInt("pagesTurned");
            pageNumber = savedInstanceState.getInt("currentPage");
        }
    }


    private void setupInitialPager() {
        singlePageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                pageNumber = singlePagerPositionToPageNumber(position);
                pagesTurned++;
                if (pagesTurned >= 8){
                    System.gc();
                    pagesTurned = 0;
                }
            }};

        dualPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
            super.onPageSelected(position);
                pageNumber = dualPagerPositionToPageNumber(position);
                pagesTurned += 2;
                if (pagesTurned >= 8){
                    System.gc();
                    pagesTurned = 0;
                }
            }
        };

        if (!isLandscape(currentOrientation) && !isForceDualPage) {
            setupSinglePager();
        } else {
            setupDualPager();
        }
    }

    private void setupSinglePager() {
        pager = findViewById(R.id.pager);
        pagerAdapter = new QuranPageAdapter(getSupportFragmentManager(), getLifecycle(), this, currentOrientation);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(1);
        pager.setCurrentItem(pageNumberToSinglePagerPosition(pageNumber), false);
        pager.registerOnPageChangeCallback(singlePageChangeCallback);
    }

    private void setupDualPager() {
        pager = findViewById(R.id.pager);
        pagerAdapter = new QuranPageAdapter(getSupportFragmentManager(), getLifecycle(), this, currentOrientation);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(1);
        pager.setCurrentItem(pageNumberToDualPagerPosition(pageNumber), false);
        pager.registerOnPageChangeCallback(dualPageChangeCallback);
        if (isForceDualPage)
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private void destroyPager() {
        if (isLandscape(currentOrientation))
            pager.unregisterOnPageChangeCallback(dualPageChangeCallback);
        else
            pager.unregisterOnPageChangeCallback(singlePageChangeCallback);
        pagerAdapter = null;
        pager = null;
        System.gc();
    }




    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private int getScreenRotation() {
        return ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
    }

    public void SystemUIListener(View view) {
        if (getWindow().getDecorView().getSystemUiVisibility() == (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN))
            hideSystemUI();
        else
            showSystemUI();
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    private void actionOnSystemUIChange(int visibility) {
        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
            getSupportActionBar().show();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        } else {
            getSupportActionBar().hide();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }
    }

    public static boolean isLandscape(int currentOrientation) {
        return currentOrientation == Surface.ROTATION_90 || currentOrientation == Surface.ROTATION_270;
    }

    private void vibrate(Vibrator v, int time) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(time, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(time);
        }
    }

    private void setOnSystemUiVisibilityChangeListener() {
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        actionOnSystemUIChange(visibility);
                    }
                });
    }

    private void setStrategy() {
        if (QuranSettings.getInstance().getMushafVersion(this) == QuranSettings.MADANI15LINE)
            strategy = new MadaniQuranActivityStrategy();
        else
            strategy = new Naskh13QuranActivityStrategy();
    }

    private int pageNumberToDualPagerPosition(int pageNumber) {
        return strategy.pageNumberToDualPagerPosition(pageNumber);
    }

    private int dualPagerPositionToPageNumber(int dualPagerPosition) {
        return strategy.dualPagerPositionToPageNumber(dualPagerPosition);
    }

    private int pageNumberToSinglePagerPosition(int pageNumber) {
        return strategy.pageNumberToSinglePagerPosition(pageNumber);
    }

    private int singlePagerPositionToPageNumber(int position) {
        return strategy.singlePagerPositionToPageNumber(position);
    }

    private void flipPageBackward(int pagesToFlip) {
        if (pageNumber - pagesToFlip >= strategy.minPage()) {
            pager.setCurrentItem(pageNumberToDualPagerPosition(pageNumber - pagesToFlip), isSmoothVolumeKeyNavigation);
        }
    }

    private void flipPageForward(int pagesToFlip) {
        if (pageNumber + pagesToFlip <= strategy.maxPage()) {
            pager.setCurrentItem(pageNumberToDualPagerPosition(pageNumber + pagesToFlip), isSmoothVolumeKeyNavigation);
        }
    }

}