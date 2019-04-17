 package com.example.personalmushaf;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;

import android.view.WindowManager;


import com.example.personalmushaf.navigation.NavigationActivity;

import com.example.personalmushaf.navigation.ThirteenLinePageData;
import com.example.personalmushaf.navigation.snappositionchangelistener.OnSnapPositionChangeListener;
import com.example.personalmushaf.navigation.snappositionchangelistener.RecyclerViewExtKt;
import com.example.personalmushaf.navigation.snappositionchangelistener.SnapOnScrollListener;
import com.example.personalmushaf.thirteenlinepage.ThirteenLineAdapter;
import com.example.personalmushaf.thirteenlinepage.ThirteenLineDualAdapter;


 public class QuranActivity extends AppCompatActivity {


	 private RecyclerView pager;
	 private RecyclerView dualPager;
	 private ThirteenLineAdapter adapter;
	 private ThirteenLineDualAdapter dualAdapter;
	 private LinearLayoutManager layoutManager;
	 private Toolbar toolbar;
	 private String currentOrientation;
	 private int pageNumber;
	 private int pagesTurned = 0;
	 private int receivedPageNumber;

     @Override

	 protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran);

        setToolbar();

        Intent activityThatCalled = getIntent();
        String from = activityThatCalled.getStringExtra("from");

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        currentOrientation = getScreenOrientation(this);
        receivedPageNumber = activityThatCalled.getIntExtra("new page number", 2);

        setPage(from, savedInstanceState);


         if (currentOrientation.equals("portrait")) {
             setSinglePagePager(layoutManager);
         }
        else {
            setDualPagePager(layoutManager);
         }
	}



     @Override
     public void onSaveInstanceState(Bundle outState) {
         super.onSaveInstanceState(outState);
         outState.putInt("currentPage", pageNumber);
         outState.putInt("pagesTurned", pagesTurned);
     }

     public  void setImmersive(View view) {
		 ActionBar actionBar = getSupportActionBar();
		 if (actionBar.isShowing()) {
			 actionBar.hide();
			 hideSystemUI();
		 }
		 else {
			 actionBar.show();
             showSystemUI();
		 }
	}



	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
		 int id = item.getItemId();

		 if (id == android.R.id.home) {

			 Intent goToNavigation = new Intent(getBaseContext(), NavigationActivity.class);

			 startActivity(goToNavigation);

			 return true;
		 }

		 return super.onOptionsItemSelected(item);
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


     public String getScreenOrientation(Context context){
		 final int screenOrientation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
		 switch (screenOrientation) {
			 case Surface.ROTATION_0:
				 return "portrait";
			 case Surface.ROTATION_90:
				 return "landscape";
			 case Surface.ROTATION_180:
				 return "portrait";
			 default:
				 return "landscape";
		 }
	 }

	 private void setToolbar() {
         toolbar = findViewById(R.id.toolbar);

         setSupportActionBar(toolbar);
         ActionBar actionBar = getSupportActionBar();
         setImmersive(new View(this));

         actionBar.setDisplayHomeAsUpEnabled(true);
         actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
         actionBar.setDisplayShowTitleEnabled(false);
         actionBar.setHomeButtonEnabled(true);
     }


	private void setPage(String from, Bundle savedInstanceState) {
        if (from != null) {
            if (savedInstanceState == null)
                pageNumber = receivedPageNumber;
            else if (savedInstanceState.getInt("pagesTurned") != 0) {
                pagesTurned = savedInstanceState.getInt("pagesTurned");
                pageNumber = savedInstanceState.getInt("currentPage");
            }
            else
                pageNumber = receivedPageNumber;
        }
        else if (pageNumber == 0 && savedInstanceState != null) {
            pageNumber = savedInstanceState.getInt("currentPage");
        }
        else
            pageNumber = 2;
    }

    private void setSinglePagePager(RecyclerView.LayoutManager layoutManager) {
        pager = findViewById(R.id.pager);
        pager.setHasFixedSize(true);
        adapter = new ThirteenLineAdapter(ThirteenLinePageData.getInstance().singlePageSets);
        pager.setLayoutManager(layoutManager);
        layoutManager.scrollToPosition(pageNumber-1);
        layoutManager.setItemPrefetchEnabled(true);
        pager.setAdapter(adapter);
        pager.setItemViewCacheSize(10);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        RecyclerViewExtKt.attachSnapHelperWithListener(pager, snapHelper, SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL_STATE_IDLE, new OnSnapPositionChangeListener() {
            @Override
            public void onSnapPositionChange(int position) {
                pageNumber = position + 1;
                pagesTurned++;
            }
        });
    }

    private void setDualPagePager(RecyclerView.LayoutManager layoutManager) {
        int dualPageNumber;
        if (pageNumber % 2 == 0)
            dualPageNumber = pageNumber/2;
        else
            dualPageNumber = (pageNumber - 1)/2;

        dualPager = findViewById(R.id.dualpager);
        dualPager.setHasFixedSize(true);
        dualAdapter = new ThirteenLineDualAdapter(ThirteenLinePageData.getInstance().dualPageSets);
        dualPager.setLayoutManager(layoutManager);
        layoutManager.scrollToPosition(dualPageNumber);
        layoutManager.setItemPrefetchEnabled(true);
        dualPager.setAdapter(dualAdapter);
        dualPager.setItemViewCacheSize(10);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        RecyclerViewExtKt.attachSnapHelperWithListener(dualPager, snapHelper, SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL_STATE_IDLE, new OnSnapPositionChangeListener() {
            @Override
            public void onSnapPositionChange(int position) {
                pageNumber = 2*position;
                pagesTurned = pagesTurned + 2;
            }
        });


    }

     @Override
     public boolean dispatchKeyEvent(KeyEvent event) {
         int dualPageNumber;
         int action = event.getAction();
         int keyCode = event.getKeyCode();
         switch (keyCode) {
             case KeyEvent.KEYCODE_VOLUME_DOWN:
                 if (action == KeyEvent.ACTION_DOWN && currentOrientation == "landscape" && pageNumber >= 2) {
                     pageNumber--;

                     if (pageNumber != 1)
                        pageNumber--;

                     if (pageNumber % 2 == 0)
                         dualPageNumber = pageNumber/2;
                     else
                         dualPageNumber = (pageNumber - 1)/2;
                     layoutManager.smoothScrollToPosition(dualPager, new RecyclerView.State(), dualPageNumber);
                     return true;
                 } else
                     return super.dispatchKeyEvent(event);
             case KeyEvent.KEYCODE_VOLUME_UP:
                 if (action == KeyEvent.ACTION_DOWN && currentOrientation == "landscape" && pageNumber <= 847) {
                     pageNumber++;

                     if (pageNumber != 848)
                        pageNumber++;
                     if (pageNumber % 2 == 0)
                         dualPageNumber = pageNumber/2;
                     else
                         dualPageNumber = (pageNumber - 1)/2;
                     layoutManager.smoothScrollToPosition(dualPager, new RecyclerView.State(), dualPageNumber);
                 return true;
                 }
                 else
                     return super.dispatchKeyEvent(event);
             default:
                 return super.dispatchKeyEvent(event);
         }
     }

}
