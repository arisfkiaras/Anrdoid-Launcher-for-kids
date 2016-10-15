package gr.aueb.cs.hci.launcher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Games extends Activity {

    ArrayList<Button> buttons = new ArrayList<Button>();
    PackageManager manager;

    final int maxPositions = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -15);
        DateFormat df = new SimpleDateFormat("EEEE dd MMMM yyyy HH:mm", Locale.getDefault());
        Date today = cal.getTime();
        String reportDate = df.format(today);
        TextView header = (TextView) findViewById(R.id.header);
        header.setText(reportDate);

        Button button = (Button) findViewById(R.id.bacbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.zoom_out_right, R.anim.zoom_out_right);
            }
        });
        buttons.add(button);

        manager = getPackageManager();
        int positionsTaken = 0;
        ArrayList<Settings.App> apps = Settings.getApps();
        for (int i = 0; i < apps.size(); i++) {
            Settings.App app = apps.get(i);
            if ((app.active) && (app.category == 0) && (positionsTaken < maxPositions)) {
                addGame(app.name, app.path, positionsTaken);
                positionsTaken++;
            }
        }

    }


    private void addGame(String name, String path, int pos) {
        RelativeLayout table = null;
        switch (pos) {
            case 0:
                table = (RelativeLayout) findViewById(R.id.game_2);
                break;
            case 1:
                table = (RelativeLayout) findViewById(R.id.game_3);
                break;
            case 2:
                table = (RelativeLayout) findViewById(R.id.game_1);
                break;
            case 3:
                table = (RelativeLayout) findViewById(R.id.game_4);
                break;
        }

        View items1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.single_app, null);


        TextView names = (TextView) items1.findViewById(R.id.Name);
        names.setText(name);

        ImageView image = (ImageView) items1.findViewById(R.id.Picture);
        try {
            image.setImageDrawable(manager.getApplicationIcon(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        items1.setTag(path);
        items1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = manager.getLaunchIntentForPackage((String) view.getTag());
                startActivity(i);
            }
        });

        if (table != null) table.addView(items1);

    }
}
