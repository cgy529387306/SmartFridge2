package com.mb.smart.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.io.File;


/**
 * Activiy 跳转
 * @ author:cgy
 */
public class NavigationHelper {

	private final static String JDShopId = "74489";
	private final static String JD_URL_WEB = "https://mall.jd.com/index-1000103102.html";
	private final static String JD_URL_NATIVE ="openApp.jdMobile://virtual?params={\"category\":\"jump\",\"des\":\"jshopMain\",\"shopId\":\""+JDShopId+"\",\"sourceType\":\"M_sourceFrom\",\"sourceValue\":\"dp\"}";


	private final static String TMShopId = "107412780";
	private final static String TM_URL_WEB = "https://qixuanqcyp.tmall.com/?spm=a220o.1000855.w5001-17297607168.3.f7a21c8cWFM2AP&scene=taobao_shop";
	private final static String TM_URL_NATIVE = "tmall://page.tm/shop?shopId="+TMShopId;

	private final static String mJDMall = "com.jingdong.app.mall";
	private final static String mTMMall = "com.tmall.wireless";
	
	public static final void startActivity(Activity act, Class<?> toActivity, Bundle bundle, boolean finish) {
		Intent intent = new Intent(act, toActivity);
		if(bundle != null) {
			intent.putExtras(bundle);
		}
		act.startActivity(intent);
		if (finish){
			act.finish();
		}
	}
	
	
	public static final void startActivityForResult(Activity act, Class<?> toActivity, Bundle bundle, int requestCode) {
		Intent intent = new Intent(act, toActivity);
		if(bundle != null) {
			intent.putExtras(bundle);
		}
		
		act.startActivityForResult(intent, requestCode);
	}
	
	
	public static final void finish(Activity act, int resultCode, Intent intent){
		if(intent != null) {
			act.setResult(resultCode);
		}
		else {
			act.setResult(resultCode, intent);
		}
		
		act.finish();
	}

	/**
	 * 判断是否安装目标应用
	 *
	 * @param packageName 目标应用安装后的包名
	 * @return 是否已安装目标应用
	 */
	private static boolean isInstallByread(String packageName) {
		return new File("/data/data/" + packageName).exists();
	}

	/**
	 * 打开天猫客户端
	 * @param context
	 */
	public static void openTM(Context context){
		try {
			if (isInstallByread(mTMMall)) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(TM_URL_NATIVE));
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			} else {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(TM_URL_WEB));
				context.startActivity(intent);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 打开京东客户端
	 * @param context
	 */
	public static void openJD(Context context){
		try {
			if (isInstallByread(mJDMall)) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(JD_URL_NATIVE));
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			} else {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(JD_URL_WEB));
				context.startActivity(intent);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
