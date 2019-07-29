package app.com.temanhijrah;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import app.com.temanhijrah.bean.DateString;
import app.com.temanhijrah.bean.Place;
import app.com.temanhijrah.bean.Prayer;
import app.com.temanhijrah.bean.Status;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DayActivity extends ToolbarActivity implements PrayerDialog.Callback {

    public static final String EXTRA_DATE_TIME = "date_time";

    @BindView(app.com.temanhijrah.R.id.day_name) TextView dayName;
    @BindView(app.com.temanhijrah.R.id.date) TextView date;
    @BindView(app.com.temanhijrah.R.id.btn_prev_day) ImageButton btnPrevDay;
    @BindView(app.com.temanhijrah.R.id.btn_next_day) ImageButton btnNextDay;
    @BindView(app.com.temanhijrah.R.id.prayer_fajr) PrayerView prayerFajr;
        @BindView(app.com.temanhijrah.R.id.prayer_duha) PrayerView prayerDuha;
    @BindView(app.com.temanhijrah.R.id.prayer_zuhr) PrayerView prayerZuhr;
    @BindView(app.com.temanhijrah.R.id.prayer_asr) PrayerView prayerAsr;
    @BindView(app.com.temanhijrah.R.id.prayer_maghrib) PrayerView prayerMaghrib;
    @BindView(app.com.temanhijrah.R.id.prayer_isha) PrayerView prayerIsha;
    @BindView(R.id.prayer_tahajud) PrayerView prayerTahajud;
    @BindView(R.id.prayer_rawatib) PrayerView prayerRawatib;
    @BindView(app.com.temanhijrah.R.id.scroll_view) ObservableScrollView scrollView;

    private DateTime currentDateTime;
    private UserRecords userRecords;
    private float maxScrollOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        setContentView(app.com.temanhijrah.R.layout.activity_day);
        ButterKnife.bind(this);

        maxScrollOffset = getResources().getDimension(app.com.temanhijrah.R.dimen.header_height);
        maxScrollOffset -= getToolbar().getHeight();
        maxScrollOffset -= getStatusBarHeight();
        scrollView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

            }

            @Override
            public void onDownMotionEvent() {
            }

            @Override
            public void onUpOrCancelMotionEvent(ScrollState scrollState) {
            }
        });


        userRecords = new UserRecords(this);

        onNewIntent(getIntent());
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    @Override
    protected void onToolbarSet(Toolbar toolbar) {
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
    }

    private void updateToolbarAlpha(float alpha) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        DateTime dateTime = intent.hasExtra(EXTRA_DATE_TIME)
                ? (DateTime) intent.getSerializableExtra(EXTRA_DATE_TIME)
                : DateTime.now();

        setCurrentDateTime(dateTime);
        loadRecords(dateTime);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(app.com.temanhijrah.R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case app.com.temanhijrah.R.id.action_calendar:
                startActivity(new CalendarActivityIntentBuilder()
                        .defDateTime(currentDateTime)
                        .build(this));
                return true;

            case app.com.temanhijrah.R.id.action_settings:
                startActivity(new Intent(this, PrefsActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadRecords(DateTime dateTime) {
        DateString dateString = DateString.of(dateTime);

        // TODO refactor this
        prayerFajr.setStatus(userRecords.getStatus(dateString, Prayer.FAJR));
        prayerZuhr.setStatus(userRecords.getStatus(dateString, Prayer.ZUHR));
        prayerTahajud.setStatus(userRecords.getStatus(dateString, Prayer.TAHAJUD));
        prayerRawatib.setStatus(userRecords.getStatus(dateString, Prayer.RAWATIB));
        prayerAsr.setStatus(userRecords.getStatus(dateString, Prayer.ASR));
        prayerMaghrib.setStatus(userRecords.getStatus(dateString, Prayer.MAGHRIB));
        prayerIsha.setStatus(userRecords.getStatus(dateString, Prayer.ISHA));
        prayerDuha.setStatus(userRecords.getStatus(dateString, Prayer.DUHA));
        prayerFajr.setPlace(userRecords.getPlace(dateString, Prayer.FAJR));
        prayerZuhr.setPlace(userRecords.getPlace(dateString, Prayer.ZUHR));
        prayerAsr.setPlace(userRecords.getPlace(dateString, Prayer.ASR));
        prayerMaghrib.setPlace(userRecords.getPlace(dateString, Prayer.MAGHRIB));
        prayerIsha.setPlace(userRecords.getPlace(dateString, Prayer.ISHA));
        prayerDuha.setPlace(userRecords.getPlace(dateString, Prayer.DUHA));
        prayerRawatib.setPlace(userRecords.getPlace(dateString, Prayer.RAWATIB));
        prayerTahajud.setPlace(userRecords.getPlace(dateString, Prayer.TAHAJUD));
    }

    @OnClick({
            app.com.temanhijrah.R.id.prayer_fajr,
            app.com.temanhijrah.R.id.prayer_zuhr,
            app.com.temanhijrah.R.id.prayer_asr,
            app.com.temanhijrah.R.id.prayer_maghrib,
            app.com.temanhijrah.R.id.prayer_isha,
            app.com.temanhijrah.R.id.prayer_duha,
            app.com.temanhijrah.R.id.prayer_tahajud,
            app.com.temanhijrah.R.id.prayer_rawatib
    })
    public void showStatusDialog(final PrayerView prayerView) {
        PrayerDialog.newInstance(DateString.of(currentDateTime), prayerView)
                .show(getSupportFragmentManager(), "prayer-dialog");
    }

    @Override
    public void updatePrayerRecord(DateString date, Prayer prayer, Status status, Place place) {
        if (!DateString.of(currentDateTime).equals(date)) {
            return;
        }

        getPrayerView(prayer).setStatus(status);
        getPrayerView(prayer).setPlace(place);
        userRecords.setStatus(date, prayer, status);
        userRecords.setPlace(date, prayer, place);
    }

    private PrayerView getPrayerView(Prayer prayer) {
        if (Prayer.FAJR.equals(prayer)) {
            return prayerFajr;
        } else if (Prayer.ZUHR.equals(prayer)) {
            return prayerZuhr;
        } else if (Prayer.ASR.equals(prayer)) {
            return prayerAsr;
        } else if (Prayer.MAGHRIB.equals(prayer)) {
            return prayerMaghrib;
        } else if (Prayer.ISHA.equals(prayer)) {
            return prayerIsha;
        } else if (Prayer.DUHA.equals(prayer)) {
            return prayerDuha;
        } else if (Prayer.TAHAJUD.equals(prayer)) {
            return prayerTahajud;
        } else if (Prayer.RAWATIB.equals(prayer)) {
            return prayerRawatib;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void showPrayerDetail(Prayer prayer) {
        startActivity(new PrayerActivityIntentBuilder(prayer).build(this));
    }

    @OnClick(app.com.temanhijrah.R.id.btn_prev_day)
    public void prevDay() {
        setCurrentDateTime(currentDateTime.minusDays(1));
    }

    @OnClick(app.com.temanhijrah.R.id.btn_next_day)
    public void nextDay() {
        setCurrentDateTime(currentDateTime.plusDays(1));
    }

    public void setCurrentDateTime(DateTime currentDateTime) {
        this.currentDateTime = currentDateTime;

        loadRecords(currentDateTime);
        dayName.setText(currentDateTime.toString("EEEE"));
        date.setText(currentDateTime.toString(DateTimeFormat.mediumDate()));

        boolean isToday = DateString.of(currentDateTime).equals(DateString.today());
        btnNextDay.setAlpha(isToday ? 0.3f : 1.0f);
        btnNextDay.setEnabled(!isToday);
    }


}
