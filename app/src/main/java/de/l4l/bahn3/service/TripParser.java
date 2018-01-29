package de.l4l.bahn3.service;

import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import de.l4l.bahn3.dto.Trip;

/**
 * https://developer.android.com/training/basics/network-ops/xml.html
 * Created by l4l on 28.01.18.
 */

public class TripParser {
    private final String LOG_TAG = "TripPrs";

    public List<Trip> parse(InputStream inputStream) throws XmlPullParserException, IOException {

        XmlPullParser xpp = Xml.newPullParser();
        xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        xpp.setInput(inputStream, "UTF-8");

        List<Trip> objects = new LinkedList<>();

        while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
            switch (xpp.getEventType()) {
                case XmlPullParser.START_TAG:
                    Log.d(LOG_TAG, "START_TAG: name = " + xpp.getName()
                            + ", depth = " + xpp.getDepth() + ", attrCount = "
                            + xpp.getAttributeCount());
                    String tmp = "";
                    for (int i = 0; i < xpp.getAttributeCount(); i++) {
                        tmp = tmp + xpp.getAttributeName(i) + " = "
                                + xpp.getAttributeValue(i) + ", ";
                    }
                    if (!TextUtils.isEmpty(tmp))
                        Log.d(LOG_TAG, "Attributes: " + tmp);
                    break;
                case XmlPullParser.END_TAG:
                    Log.d(LOG_TAG, "END_TAG: name = " + xpp.getName());
                    break;
                case XmlPullParser.TEXT:
                    Log.d(LOG_TAG, "text = " + xpp.getText());
                    break;
                default:
                    break;
            }
            xpp.next();
        }

        return objects;
    }
}
