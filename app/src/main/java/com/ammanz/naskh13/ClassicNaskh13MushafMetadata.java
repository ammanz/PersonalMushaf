package com.ammanz.naskh13;

import com.ammanz.personalmushaf.util.FileUtils;

public class ClassicNaskh13MushafMetadata extends ModernNaskh13MushafMetadata {

    public ClassicNaskh13MushafMetadata() {
        assetName = "classic_naskh_13_line";
        databasePath = FileUtils.ASSETSDIRECTORY + "/" + assetName + "/databases/ayahinfo_classicnaskh13line.db";
        name = "Classic 13 Line Naskh Mushaf";
        description = "Popular with huffadh in the Indian Subcontinent and South Africa.";
        previewDrawableIDs = new int[2];
        /*previewDrawableIDs[0] = R.drawable.modern_naskh_13_line_preview1;
        previewDrawableIDs[1] = R.drawable.modern_naskh_13_line_preview2;*/
    }
}
