package app.com.temanhijrah;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class activityradio extends AppCompatActivity {

    private Spinner combo;
    private TextView txtDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityradio);

        //inisialisasi
        combo = (Spinner) findViewById(R.id.combo);
        txtDetails = (TextView) findViewById(R.id.txtDetails);

        //set value to list
        final List<Radio> listRadio = new ArrayList<>();
        Radio radio1 = new Radio("MQ FM Bandung", "http://125.160.17.86:8022/;");
        Radio radio2 = new Radio("Radio Rodja", "http://live.radiorodja.com/;stream.mp3");
        Radio radio3 = new Radio("Radio Muslim", "http://128.199.156.6/;stream/1");
        Radio radio4 = new Radio("Radio KITA Cirebon", "http://live.radiosunnah.net/;");
        Radio radio5 = new Radio("Radio Hidayah", "http://radio.hidayahfm.com:9988/;stream.mp3");
        listRadio.add(radio1);
        listRadio.add(radio2);
        listRadio.add(radio3);
        listRadio.add(radio4);
        listRadio.add(radio5);

        final String[] radioArr = new String[listRadio.size()];
        for (int i = 0; i < listRadio.size(); i++) {
            radioArr[i] = listRadio.get(i).getName();
        }

        //set value to autocomplete
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, radioArr);
        combo.setAdapter(adapter);
        combo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i);
                int pos = -1;

                for (int j = 0; j < radioArr.length; j++) {
                    if (radioArr[j].equals(selection)) {
                        pos = j;
                        break;
                    }
                }


                callRadio(listRadio.get(pos).getUrl(), listRadio.get(pos).getName());
                txtDetails.setText(listRadio.get(pos).getName() + " is Now Playing");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void callRadio(String url, String name) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("name", name);
        Intent serviceOn = new Intent(this, StreamingService.class);
        serviceOn.putExtras(bundle);
        startService(serviceOn);
    }

    class Radio {
        private String name, url;

        public Radio() {
        }

        public Radio(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}