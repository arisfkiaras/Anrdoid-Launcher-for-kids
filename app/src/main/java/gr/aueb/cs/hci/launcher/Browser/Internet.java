package gr.aueb.cs.hci.launcher.Browser;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import gr.aueb.cs.hci.launcher.R;
import gr.aueb.cs.hci.launcher.Settings;

public class Internet extends Activity {

    ArrayList<Button> buttons = new ArrayList<Button>();
    PackageManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);

        Button button = (Button) findViewById(R.id.bacbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.zoom_out_bot, R.anim.zoom_out_bot);
            }
        });

        buttons.add(button);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        DateFormat df = new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.getDefault());
        Date today = cal.getTime();
        String reportDate = df.format(today);
        TextView header = (TextView) findViewById(R.id.header);
        header.setText(reportDate);

        manager = getPackageManager();
        ArrayList<Settings.Bookmark> bookmarks = Settings.getBookmarks();
        for (int i = 0; i < bookmarks.size(); i++) {
            String url = bookmarks.get(i).url;
            if (!((url.contains("http://") || url.contains("https://")))) {
                url = "http://" + url;
            }
            addBookmark(bookmarks.get(i).name, url, i);
        }
    }

    private void addBookmark(String name, String url, int pos) {
        RelativeLayout table = null;
        switch (pos) {
            case 0:
                table = (RelativeLayout) findViewById(R.id.site_1);
                break;
            case 1:
                table = (RelativeLayout) findViewById(R.id.site_2);
                break;
            case 2:
                table = (RelativeLayout) findViewById(R.id.site_3);
                break;
            case 3:
                table = (RelativeLayout) findViewById(R.id.site_4);
                break;
            case 4:
                table = (RelativeLayout) findViewById(R.id.site_5);
                break;
            case 5:
                table = (RelativeLayout) findViewById(R.id.site_6);
                break;
        }
        View items1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.single_bookmark, null);
        TextView names = (TextView) items1.findViewById(R.id.Name);
        names.setText(name);
        try {
            String urlF = (url + "/favicon.ico");
            ImageView image = (ImageView) items1.findViewById(R.id.Picture);
            new DownloadImageTask(image).execute(urlF);
        } catch (Exception e) {
            e.printStackTrace();
        }
        items1.setTag(url);
        items1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSite((String) view.getTag());
            }
        });
        if (table != null) {
            table.addView(items1);
        }
    }


    private void goToSite(String site) {
        Intent intent = new Intent(this, LoadedSiteActivity.class);
        intent.putExtra("url", site);
        startActivity(intent);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                if (mIcon11 == null) {
                    urldisplay = urldisplay.replace("http://", "https://");
                    in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                bmImage.setImageBitmap(result);
            } else {
                bmImage.setImageDrawable(getResources().getDrawable(R.drawable.site_icon));
            }
        }
    }
}
