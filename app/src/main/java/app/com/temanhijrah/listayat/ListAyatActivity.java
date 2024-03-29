package app.com.temanhijrah.listayat;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import app.com.temanhijrah.Model.Ayat;
import app.com.temanhijrah.R;
import app.com.temanhijrah.base.BaseActivity;
import butterknife.BindView;

public class ListAyatActivity extends BaseActivity<ListAyatPresenter> implements ListAyatView {



    @BindView(R.id.listAyat)
    RecyclerView listAyat;

    private ListAyatAdapter listAyatAdapter;

    public static final String KEY_SURAH = "surah";
    public static final String KEY_AYAT = "ayat";
    public static final String KEY_TERJEMAHAN = "terjemahan";
    public static final String KEY_LOAD_TERJEMAHAN = "load_terjemahan";

    @Override
    public ListAyatPresenter initPresenter() {
        return new ListAyatPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ayat);

        String surah = getIntent().getStringExtra(KEY_SURAH);
        String ayat = getIntent().getStringExtra(KEY_AYAT);
        String terjemahan = getIntent().getStringExtra(KEY_TERJEMAHAN);
        String loadTerjemahan = getIntent().getStringExtra(KEY_LOAD_TERJEMAHAN);

        listAyatAdapter = new ListAyatAdapter(new ArrayList<Ayat>());

        listAyat.setHasFixedSize(true);
        listAyat.setLayoutManager(new LinearLayoutManager(this));
        listAyat.setAdapter(listAyatAdapter);

        mPresenter.loadAyat(surah, loadTerjemahan);
    }

    @Override
    public void onLoad(ArrayList<Ayat> data) {
        listAyatAdapter.refresh(data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
