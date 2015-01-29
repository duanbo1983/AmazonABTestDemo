package demo.abtest.com.amazonabtestdemo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.amazon.insights.ABTestClient;
import com.amazon.insights.AmazonInsights;
import com.amazon.insights.Event;
import com.amazon.insights.EventClient;
import com.amazon.insights.InsightsCallback;
import com.amazon.insights.Variation;
import com.amazon.insights.VariationSet;
import com.amazon.insights.error.InsightsError;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InsightManger.init(getApplicationContext());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            onLoadLevel();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onLoadLevel() {
        final AmazonInsights insights = InsightManger.getInsights();
        ABTestClient abClient = insights.getABTestClient();
        // Allocate/obtain variations for level 3
        abClient.getVariations("ScoutAndroid-Test")
                .setCallback(new InsightsCallback<VariationSet>() {
                                 @Override
                                 public void onComplete(VariationSet variations) {
                                     // Request a Variation out of the VariationSet
                                     final Variation scoutTest = variations.getVariation("ScoutAndroid-Test");
                                     final double meetup = scoutTest.getVariableAsDouble("meetup", 1.0);

                                     System.out.println("DB-Test get Meetup : " + meetup);
                                     EventClient eventClient = insights.getEventClient();

                                     // Create a visit event when the user starts playing level 3.
                                     Event level3Start = eventClient.createEvent("meetup");

                                     // Record the visit event.
                                     eventClient.recordEvent(level3Start);
                                 }

                                 @Override
                                 public void onError(final InsightsError error) {

                                     // base class will record the error to log cat
                                     super.onError(error);
                                 }
                             }
                );

    }
}
