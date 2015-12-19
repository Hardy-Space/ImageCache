package com.xhw.three.cache.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class NetCacheUtils
{

	private LocalCacheUtils localCacheUtils;
	private MemoryCacheUtils memoryCacheUtils;

	public NetCacheUtils(LocalCacheUtils localCacheUtils,
			MemoryCacheUtils memoryCacheUtils)
	{
		this.localCacheUtils = localCacheUtils;
		this.memoryCacheUtils = memoryCacheUtils;
	}

	/**
	 * �������ȡͼƬ
	 * 
	 * @param view
	 * @param url
	 */
	public void getBitmapFromNet(ImageView view, String url)
	{
		new BitmapTask().execute(view, url);// ����AsyncTask,��������doInBackground�л�ȡ
	}

	/**
	 * ����ͼƬ
	 * 
	 * @param downloadUrl
	 * @return
	 */
	private Bitmap downloadBitmap(String downloadUrl)
	{
		HttpURLConnection conn = null;
		try
		{
			URL url = new URL(downloadUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			conn.connect();
			int responseCode = conn.getResponseCode();
			if (responseCode == 200)
			{
				InputStream is = conn.getInputStream();
				// ͼƬѹ��
//				BitmapFactory.Options options = new Options();
//				options.inSampleSize = 2;// ���ѹ��Ϊԭ���Ķ���֮һ
//				options.inPreferredConfig = Bitmap.Config.RGB_565;// ����ͼƬ��ʽ
//				Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
				
				//��ѹ��д��
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				return bitmap;
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			conn.disconnect();
		}
		return null;
	}

	class BitmapTask extends AsyncTask<Object, Void, Bitmap>
	{
		private ImageView view;
		private String url;

		/**
		 * ��̨��ʱ�������ڴ�ִ��,�����߳�ִ��
		 */
		@Override
		protected Bitmap doInBackground(Object... params)
		{
			view = (ImageView) params[0];
			url = (String) params[1];
			view.setTag(url);// ���ؼ���url��
			return downloadBitmap(url);
		}

		// ���½��ȣ������̵߳���
		@Override
		protected void onProgressUpdate(Void... values)
		{
			super.onProgressUpdate(values);
		}

		// ��ʱ����������ִ�д˷����������̵߳���
		@Override
		protected void onPostExecute(Bitmap result)
		{
			if (result != null)
			{
				String bindUrl = (String) view.getTag();
				if (url.equals(bindUrl))// ��֤ͼƬ���ø�����ȷ�Ŀؼ�
				{
					view.setImageBitmap(result);
					System.out.println("�������ȡͼƬ...");
					// �����ڱ���
					localCacheUtils.setBitmapToLocal(url, result);
					// �ڴ汣��һ��
					memoryCacheUtils.setBitmapToMemory(url, result);
				}
			}
		}
	}

	// class BitmapTask extends AsyncTask<X, Y, Z>
	// {
	// /**
	// * ��̨��ʱ�������ڴ�ִ��,�����߳�ִ��
	// */
	// @Override
	// protected Z doInBackground(X... params)
	// {
	// return null;
	// }
	//
	// //���½��ȣ������̵߳���
	// @Override
	// protected void onProgressUpdate(Y... values)
	// {
	// super.onProgressUpdate(values);
	// }
	//
	// //��ʱ����������ִ�д˷����������̵߳���
	// @Override
	// protected void onPostExecute(Z result)
	// {
	// super.onPostExecute(result);
	// }
	// }

}
