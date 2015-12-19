package com.xhw.three.cache.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class MyBitmapUtils
{
	private NetCacheUtils netCacheUtils;
	private LocalCacheUtils localCacheUtils;
	private MemoryCacheUtils memoryCacheUtils;
	
	
	public MyBitmapUtils()
	{
		memoryCacheUtils=new MemoryCacheUtils();
		localCacheUtils=new LocalCacheUtils();
		netCacheUtils=new NetCacheUtils(localCacheUtils,memoryCacheUtils);
	}
	
	public void display(ImageView view,String url)
	{
		//���ڴ��,���ޣ�����һ��
		Bitmap bitmapFromMemory = memoryCacheUtils.getBitmapFromMemory(url);
		if(bitmapFromMemory!=null)
		{
			view.setImageBitmap(bitmapFromMemory);
			System.out.println("���ڴ��ȡͼƬ...");
			return;
		}
		
		
		//�ӱ��ض�(sd��,�ֻ��洢),���ޣ�����һ��
		Bitmap bitmapFromLocal = localCacheUtils.getBitmapFromLocal(url);
		if(bitmapFromLocal!=null)
		{
			view.setImageBitmap(bitmapFromLocal);
			System.out.println("�ӱ��ض�ȡͼƬ...");
			//�ڴ�Ҳ����һ��
			memoryCacheUtils.setBitmapToMemory(url, bitmapFromLocal);
			return;
		}
		//�������
		netCacheUtils.getBitmapFromNet(view,url);
	}
}
