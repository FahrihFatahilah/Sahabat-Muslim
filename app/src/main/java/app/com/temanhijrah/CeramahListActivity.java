package app.com.temanhijrah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import app.com.temanhijrah.Model.model;

public class CeramahListActivity extends AppCompatActivity {

    RecyclerView mRecylcerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ceramah_list);



        //add Glide implementation into the build.gradle file.


        mRecylcerView = findViewById(R.id.recycle);
        mRecylcerView.setHasFixedSize(true);


    mRecylcerView.setLayoutManager(new LinearLayoutManager(this));
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef =  mFirebaseDatabase.getReference("Data");
    }


    protected void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<model,ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<model, ViewHolder>(
                model.class,
                R.layout.row,
                ViewHolder.class,
                mRef
        ) {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, model model, int position) {

                    viewHolder.setDetails(getApplicationContext(),model.getTitle(),model.getDescription(),model.getImage());

            }


            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                ViewHolder viewHolder = super.onCreateViewHolder(parent , viewType);

                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        TextView mTitleTv = view.findViewById(R.id.rTitleTv);
                        TextView mDescTv = view.findViewById(R.id.rDescriptionTv);
                        ImageView mImageView = view.findViewById(R.id.rImageView);
                        String mTitle = mTitleTv.getText().toString();
                        String mDesc = mDescTv.getText().toString();
                        Drawable mDrawable = mImageView.getDrawable();

                        Bitmap mBitmap =((BitmapDrawable)mDrawable).getBitmap();
                        Intent intent = new Intent(view.getContext(), KajianDetailActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        mBitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                        byte [] bytes = stream.toByteArray();
                        intent.putExtra("images",bytes);
                        intent.putExtra("title",mTitle);
                        intent.putExtra("description",mDesc);
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });

                    return viewHolder;
            }
        };
            mRecylcerView.setAdapter(firebaseRecyclerAdapter);

    }

}
