package gr.aueb.cs.hci.launcher.Setup;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import gr.aueb.cs.hci.launcher.Settings;

public class Wizard extends TabActivity {

    TabHost tabhost;
    int currentTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabhost = getTabHost();
        tabhost.addTab(tabhost.newTabSpec("first").setIndicator("General").setContent(new Intent(this, WizardPage0.class)));
        tabhost.addTab(tabhost.newTabSpec("second").setIndicator("Contacts").setContent(new Intent(this, WizardPage1.class)));
        tabhost.addTab(tabhost.newTabSpec("first").setIndicator("Applications").setContent(new Intent(this, WizardPage2.class)));
        tabhost.addTab(tabhost.newTabSpec("second").setIndicator("Internet").setContent(new Intent(this, WizardPage3.class)));
        tabhost.setCurrentTab(0);

    }

    public void tabsChanged() {
        tabhost.clearAllTabs();

        tabhost.addTab(tabhost.newTabSpec("first").setIndicator("General").setContent(new Intent(this, WizardPage0.class)));
        if (Settings.getAbilities()[0])
            tabhost.addTab(tabhost.newTabSpec("second").setIndicator("Contacts").setContent(new Intent(this, WizardPage1.class)));
        if (Settings.getAbilities()[1] || Settings.getAbilities()[2])
            tabhost.addTab(tabhost.newTabSpec("first").setIndicator("Applications").setContent(new Intent(this, WizardPage2.class)));
        if (Settings.getAbilities()[3])
            tabhost.addTab(tabhost.newTabSpec("second").setIndicator("Internet").setContent(new Intent(this, WizardPage3.class)));

        tabhost.setCurrentTab(0);
    }

    public void nextTab() {
        int currentTab = tabhost.getCurrentTab();
        tabhost.setCurrentTab(currentTab + 1);
        if (currentTab == tabhost.getCurrentTab()) {
            String message = "You will now enter Child Mode.\nTo come back here press the volume down button 3 time in the row.\nThank you!";
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void prevTab() {
        tabhost.setCurrentTab(tabhost.getCurrentTab() - 1);
    }
}


