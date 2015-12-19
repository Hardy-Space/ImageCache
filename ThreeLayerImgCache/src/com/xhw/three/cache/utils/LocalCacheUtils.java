package com.xhw.three.cache.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

/**
 * ���ػ���
 * 
 * @author admin
 *
 */
public class LocalCacheUtils
{

	private final static String LOCAL_CACHE_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/cachedemo";

	/**
	 * �ж�SD���Ƿ����
	 * 
	 * @return
	 */
	private boolean isSdcardCanUse()
	{
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
		{
			return true;
		}
		return false;
	}

	/**
	 * ����ͼƬ������
	 * 
	 * @throws SdcardNotFoundException
	 */
	public void setBitmapToLocal(String url, Bitmap bitmap)
	{

		if (!isSdcardCanUse())
		{
			return;
		}
		// ��֤Ŀ¼����
		File dir = new File(LOCAL_CACHE_PATH);
		if (!dir.exists())
		{
			dir.mkdirs();
		}

		FileOutputStream fos = null;
		try
		{
			fos = new FileOutputStream(new File(dir, MD5Utils.getTextMd5(url)));
			bitmap.compress(CompressFormat.JPEG, 100, fos);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} finally
		{
			if (fos != null)
			{
				try
				{
					fos.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				} finally
				{
					fos = null;
				}
			}
		}
	}

	/**
	 * �ӱ��ػ�ȡͼƬBitmap
	 * 
	 * @return
	 * @throws SdcardNotFoundException
	 */
	public Bitmap getBitmapFromLocal(String url)
	{
		if (!isSdcardCanUse())
		{
			return null;
		}

		String fileName = MD5Utils.getTextMd5(url);
		File file = new File(LOCAL_CACHE_PATH, fileName);
		if (file.exists())
		{
			FileInputStream fis = null;
			try
			{
				fis = new FileInputStream(file);
				System.out.println("�ӱ��ػ�ȡͼƬ...");
				return BitmapFactory.decodeStream(fis);
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} finally
			{
				try
				{
					fis.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
