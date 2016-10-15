package gr.aueb.cs.hci.launcher.Setup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

import gr.aueb.cs.hci.launcher.R;
import gr.aueb.cs.hci.launcher.Settings;

public class WizardPage3 extends Activity {

    ArrayList<View> items = new ArrayList<View>();
    ArrayList<Button> buttons = new ArrayList<Button>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Settings.getAbilities()[3]) {
            return;
        }
        setContentView(R.layout.activity_wizard_page3);

        Button button = (Button) findViewById(R.id.add_bookmark);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newBookmark();
            }
        });
        buttons.add(button);
        items = new ArrayList<View>();
        ArrayList<Settings.Bookmark> bookmarks = Settings.getBookmarks();
        for (int i = 0; i < bookmarks.size(); i++) {
            insertBookmark(bookmarks.get(i).name, bookmarks.get(i).url, i);
        }

        Button prev = (Button) findViewById(R.id.prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevTab();
            }
        });
        buttons.add(prev);
        final Button finish = (Button) findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEnd();
            }
        });
        buttons.add(finish);
    }

    private void editBookmark(int id) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(
                R.layout.wizard_bookmark_form, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);

        EditText name = (EditText) deleteDialogView.findViewById(R.id.input_name);
        EditText number = (EditText) deleteDialogView.findViewById(R.id.input_url);


        String prevName = Settings.getBookmarks().get(id).name;
        String prevUrl = Settings.getBookmarks().get(id).url;

        name.setText(prevName);
        number.setText(prevUrl);

        deleteDialogView.setTag(id);
        deleteDialogView.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) deleteDialog.findViewById(R.id.input_name);
                EditText url = (EditText) deleteDialog.findViewById(R.id.input_url);

                Settings.getBookmarks().get((int) deleteDialogView.getTag()).name = name.getText().toString();
                Settings.getBookmarks().get((int) deleteDialogView.getTag()).url = url.getText().toString();
                insertBookmark(name.getText().toString(), url.getText().toString(), (int) deleteDialogView.getTag());
                deleteDialog.dismiss();
            }
        });

        deleteDialogView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();

            }
        });
        deleteDialog.show();

    }

    private void newBookmark() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(
                R.layout.wizard_bookmark_form, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);


        deleteDialogView.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) deleteDialog.findViewById(R.id.input_name);
                EditText url = (EditText) deleteDialog.findViewById(R.id.input_url);
                Settings.getBookmarks().add(new Settings.Bookmark(name.getText().toString(), url.getText().toString(), null));
                insertBookmark(name.getText().toString(), url.getText().toString(), items.size());
                deleteDialog.dismiss();
            }
        });

        deleteDialogView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });

        deleteDialog.show();
    }

    private int insertBookmark(String name, String number, int id) {
        TableLayout table = (TableLayout) findViewById(R.id.bookmark_list);
        if (id >= items.size()) {
            View items1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.wizard_bookmark_row, null);
            TextView nameV = (TextView) items1.findViewById(R.id.bookmark_name);
            TextView numberV = (TextView) items1.findViewById(R.id.bookmark_url);
            nameV.setText(name);
            numberV.setText(number);

            ImageView edit = (ImageView) items1.findViewById(R.id.edit_bookmark);
            edit.setTag(id);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editBookmark(items.indexOf(view.getParent().getParent().getParent()));
                }
            });
            ImageView delete = (ImageView) items1.findViewById(R.id.delete_bookmark);
            delete.setTag(id);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    remove(items.indexOf(view.getParent().getParent().getParent()));
                }
            });
            items.add(items1);
            table.addView(items1);
            TextView counter = (TextView) findViewById(R.id.manage_website);
            counter.setText("Manage Website:(" + items.size() + "/5)");
            if (items.size() == 5) {
                buttons.get(0).setVisibility(View.INVISIBLE);
            }
        } else {
            View items1 = table.getChildAt(id);
            TextView nameV = (TextView) items1.findViewById(R.id.bookmark_name);
            TextView numberV = (TextView) items1.findViewById(R.id.bookmark_url);
            nameV.setText(name);
            numberV.setText(number);
        }

        return id;
    }

    private void remove(int i) {
        TableLayout table = (TableLayout) findViewById(R.id.bookmark_list);
        table.removeView(items.get(i));
        items.remove(i);
        TextView counter = (TextView) findViewById(R.id.manage_website);
        counter.setText("Manage Website:(" + items.size() + "/5)");

        if (items.size() == 4) {
            buttons.get(0).setVisibility(View.VISIBLE);
        }
        Settings.getBookmarks().remove(i);
    }

    private void goToEnd() {
        String message = "You will now enter Child Mode.\nTo come back here press the volume down button 3 time in the row.\nThank you";
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

    private void nextTab() {
        Wizard ta = (Wizard) this.getParent();
        ta.nextTab();
    }

    private void prevTab() {
        Wizard ta = (Wizard) this.getParent();
        ta.prevTab();
    }
}
