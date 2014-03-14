package com.pfives.coinraiser.views;

import com.pfives.coinraiser.R;
import com.pfives.coinraiser.utils.FontManager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class TextFontView extends TextView {

	public TextFontView(Context context) {
		this(context, null);
	}

	public TextFontView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TextFontView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setupAttributes(attrs);
	}

	private void setupAttributes(AttributeSet attrs) {
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TextFontView);
		String customFont = a.getString(R.styleable.TextFontView_customFont);
		if (!isInEditMode() && customFont != null) {
			setCustomFont(getContext(), customFont);
		}
		a.recycle();
	}
	
	public boolean setCustomFont(Context context, String asset) {
		Typeface typeface = FontManager.getTypeface(context, asset);
		if (typeface != null) {
			setTypeface(typeface);			
		}
		return (typeface != null);
	}
}
