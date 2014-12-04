package com.std.framework.widget.dialog;

import java.util.Calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.std.framework.R;

public class DateTimeDialog extends AlertDialog {
	private static final int S_DATEDIALOG = 0;// ���ڿ�
	private static final int S_TIMEDIALOG = 1;// ʱ���
	private static final int S_DATETIMEDIALOG = 2;// ��Ͽ�

	private DatePicker mDatePicker;
	private TimePicker mTimePicker;
	private String mTitleTxt;
	private TextView mTitleView;
	private int dialog = S_DATEDIALOG;
	private Calendar assigDate; // Ԥ��ʱ��
	private onCallBackListener onCallBackListener; // ����ѡ��ص��ӿ�

	public DateTimeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public DateTimeDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	public DateTimeDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.datetimepicker);
		mDatePicker = (DatePicker) findViewById(R.id.dp_datePicker);
		mTimePicker = (TimePicker) findViewById(R.id.tp_timePicker);
		mTitleView = (TextView) findViewById(R.id.tv_title);
		mTitleView.setText(mTitleTxt);
		mDatePicker.setDate(assigDate);
		mTimePicker.setDate(assigDate);
		Button positivebButton = (Button) findViewById(R.id.btn_positive);
		Button negativeButton = (Button) findViewById(R.id.btn_close);
		positivebButton.setOnClickListener(positiveClickListener);
		negativeButton.setOnClickListener(negativeClickListener);

		switch (dialog) {
			case S_DATEDIALOG:
				mDatePicker.setVisibility(View.VISIBLE);
				mTimePicker.setVisibility(View.GONE);
				break;
			case S_TIMEDIALOG:
				mDatePicker.setVisibility(View.GONE);
				mTimePicker.setVisibility(View.VISIBLE);
				break;
			case S_DATETIMEDIALOG:
				mDatePicker.setVisibility(View.VISIBLE);
				mTimePicker.setVisibility(View.VISIBLE);
				break;
		}
	}

	private View.OnClickListener positiveClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Calendar calendar = Calendar.getInstance();
			calendar.set(mDatePicker.getYear(), mDatePicker.getMonth(), mDatePicker.getDay(), mTimePicker.getCalendar()
					.get(Calendar.HOUR_OF_DAY), mTimePicker.getCalendar().get(Calendar.MINUTE));
			onCallBackListener.onCallBack(calendar);
			dismiss();
		}
	};

	private View.OnClickListener negativeClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			dismiss();
		}
	};

	public static DateTimeDialog showDateDialog(Context context, onCallBackListener onCallBackListener) {
		DateTimeDialog dateDialog = new DateTimeDialog(context);
		dateDialog.dialog = S_DATEDIALOG;
		dateDialog.mTitleTxt = "ѡ������";
		dateDialog.onCallBackListener = onCallBackListener;
		dateDialog.show();
		dateDialog.ajustScreen();
		return dateDialog;
	}

	public static DateTimeDialog showDateDialog(Context context, Calendar date, onCallBackListener onCallBackListener) {
		DateTimeDialog dateDialog = new DateTimeDialog(context);
		dateDialog.dialog = S_DATEDIALOG;
		dateDialog.assigDate = date;
		dateDialog.mTitleTxt = "ѡ������";
		dateDialog.onCallBackListener = onCallBackListener;
		dateDialog.show();
		dateDialog.ajustScreen();
		return dateDialog;
	}

	public static DateTimeDialog showTimeDialog(Context context, onCallBackListener onCallBackListener) {
		DateTimeDialog dateDialog = new DateTimeDialog(context);
		dateDialog.dialog = S_TIMEDIALOG;
		dateDialog.mTitleTxt = "ѡ��ʱ��";
		dateDialog.onCallBackListener = onCallBackListener;
		dateDialog.show();
		dateDialog.ajustScreen();
		return dateDialog;
	}

	public static DateTimeDialog showTimeDialog(Context context, Calendar date, onCallBackListener onCallBackListener) {
		DateTimeDialog dateDialog = new DateTimeDialog(context);
		dateDialog.dialog = S_TIMEDIALOG;
		dateDialog.mTitleTxt = "ѡ��ʱ��";
		dateDialog.assigDate = date;
		dateDialog.onCallBackListener = onCallBackListener;
		dateDialog.show();
		dateDialog.ajustScreen();
		return dateDialog;
	}

	public static DateTimeDialog showDateTimeDialog(Context context, onCallBackListener onCallBackListener) {
		DateTimeDialog dialog = new DateTimeDialog(context);
		dialog.dialog = S_DATETIMEDIALOG;
		dialog.mTitleTxt = "��ѡ��";
		dialog.onCallBackListener = onCallBackListener;
		dialog.show();
		dialog.ajustScreen();
		return dialog;
	}
	
	public static DateTimeDialog showDateTimeDialog(Context context, Calendar date, onCallBackListener onCallBackListener) {
		DateTimeDialog dateDialog = new DateTimeDialog(context);
		dateDialog.dialog = S_DATETIMEDIALOG;
		dateDialog.mTitleTxt = "ѡ��ʱ��";
		dateDialog.assigDate = date;
		dateDialog.onCallBackListener = onCallBackListener;
		dateDialog.show();
		dateDialog.ajustScreen();
		return dateDialog;
	}
	
	
	/**
	 * 
	 * ��          �� ������ʱ����ʾ��ȷ�ϻص��ӿ�
	 * ��������  : 2013-6-13
	 * ��           �� �� 
	 * �޸�����  : 
	 * ��   ��   �� ��
	 * @version   : 1.0
	 */
	public interface onCallBackListener {
		void onCallBack(Calendar calendar);
	}
	
	public  void ajustScreen() {
		int width = getContext().getResources().getDisplayMetrics().widthPixels;
		LayoutParams params = getWindow().getAttributes();
		params.width = (int) (width * 0.9);
		getWindow().setAttributes(params);
	}
	
}
