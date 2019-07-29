package app.com.temanhijrah;

import android.os.Bundle;
import android.widget.TextView;

import app.com.temanhijrah.R;
import app.com.temanhijrah.ToolbarActivity;
import app.com.temanhijrah.bean.Prayer;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.emilsjolander.intentbuilder.Extra;
import se.emilsjolander.intentbuilder.IntentBuilder;

@IntentBuilder
public class PrayerActivity extends ToolbarActivity {

    @BindView(R.id.name) TextView name;
    @BindView(R.id.description) TextView description;

    @Extra Prayer prayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer);
        ButterKnife.bind(this);
        PrayerActivityIntentBuilder.inject(getIntent(), this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name.setText(prayer.getLabelResId());
    }

}
