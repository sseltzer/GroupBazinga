/*
 * Group: BAZINGA!
 * @author: Gustavo Maturana
 * Date: 11/07/13
 * File Name: AaboutCalss.java
 */

package com.example.grocerylist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
 
public class AboutClass extends Activity {
 

	Button okBtn;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);
        
 
        okBtn = (Button)findViewById(R.id.okBtn);
        
        okBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(AboutClass.this, MainActivity.class);
				startActivity(i);
				finish();
				
			}
		});

    }
 
}
