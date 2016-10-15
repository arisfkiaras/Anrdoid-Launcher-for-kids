package gr.aueb.cs.hci.launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import gr.aueb.cs.hci.launcher.Browser.Internet;
import gr.aueb.cs.hci.launcher.Setup.Wizard;

import static gr.aueb.cs.hci.launcher.Settings.setUp;

public class MainActivity extends Activity {

    ArrayList<ImageView> buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        createButtons();
        setUp(getApplicationContext());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        DateFormat df = new SimpleDateFormat("EEEE dd MMMM yyyy  HH:mm", Locale.getDefault());
        Date today = cal.getTime();
        String reportDate = df.format(today);
        System.out.println(reportDate);
        TextView header = (TextView) findViewById(R.id.header);
        header.setText(reportDate);

        removeClosed();
        goTo(Wizard.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        removeClosed();
    }

    private void removeClosed() {
        ImageView contacts = (ImageView) findViewById(R.id.contacts_button);
        ImageView games = (ImageView) findViewById(R.id.games_button);
        ImageView tools = (ImageView) findViewById(R.id.tools_button);
        ImageView internet = (ImageView) findViewById(R.id.internet_button);
        contacts.setVisibility(View.VISIBLE);
        games.setVisibility(View.VISIBLE);
        tools.setVisibility(View.VISIBLE);
        internet.setVisibility(View.VISIBLE);

        if (!Settings.getAbilities()[0]) {
            contacts.setVisibility(View.INVISIBLE);
        }
        if (!Settings.getAbilities()[1]) {
            tools.setVisibility(View.INVISIBLE);
        }
        if (!Settings.getAbilities()[2]) {
            games.setVisibility(View.INVISIBLE);
        }
        if (!Settings.getAbilities()[3]) {
            internet.setVisibility(View.INVISIBLE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void createButtons() {
        buttons = new ArrayList<ImageView>();

        ImageView contacts = (ImageView) findViewById(R.id.contacts_button);
        contacts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goTo(Contacts.class);
            }
        });
        buttons.add(contacts);

        ImageView playroom = (ImageView) findViewById(R.id.games_button);
        playroom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goTo(Games.class);
            }
        });
        buttons.add(playroom);

        ImageView tools = (ImageView) findViewById(R.id.tools_button);
        tools.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goTo(Tools.class);
            }
        });
        buttons.add(tools);

        ImageView internet = (ImageView) findViewById(R.id.internet_button);
        internet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goTo(Internet.class);
            }
        });
        buttons.add(internet);

    }

    private void goTo(Class classs) {
        Intent intent = new Intent(this, classs);
        startActivity(intent);
        if (classs.equals(Tools.class)) {
            overridePendingTransition(R.anim.zoom_in_left, R.anim.zoom_in_left);
        } else if (classs.equals(Games.class)) {
            overridePendingTransition(R.anim.zoom_in_right, R.anim.zoom_in_right);
        } else if (classs.equals(Contacts.class)) {
            overridePendingTransition(R.anim.zoom_in_top, R.anim.zoom_in_top);
        } else {
            overridePendingTransition(R.anim.zoom_in_bot, R.anim.zoom_in_bot);
        }
    }

    long lastClick = 0;
    int numberOfClicks = 0;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_UP) {
                    long time = System.currentTimeMillis();
                    if ((time - lastClick) < 1500) {
                        numberOfClicks++;
                    } else {
                        numberOfClicks = 0;
                    }
                    if (numberOfClicks > 1) {
                        goTo(Wizard.class);
                    }
                    lastClick = time;
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }
}
