package com.example.imageprocbrightnessandroidjava;

import android.graphics.Bitmap;

public class Brightness {
    /**
     * コンストラクタ
     */
    public Brightness() {};

    /**
     * グレースケールの実行
     * @args bitmap ビットマップ
     * @args alphaValue  明るさ　%
     * @return return グレースケール後のビットマップ
     */
    public Bitmap goImageProcessing(Bitmap bitmap, int alphaValue) {
        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        int[] resultPixels = new int[bitmap.getWidth() * bitmap.getHeight()];

        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        int count = 0;
        for (int argb : pixels) {
            int red = android.graphics.Color.red(argb);
            int green = android.graphics.Color.green(argb);
            int blue = android.graphics.Color.blue(argb);
            int alpha = android.graphics.Color.alpha(argb) * alphaValue / 100;
            int gray = (red + green + blue) / 3;
            resultPixels[count++] = android.graphics.Color.argb(alpha, red, green, blue);
        }

        Bitmap mutableBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        mutableBitmap.setPixels(resultPixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        return mutableBitmap;
    }
}
