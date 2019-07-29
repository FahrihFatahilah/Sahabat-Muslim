package app.com.temanhijrah;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import app.com.temanhijrah.Database.DatabaseHelper;
import app.com.temanhijrah.listsurah.ListSurahActivity;
import app.com.temanhijrah.util.PreferenceApp;

public class ActivityUtama extends AppCompatActivity {

    private static Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new ActivityUtama.FirebaseNotificationOpenedHandler(this))
                .init();

        setContentView(R.layout.activity_utama);
        resources = getResources();
        DatabaseHelper.initDatabase(this);
        PreferenceApp.initPreferences(this);

    }
    public static BufferedReader getRawResources(int res){
        InputStream streamReader = resources.openRawResource(res);
        return new BufferedReader(new InputStreamReader(streamReader));
    }
    public void masjid (View view){
    Intent intent = new Intent(ActivityUtama.this, MasjidFinder.class);
    startActivity(intent);
}
    public void AlQuraan (View view){
        Intent intent = new Intent(ActivityUtama.this, ListSurahActivity.class);
        startActivity(intent);
    }
    public void pelacak (View view){
        Intent intent = new Intent(ActivityUtama.this, DayActivity.class);
        startActivity(intent);
    }
    public void about (View view){
        Intent intent = new Intent(ActivityUtama.this, ActivityAbout.class);
        startActivity(intent);
    }
    public void kajian (View view){
        Intent intent = new Intent(ActivityUtama.this, CeramahListActivity.class);
        startActivity(intent);
    }
    public void asmaulhusna (View view){
        Intent intent = new Intent(ActivityUtama.this, ActivityAsmaulHusna.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void jadwalshalat (View view){
        Intent intent = new Intent(ActivityUtama.this, JadwalShalat.class);
        startActivity(intent);
    }
    public class FirebaseNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        Context ctx;
        FirebaseNotificationOpenedHandler(Context context) {
            ctx = context;
        }
        // This fires when a notification is opened by tapping on it.
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
            JSONObject data = result.notification.payload.additionalData;
            Toast.makeText(ctx, "Halo, saya klik notifikasi ya", Toast.LENGTH_SHORT).show();
            if (data != null) {
                String customKey = data.optString("customkey", null);
                String lagikey = data.optString("lagikey", null);
                if (customKey != null)
                    Log.i("OneSignalExample", "customkey set with value: " + customKey);
                if (lagikey != null)
                    Log.i("OneSignalExample", "lagikey set with value: " + lagikey);
            }
            if (actionType == OSNotificationAction.ActionType.ActionTaken)
                Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);
            Intent intent = new Intent(getApplicationContext(), JadwalKajian.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        }
    }
}




