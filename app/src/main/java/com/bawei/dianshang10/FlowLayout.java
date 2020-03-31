package com.bawei.dianshang10;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * 自定义布局类（流式布局）
 */
public class FlowLayout extends FrameLayout {
    //定义
    private OnFlowClickListener onFlowClickListener;
    private float textSize;
    private int color;
    //封装
    public void setOnFlowClickListener(OnFlowClickListener onFlowClickListener) {
        this.onFlowClickListener = onFlowClickListener;
    }
    //构造
    public FlowLayout(@NonNull Context context) {
        super(context);
    }
    public FlowLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }
    public FlowLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }
    //初始化
    private void initAttrs(AttributeSet attrs){
        //context通过调用obtainStyledAttributes方法来获取一个TypeArray，然后由该TypeArray来对属性进行设置
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.flow);
        //判断是否为空
        if(typedArray != null){
            //获取文字大小
            textSize = typedArray.getDimension(R.styleable.flow_textSize,20);
            //获取文字颜色
            color = typedArray.getColor(R.styleable.flow_color, Color.GRAY);
        }
    }
    //测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    //布局
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //获取父控件Width
        int width = getWidth();//宽度
        //设置控件上下左右间隔为20像素
        int viewMargin = 30;
        //定义
        int sumChildWidth = viewMargin;//子控件的宽度求和，不能超过width，也就是每行的宽度不能超出父控件宽度
        int rows  = 1;//行数
        /*getChildCount()：获取子控件的数量
        getChildAt(i)：获取一个子控件*/
        for (int i = 0; i < getChildCount(); i++) {
            //获取当前控件
            View view = getChildAt(i);
            //判断：当每行总体的宽度小于父控件的宽度的时候，则把控件布局在本行，否则，行数加1
            if(sumChildWidth + view.getWidth() > width){
                rows ++;
                sumChildWidth = viewMargin;
            }
            //对控件重新进行布局
            view.layout(sumChildWidth,
                    view.getHeight() * (rows-1) + viewMargin * rows,
                    sumChildWidth + view.getWidth(),
                    view.getHeight() * rows + viewMargin * rows);
            //累计单行控件所有的宽度求和
            sumChildWidth = sumChildWidth + view.getWidth() + viewMargin;
        }
    }
    //绘图
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
    //生成动态添加控件方法
    public void addFlow(String wenben) {
        //根据我们的item，动态加载添加到流式布局中
        TextView textView = (TextView) View.inflate(getContext(), R.layout.item_view, null);
        //设置文本
        textView.setText(wenben);
        //设置自定义属性
        textView.setTextSize(COMPLEX_UNIT_SP,textSize);
        textView.setTextColor(color);
        //传值
        textView.setTag(wenben);
        //设置点击事件
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = (String) v.getTag();
                if(onFlowClickListener != null && tag != null){
                    //回调
                    onFlowClickListener.onFlowClick(tag);
                }
            }
        });
        //LayoutParams 动态布局        作用：子控件告诉父控件，自己要如何布局。
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //布局重绘
        textView.setLayoutParams(layoutParams);
        //把控件添加到父控件中
        addView(textView);
    }
    //定义一个点击监听接口
    public interface OnFlowClickListener{
        void onFlowClick(String wenben);
    }
}
