package app.com.temanhijrah;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import app.com.temanhijrah.bean.Place;
import app.com.temanhijrah.bean.Prayer;
import app.com.temanhijrah.bean.Status;
import app.com.temanhijrah.util.ViewUtils;


import butterknife.ButterKnife;

public class PrayerView extends LinearLayout {


    @BindView(R.id.root) ViewGroup root;
    @BindView(R.id.name) TextView lblName;
    @BindView(R.id.status) ImageView imgStatus;
    @BindView(R.id.place) ImageView imgPlace;

    private Prayer prayer;
    private Status status;
    private Place place;

    public PrayerView(Context context) {
        this(context, null, 0);
    }

    public PrayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PrayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.view_prayer, this, true);
        ButterKnife.bind(this);

        if (attrs == null) {
            setPrayer(Prayer.FAJR);
            setStatus(Status.DEFAULT);
            setPlace(Place.DEFAULT);
        } else {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.PrayerView,
                    0, 0);

            try {
                int prayerId = a.getInt(R.styleable.PrayerView_prayer, Prayer.FAJR.getId());
                setPrayer(Prayer.ofId(prayerId));

                int statusId = a.getInt(R.styleable.PrayerView_name, Status.UNSET.getId());
                setStatus(Status.ofId(statusId));
            } finally {
                a.recycle();
            }
        }
    }

    public void setPrayer(Prayer prayer) {
        this.prayer = prayer;
        root.setBackgroundResource(prayer.getBgResId());
        lblName.setText(prayer.getLabelResId());
        lblName.setTextSize(15);
    }

    public void setStatus(Status status) {
        this.status = status;
        imgStatus.setContentDescription(getResources().getString(status.getLabelResId()));
        imgStatus.setImageResource(status.getIconResId());
        ViewUtils.setVisibility(imgStatus, status.getIconResId() != 0, GONE);
    }

    public void setPlace(Place place) {
        this.place = place;
        imgPlace.setContentDescription(getResources().getString(place.getLabelResId()));
        imgPlace.setImageResource(place.getIconResId());
        ViewUtils.setVisibility(imgPlace, place.getIconResId() != 0 && this.status.isDone(), GONE);
    }

    public Prayer getPrayer() {
        return prayer;
    }

    public Status getStatus() {
        return status;
    }

    public Place getPlace() {
        return place;
    }

    public String getPrayerName() {
        return getResources().getString(prayer.getLabelResId());
    }
}
