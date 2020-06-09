package com.example.imageprocbrightnessandroidandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private Bitmap mBitmap;
    private final int READ_REQUEST_CODE = 42;
    private final String ERROR_MESSAGE_SELECT_IMAGE = "画像選択のエラーが発生しました" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case READ_REQUEST_CODE:
                try {
                    if (data.getData() != null)
                    {
                        Uri uri = data.getData();
                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        Bitmap image = BitmapFactory.decodeStream(inputStream);
                        ImageView imageView = findViewById(R.id.image);
                        imageView.setImageBitmap(image);
                        mBitmap = image;
                    }
                } catch (Exception e) {
                    Toast.makeText(this, ERROR_MESSAGE_SELECT_IMAGE, Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * レイアウトの初期化
     */
    private void initLayout() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog);

        Button btnReset = findViewById(R.id.reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnReset();
            }
        });

        SeekBar seekBarAlpha = findViewById(R.id.alpha_seekBar);
        seekBarAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String alphaValue = Integer.toString(progress) + " %";
                TextView textViewAlphaValue = findViewById(R.id.alpha_value);
                textViewAlphaValue.setText(alphaValue);

                Brightness brightness = new Brightness();
                Bitmap mutableBitmap = brightness.goImageProcessing(mBitmap, progress);
                ImageView imageView = findViewById(R.id.image);
                imageView.setImageBitmap(mutableBitmap.copy(Bitmap.Config.ARGB_8888, false));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button btnImageSelect = findViewById(R.id.select_image);
        btnImageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnSelectImage();
            }
        });
    }

    /**
     * リセットボタンのクリックイベント
     */
    private void onClickBtnReset() {
        ImageView imageView = findViewById(R.id.image);
        imageView.setImageBitmap(mBitmap);
    }

    /**
     * 画像選択のクリックイベント
     */
    private void onClickBtnSelectImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }
}