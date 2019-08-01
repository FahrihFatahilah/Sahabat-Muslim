package app.com.temanhijrah;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.mbms.StreamingService;


public class StreamingReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String param = intent.getAction();
        if (param.equals("exit")) {
            Intent service1 = new Intent(context, StreamingService.class);
            context.stopService(service1);
        } else if (param.equals("stop")) {
            context.sendBroadcast(new Intent("stop"));
        } else if (param.equals("start")) {
            context.sendBroadcast(new Intent("start"));
        }
    }
}
