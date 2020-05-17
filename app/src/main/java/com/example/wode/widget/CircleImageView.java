package com.example.wode.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class CircleImageView extends ImageView {
    /**
     * 自己写一个圆角图片 但设置图片的时候只能set  用Gild会报错
     */
    Context context;

    public CircleImageView(Context context) {
        super(context);
        this.context=context;
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }

    /**
     * 有两种方式
     * 1.创建一个空白的bitmap  将之前的bitmap附着到paint上 然后绘制这个新的空白bitmap
     * 2.直接剪切画布 这里用第2种
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
//super.onDraw();
//        Drawable drawable=getDrawable();
//        Bitmap bitmap=((BitmapDrawable)drawable).getBitmap();//获取图片
//
//        Bitmap newbit=Bitmap.createBitmap(getWidth(),getHeight(),Bitmap.Config.ARGB_8888);
//        Canvas can=new Canvas(newbit);
//        Paint paint=new Paint();
//        can.drawCircle(newbit.getWidth()/2,newbit.getHeight()/2,newbit.getWidth()/2,paint);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        can.drawBitmap(bitmap,new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()),
//                new Rect(0,0,newbit.getWidth(),newbit.getHeight()),paint);
//        canvas.drawBitmap(newbit,0,0,null);
        Path mPath = new Path();
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mPath.addCircle(width/2, height/2, height/2, Path.Direction.CW);
        canvas.clipPath(mPath);//将画布裁剪成圆形
        super.onDraw(canvas);

    }
}
