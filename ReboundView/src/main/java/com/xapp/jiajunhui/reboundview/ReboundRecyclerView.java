package com.xapp.jiajunhui.reboundview;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by Taurus on 2016/9/9.
 */
public class ReboundRecyclerView extends RecyclerView {

    private final String TAG = "ReboundRecyclerView";
    private float y;
    private Rect normal = new Rect();
    private boolean animationFinish = true;
    private boolean overScrolled = false;

    private boolean reboundEnable = true;

    private boolean topReboundEnable = true;
    private boolean bottomReboundEnable = true;

    public ReboundRecyclerView(Context context) {
        super(context);
        init();
    }

    public ReboundRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ReboundRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public boolean isReboundEnable() {
        return reboundEnable;
    }

    public void setReboundEnable(boolean reboundEnable) {
        this.reboundEnable = reboundEnable;
    }

    public boolean isTopReboundEnable() {
        return topReboundEnable;
    }

    public void setTopReboundEnable(boolean topReboundEnable) {
        this.topReboundEnable = topReboundEnable;
    }

    public boolean isBottomReboundEnable() {
        return bottomReboundEnable;
    }

    public void setBottomReboundEnable(boolean bottomReboundEnable) {
        this.bottomReboundEnable = bottomReboundEnable;
    }

    private void init() {
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_SETTLING){
                    overScrolled = false;
                    Log.d(TAG,"onScrolled ......" + " overScrolled = false");
                }
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    overScrolled = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                overScrolled = true;
                Log.d(TAG," ......" + " overScrolled = true");
            }
        });
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(reboundEnable){
            commOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    public void commOnTouchEvent(MotionEvent ev) {
        if (animationFinish) {
            int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    y = ev.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    y = 0;
                    if (isNeedAnimation()) {
                        animation();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    final float preY = y == 0 ? ev.getY() : y;
                    float nowY = ev.getY();
                    int deltaY = (int) (preY - nowY);

                    y = nowY;
                    // 当滚动到最上或者最下时就不会再滚动，这时移动布局
                    if (isNeedMove(deltaY)) {
                        if (normal.isEmpty()) {
                            // 保存正常的布局位置
                            normal.set(getLeft(), getTop(), getRight(), getBottom());
                        }
                        // 移动布局
                        layout(getLeft(), getTop() - deltaY / 2, getRight(), getBottom() - deltaY / 2);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    // 开启动画移动
    public void animation() {
        // 开启移动动画
        TranslateAnimation ta = new TranslateAnimation(0, 0, 0, normal.top - getTop());
        ta.setDuration(200);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animationFinish = false;

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                clearAnimation();
                // 设置回到正常的布局位置
                layout(normal.left, normal.top, normal.right, normal.bottom);
                normal.setEmpty();
                animationFinish = true;
            }
        });
        startAnimation(ta);
    }

    // 是否需要开启动画
    public boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    // 是否需要移动布局
    public boolean isNeedMove(float deltaY) {
        if (overScrolled && getCount() > 0) {
//            Log.d(TAG,"isNeedMove ......" + " deltaY : " + deltaY + " getLastVisiblePosition() == getCount() - 1 : " + (getLastVisiblePosition() == getCount() - 1));
            if (bottomReboundEnable && getLastVisiblePosition() == getCount() - 1 && deltaY >= 0) {
                return true;
            }
            if (topReboundEnable && getFirstVisiblePosition() == 0 && deltaY <= 0) {
                return true;
            }
        }
        return false;
    }

    private int getCount(){
        Adapter adapter = getAdapter();
        if(adapter==null)
            return 0;
        return adapter.getItemCount();
    }

    private int getLastVisiblePosition(){
        LinearLayoutManager layoutManager = getRecyclerLayoutManager();
        if(layoutManager==null)
            return 0;
        return layoutManager.findLastCompletelyVisibleItemPosition();
    }

    private int getFirstVisiblePosition(){
        LinearLayoutManager layoutManager = getRecyclerLayoutManager();
        if(layoutManager==null)
            return 0;
        return layoutManager.findFirstCompletelyVisibleItemPosition();
    }

    private LinearLayoutManager getRecyclerLayoutManager(){
        return (LinearLayoutManager) getLayoutManager();
    }
}
