package gr.aueb.cs.hci.launcher.Setup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import java.util.ArrayList;

import gr.aueb.cs.hci.launcher.R;
import gr.aueb.cs.hci.launcher.Settings;

public class WizardPage0 extends Activity {

    ArrayList<Button> buttons = new ArrayList<Button>();
    CheckBox[] checks = new CheckBox[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard_page0);

        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextTab();
            }
        });
        buttons.add(next);

        ImageView help = (ImageView) findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                giveHelp("Set the maximum hours you want your child to play games,browse the internet or use tool apps per day.Contact calling will still work as usual.");
            }
        });

        checks[0] = (CheckBox) findViewById(R.id.make_phonecalls);
        checks[1] = (CheckBox) findViewById(R.id.use_tools);
        checks[2] = (CheckBox) findViewById(R.id.play_games);
        checks[3] = (CheckBox) findViewById(R.id.access_internet);

        for (int i = 0; i < checks.length; i++) {
            checks[i].setChecked(Settings.getAbilities()[i]);
            checks[i].setTag(i);
            checks[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        Settings.getAbilities()[(int) buttonView.getTag()] = true;
                    } else {
                        Settings.getAbilities()[(int) buttonView.getTag()] = false;
                    }
                    changed();
                }
            });
        }


    }

    private void giveHelp(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void nextTab() {
        Wizard ta = (Wizard) this.getParent();
        ta.nextTab();
    }

    private void changed() {
        Wizard ta = (Wizard) this.getParent();
        ta.tabsChanged();
    }
}
