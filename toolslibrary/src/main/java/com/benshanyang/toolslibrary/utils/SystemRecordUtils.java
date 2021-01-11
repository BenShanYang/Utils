package com.benshanyang.toolslibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: SystemRecordUtils
 * @Description: 调用系统的视频录制
 * @Author: YangKuan
 * @Date: 2020/12/16 9:17
 */
public class SystemRecordUtils {

    private static final int SYSTEM_RECORD_REQUESTCODE = 1001;//录制视频的请求码

    /**
     * 视频id
     */
    public static final String VIDEO_ID = MediaStore.Video.Media._ID;
    /**
     * 视频名称
     */
    public static final String TITLE = MediaStore.Video.Media.TITLE;
    /**
     * 视频路径
     */
    public static final String VIDEO_PATH = MediaStore.Video.Media.DATA;
    /**
     * 视频时长
     */
    public static final String DURATION = "duration";
    /**
     * 视频大小
     */
    public static final String SIZE = MediaStore.Video.Media.SIZE;
    /**
     * 视频缩略图路径
     */
    public static final String THUMBNAIL_PATH = MediaStore.Images.Media.DATA;
    /**
     * 缩略图id
     */
    public static final String THUMBNAIL_ID = MediaStore.Images.Media._ID;

    /**
     * 调用系统相机录视视频
     *
     * @param activity 上下文
     */
    public static void recordVideo(Activity activity) {
        recordVideo(activity, null, null, SYSTEM_RECORD_REQUESTCODE);
    }

    /**
     * 调用系统相机录视视频
     *
     * @param activity 上下文
     * @param fileName 视频名称
     */
    public static void recordVideo(Activity activity, String fileName) {
        recordVideo(activity, null, fileName, SYSTEM_RECORD_REQUESTCODE);
    }

    /**
     * 调用系统相机录视视频
     *
     * @param activity 上下文
     * @param filePath 视频所在文件夹路径
     * @param fileName 视频名称
     */
    public static void recordVideo(Activity activity, String filePath, String fileName) {
        recordVideo(activity, filePath, fileName, SYSTEM_RECORD_REQUESTCODE);
    }

    /**
     * 调用系统相机录视视频
     *
     * @param activity    上下文
     * @param filePath    视频所在文件夹路径
     * @param fileName    视频名称
     * @param requestCode 请求码
     */
    public static void recordVideo(Activity activity, String filePath, String fileName, int requestCode) {
        recordVideo(activity, filePath, fileName, -1, -1, requestCode);
    }

    /**
     * 调用系统相机录视视频
     *
     * @param activity    上下文
     * @param filePath    视频所在文件夹路径
     * @param fileName    视频名称
     * @param quality     录制视频的质量，从 0-1，越大表示质量越好，同时视频也越大
     * @param duration    设置视频录制的最长时间 单位秒
     * @param requestCode 请求码
     */
    public static void recordVideo(Activity activity, String filePath, String fileName, int quality, int duration, int requestCode) {
        //将拍摄的照片保存在一个指定好的文件下
        if (activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
            String videoPath = TextUtils.isEmpty(filePath) ? Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() : filePath;
            String videoName = TextUtils.isEmpty(fileName) ? String.format("%s.mp4", System.currentTimeMillis()) : fileName;
            String fullPath = String.format("%s/%s", videoPath, videoName);

            Uri uri = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) ? Uri.parse(fullPath) : Uri.fromFile(new File(fullPath));

            //调用系统相机
            Intent intentVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intentVideo.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
            }
            intentVideo.addCategory(Intent.CATEGORY_DEFAULT);
            intentVideo.putExtra(MediaStore.EXTRA_OUTPUT, uri);//将拍照结果保存至photo_file的Uri中
            if (quality >= 0 && quality <= 1) {
                intentVideo.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, quality);// MediaStore.EXTRA_VIDEO_QUALITY 表示录制视频的质量，从 0-1，越大表示质量越好，同时视频也越大
            }
            if (duration > 0) {
                intentVideo.putExtra(MediaStore.EXTRA_DURATION_LIMIT, duration); // 设置视频录制的最长时间 单位秒
            }
            activity.startActivityForResult(intentVideo, requestCode);
        }
    }

    /**
     * 获取视频信息
     *
     * @param context  上下文
     * @param videoUri 视频的Uri
     *                 通过 onActivityResult(int requestCode, int resultCode, Intent data) 方法的intent 获取 Uri videoUri = data.getData();
     * @return 返回视频信息
     */
    public static Map<String, Object> getVideoInformation(Context context, Uri videoUri) {
        Map<String, Object> videoInfor = null;
        Cursor cursor = (context == null) ? null : context.getContentResolver().query(videoUri, null, null, null, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                videoInfor = new HashMap<String, Object>();
                // 视频ID:MediaStore.Audio.Media._ID
                int videoId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                videoInfor.put(VIDEO_ID, videoId);

                // 视频名称：MediaStore.Audio.Media.TITLE
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                videoInfor.put(TITLE, title);

                // 视频路径：MediaStore.Audio.Media.DATA
                String videoPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                videoInfor.put(VIDEO_PATH, videoPath);

                // 视频时长：MediaStore.Audio.Media.DURATION
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                    videoInfor.put(DURATION, duration);
                } else {
                    //https://www.jianshu.com/p/d26e7d788c0e
                    MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
                    metadataRetriever.setDataSource(context, videoUri);
                    String durationString = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                    videoInfor.put(DURATION, !TextUtils.isEmpty(durationString) ? Long.parseLong(durationString) : 0L);
                    metadataRetriever.release();
                }

                // 视频大小：MediaStore.Audio.Media.SIZE
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                videoInfor.put(SIZE, size);

                // 视频缩略图路径：MediaStore.Images.Media.DATA
                String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                videoInfor.put(THUMBNAIL_PATH, imagePath);

                // 缩略图ID:MediaStore.Audio.Media._ID
                int imageId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                videoInfor.put(THUMBNAIL_ID, imageId);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return videoInfor;
    }

}
