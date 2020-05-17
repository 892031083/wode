package com.example.wode.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.wode.R;

public class CalculationText extends LinearLayout {
    private OnClickNum onClickNum;

    public void setOnClickNum(OnClickNum onClickNum) {
        this.onClickNum = onClickNum;
    }

    public CalculationText(Context context) {
        super(context);
        initView(context);
    }

    public CalculationText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CalculationText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    TextView numBtn;
    private void initView(Context context) {
        View view=LayoutInflater.from(context).inflate(R.layout.calculation_text,null);
        addView(view);
        TextView addBtn=view.findViewById(R.id.up);
        TextView DeleBtn=view.findViewById(R.id.down);
         numBtn=view.findViewById(R.id.num);
        Log.i("=====",numBtn.getText().toString()+"ss");
        addBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {//增加接口
                if (onClickNum!=null){
                    onClickNum.onClickUpdateNum(Integer.valueOf(numBtn.getText().toString())+1,true);//用户减少商品数量时把值传过去
                    numBtn.setText(Integer.valueOf(numBtn.getText().toString())+1+"");

                }
            }
        });
        DeleBtn.setOnClickListener(new OnClickListener() {//减少接口
            @Override
            public void onClick(View v) {
                if (onClickNum!=null){

                    if (numBtn.getText().toString().equals("1")){
                        numBtn.setEnabled(false);
                        onClickNum.onClickDeleZero();
                        return;
                    }
                    onClickNum.onClickUpdateNum(Integer.valueOf(numBtn.getText().toString())-1,false);
                    numBtn.setText(Integer.valueOf(numBtn.getText().toString())-1+"");

                }

            }
        });
    }

    public interface OnClickNum{
        void onClickUpdateNum(int Datanum, boolean isAdd);//+ 增加减少的标示  true代表增加

        void onClickDeleZero();//删除
    }
}
