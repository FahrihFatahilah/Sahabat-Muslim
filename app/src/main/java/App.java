import android.app.Application;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import app.com.temanhijrah.Database.DatabaseHelper;
import app.com.temanhijrah.util.PreferenceApp;

/**
 * Created by jonesrandom on 2/20/18.
 *
 * @site www.androidexample.web.id
 * @github @alfianyusufabdullah
 */

public class App extends Application {

    private static Resources resources;

    @Override
    public void onCreate() {
        super.onCreate();

        resources = getResources();
        DatabaseHelper.initDatabase(this);
        PreferenceApp.initPreferences(this);
    }

    public static BufferedReader getRawResources(int res){
        InputStream streamReader = resources.openRawResource(res);
        return new BufferedReader(new InputStreamReader(streamReader));
    }

}
