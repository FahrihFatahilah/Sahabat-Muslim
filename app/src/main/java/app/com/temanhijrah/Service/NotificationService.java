package app.com.temanhijrah.Service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;



import java.util.ArrayList;
import java.util.Calendar;

import app.com.temanhijrah.Database.AppDatabase;
import app.com.temanhijrah.Database.PrayerData;
import app.com.temanhijrah.JadwalShalat;
import app.com.temanhijrah.R;

public class NotificationService extends IntentService {

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.getAction().equals("scheduling")) {
            scheduleNotifications();
        } else if (intent.getAction().equals("push_notification")) {
            pushNotification(intent.getStringExtra("prayer"));
        }
    }

    private void scheduleNotifications() {
        AppDatabase database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "prayer_notifier").build();


        Calendar calendar = Calendar.getInstance();
        String currentMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        if (currentMonth.charAt(0) != '1') {
            currentMonth = "0" + currentMonth;
        }
        String prayTodayData = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "-" +
                currentMonth + "-" +
                String.valueOf(calendar.get(Calendar.YEAR));

        PrayerData prayerData = database.prayDao().loadByDate(prayTodayData);
        if (prayerData != null) {

            ArrayList<Integer> times = new ArrayList<>();
            times.add(Integer.valueOf(prayerData.getFajr().substring(0, 2)));
            times.add(Integer.valueOf(prayerData.getFajr().substring(3, 5)));

            times.add(Integer.valueOf(prayerData.getDhuhr().substring(0, 2)));
            times.add(Integer.valueOf(prayerData.getDhuhr().substring(3, 5)));

            times.add(Integer.valueOf(prayerData.getAsr().substring(0, 2)));
            times.add(Integer.valueOf(prayerData.getAsr().substring(3, 5)));

            times.add(Integer.valueOf(prayerData.getMghrib().substring(0, 2)));
            times.add(Integer.valueOf(prayerData.getMghrib().substring(3, 5)));

            times.add(Integer.valueOf(prayerData.getIsha().substring(0, 2)));
            times.add(Integer.valueOf(prayerData.getIsha().substring(3, 5)));

            int j = 0;

            //schedule notification for all prayers
            for (int i = 0; i < 5; ++i) {
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                c.set(Calendar.HOUR_OF_DAY, times.get(j++));
                c.set(Calendar.MINUTE, times.get(j++));
                c.set(Calendar.SECOND, 0);

                //if we passed the time of the notification, don't schedule it for today
                if (!(c.getTimeInMillis() < System.currentTimeMillis())) {
                    Intent intent = new Intent(getApplicationContext(), NotificationService.class);
                    intent.setAction("push_notification");
                    intent.putExtra("prayer", String.valueOf(i));//value of i is important here

                    PendingIntent pendingIntent = PendingIntent.getService(this, i, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    if (alarmManager != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                                    c.getTimeInMillis(), pendingIntent);
                        } else {
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                                    c.getTimeInMillis(), pendingIntent);
                        }
                    }
                }
            }
        }
    }

    private void pushNotification(String prayer) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "1")
                .setContentTitle("Prayer Time")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(R.mipmap.ic_notification)
                .setContentIntent(PendingIntent.getActivity(
                        getApplicationContext(),
                        Integer.valueOf(prayer),
                        new Intent(getApplicationContext(), JadwalShalat.class),
                        PendingIntent.FLAG_CANCEL_CURRENT))
                .setAutoCancel(true);

        switch (prayer) {
            case "0":
                mBuilder.setContentText("It's alfajr prayer time");
                break;
            case "1":
                mBuilder.setContentText("It's aldhur prayer time");
                break;
            case "2":
                mBuilder.setContentText("It's alasr prayer time");
                break;
            case "3":
                mBuilder.setContentText("It's almaghrib prayer time");
                break;
            case "4":
                mBuilder.setContentText("It's alisha prayer time");
                break;
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String CHANNEL_ID = "1";
            String name = "PrayNotifier";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.enableVibration(true);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(mChannel);
            }
        }

        // notificationId is a unique int for each notification that you must define
        if (notificationManager != null) {
            notificationManager.notify(0, mBuilder.build());
        }
    }
}
