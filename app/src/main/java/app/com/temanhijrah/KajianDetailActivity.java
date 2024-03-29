package app.com.temanhijrah;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class KajianDetailActivity extends AppCompatActivity {
    TextView mTitleTv,mDetailTv;
    ImageView mImageIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kajian_detail);
            mTitleTv = findViewById(R.id.titleTv);
            mDetailTv = findViewById(R.id.descriptionTv);
            mImageIv = findViewById(R.id.imageView);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

            byte[]  bytes = getIntent().getByteArrayExtra("images");
            String title  = getIntent().getStringExtra("title");
            String desc   = getIntent().getStringExtra("description");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

        mTitleTv.setText(title);
        mDetailTv.setText(desc);
        mImageIv.setImageBitmap(bmp);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
