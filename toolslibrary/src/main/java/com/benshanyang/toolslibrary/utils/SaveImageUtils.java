package com.benshanyang.toolslibrary.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @ClassName: SaveFile
 * @Description: 保存文件到本地
 * @Author: YangKuan
 * @Date: 2020/12/15 16:58
 */
public class SaveImageUtils {

    /**
     * 保存图片
     *
     * @param context 上下文
     * @param bitmap  Bitmap
     * @return true-保存成功 false-保存失败
     */
    public static boolean savePicture(Context context, Bitmap bitmap) {
        return savePicture(context, bitmap, null);
    }

    /**
     * 保存图片
     *
     * @param context  上下文
     * @param drawable Bitmap
     * @return true-保存成功 false-保存失败
     */
    public static boolean savePicture(Context context, Drawable drawable) {
        return savePicture(context, drawable2Bitmap(drawable), null);
    }

    /**
     * 保存图片
     *
     * @param context  上下文
     * @param drawable Bitmap
     * @param name     图片名称
     * @return true-保存成功 false-保存失败
     */
    public static boolean savePicture(Context context, Drawable drawable, CharSequence name) {
        return savePicture(context, drawable2Bitmap(drawable), name);
    }

    /**
     * 将Bitmap保存到本地
     *
     * @param context 上下文
     * @param bitmap  Bitmap
     * @param name    图片名称
     * @return true-保存成功 false-保存失败
     */
    public static boolean savePicture(Context context, Bitmap bitmap, CharSequence name) {
        boolean saveFlag = true;
        OutputStream fOut = null;
        try {
            File childFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            String filePath = childFolder.getAbsolutePath() + "/" + (TextUtils.isEmpty(name) ? System.currentTimeMillis() : name) + ".png";
            File imageFile = new File(filePath);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                //适配Android 10
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DESCRIPTION, "This is an image");
                values.put(MediaStore.Images.Media.DISPLAY_NAME, "Image.png");
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
                values.put(MediaStore.Images.Media.TITLE, "Image.png");
                values.put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/");

                ContentResolver resolver = context.getContentResolver();
                Uri insertUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                if (insertUri != null) {
                    fOut = resolver.openOutputStream(insertUri);
                }
            } else {
                fOut = new FileOutputStream(imageFile);
            }
            if (fOut != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fOut);//将bg输出至文件
                fOut.flush();
                ((FileOutputStream) fOut).getFD().sync();
            }
            //保存图片后发送广播通知更新数据库
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imageFile)));
        } catch (Exception e) {
            e.printStackTrace();
            saveFlag = false;
        } finally {
            try {
                if (fOut != null) {
                    fOut.close(); // do not forget to close the stream
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return saveFlag;
    }

    /**
     * Drawable转换成Bitmap
     *
     * @param drawable 源图片的Drawable
     * @return 转换后的Bitmap
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }


}
