package app.com.temanhijrah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.com.temanhijrah.base.BaseActivity;
import app.com.temanhijrah.listsurah.ListSurahActivity;
import butterknife.BindView;


public class SplashScreen extends BaseActivity<SplashscreenPresenter> implements SplashscreenView {
    private int waktu_loading=4000;
    @BindView(R.id.layoutLoadingInfo)
    LinearLayout mLayoutLoadingInfo;

    @BindView(R.id.progressInfo)
    ProgressBar mProgressInfo;

    String[] permissions = new String[]{
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION};

    private static final int REQUEST_MULTIPLE_PERMISSIONS = 117;
    private ProgressDialog progressDialog;
    public SplashscreenPresenter initPresenter() {
        return new SplashscreenPresenter(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean perms = checkPermissions();

        if (perms) {


            if (savedInstanceState != null) {

            }
        }
        setContentView(R.layout.activity_splash_screen);
        mPresenter.startPrepareData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //setelah loading maka akan langsung berpindah ke home activity
                Intent home=new Intent(SplashScreen.this, ActivityUtama.class);
                startActivity(home);
                finish();




            }


        },waktu_loading);

    }
    public void onProgress(int progress) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mProgressInfo.setProgress(progress, true);
        } else {
            mProgressInfo.setProgress(progress);
        }
    }

    public void onSuccess() {

    }
    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
    public void onPrepare() {
        mLayoutLoadingInfo.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                } else {
                    finish();
                    Toast.makeText(SplashScreen.this, "Please Grant All Permission to Use All Feature", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }

    }
}
