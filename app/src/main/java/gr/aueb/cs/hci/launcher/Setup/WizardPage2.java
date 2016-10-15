package gr.aueb.cs.hci.launcher.Setup;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

import gr.aueb.cs.hci.launcher.R;
import gr.aueb.cs.hci.launcher.Settings;


public class WizardPage2 extends Activity {
    PackageManager manager;
    ArrayList<Button> buttons = new ArrayList<Button>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((!Settings.getAbilities()[1]) && (!Settings.getAbilities()[2])) {
            return;
        }
        setContentView(R.layout.activity_wizard_page2);

        Button next = (Button) findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextTab();
            }
        });
        Button prev = (Button) findViewById(R.id.prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevTab();
            }
        });
        buttons.add(next);
        buttons.add(prev);
        manager = getPackageManager();

        TableLayout table = (TableLayout) findViewById(R.id.app_list);

        if (Settings.getAbilities()[2]) {
            View h1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.wizard_h1, null);
            TextView title = (TextView) h1.findViewById(R.id.cat_name);
            title.setText("Games:");
            table.addView(h1);
        }
        ArrayList<Settings.App> apps = Settings.getApps();
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).category == 0) {
                if (Settings.getAbilities()[2])
                    addApp(apps.get(i).name, apps.get(i).desc, apps.get(i).path, apps.get(i).active, table, i);
            }
        }

        if (Settings.getAbilities()[1]) {
            View h1_2 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.wizard_h1, null);
            TextView title_2 = (TextView) h1_2.findViewById(R.id.cat_name);
            title_2.setText("Tools:");
            table.addView(h1_2);
        }
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).category == 1) {
                if (Settings.getAbilities()[1])
                    addApp(apps.get(i).name, apps.get(i).desc, apps.get(i).path, apps.get(i).active, table, i);
            }
        }

    }

    private void addApp(String name, String description, String path, boolean isActive, View parent, int i) {
        View items1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.wizard_app_row, null);
        TextView appName = (TextView) items1.findViewById(R.id.app_name);
        TextView desc = (TextView) items1.findViewById(R.id.app_desc);
        appName.setText(name);
        desc.setText(description);

        CheckBox check = (CheckBox) items1.findViewById(R.id.checkBox);

        check.setChecked(isActive);
        check.setTag(i);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Settings.getApps().get((int) buttonView.getTag()).active = true;
                } else {
                    Settings.getApps().get((int) buttonView.getTag()).active = false;
                }

            }
        });

        ImageView image = (ImageView) items1.findViewById(R.id.app_picture);
        try {
            image.setImageDrawable(manager.getApplicationIcon(path));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        ((TableLayout) parent).addView(items1);

    }

    private void nextTab() {
        Wizard ta = (Wizard) this.getParent();
        ta.nextTab();
    }

    private void prevTab() {
        Wizard ta = (Wizard) this.getParent();
        ta.prevTab();
    }
}
