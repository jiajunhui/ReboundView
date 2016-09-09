package com.xapp.jiajunhui.reboundview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * Created by Taurus on 2016/9/9.
 */
public class ReboundRelativeLayout extends RelativeLayout {

    private Scroller mScroller;
    private GestureDetector mGestureDetector;

    private boolean reboundEnable = true;

    public ReboundRelativeLayout(Context context) {
        super(context);
        init(context);
    }

    public ReboundRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ReboundRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public boolean isReboundEnable() {
        return reboundEnable;
    }

    public void setReboundEnable(boolean reboundEnable) {
        this.reboundEnable = reboundEnable;
    }

    private void init(Context context){
        setClickable(true);
        setLongClickable(true);
        mScroller = new Scroller(context);
        mGestureDetector = new GestureDetector(context,
                new GestureListenerImpl());
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            // 必须执行postInvalidate()从而调用computeScroll()
            // 其实,在此调用invalidate();亦可
            postInvalidate();
        }
        super.computeScroll();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!reboundEnable){
            return super.onTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                // 手指抬起时回到最初位置
                prepareScroll(0, 0);
                break;
            default:
                // 其余情况交给GestureDetector手势处理
                return mGestureDetector.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }


    class GestureListenerImpl implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }


        @Override
        public void onShowPress(MotionEvent e) {


        }


        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }


        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            int disY = (int) ((distanceY - 0.1) / 2);
            beginScroll(0, disY);
            return false;
        }


        public void onLongPress(MotionEvent e) {


        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            return false;
        }


    }


    // 滚动到初始位置
    protected void prepareScroll(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        beginScroll(dx, dy);
    }


    // 设置滚动的相对偏移
    protected void beginScroll(int dx, int dy) {
        // 第一,二个参数起始位置;第三,四个滚动的偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx,
                dy);
        // 必须执行invalidate()从而调用computeScroll()
        invalidate();
    }
}
