package app.com.temanhijrah.listayat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import app.com.temanhijrah.Model.Ayat;
import app.com.temanhijrah.R;
import butterknife.BindViews;
import butterknife.ButterKnife;


class ListAyatAdapter extends RecyclerView.Adapter<ListAyatAdapter.AyatHolder> {
    private final int limit = 113;
    private ArrayList<Ayat> ayatList;

    ListAyatAdapter(ArrayList<Ayat> data) {
        this.ayatList = data;
    }

    @Override
    public AyatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_ayat, parent, false);
        return new AyatHolder(view);
    }

    @Override
    public void onBindViewHolder(AyatHolder holder, int position) {
        holder.setContent(ayatList.get(position));
    }

    @Override
    public int getItemCount() {
        if (ayatList.size() > limit) {
            return limit;
        } else {
            return ayatList.size();
        }
    }

    void refresh(ArrayList<Ayat> fill) {
        ayatList = new ArrayList<>();
        ayatList.addAll(fill);

        notifyDataSetChanged();
    }

    class AyatHolder extends RecyclerView.ViewHolder {

        @BindViews({R.id.rowAyat, R.id.rowArabic, R.id.rowTerjemahan})
        List<TextView> rowAyat;

        AyatHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setContent(Ayat ayat) {
            rowAyat.get(0).setText(ayat.getAyat());
            rowAyat.get(1).setText(ayat.getArab());
            rowAyat.get(2).setText(ayat.getTerjemahan());
        }
    }
}
