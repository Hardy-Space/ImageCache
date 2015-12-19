package com.xhw.three.cache.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * �ڴ滺��
 * 
 * @author admin
 *
 */
public class MemoryCacheUtils
{
	//android2.3�Ժ������ûᱻ��ǰ���գ���ʹ�ڴ湻��
	//private Map<String, SoftReference<Bitmap>> memoryCache = new HashMap<String, SoftReference<Bitmap>>();

	//Lru Least recently use �������ʹ���㷨
	private LruCache<String, Bitmap> memoryCache;
	
	public MemoryCacheUtils()
	{
		//��ȡ�ֻ������Ӧ�õ�����ڴ棬ģ������16mb
		long maxMemory = Runtime.getRuntime().maxMemory();
		//LruCacheҪָ������ڴ��С�������泬���˷�ֵ�����Զ�����
		memoryCache=new LruCache<String, Bitmap>((int)(maxMemory/8)){
			@Override
			protected int sizeOf(String key, Bitmap value)
			{
				//��ȡbitmapռ���ڴ��С
				int size=value.getRowBytes()*value.getHeight();
				return size;
			}
		};
	}
	
	public void setBitmapToMemory(String url, Bitmap bitmap)
	{
//		SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(bitmap);
//		memoryCache.put(url, softReference);
		
		memoryCache.put(url, bitmap);
	}

	public Bitmap getBitmapFromMemory(String url)
	{
//		SoftReference<Bitmap> softReference = memoryCache.get(url);
//		if (softReference != null)
//		{
//			return softReference.get();
//		}
//		return null;
		return memoryCache.get(url);
	}
}
