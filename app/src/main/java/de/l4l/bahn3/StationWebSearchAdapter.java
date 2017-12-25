package de.l4l.bahn3;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import de.l4l.bahn3.dto.Station;

/**
 * Created by l4lx on 21.12.17.
 * http://makovkastar.github.io/blog/2014/04/12/android-autocompletetextview-with-suggestions-from-a-web-service/
 * https://www.openvrr.de/en/api/3/action/help_show?name=datastore_search
 * https://developer.android.com/training/volley/request.html
 * https://www.openvrr.de/en/api/3/action/datastore_search?resource_id=b1c348f0-8730-48f2-8835-080bbf6aa469&q=graf
 */
public class StationWebSearchAdapter extends ArrayAdapter<Station> implements Filterable {
    private static final String URL = "https://www.openvrr.de/en/api/3/action/datastore_search?resource_id=b1c348f0-8730-48f2-8835-080bbf6aa469&q=";

    private List<Station> data = new ArrayList<>();
    private Context context;
    private final RequestQueue queue;

    public StationWebSearchAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                data.clear();
                results.count = 0;
                if (charSequence != null) {
                    data.addAll(requestPlaces(charSequence));
                    results.count = data.size();
                }

                results.values = data;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults != null && filterResults.count > 0) {
                    notifyDataSetChanged();
                } else
                    notifyDataSetInvalidated();
            }
        };
    }

    private List<? extends Station> requestPlaces(CharSequence request) {
        if (request == null || request.length() == 0)
            return Collections.emptyList();

        // preparing request, HTTP_GET(URL+request)
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        };
        JsonObjectRequest jsRequest = new JsonObjectRequest(URL + request.toString(),  new JSONObject(), future, errorListener);
        queue.add(jsRequest);

        List<Station> stations = new LinkedList<>();
        try {
            JSONObject response = future.get(1, TimeUnit.SECONDS);
            if (response.optBoolean("success")) {
                JSONObject jsResult = response.optJSONObject("result");
                if (jsResult != null) {
                    int total = jsResult.optInt("total", 0);
                    JSONArray records = jsResult.optJSONArray("records");
                    if (records != null)
                    for (int i = 0; i < total; i++) {
                        JSONObject rec = records.optJSONObject(i);
                        if (rec == null)
                            continue;
                        stations.add(Station.builder()
                                .name(rec.optString("Name ohne Ort", "(null)"))
                                .description(rec.optString("Ort", "(null)"))
                                .build()
                        );
                    }
                }
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        return stations;
    }

    @Override
    public Station getItem(int i) {
        return data.get(i);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.simple_dropdown_item_2line, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.text1)).setText(getItem(position).getName());
        ((TextView) convertView.findViewById(R.id.text2)).setText(getItem(position).getDescription());
        return convertView;
    }
}
