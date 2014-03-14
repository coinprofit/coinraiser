package com.pfives.coinraiser.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class SquareRelativeLayout extends RelativeLayout {
	
	public SquareRelativeLayout(Context context){
		this(context, null);
	}
	
	public SquareRelativeLayout(Context context, AttributeSet attrs){
		this(context, attrs, 0);
	}
	
	public SquareRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {		
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}

}