package com.ammanz.personalmushaf.mushafmetadata.naskh13;

import com.ammanz.personalmushaf.QuranSettings;
import com.ammanz.personalmushaf.R;
import com.ammanz.personalmushaf.mushafmetadata.MushafMetadata;
import com.ammanz.personalmushaf.navigation.NavigationData;

public class ModernNaskh13CroppedMushafMetadata extends MushafMetadata {

    public ModernNaskh13CroppedMushafMetadata() {
        assetPath = quranSettings.getMushafLocation(QuranSettings.MODERNNASKH13CROPPED);
        id = "modernnaskh13cropped";
        name = "Modern 13 Line Naskh Mushaf (Cropped)";
        description = "Popular with huffadh in the Indian Subcontinent and South Africa.";
        previewDrawableIDs = new int[2];
        previewDrawableIDs[0] = R.drawable.modernnaskh13cropped_preview1;
        previewDrawableIDs[1] = R.drawable.modernnaskh13cropped_preview2;

        minPage = 2;
        maxPage = 848;
        danglingDualPage = 423;
        doesAyahSpanPages = true;
        downloadSize = 85.7;
    }


    public NavigationData getNavigationData() {
        if (navigationData == null) {
            navigationData = new Naskh13NavigationData();
        }

        return navigationData;
    }

    public boolean getShouldDoRuku(int landmarkSystem) {
        return landmarkSystem == QuranSettings.DEFAULTLANDMARKSYSTEM || landmarkSystem == QuranSettings.RUKU;
    }
}
