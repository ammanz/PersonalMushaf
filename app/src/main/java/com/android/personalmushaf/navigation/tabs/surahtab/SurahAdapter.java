package com.android.personalmushaf.navigation.tabs.surahtab;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andexert.library.RippleView;
import com.android.personalmushaf.QuranActivity;
import com.android.personalmushaf.R;

public class SurahAdapter extends RecyclerView.Adapter<SurahAdapter.SurahViewHolder> {
    private int[][] surahInfo;
    private int[] surahPageNumbers;
    private String[] prefixes;
    private double[] surahLengthsInJuz;

    public static class SurahViewHolder extends RecyclerView.ViewHolder {
        public RippleView rippleView;
        public SurahViewHolder(RippleView v) {
            super(v);
            rippleView = v;
        }
    }

    public SurahAdapter(int[][] surahInfo, int[] surahPageNumbers, String[] prefixes, double[] surahLengthsInJuz) {
        this.surahInfo = surahInfo;
        this.surahPageNumbers = surahPageNumbers;
        this.prefixes = prefixes;
        this.surahLengthsInJuz = surahLengthsInJuz;
    }

    @Override
    public SurahViewHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType) {
        // create a new view
        RippleView v = (RippleView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_surah, parent, false);

        SurahViewHolder vh = new SurahViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final @NonNull SurahViewHolder holder, final int position) {
        LinearLayout layout = (LinearLayout) holder.rippleView.getChildAt(0);

        TextView surahNumber = (TextView) layout.getChildAt(0);
        TextView surahLength = (TextView) ((LinearLayout) layout.getChildAt(1)).getChildAt(0);
        TextView surahPageNumber = (TextView) ((LinearLayout) ((LinearLayout) layout.getChildAt(1)).getChildAt(1)).getChildAt(1);
        TextView surahOrigin = (TextView) ((LinearLayout) ((LinearLayout) layout.getChildAt(1)).getChildAt(1)).getChildAt(0);
        TextView surahStart = (TextView) layout.getChildAt(2);


        String origin = surahInfo[position][2] == 1 ? "مكي" : "مدني";

        final int pageNumber = surahPageNumbers[position];

        surahStart.setText(prefixes[position]);
        surahLength.setText(String.format("%.2f", surahLengthsInJuz[position]) + " pages");
        surahPageNumber.setText(Integer.toString(pageNumber));
        surahNumber.setText(layout.getResources().getStringArray(R.array.three_digit_arabic_numerals)[surahInfo[position][0] - 1]);
        surahOrigin.setText(origin);

        alternateBackgroundColor(layout, position);

        holder.rippleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RippleView rippleView = (RippleView) v;
                rippleView.setRippleDuration(75);
                rippleView.setFrameRate(10);


                final Intent goToSurah = new Intent(rippleView.getContext(), QuranActivity.class);

                goToSurah.putExtra("new page number", pageNumber);

                goToSurah.putExtra("from", "NavigationActivity");

                rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        rippleView.getContext().startActivity(goToSurah);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return surahInfo.length;
    }

    private void alternateBackgroundColor(LinearLayout textView, int position) {
        if (position % 2 == 0)
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorPrimary, textView.getContext().getTheme()));
        else
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorAccent, textView.getContext().getTheme()));
    }
}
