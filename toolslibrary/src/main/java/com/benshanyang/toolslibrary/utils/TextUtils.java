package com.benshanyang.toolslibrary.utils;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 类描述: 字符串处理工具类 </br>
 * 时间: 2019/3/20 10:52
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class TextUtils {

    /**
     * 左
     */
    public static final int LEFT = 0;
    /**
     * 上
     */
    public static final int TOP = 1;
    /**
     * 右
     */
    public static final int RIGHT = 2;
    /**
     * 下
     */
    public static final int BOTTOM = 3;
    /**
     * 中间
     */
    public static final int CENTER = 4;
    /**
     * 上下居中
     */
    public static final int CENTER_VERTICAL = 5;
    /**
     * 左右居中
     */
    public static final int CENTER_HORIZONTAL = 6;

    /**
     * 判断字符串是否为空
     *
     * @param str 输入的内容
     * @return true-为空 false-不为空
     */
    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 判断字符串是否为空
     *
     * @param str    字符串
     * @param isTrim 是否将前后空格也算做内容
     * @return true-为空 false-不为空
     */
    public static boolean isEmpty(CharSequence str, boolean isTrim) {
        if (isTrim) {
            return (str == null || str.toString().trim().length() == 0);
        } else {
            return isEmpty(str);
        }
    }

    /**
     * 获取TextView或EditText的控件内容
     *
     * @param textView 传入TextView或EditText控件
     * @return 返回控件的内容
     */
    public static String getText(TextView textView) {
        String text = "";
        if (textView != null) {
            CharSequence cText = textView.getText();
            if (cText != null) {
                text = cText.toString();
            }
        }
        return text;
    }

    /**
     * 为TextView设置内容,已判断textview是否为null
     *
     * @param textView 显示文字的控件
     * @param text     文字内容
     */
    public static void setText(TextView textView, CharSequence text) {
        if (textView != null) {
            textView.setText(text != null ? text : "");
        }
    }

    /**
     * 设置字体粗细
     *
     * @param textView  显示文字的控件
     * @param text      要显示的文字
     * @param thickness 文字的粗细程度
     */
    public static void setText(@NonNull TextView textView, CharSequence text, float thickness) {
        setText(textView, text, thickness, textView.getCurrentTextColor());
    }

    /**
     * 设置字体粗细
     *
     * @param textView  显示文字的控件
     * @param text      要显示的文字
     * @param thickness 文字的粗细程度
     * @param color     文字的颜色
     */
    public static void setText(@NonNull TextView textView, CharSequence text, final float thickness, @ColorInt final int color) {
        if (textView != null && !isEmpty(text)) {
            SpannableStringBuilder spannableString = new SpannableStringBuilder(text);
            spannableString.setSpan(new CharacterStyle() {
                @Override
                public void updateDrawState(TextPaint textPaint) {
                    //tp.setFakeBoldText(true);//一种伪粗体效果，比原字体加粗的效果弱一点
                    textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                    textPaint.setColor(color);//字体颜色
                    textPaint.setStrokeWidth(thickness > 0 ? thickness : 0);//控制字体加粗的程度
                }
            }, 0, isEmpty(text) ? 0 : text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            textView.setText(spannableString);
        }
    }


    /**
     * 判断两个字符串是否相等
     *
     * @param a 字符串a
     * @param b 字符串b
     * @return true-相等 false-不相等
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 获取字符串的长度
     *
     * @param str 输入的字符串
     * @return 字符串的长度
     */
    public static int length(CharSequence str) {
        return isEmpty(str) ? 0 : str.length();
    }

    /**
     * 为TextView设置Drawable图片
     *
     * @param textView     文字控件 TextView
     * @param charSequence 设置的文字
     * @param resId        图片的资源id
     * @param gravity      位置
     */
    public static void setDrawable(TextView textView, CharSequence charSequence, int resId, @Gravity int gravity) {
        setDrawable(textView, charSequence, resId, 0, gravity);
    }

    /**
     * 为TextView设置Drawable图片
     *
     * @param textView        文字控件 TextView
     * @param charSequence    设置的文字
     * @param resId           图片的资源id
     * @param drawablePadding 文字和Icon的距离
     * @param gravity         位置
     */
    public static void setDrawable(TextView textView, CharSequence charSequence, int resId, int drawablePadding, @Gravity int gravity) {
        if (textView != null) {
            Context context = textView.getContext();
            Drawable imgDrawable = context.getResources().getDrawable(resId);//获取资源图片
            setDrawable(textView, imgDrawable, drawablePadding, gravity);
            textView.setText(isEmpty(charSequence) ? "" : charSequence);
        }
    }

    /**
     * 为TextView设置Drawable图片
     *
     * @param textView     文字控件 TextView
     * @param charSequence 设置的文字
     * @param imgDrawable  图片
     * @param gravity      位置
     */
    public static void setDrawable(TextView textView, CharSequence charSequence, Drawable imgDrawable, @Gravity int gravity) {
        setDrawable(textView, charSequence, imgDrawable, 0, gravity);
    }

    /**
     * 为TextView设置Drawable图片
     *
     * @param textView        文字控件 TextView
     * @param charSequence    设置的文字
     * @param imgDrawable     图片
     * @param drawablePadding 文字和Icon的距离
     * @param gravity         位置
     */
    public static void setDrawable(TextView textView, CharSequence charSequence, Drawable imgDrawable, int drawablePadding, @Gravity int gravity) {
        if (textView != null) {
            setDrawable(textView, imgDrawable, drawablePadding, gravity);
            textView.setText(isEmpty(charSequence) ? "" : charSequence);
        }
    }

    /**
     * 为TextView设置Drawable图片
     *
     * @param textView 文字控件 TextView
     * @param resId    图片的资源id
     * @param gravity  位置
     */
    public static void setDrawable(TextView textView, int resId, @Gravity int gravity) {
        setDrawable(textView, resId, 0, gravity);
    }

    /**
     * 为TextView设置Drawable图片
     *
     * @param textView        文字控件 TextView
     * @param resId           图片的资源id
     * @param drawablePadding 图片和文字的间距
     * @param gravity         位置
     */
    public static void setDrawable(TextView textView, int resId, int drawablePadding, @Gravity int gravity) {
        if (textView != null) {
            Context context = textView.getContext();
            Drawable imgDrawable = context.getResources().getDrawable(resId);//获取资源图片

            setDrawable(textView, imgDrawable, drawablePadding, gravity);
        }
    }

    /**
     * 为TextView设置Drawable图片
     *
     * @param textView    文字控件 TextView
     * @param imgDrawable 图片
     * @param gravity     位置
     */
    public static void setDrawable(TextView textView, Drawable imgDrawable, @Gravity int gravity) {
        setDrawable(textView, imgDrawable, 0, gravity);
    }

    /**
     * 为TextView设置Drawable图片
     *
     * @param textView        文字控件 TextView
     * @param imgDrawable     图片
     * @param drawablePadding 图片和文字的间距
     * @param gravity         位置
     */
    public static void setDrawable(TextView textView, Drawable imgDrawable, int drawablePadding, @Gravity int gravity) {
        if (textView != null) {
            switch (gravity) {
                case LEFT:
                    //左侧icon
                    textView.setCompoundDrawablesWithIntrinsicBounds(imgDrawable, null, null, null);
                    break;
                case TOP:
                    //上边icon
                    textView.setCompoundDrawablesWithIntrinsicBounds(null, imgDrawable, null, null);
                    break;
                case RIGHT:
                    //右侧icon
                    textView.setCompoundDrawablesWithIntrinsicBounds(null, null, imgDrawable, null);
                    break;
                case BOTTOM:
                    //下边icon
                    textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, imgDrawable);
                    break;
            }
            textView.setCompoundDrawablePadding(drawablePadding);
        }
    }

    /**
     * 为TextView设置Drawable图片
     *
     * @param textView        文字控件 TextView
     * @param drawablePadding 图片和文字的间距
     * @param leftDrawable    左图片
     * @param topDrawable     上图片
     * @param rightDrawable   右图片
     * @param bottomDrawable  下图片
     */
    public static void setDrawable(TextView textView, int drawablePadding, Drawable leftDrawable, Drawable topDrawable, Drawable rightDrawable, Drawable bottomDrawable) {
        setDrawable(textView, "", drawablePadding, leftDrawable, topDrawable, rightDrawable, bottomDrawable);
    }

    /**
     * 为TextView设置Drawable图片
     *
     * @param textView        文字控件 TextView
     * @param charSequence    文字内容
     * @param drawablePadding 图片和文字的间距
     * @param leftDrawable    左图片
     * @param topDrawable     上图片
     * @param rightDrawable   右图片
     * @param bottomDrawable  下图片
     */
    public static void setDrawable(TextView textView, CharSequence charSequence, int drawablePadding, Drawable leftDrawable, Drawable topDrawable, Drawable rightDrawable, Drawable bottomDrawable) {
        if (textView != null) {
            textView.setText(isEmpty(charSequence) ? "" : charSequence);
            textView.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, topDrawable, rightDrawable, bottomDrawable);
            textView.setCompoundDrawablePadding(drawablePadding);
        }
    }

    /**
     * 描述位置的常量类
     */
    @IntDef({LEFT, TOP, RIGHT, BOTTOM, CENTER, CENTER_VERTICAL, CENTER_HORIZONTAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Gravity {
    }

}
