package com.pfives.coinraiser.utils;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;
/*
 * Craziness http://code.google.com/p/android/issues/detail?id=9904#c7
 */
public class FontManager {

	static Map<String,Typeface> typefaceAssets = new HashMap<String,Typeface>();
	
	public static Typeface getTypeface(Context context, String asset) {
		Typeface typeface = typefaceAssets.get(asset);
		if (typeface == null) {
			typeface = Typeface.createFromAsset(context.getAssets(), asset);
			if (typeface !=  null) {
				typefaceAssets.put(asset, typeface);
			}
		}
		return typeface;
	}
}
