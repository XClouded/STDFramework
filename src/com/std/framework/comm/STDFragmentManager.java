package com.std.framework.comm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class STDFragmentManager {
	private static STDFragmentManager instance;
	private FragmentManager mFragmentManager;
	
	private STDFragmentManager(){
	}
	
	/**
	 * 
	 * 描      述 ：实例化一个布局块管理器
	 * 创建日期 ： 2014-4-3
	 * 作      者 ： lx
	 * 修改日期 ： 
	 * 修  改 者 ：
	 * @version： 1.0
	 * @param fragmentActivity
	 * @return
	 *
	 */
	public static STDFragmentManager getInstance(FragmentActivity fragmentActivity){
		if(instance == null){
			synchronized(STDFragmentManager.class){
				if(instance == null)
					instance = new STDFragmentManager();
			}
		}
		instance.mFragmentManager = fragmentActivity.getSupportFragmentManager();
		return instance;
	}
	
	/**
	 * 
	 * 描      述 ：添加布局块
	 * 创建日期 ： 2014-4-3
	 * 作      者 ： lx
	 * 修改日期 ： 
	 * 修  改 者 ：
	 * @version： 1.0
	 * @param vId 布局块对应的布局Id
	 * @param newFragment 布局块
	 *
	 */
	public void add(int vId,Fragment newFragment){
		mFragmentManager.beginTransaction().add(vId, newFragment).commit();
	}
	
	/**
	 * 
	 * 描      述 ：将布局块添加到后退栈中
	 * 创建日期 ： 2014-4-22
	 * 作      者 ： lx
	 * 修改日期 ： 
	 * 修  改 者 ：
	 * @version： 1.0
	 * @param vId
	 * @param newFragment
	 *
	 */
	public void addToBack(int vId,Fragment newFragment){
		mFragmentManager.beginTransaction()
		.add(vId, newFragment)
		.addToBackStack(null)
		.commit();
	}
	
	/**
	 * 
	 * 描      述 ：替换原有的布局块
	 * 创建日期 ： 2014-4-3
	 * 作      者 ： lx
	 * 修改日期 ： 
	 * 修  改 者 ：
	 * @version： 1.0
	 * @param vId
	 * @param newFragment
	 *
	 */
	public void replace(int vId,Fragment newFragment){
		mFragmentManager.beginTransaction().replace(vId, newFragment).commit();
	}
	
	/**
	 * 
	 * 描      述 ：替换布局块并添加到后退栈中
	 * 创建日期 ： 2014-4-30
	 * 作      者 ： lx
	 * 修改日期 ： 
	 * 修  改 者 ：
	 * @version： 1.0
	 * @param vId
	 * @param newFragment
	 *
	 */
	public void replaceToBack(int vId,Fragment newFragment){
		mFragmentManager.beginTransaction()
		.replace(vId, newFragment)
		.addToBackStack(null)
		.commit();
	}
	
	public void remove(Fragment fragment){
		mFragmentManager.beginTransaction()
		.remove(fragment)
		.commit();
		mFragmentManager.popBackStack();
	}
}
