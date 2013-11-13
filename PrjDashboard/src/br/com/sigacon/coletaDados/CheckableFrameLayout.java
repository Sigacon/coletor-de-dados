/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.sigacon.coletaDados;

import br.com.sigacon.prjdashboard.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.TextView;

public class CheckableFrameLayout extends FrameLayout implements Checkable {
    private boolean mChecked;
    public static final int[] CHECKED_STATE = {android.R.attr.state_checked};
    
    public CheckableFrameLayout(Context context) {
        super(context);
    }

    public CheckableFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public CheckableFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    @Override
	public void setChecked(boolean checked) {
        mChecked = checked;
        TextView txtEstrato = (TextView) findViewById(android.R.id.text1);
        if(checked){
        	setBackgroundDrawable(getResources().getDrawable(R.drawable.list_activated));
        	txtEstrato.setTextColor(getResources().getColor(R.color.white));
            
        }else{
        	setBackgroundDrawable(getResources().getDrawable(R.drawable.list_arrowlist_activate));
        	txtEstrato.setTextColor(getResources().getColor(R.color.black));
        }
    	refreshDrawableState();	
	}

	@Override
	protected int[] onCreateDrawableState(int extraSpace) {
        int[] states =  super.onCreateDrawableState(extraSpace + 1);
        if (mChecked){
            mergeDrawableStates(states, CHECKED_STATE);
        }
        return states;
	}

	public boolean isChecked() {
        return mChecked;	
    }

    public void toggle() {
    	mChecked = !mChecked;
        refreshDrawableState();;
    }

}
