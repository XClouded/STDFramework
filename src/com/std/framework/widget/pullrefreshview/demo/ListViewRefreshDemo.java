package com.std.framework.widget.pullrefreshview.demo;

import com.std.framework.widget.pullrefreshview.PullRefreshView;
import com.std.framework.widget.pullrefreshview.PullRefreshView.OnFooterRefreshListener;
import com.std.framework.widget.pullrefreshview.PullRefreshView.OnHeaderRefreshListener;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.ListView;

/**LRefreshView����������ˢ�¿ؼ�������ʾ
 * 
 * gridView,ScrollView ʹ�÷�������
 * @author Administrator
 *
 */
class ListViewRefreshDemo extends Activity implements OnHeaderRefreshListener,OnFooterRefreshListener{
	private ListView mListView;
	private PullRefreshView refreshView ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.listviewrefreshdemo);
//		mListView = (ListView) findViewById(R.id.lst);
//		refreshView = (LRefreshView) findViewById(R.id.main_pull_refresh_view);
//		refreshView.setOnFooterRefreshListener(this);
//		refreshView.setOnHeaderRefreshListener(this);
//		DataAdapter dataAdapter = new DataAdapter(this);
//		mListView.setAdapter(dataAdapter);
	}

	/**
	 * ����ˢ��
	 */
	@Override
	public void onFooterRefresh(PullRefreshView view) {
		/**
		 *  �˴�ִ������ˢ���û���������
		 *  1.
		 *  2.
		 *  ����������������������
		 */
		refreshView.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				//������ɽ���ˢ��
				refreshView.finishFooterRefresh();
			}
		}, 500);
	}

	/**
	 * ����ˢ��
	 */
	@Override
	public void onHeaderRefresh(PullRefreshView view) {
		/**
		 *  �˴�ִ������ˢ���û���������
		 *  1.
		 *  2.
		 *  ����������������������
		 */
		refreshView.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Time time = new Time();
				time.setToNow();
				//������ɽ���ˢ��
				refreshView.finishHeaderRefresh("��������" + time.hour +":" + time.minute);
			}
		}, 500);
	}
	
}
