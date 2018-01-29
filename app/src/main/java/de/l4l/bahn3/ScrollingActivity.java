package de.l4l.bahn3;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import de.l4l.bahn3.dto.Station;
import de.l4l.bahn3.dto.Trip;
import de.l4l.bahn3.service.TripLoader;
import de.l4l.bahn3.service.TripLoaderRequest;

public class ScrollingActivity extends AppCompatActivity {
    private final  String TAG = "ScrAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        final AutoCompleteTextView fromField = findViewById(R.id.fromField);
        fromField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Station station = (Station)view.getTag();
                if (station == null) {
                    //fromField.setText("");
                    Log.d(TAG, "onItemClick: null");
                    return;
                }
                Log.d(TAG, "onItemClick: station.toString");
                //fromField.setText(station.toString());
            }
        });
        fromField.setAdapter(new StationWebSearchAdapter(this, R.layout.activity_scrolling));

        final AutoCompleteTextView toField = findViewById(R.id.toField);
        toField.setAdapter(new StationWebSearchAdapter(this, R.layout.activity_scrolling));

        Button buttSearch = findViewById(R.id.buttSearch);
        buttSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoCompleteTextView fromField = findViewById(R.id.fromField);
                AutoCompleteTextView toField = findViewById(R.id.toField);
                TripLoaderRequest req = TripLoaderRequest.builder()
                        .name_origin(fromField.getText().toString())
                        .name_destination(toField.getText().toString())
                        .build();
                new RetrieveFeedTask().execute(req.toString());

                String url = "https://openservice-test.vrr.de/static02/XML_STOPFINDER_REQUEST?sessionID=0&" +
                        "requestID=0&" +
                        "language=DE&" +
                        "coordOutputFormat=WGS84&" +
                        "place_sf=Gelsenkirchen&" +
                        "placeState_sf=empty&" +
                        "type_sf=stop&" +
                        "name_sf=Lukaskirche&" +
                        "nameState_sf=empty&" +
                        "itdDateYear=2011&" +
                        "itdDateMonth=10&" +
                        "itdDateDay=24&" +
                        "itdTimeHour=11&" +
                        "itdTimeMinute=9&" +
                        "coordOutputFormatTail=0";

                String s2 = "https://openservice-test.vrr.de/static02/XML_TRIP_REQUEST2?language=de&" +
                        "sessionID=0&" +
                        "odvMacro=true&" +
                        "commonMacro=true&" +
                        "lineRestriction=403&" +
                        "SpEncId=0&" +
                        "type_origin=any&" +
                        "type_destination=any&" +
                        "itdLPxx_transpCompany=vrr&" +
                        "useRealtime=1&" +
                        "nameInfo_origin=invalid&" +
                        "nameInfo_destination=invalid&" +
                        "name_origin=Aachen&" +
                        "name_destination=K%C3%B6ln";
                s2 += "";
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, List<Trip>> {
        private Exception exception;

        @Override
        protected List<Trip> doInBackground(String... urls) {
            try {
                return new TripLoader().loadTrips(urls[0]);
            } catch (IOException | XmlPullParserException e) {
                Log.e(TAG, "onClick: ", e);
                Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(),
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return Collections.emptyList();
            }
        }

        @Override
        protected void onPostExecute(List<Trip> tripList) {
            Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(tripList.size()),
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
