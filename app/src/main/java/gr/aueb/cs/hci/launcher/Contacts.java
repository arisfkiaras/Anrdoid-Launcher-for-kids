package gr.aueb.cs.hci.launcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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

public class Contacts extends Activity {

    ArrayList<Button> buttons = new ArrayList<Button>();
    Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -15);
        DateFormat df = new SimpleDateFormat("EEEE dd MMMM yyyy HH:mm", Locale.getDefault());
        Date today = cal.getTime();
        String reportDate = df.format(today);
        System.out.println(reportDate);
        TextView header = (TextView) findViewById(R.id.header);
        header.setText(reportDate);
        header.setText(reportDate);

        Button button = (Button) findViewById(R.id.bacbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.zoom_out_top, R.anim.zoom_out_top);
            }
        });
        buttons.add(button);

        ArrayList<Settings.Contact> contacts = Settings.getContacts();
        for (int i = 0; i < contacts.size(); i++) {
            Settings.Contact temp = contacts.get(i);
            System.out.println(temp.name + " " + temp.number + " " + temp.path);
            addContact(temp.name, temp.number, temp.path, i);
        }

    }

    private void addContact(String name, String number, Drawable bmp, int pos) {
        RelativeLayout table = null;
        switch (pos) {
            case 0:
                table = (RelativeLayout) findViewById(R.id.contact_2);
                break;
            case 1:
                table = (RelativeLayout) findViewById(R.id.contact_3);
                break;
            case 2:
                table = (RelativeLayout) findViewById(R.id.contact_1);
                break;
            case 3:
                table = (RelativeLayout) findViewById(R.id.contact_4);
                break;
        }
        View items1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.single_person, null);
        TextView names = (TextView) items1.findViewById(R.id.Name);
        names.setText(name);
        ImageView image = (ImageView) items1.findViewById(R.id.Picture);

        image.setImageDrawable(bmp);
        items1.setTag(number);
        items1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callnumber((String) view.getTag());
            }
        });
        if (table != null) table.addView(items1);

    }


    public void callnumber(String Number) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + Number));

        try {
            startActivity(callIntent);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
