package com.std.framework.widget.dialog;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.std.framework.R;

class WheelView extends View {
	/** ����������ʱ�� */
	private static final int SCROLLING_DURATION = 400;

	/** ���ٹ�����λ�� */
	private static final int MIN_DELTA_FOR_SCROLLING = 1;

	/** ��ǰֵ�ͱ�ǩ����ɫ */
	private static final int VALUE_TEXT_COLOR = 0xF0FF6347;

	/** item���ֵ���ɫ */
	private static final int ITEMS_TEXT_COLOR = 0xFF000000;

	/** �����͵ײ���Ӱ����ɫ */
	private static final int[] SHADOWS_COLORS = new int[] { 0xFF111111, 0x00AAAAAA, 0x00AAAAAA };

	/** ���ӵ�item�ĸ߶� */
	private static final int ADDITIONAL_ITEM_HEIGHT = 15;

	/** �����С */
	public static int TEXT_SIZE = 30;

	/** �����͵ײ�item��ƫ��ֵ */
	private static final int ITEM_OFFSET = TEXT_SIZE / 5;

	/** item���ֵĸ��ӿ��� */
	private static final int ADDITIONAL_ITEMS_SPACE = 10;

	/** ��ǩƫ��ֵ */
	private static final int LABEL_OFFSET = 8;

	/** ����paddingֵ */
	private static final int PADDING = 10;

	/** Ĭ�Ͽɼ���item��Ŀ */
	private static final int DEF_VISIABLE_ITEMS = 5;

	/** ��ʼ��wheeladpater */
	private WheelAdapter adapter = null;

	/** ��ǰitemλ�� */
	private int currentItem = 0;

	/** item���� */
	private int itemsWidth = 0;

	/** ��ǩ���� */
	private int labelWidth = 0;

	/** �ɼ�item��Ŀ */
	private int visibleItems = DEF_VISIABLE_ITEMS;

	/** item�߶� */
	private int itemHeight = 0;

	/** item���ַ������Զ��� */
	private TextPaint itemsPaint;

	/** value���ַ������Զ��� */
	private TextPaint valuePaint;

	// Layouts
	private StaticLayout itemsLayout, labelLayout, valueLayout;

	private String label;
	private Drawable centerDrawable;

	/** ��������drawable���� */
	private GradientDrawable topShadow;

	/** ��������drawable���� */
	private GradientDrawable bottomShadow;

	/** ���������Ƿ�ִ�� */
	private boolean isScrollingPerformed;

	/** ����ƫ���� */
	private int scrollingOffset;

	/** ���������� */
	private GestureDetector gestureDetector;

	private Scroller scroller;
	private int lastScrollY;

	/** �Ƿ��ѭ�� */
	private boolean isCyclic = false;

	/** ʵ����OnWheelChangedListener */
	private List<OnWheelChangedListener> changingListeners = new LinkedList<OnWheelChangedListener>();

	/** ʵ����OnWheelScrollListener */
	private List<OnWheelScrollListener> scrollingListeners = new LinkedList<OnWheelScrollListener>();

	/**
	 * 3���������캯��
	 */
	public WheelView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initData(context);
	}

	/**
	 * 2���������캯��
	 */
	public WheelView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initData(context);
	}

	/**
	 * 1���������캯��
	 */
	public WheelView(Context context) {
		super(context);
		initData(context);
	}

	private void initData(Context context) {
		gestureDetector = new GestureDetector(context, gestureListener);
		gestureDetector.setIsLongpressEnabled(false);// �������Ƴ�����������

		scroller = new Scroller(context);
	}

	/**
	 * ��ȡ����������
	 * 
	 * @return
	 */
	public WheelAdapter getAdapter() {
		return adapter;
	}

	/**
	 * ���ù���������
	 * 
	 * @param adapter
	 */
	public void setAdapter(WheelAdapter adapter) {
		this.adapter = adapter;
		invalidateLayouts();
		invalidate();// ����ͼ��Ч

	}

	/**
	 * ����ָ���Ĺ��ֶ����仯��
	 * 
	 * @param interpolator
	 */
	public void setInterpolator(Interpolator interpolator) {
		scroller.forceFinished(true);
		scroller = new Scroller(getContext(), interpolator);
	}

	/**
	 * �õ��ɼ�item����Ŀ
	 * 
	 * @return the count of visible items
	 */
	public int getVisibleItems() {
		return visibleItems;
	}

	/**
	 * ���ÿɼ�item����Ŀ
	 * 
	 * @param count
	 *                the new count
	 */
	public void setVisibleItems(int count) {
		visibleItems = count;
		invalidate();
	}

	/**
	 * �õ���ǩ
	 * 
	 * @return
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * ���ñ�ǩ
	 * 
	 * @param newLabel
	 */
	public void setLabel(String newLabel) {
		if (label == null || !label.equals(newLabel)) {
			label = newLabel;
			labelLayout = null;
			invalidate();
		}
	}

	/**
	 * ���ӹ��ֱ仯������
	 * 
	 * @param listener
	 */
	public void addChangingListener(OnWheelChangedListener listener) {
		changingListeners.add(listener);
	}

	/**
	 * �Ƴ����ֱ仯������
	 * 
	 * @param listener
	 */
	public void removeChangingListener(OnWheelChangedListener listener) {
		changingListeners.remove(listener);
	}

	/**
	 * ֪ͨ�ı�ļ�����
	 * 
	 * @param oldValue
	 * @param newValue
	 */
	protected void notifyChangingListeners(int oldValue, int newValue) {
		for (OnWheelChangedListener listener : changingListeners) {
			listener.onChanged(this, oldValue, newValue);
		}
	}

	/**
	 * ���ӹ��ּ�����
	 * 
	 * @param listener
	 *                the listener
	 */
	public void addScrollingListener(OnWheelScrollListener listener) {
		scrollingListeners.add(listener);
	}

	/**
	 * �Ƴ����ּ�����
	 * 
	 * @param listener
	 *                the listener
	 */
	public void removeScrollingListener(OnWheelScrollListener listener) {
		scrollingListeners.remove(listener);
	}

	/**
	 * ֪ͨ��������ʼ����
	 */
	protected void notifyScrollingListenersAboutStart() {
		for (OnWheelScrollListener listener : scrollingListeners) {
			listener.onScrollingStarted(this);
		}
	}

	/**
	 * ֪ͨ��������������
	 */
	protected void notifyScrollingListenersAboutEnd() {
		for (OnWheelScrollListener listener : scrollingListeners) {
			listener.onScrollingFinished(this);
		}
	}

	/**
	 * ȡ�õ�ǰitem
	 * 
	 * @return
	 */
	public int getCurrentItem() {
		return currentItem;
	}

	/**
	 * ���õ�ǰitem
	 * 
	 * @param index
	 * @param animated
	 */
	public void setCurrentItem(int index, boolean animated) {
		if (adapter == null || adapter.getItemsCount() == 0) {
			return;
		}
		if (index < 0 || index >= adapter.getItemsCount()) {
			if (isCyclic) {
				while (index < 0) {
					index += adapter.getItemsCount();
				}
				index %= adapter.getItemsCount();
			}
			else {
				return;
			}
		}
		if (index != currentItem) {
			if (animated) {
				scroll(index - currentItem, SCROLLING_DURATION);
			}
			else {
				invalidateLayouts();

				int old = currentItem;
				currentItem = index;

				notifyChangingListeners(old, currentItem);
				invalidate();
			}
		}
	}

	/**
	 * ���õ�ǰitem w/o ����. ��index�����ǲ����κ���Ӧ.
	 * 
	 * @param index
	 *                the item index
	 */
	public void setCurrentItem(int index) {
		setCurrentItem(index, false);
	}

	/**
	 * ���Թ����Ƿ��ѭ��.
	 * 
	 * @return true if wheel is cyclic
	 */
	public boolean isCyclic() {
		return isCyclic;
	}

	/**
	 * ���ù���ѭ����־
	 * 
	 * @param isCyclic
	 *                the flag to set
	 */
	public void setCyclic(boolean isCyclic) {
		this.isCyclic = isCyclic;

		invalidate();
		invalidateLayouts();
	}

	/**
	 * ʹ������Ч
	 */
	private void invalidateLayouts() {
		itemsLayout = null;
		valueLayout = null;
		scrollingOffset = 0;
	}

	/**
	 * ��ʼ����Դ��Ϣ
	 */
	private void initResourceIfNecessary() {
		if (itemsPaint == null) {
			itemsPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.FAKE_BOLD_TEXT_FLAG);
			itemsPaint.setTextSize(TEXT_SIZE);
		}

		if (valuePaint == null) {
			valuePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.FAKE_BOLD_TEXT_FLAG | Paint.DITHER_FLAG);
			valuePaint.setTextSize(TEXT_SIZE);
			valuePaint.setShadowLayer(0.1f, 0, 0.1f, 0xFFC0C0C0);
		}

		if (centerDrawable == null) {
			centerDrawable = getContext().getResources().getDrawable(R.drawable.wheel_val);
		}

		if (topShadow == null) {
			topShadow = new GradientDrawable(Orientation.TOP_BOTTOM, SHADOWS_COLORS);
		}

		if (bottomShadow == null) {
			bottomShadow = new GradientDrawable(Orientation.BOTTOM_TOP, SHADOWS_COLORS);
		}

		setBackgroundResource(R.drawable.wheel_bg);
	}

	/**
	 * ����layout����ĸ߶�
	 * 
	 * @param layout
	 * @return
	 */
	private int getDesiredHeight(Layout layout) {
		if (layout == null) {
			return 0;
		}

		int desired = getItemHeight() * visibleItems - ITEM_OFFSET * 2 - ADDITIONAL_ITEM_HEIGHT;

		desired = Math.max(desired, getSuggestedMinimumHeight());
		return desired;
	}

	/**
	 * ͨ��index�õ�text
	 * 
	 * @param index
	 * @return
	 */
	private String getTextItem(int index) {
		if (adapter == null || adapter.getItemsCount() == 0) {
			return null;
		}
		int count = adapter.getItemsCount();
		if ((index < 0 || index >= count) && !isCyclic) {
			return null;
		}
		else {
			while (index < 0) {
				index += count;
			}
		}
		index %= count;
		return adapter.getItem(index);
	}

	/**
	 * ���ݵ�ǰֵ����text
	 * 
	 * @param useCurrentValue
	 * @return the text
	 */
	private String buildText(boolean useCurrentValue) {
		StringBuilder itemsText = new StringBuilder();
		int addItems = visibleItems / 2 + 1;

		for (int i = currentItem - addItems; i <= currentItem + addItems; i++) {
			if (useCurrentValue || i != currentItem) {
				String text = getTextItem(i);
				if (text != null) {
					itemsText.append(text);
				}
			}
			if (i < currentItem + addItems) {
				itemsText.append("\n");
			}
		}

		return itemsText.toString();
	}

	/**
	 * ���ؿ��Ա�ʾ��item����󳤶�
	 * 
	 * @return the max length
	 */
	private int getMaxTextLength() {
		WheelAdapter adapter = getAdapter();
		if (adapter == null) {
			return 0;
		}

		int adapterLength = adapter.getMaximumLength();
		if (adapterLength > 0) {
			return adapterLength;
		}

		String maxText = null;
		int addItems = visibleItems / 2;
		for (int i = Math.max(currentItem - addItems, 0); i < Math.min(currentItem + visibleItems, adapter.getItemsCount()); i++) {
			String text = adapter.getItem(i);
			if (text != null && (maxText == null || maxText.length() < text.length())) {
				maxText = text;
			}
		}

		return maxText != null ? maxText.length() : 0;
	}

	/**
	 * ���ع���item�ĸ߶�
	 * 
	 * @return the item height
	 */
	private int getItemHeight() {
		if (itemHeight != 0) {
			return itemHeight;
		}
		else if (itemsLayout != null && itemsLayout.getLineCount() > 2) {
			itemHeight = itemsLayout.getLineTop(2) - itemsLayout.getLineTop(1);
			return itemHeight;
		}

		return getHeight() / visibleItems;
	}

	/**
	 * ������ƿ��Ⱥʹ���text����
	 * 
	 * @param widthSize
	 *                the input layout width
	 * @param mode
	 *                the layout mode
	 * @return the calculated control width
	 */
	private int calculateLayoutWidth(int widthSize, int mode) {
		initResourceIfNecessary();

		int width = widthSize;

		int maxLength = getMaxTextLength();
		if (maxLength > 0) {
			float textWidth = FloatMath.ceil(Layout.getDesiredWidth("0", itemsPaint));
			itemsWidth = (int) (maxLength * textWidth);
		}
		else {
			itemsWidth = 0;
		}
		itemsWidth += ADDITIONAL_ITEMS_SPACE; // make it some more

		labelWidth = 0;
		if (label != null && label.length() > 0) {
			labelWidth = (int) FloatMath.ceil(Layout.getDesiredWidth(label, valuePaint));
		}

		boolean recalculate = false;
		if (mode == MeasureSpec.EXACTLY) {
			width = widthSize;
			recalculate = true;
		}
		else {
			width = itemsWidth + labelWidth + 2 * PADDING;
			if (labelWidth > 0) {
				width += LABEL_OFFSET;
			}

			// Check against our minimum width
			width = Math.max(width, getSuggestedMinimumWidth());

			if (mode == MeasureSpec.AT_MOST && widthSize < width) {
				width = widthSize;
				recalculate = true;
			}
		}

		if (recalculate) {
			// recalculate width
			int pureWidth = width - LABEL_OFFSET - 2 * PADDING;
			if (pureWidth <= 0) {
				itemsWidth = labelWidth = 0;
			}
			if (labelWidth > 0) {
				double newWidthItems = (double) itemsWidth * pureWidth / (itemsWidth + labelWidth);
				itemsWidth = (int) newWidthItems;
				labelWidth = pureWidth - itemsWidth;
			}
			else {
				itemsWidth = pureWidth + LABEL_OFFSET; // no
								       // label
			}
		}

		if (itemsWidth > 0) {
			createLayouts(itemsWidth, labelWidth);
		}

		return width;
	}

	/**
	 * ��������
	 * 
	 * @param widthItems
	 *                width of items layout
	 * @param widthLabel
	 *                width of label layout
	 */
	private void createLayouts(int widthItems, int widthLabel) {
		if (itemsLayout == null || itemsLayout.getWidth() > widthItems) {
			itemsLayout = new StaticLayout(buildText(isScrollingPerformed), itemsPaint, widthItems,
					widthLabel > 0 ? Layout.Alignment.ALIGN_OPPOSITE : Layout.Alignment.ALIGN_CENTER, 1, ADDITIONAL_ITEM_HEIGHT,
					false);
		}
		else {
			itemsLayout.increaseWidthTo(widthItems);
		}

		if (!isScrollingPerformed && (valueLayout == null || valueLayout.getWidth() > widthItems)) {
			String text = getAdapter() != null ? getAdapter().getItem(currentItem) : null;
			valueLayout = new StaticLayout(text != null ? text : "", valuePaint, widthItems,
					widthLabel > 0 ? Layout.Alignment.ALIGN_OPPOSITE : Layout.Alignment.ALIGN_CENTER, 1, ADDITIONAL_ITEM_HEIGHT,
					false);
		}
		else if (isScrollingPerformed) {
			valueLayout = null;
		}
		else {
			valueLayout.increaseWidthTo(widthItems);
		}

		if (widthLabel > 0) {
			if (labelLayout == null || labelLayout.getWidth() > widthLabel) {
				labelLayout = new StaticLayout(label, valuePaint, widthLabel, Layout.Alignment.ALIGN_NORMAL, 1,
						ADDITIONAL_ITEM_HEIGHT, false);
			}
			else {
				labelLayout.increaseWidthTo(widthLabel);
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int width = calculateLayoutWidth(widthSize, widthMode);

		int height;
		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		}
		else {
			height = getDesiredHeight(itemsLayout);

			if (heightMode == MeasureSpec.AT_MOST) {
				height = Math.min(height, heightSize);
			}
		}

		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (itemsLayout == null) {
			if (itemsWidth == 0) {
				calculateLayoutWidth(getWidth(), MeasureSpec.EXACTLY);
			}
			else {
				createLayouts(itemsWidth, labelWidth);
			}
		}

		if (itemsWidth > 0) {
			canvas.save();
			// Skip padding space and hide a part of top and bottom
			// items
			canvas.translate(PADDING, -ITEM_OFFSET);
			drawItems(canvas);
			drawValue(canvas);
			canvas.restore();
		}

		drawCenterRect(canvas);
		drawShadows(canvas);
	}

	/**
	 * �ڶ����͵ײ�����Ӱ�Ŀ���
	 * 
	 * @param canvas
	 *                the canvas for drawing
	 */
	private void drawShadows(Canvas canvas) {
		topShadow.setBounds(0, 0, getWidth(), getHeight() / visibleItems);
		topShadow.draw(canvas);

		bottomShadow.setBounds(0, getHeight() - getHeight() / visibleItems, getWidth(), getHeight());
		bottomShadow.draw(canvas);
	}

	/**
	 * ��value�ͱ�ǩ�Ĳ���
	 * 
	 * @param canvas
	 *                the canvas for drawing
	 */
	private void drawValue(Canvas canvas) {
		valuePaint.setColor(VALUE_TEXT_COLOR);
		valuePaint.drawableState = getDrawableState();

		Rect bounds = new Rect();
		itemsLayout.getLineBounds(visibleItems / 2, bounds);

		// draw label
		if (labelLayout != null) {
			canvas.save();
			canvas.translate(itemsLayout.getWidth() + LABEL_OFFSET, bounds.top);
			labelLayout.draw(canvas);
			canvas.restore();
		}

		// draw current value
		if (valueLayout != null) {
			canvas.save();
			canvas.translate(0, bounds.top + scrollingOffset);
			valueLayout.draw(canvas);
			canvas.restore();
		}
	}

	/**
	 * ��items
	 * 
	 * @param canvas
	 *                the canvas for drawing
	 */
	private void drawItems(Canvas canvas) {
		canvas.save();

		int top = itemsLayout.getLineTop(1);
		canvas.translate(0, -top + scrollingOffset);

		itemsPaint.setColor(ITEMS_TEXT_COLOR);
		itemsPaint.drawableState = getDrawableState();
		itemsLayout.draw(canvas);

		canvas.restore();
	}

	/**
	 * ����ǰֵ�ľ���
	 * 
	 * @param canvas
	 *                the canvas for drawing
	 */
	private void drawCenterRect(Canvas canvas) {
		int center = getHeight() / 2;
		int offset = getItemHeight() / 2;
		centerDrawable.setBounds(0, center - offset, getWidth(), center + offset);
		centerDrawable.draw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		WheelAdapter adapter = getAdapter();
		if (adapter == null) {
			return true;
		}

		if (!gestureDetector.onTouchEvent(event) && event.getAction() == MotionEvent.ACTION_UP) {
			justify();
		}
		return true;
	}

	/**
	 * ��������
	 * 
	 * @param delta
	 *                the scrolling value
	 */
	private void doScroll(int delta) {
		scrollingOffset += delta;

		int count = scrollingOffset / getItemHeight();
		int pos = currentItem - count;
		if (isCyclic && adapter.getItemsCount() > 0) {
			// fix position by rotating
			while (pos < 0) {
				pos += adapter.getItemsCount();
			}
			pos %= adapter.getItemsCount();
		}
		else if (isScrollingPerformed) {
			//
			if (pos < 0) {
				count = currentItem;
				pos = 0;
			}
			else if (pos >= adapter.getItemsCount()) {
				count = currentItem - adapter.getItemsCount() + 1;
				pos = adapter.getItemsCount() - 1;
			}
		}
		else {
			// fix position
			pos = Math.max(pos, 0);
			pos = Math.min(pos, adapter.getItemsCount() - 1);
		}

		int offset = scrollingOffset;
		if (pos != currentItem) {
			setCurrentItem(pos, false);
		}
		else {
			invalidate();
		}

		// update offset
		scrollingOffset = offset - count * getItemHeight();
		if (scrollingOffset > getHeight()) {
			scrollingOffset = scrollingOffset % getHeight() + getHeight();
		}
	}

	// gesture listener
	private SimpleOnGestureListener gestureListener = new SimpleOnGestureListener() {
		public boolean onDown(MotionEvent e) {
			if (isScrollingPerformed) {
				scroller.forceFinished(true);
				clearMessages();
				return true;
			}
			return false;
		}

		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			startScrolling();
			doScroll((int) -distanceY);
			return true;
		}

		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			lastScrollY = currentItem * getItemHeight() + scrollingOffset;
			int maxY = isCyclic ? 0x7FFFFFFF : adapter.getItemsCount() * getItemHeight();
			int minY = isCyclic ? -maxY : 0;
			scroller.fling(0, lastScrollY, 0, (int) -velocityY / 2, 0, 0, minY, maxY);
			setNextMessage(MESSAGE_SCROLL);
			return true;
		}
	};

	// Messages
	private final int MESSAGE_SCROLL = 0;
	private final int MESSAGE_JUSTIFY = 1;

	/**
	 * Set next message to queue. Clears queue before.
	 * 
	 * @param message
	 *                the message to set
	 */
	private void setNextMessage(int message) {
		clearMessages();
		animationHandler.sendEmptyMessage(message);
	}

	/**
	 * Clears messages from queue
	 */
	private void clearMessages() {
		animationHandler.removeMessages(MESSAGE_SCROLL);
		animationHandler.removeMessages(MESSAGE_JUSTIFY);
	}

	// animation handler
	private Handler animationHandler = new Handler() {
		public void handleMessage(Message msg) {
			scroller.computeScrollOffset();
			int currY = scroller.getCurrY();
			int delta = lastScrollY - currY;
			lastScrollY = currY;
			if (delta != 0) {
				doScroll(delta);
			}

			// scrolling is not finished when it comes to final Y
			// so, finish it manually
			if (Math.abs(currY - scroller.getFinalY()) < MIN_DELTA_FOR_SCROLLING) {
				currY = scroller.getFinalY();
				scroller.forceFinished(true);
			}
			if (!scroller.isFinished()) {
				animationHandler.sendEmptyMessage(msg.what);
			}
			else if (msg.what == MESSAGE_SCROLL) {
				justify();
			}
			else {
				finishScrolling();
			}
		}
	};

	/**
	 * Justifies wheel
	 */
	private void justify() {
		if (adapter == null) {
			return;
		}

		lastScrollY = 0;
		int offset = scrollingOffset;
		int itemHeight = getItemHeight();
		boolean needToIncrease = offset > 0 ? currentItem < adapter.getItemsCount() : currentItem > 0;
		if ((isCyclic || needToIncrease) && Math.abs((float) offset) > (float) itemHeight / 2) {
			if (offset < 0)
				offset += itemHeight + MIN_DELTA_FOR_SCROLLING;
			else
				offset -= itemHeight + MIN_DELTA_FOR_SCROLLING;
		}
		if (Math.abs(offset) > MIN_DELTA_FOR_SCROLLING) {
			scroller.startScroll(0, 0, 0, offset, SCROLLING_DURATION);
			setNextMessage(MESSAGE_JUSTIFY);
		}
		else {
			finishScrolling();
		}
	}

	/**
	 * ��ʼ����
	 */
	private void startScrolling() {
		if (!isScrollingPerformed) {
			isScrollingPerformed = true;
			notifyScrollingListenersAboutStart();
		}
	}

	/**
	 * ֹͣ����
	 */
	void finishScrolling() {
		if (isScrollingPerformed) {
			notifyScrollingListenersAboutEnd();
			isScrollingPerformed = false;
		}
		invalidateLayouts();
		invalidate();
	}

	public void scroll(int itemsToScroll, int time) {
		scroller.forceFinished(true);

		lastScrollY = scrollingOffset;

		int offset = itemsToScroll * getItemHeight();

		scroller.startScroll(0, lastScrollY, 0, offset - lastScrollY, time);
		setNextMessage(MESSAGE_SCROLL);

		startScrolling();
	}

	interface OnWheelScrollListener {
		/**
		 * Callback method to be invoked when scrolling started.
		 * 
		 * @param wheel
		 *                the wheel view whose state has changed.
		 */
		void onScrollingStarted(WheelView wheel);

		/**
		 * Callback method to be invoked when scrolling ended.
		 * 
		 * @param wheel
		 *                the wheel view whose state has changed.
		 */
		void onScrollingFinished(WheelView wheel);
	}

	public interface OnWheelChangedListener {
		/**
		 * Callback method to be invoked when current item changed
		 * 
		 * @param wheel
		 *                the wheel view whose state has changed
		 * @param oldValue
		 *                the old value of current item
		 * @param newValue
		 *                the new value of current item
		 */
		void onChanged(WheelView wheel, int oldValue, int newValue);
	}

	public interface WheelAdapter {
		/**
		 * Gets items count
		 * 
		 * @return the count of wheel items
		 */
		public int getItemsCount();

		/**
		 * Gets a wheel item by index.
		 * 
		 * @param index
		 *                the item index
		 * @return the wheel item text or null
		 */
		public String getItem(int index);

		/**
		 * Gets maximum item length. It is used to determine the wheel
		 * width. If -1 is returned there will be used the default wheel
		 * width.
		 * 
		 * @return the maximum item length or -1
		 */
		public int getMaximumLength();
	}

	public class NumericWheelAdapter implements WheelAdapter {

		/** The default min value */
		public static final int DEFAULT_MAX_VALUE = 9;

		/** The default max value */
		private static final int DEFAULT_MIN_VALUE = 0;

		// Values
		private int minValue;
		private int maxValue;

		// format
		private String format;

		/**
		 * Default constructor
		 */
		public NumericWheelAdapter() {
			this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
		}

		/**
		 * Constructor
		 * 
		 * @param minValue
		 *                the wheel min value
		 * @param maxValue
		 *                the wheel max value
		 */
		public NumericWheelAdapter(int minValue, int maxValue) {
			this(minValue, maxValue, null);
		}

		/**
		 * Constructor
		 * 
		 * @param minValue
		 *                the wheel min value
		 * @param maxValue
		 *                the wheel max value
		 * @param format
		 *                the format string
		 */
		public NumericWheelAdapter(int minValue, int maxValue, String format) {
			this.minValue = minValue;
			this.maxValue = maxValue;
			this.format = format;
		}

		@Override
		public String getItem(int index) {
			if (index >= 0 && index < getItemsCount()) {
				int value = minValue + index;
				return format != null ? String.format(format, value) : Integer.toString(value);
			}
			return null;
		}

		@Override
		public int getItemsCount() {
			return maxValue - minValue + 1;
		}

		@Override
		public int getMaximumLength() {
			int max = Math.max(Math.abs(maxValue), Math.abs(minValue));
			int maxLen = Integer.toString(max).length();
			if (minValue < 0) {
				maxLen++;
			}
			return maxLen;
		}

		public int getMin() {
			return minValue;
		}

		public int getMax() {
			return maxValue;
		}
	}

	public class ArrayWheelAdapter<T> implements WheelAdapter {

		/** The default items length */
		public static final int DEFAULT_LENGTH = -1;

		// items
		private T items[];
		// length
		private int length;

		/**
		 * Constructor
		 * 
		 * @param items
		 *                the items
		 * @param length
		 *                the max items length
		 */
		public ArrayWheelAdapter(T items[], int length) {
			this.items = items;
			this.length = length;
		}

		/**
		 * Contructor
		 * 
		 * @param items
		 *                the items
		 */
		public ArrayWheelAdapter(T items[]) {
			this(items, DEFAULT_LENGTH);
		}

		@Override
		public String getItem(int index) {
			if (index >= 0 && index < items.length) {
				return items[index].toString();
			}
			return null;
		}

		@Override
		public int getItemsCount() {
			return items.length;
		}

		@Override
		public int getMaximumLength() {
			return length;
		}

	}
}