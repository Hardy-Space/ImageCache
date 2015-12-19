package com.xhw.three.cache;

import com.xhw.three.cache.utils.MyBitmapUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends Activity
{

	private ImageView iv_pic;

	private final static String IMG_URL="http://www.juzi2.com/uploads/allimg/130619/1_130619193157_1.jpg";

	private MyBitmapUtils bitmapUtils;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		iv_pic = (ImageView) this.findViewById(R.id.iv_pic);
		//����BitmapUtils���÷�
		bitmapUtils = new MyBitmapUtils();
	}
	
	//onStart�����˷���
	@Override
	protected void onStart()
	{
		super.onStart();
		bitmapUtils.display(iv_pic, IMG_URL);
	}

}
