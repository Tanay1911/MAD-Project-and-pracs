package com.example.prac8;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    // Mobile model Names
    String[] mobileNames = {
            "iPhone 16 Plus",
            "Samsung S25 Ultra",
            "Pixel 9 Pro",
            "Vivo V40",
            "Asus ROG Phone 7",
            "Oppo Reno 12 5G"
    };
    // Mobile model URLs (iPhone 15, Samsung S24 Ultra, Pixel 8, etc.)
    String[] mobileUrls = {
            "https://inspireonline.in/cdn/shop/files/iPhone_16_Plus_Ultramarine_PDP_Image_Position_1__en-IN_2bbd3f38-52cd-4666-b40e-ad1fb8ad9c3f.jpg?v=1727248677&width=1920", // iPhone 16 Plus
            "https://cdn.beebom.com/mobile/samsung-galaxy-s25-ultra-front-and-back-1.png", // Samsung S25 Ultra
            "https://media-ik.croma.com/prod/https://media.tatacroma.com/Croma%20Assets/Communication/Mobiles/Images/309165_0_gvylu0.png?tr=w-600", // Pixel 9 Pro
            "https://cdn.beebom.com/mobile/vivo-v40-front-and-back-1.png", // Vivo V40
            "https://www.xtechzplus.com/cdn/shop/files/h732_53aaa49c-a42e-488b-8105-777f007f1d5c.png?v=1684567915", // Asus rog phone 7
            "https://cdn.beebom.com/mobile/2024/05/Oppo-Reno-12.png" // Oppo Reno 12 5G
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = findViewById(R.id.gridView);
        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);
    }

    private class CustomAdapter extends BaseAdapter {
        private final ExecutorService executor = Executors.newFixedThreadPool(4);
        private final Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public int getCount() {
            return mobileUrls.length;
        }

        @Override
        public Object getItem(int i) {
            return mobileUrls[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.grid_item, viewGroup, false);
            }
            ImageView imageView = view.findViewById(R.id.image_view);
            TextView textView = view.findViewById(R.id.text_view);

            textView.setText(mobileNames[i]);
            imageView.setImageResource(android.R.drawable.ic_menu_gallery);

            String imageUrl = mobileUrls[i];
            executor.execute(() -> {
                try {
                    URL url = new URL(imageUrl);
                    InputStream inputStream = url.openConnection().getInputStream();
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    handler.post(() -> {
                        imageView.setImageBitmap(bitmap);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return view;
        }
    }
}
