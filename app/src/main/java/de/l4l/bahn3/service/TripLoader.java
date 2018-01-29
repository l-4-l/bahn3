package de.l4l.bahn3.service;

import android.support.annotation.NonNull;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import de.l4l.bahn3.dto.Trip;

/**
 * Created by l4l on 21.01.18.
 */
public class TripLoader {

    // Given a string representation of a URL, sets up a connection and gets
    // an input stream.
    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();//TODO: use try-with-resources somewhere here
    }

    public List<Trip> loadTrips(@NonNull String url) throws IOException, XmlPullParserException {
        InputStream inputStream = downloadUrl(url);

        return new TripParser().parse(inputStream);
    }

}