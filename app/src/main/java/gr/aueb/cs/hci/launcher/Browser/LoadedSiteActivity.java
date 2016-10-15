package gr.aueb.cs.hci.launcher.Browser;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import gr.aueb.cs.hci.launcher.R;

public class LoadedSiteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebView webview = new WebView(this);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        webview.getSettings().setJavaScriptEnabled(true);

        setContentView(R.layout.activity_loaded_site);
        LinearLayout placeHolder = (LinearLayout) findViewById(R.id.place_holder);
        placeHolder.addView(webview);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -45);
        DateFormat df = new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.getDefault());
        Date today = cal.getTime();
        String reportDate = df.format(today);
        TextView header = (TextView) findViewById(R.id.header);
        header.setText(reportDate);

        final Activity activity = this;
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                activity.setProgress(progress * 1000);
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }
        });
        String url = "g.com";
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            url = null;
        } else {
            url = extras.getString("url");
        }
        webview.loadUrl(url);
    }

}
