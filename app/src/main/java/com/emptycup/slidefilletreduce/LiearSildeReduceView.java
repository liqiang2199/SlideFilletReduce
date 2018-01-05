package com.emptycup.slidefilletreduce;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by empty cup on 2018/1/5.
 * 当滑动时实现 背景圆角的变化
 */

public class LiearSildeReduceView extends LinearLayout {

    private float radio_X;
    private float radio_Y;
    private float save_X;

    private Context mContext;
    private int reduceBackground;
    private int defaultColor = 0xFFFFFFFF;

    private ScrollView mScrollView;
    private float ixy = 0;
    private Paint paint;
    private RectF rectF;

    public LiearSildeReduceView(Context context) {
        super(context);
        this.mContext = context;
    }

    public LiearSildeReduceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        InitsetAttrsbute(attrs);
    }

    public LiearSildeReduceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        InitsetAttrsbute(attrs);
    }
    //获取自定义的 样式
    private void InitsetAttrsbute(AttributeSet attrs){
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs,R.styleable.slideReduce);

        reduceBackground = typedArray.getColor(R.styleable.slideReduce_reduceBackground,defaultColor);
        radio_X = typedArray.getFloat(R.styleable.slideReduce_radio_X,0f);
        radio_Y = typedArray.getFloat(R.styleable.slideReduce_radio_Y,0f);
        this.ixy = radio_X;
        this.save_X = radio_X;
        paint = new Paint();
        rectF = new RectF();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        ViewGroup.LayoutParams params = this.getLayoutParams();
        ViewGroup.MarginLayoutParams marginParams = null;
        //获取view的margin设置参数
        if (params instanceof ViewGroup.MarginLayoutParams) {
            marginParams = (ViewGroup.MarginLayoutParams) params;
        } else {
            //不存在时创建一个新的参数
            marginParams = new ViewGroup.MarginLayoutParams(params);
        }
        marginParams.setMargins(20,10,20,0);
        this.setLayoutParams(marginParams);

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Log.v("LinearLayout","##############dispatchDraw");

        paint.setColor(reduceBackground);

        rectF.left = 0;
        rectF.right = getWidth();
        rectF.top = 0;
        rectF.bottom = getHeight()-(radio_X/5);
        canvas.drawRoundRect(rectF,radio_X,radio_Y,paint);
    }
    //重新刷新
    private void Pull_InitXY(float radio_X,float radio_Y){
        this.radio_X = radio_X;
        this.radio_Y = radio_Y;
        invalidate();
    }

    public float getRadio_X() {
        return radio_X;
    }

    public float getRadio_Y() {
        return radio_Y;
    }

    public void Bind_ScrollVeiw(final ScrollView mScrollView) throws BindViewException {
        this.mScrollView = mScrollView;

        if (mScrollView != null){

            mScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    Log.v("slide_ScrollView",ixy+"    !!!!!! "+getRadio_X()+"    ##### "+ scrollY+"    @@@@@@   "+oldScrollY);
                    //滑动的动态处理
                    //当 滑动到 1000 时才处理
                    if (scrollY > oldScrollY&&scrollY > mScrollView.getHeight()/3+mScrollView.getWidth()/2){
                        //向上滑动
                        if (ixy == getRadio_X()){
                            ixy-= 5;
                            if (ixy >= 0){
                                Pull_InitXY(ixy,ixy);
                            }else{
                                ixy = 0;
                            }
                        }
                    }else{
                        //向下滑动
                        if (ixy == getRadio_X()){
                            ixy+= 5;
                            if (ixy <= save_X){
                                Pull_InitXY(ixy,ixy);
                            }else{
                                ixy = save_X;
                            }
                        }
                    }
                }
            });
        }else{
           throw  new BindViewException("NO Bind ScrollView");
        }
    }

}
