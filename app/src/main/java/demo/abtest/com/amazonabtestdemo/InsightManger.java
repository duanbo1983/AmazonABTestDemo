package demo.abtest.com.amazonabtestdemo;

import android.content.Context;

import com.amazon.insights.AmazonInsights;
import com.amazon.insights.InsightsCredentials;

/**
 * Created by bod on 1/28/15.
 */
public class InsightManger {

    private static AmazonInsights insightsInstance;

    public static void init(Context appContext) {
        // Create a credentials object using the keys from the
        // Amazon Apps & Games Developer Portal's A/B Testing site.
        InsightsCredentials credentials = AmazonInsights.newCredentials("9bd0c209d1d948caa087c01c6af7542e", "Ehac2ZjIVzmLsvTNLAUQGpEUEaTo3C81vwRKEQ+UGG0=");

        // Initialize a new instance of AmazonInsights specifically for your application.
        // The AmazonInsights library requires the Android context in order to access
        // Android services (i.e. SharedPrefs, etc)
        insightsInstance = AmazonInsights.newInstance(credentials, appContext);
    }

    public static AmazonInsights getInsights() {
        return insightsInstance;
    }
}
