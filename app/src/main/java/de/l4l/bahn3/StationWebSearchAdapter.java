package de.l4l.bahn3;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by l4lx on 21.12.17.
 * http://makovkastar.github.io/blog/2014/04/12/android-autocompletetextview-with-suggestions-from-a-web-service/
 * https://www.openvrr.de/en/api/3/action/help_show?name=datastore_search
 */
public class StationWebSearchAdapter extends ArrayAdapter<String> implements Filterable {

    private List<String> data = new ArrayList<>();
    Context context;

    public StationWebSearchAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        this.context = context;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                if (charSequence != null) {
                    data.clear();
                    data.addAll(requestPlaces(charSequence));
                    results.count = data.size();
                    results.values = data;
                }

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

    private List<? extends String> requestPlaces(CharSequence request) {
        // https://developer.android.com/training/volley/request.html
        // https://www.openvrr.de/en/api/3/action/datastore_search?resource_id=b1c348f0-8730-48f2-8835-080bbf6aa469&q=graf
    }

    @Override
    public String getItem(int i) {
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
        ((TextView) convertView.findViewById(R.id.text1)).setText(getItem(position));
        ((TextView) convertView.findViewById(R.id.text2)).setText(getItem(position) + " aaa111!");
        return convertView;
    }
}
