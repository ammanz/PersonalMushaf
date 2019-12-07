package com.example.personalmushaf.navigation.tabs.rukucontenttab;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.andexert.library.RippleView;
import com.example.personalmushaf.QuranActivity;
import com.example.personalmushaf.R;
import com.example.personalmushaf.navigation.QuranConstants;

public class RukuContentAdapter extends RecyclerView.Adapter<RukuContentAdapter.JuzViewHolder> {
    private String[][] dataSet;

    public static class JuzViewHolder extends RecyclerView.ViewHolder {
        public RippleView rippleView;
        public JuzViewHolder(RippleView v) {
            super(v);
            rippleView = v;
        }
    }

    public RukuContentAdapter(String[][] myDataSet) {
        dataSet = myDataSet;
    }

    @Override
    public JuzViewHolder onCreateViewHolder(ViewGroup parent,
                                            int viewType) {
        RippleView v = (RippleView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_ruku_content, parent, false);


        JuzViewHolder vh = new JuzViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final JuzViewHolder holder, final int position) {

        LinearLayout layout = (LinearLayout) holder.rippleView.getChildAt(0);

        TextView rukuNumber = (TextView) layout.getChildAt(0);
        TextView rukuPageNumber = (TextView) ((LinearLayout) layout.getChildAt(1)).getChildAt(1);
        TextView ayahRange = (TextView) ((LinearLayout) layout.getChildAt(1)).getChildAt(0);
        TextView rukuPrefix = (TextView) layout.getChildAt(2);

        String[] rukuInfo = dataSet[position];

        rukuNumber.setText(QuranConstants.arabicNumerals[position]);
        ayahRange.setText(rukuInfo[1] + ":" + rukuInfo[2]);
        rukuPageNumber.setText(rukuInfo[0]);
        rukuPrefix.setText(rukuInfo[3]);

        alternateBackgroundColor(layout, position);

        holder.rippleView.setOnClickListener(new RippleView.OnClickListener() {
            @Override
            public void onClick(final View view) {
                RippleView rippleView = (RippleView) view;
                rippleView.setRippleDuration(75);
                rippleView.setFrameRate(10);
                rukuContentToPage(rippleView, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.length;
    }


    private void alternateBackgroundColor(LinearLayout textView, int position) {
        if (position % 2 == 0)
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorPrimary, textView.getContext().getTheme()));
        else
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorAccent, textView.getContext().getTheme()));
    }



    private void rukuContentToPage(RippleView rippleView, int selectedRuku) {
        final Intent goToRukuAyah = new Intent(rippleView.getContext(), QuranActivity.class);

        goToRukuAyah.putExtra("from", "NavigationActivity");

        int pageNumber = Integer.valueOf(dataSet[selectedRuku][0]);

        goToRukuAyah.putExtra("new page number", pageNumber);

        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                rippleView.getContext().startActivity(goToRukuAyah);
            }
        });
    }

}

