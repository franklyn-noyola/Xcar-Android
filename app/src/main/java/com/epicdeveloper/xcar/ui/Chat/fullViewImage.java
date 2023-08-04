package com.epicdeveloper.xcar.ui.Chat;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import com.epicdeveloper.xcar.R;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

public class fullViewImage extends AppCompatActivity {
    private ScaleGestureDetector scaleGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullimagescreen);
        ImageView imageView = (ImageView) findViewById(R.id.fullview);
        Picasso.get().load(fragment_chat.imageView).into(imageView);
        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(imageView);
        pAttacher.update();
    }
}
