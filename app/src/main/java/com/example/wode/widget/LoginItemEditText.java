package com.example.wode.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.wode.R;

public class LoginItemEditText extends LinearLayout {
    private String CodeBtntxt="";
    private OnClickByCodeButton onClickByCodeButton;//发送验证码点击事件
    private TextView linTextTitle;//左边的文本
    private EditText linTextEdit;//输入框
    private Button linTextBtn;//发送验证码的按钮
    public LoginItemEditText(Context context) {
        super(context);
        initView(context,null);
    }

    public LoginItemEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public LoginItemEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    private void initView(Context context,AttributeSet attrs) {
        ViewGroup view= (ViewGroup) LayoutInflater.from(context).inflate(R.layout.layout_lintext,null);
        addView(view,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //ImageView imageView=view.findViewById(R.id.tolodinganim);
         linTextTitle=view.findViewById(R.id.linTextTitle);//文本
         linTextEdit=view.findViewById(R.id.linTextEdit);//输入框
         linTextBtn=view.findViewById(R.id.linTextbtn);//发送验证码按钮

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoginItemEditText);
        if (typedArray != null) {
            String hint = typedArray.getString(R.styleable.LoginItemEditText_hint);
            String text= typedArray.getString(R.styleable.LoginItemEditText_text);
            int testsize=typedArray.getInteger(R.styleable.LoginItemEditText_textSize,13);

            linTextEdit.setHint(hint);
            linTextTitle.setText(text);//设置TextView的文本
            linTextEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP,testsize);
            linTextTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,testsize);
            boolean isbtn=typedArray.getBoolean(R.styleable.LoginItemEditText_IsBtn,false);//是否显示按钮

            if (isbtn){
                linTextBtn.setVisibility(VISIBLE);
                linTextBtn.setOnClickListener(new OnClickListener() {//当出发点击时  触动自定义的按钮点击接口
                    @Override
                    public void onClick(View v) {
                        if (onClickByCodeButton!=null) onClickByCodeButton.onClick(LoginItemEditText.this);
                    }
                });
            }
            if (typedArray.getBoolean(R.styleable.LoginItemEditText_IsPass,false)){//如果是密码框
                //密码
                linTextEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }

            typedArray.recycle();
        }
   // CodeOpenStart(1000);
    }


    /**
     * 用户点击发送验证按钮是 应该调用此函数 开启一个定时器，验证码的倒计时
     */
    boolean tr=true;//控制这个线程
    int codeTime=0;
    public void CodeOpenStart( int time){//发送验证码成功时候调用 一个倒计时
        linTextBtn.setEnabled(false);//禁止点击
        tr=true;
        codeTime=time;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (tr&&codeTime>=0){
                    linTextBtn.setText(codeTime+"S");
                    codeTime--;
                    new Handler().postDelayed(this,1000);
                }else{
                    CodeOpenEnd("重新发送");
                }

            }
        },0);

    }
    //关闭验证码倒计时  如：验证码时间到 按钮恢复可以点击状态
    public void CodeOpenEnd(String str){
        tr=false;
        linTextBtn.setText(str);
        linTextBtn.setEnabled(true);//允许点击
    }

    //获取用户输入
    public String getEditText(){
        return linTextEdit.getText().toString();
    }

    //继承此接口监听验证码按钮点击事件
    public interface OnClickByCodeButton{

        void onClick(LoginItemEditText view);
    }

    public void setOnClickByCodeButton(OnClickByCodeButton onClickByCodeButton) {
        this.onClickByCodeButton = onClickByCodeButton;
    }

    @Override
    protected void onDetachedFromWindow() {//当View离开附着的窗口时触发，比如在Activity调用onDestroy方法时View就会离开窗口。和一开始的AttachedToWindow相对，都只会被调用一次。
        super.onDetachedFromWindow();
        tr=false;
    }
}
