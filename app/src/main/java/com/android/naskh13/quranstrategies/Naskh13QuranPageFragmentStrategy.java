package com.android.naskh13.quranstrategies;

import com.android.personalmushaf.model.PageData;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.strategies.abstractquranstrategies.AbstractQuranPageFragmentStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranPageFragmentStrategy;


public class Naskh13QuranPageFragmentStrategy extends AbstractQuranPageFragmentStrategy implements QuranPageFragmentStrategy {

    public Naskh13QuranPageFragmentStrategy(MushafMetadata mushafMetadata) {
        super(mushafMetadata);
    }

    public String getPagePath(int pageNumber) {
        return super.getPagePath(pageNumber);
    }

    public PageData getPageData(int pageNumber) {
        return super.getPageData(pageNumber);
    }
}
