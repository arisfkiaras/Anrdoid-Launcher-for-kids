package gr.aueb.cs.hci.launcher;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;


public class Settings {

    public static class App {

        public String name;
        public String desc;
        public String path;
        public boolean active;
        public int category;

        public App(String name, String desc, String path, int category) {
            this.name = name;
            this.desc = desc;
            this.path = path;
            this.category = category;
            this.active = true;
        }

    }

    public static class Contact {
        public String name;
        public String number;
        public Drawable path;

        public Contact(String name, String number, Drawable path) {
            this.name = name;
            this.number = number;
            this.path = path;
        }
    }

    public static class Bookmark {

        public String name;
        public String url;
        public Drawable path;

        public Bookmark(String name, String url, Drawable path) {
            this.name = name;
            this.url = url;
            this.path = path;
        }

    }

    public static Context myContext;

    private static ArrayList<App> apps;
    private static ArrayList<Contact> contacts;
    private static ArrayList<Bookmark> bookmarks;

    private static boolean[] abilities = {true, true, true, true};

    public static ArrayList<Contact> getContacts() {
        return contacts;
    }

    public static ArrayList<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public static ArrayList<App> getApps() {
        return apps;
    }

    public static void setUp(Context context) {
        myContext = context;
        contacts = new ArrayList<Contact>();
        bookmarks = new ArrayList<Bookmark>();
        apps = new ArrayList<App>();

        apps.add(new App("Calculator", "Easy to use calculator.", "com.android.calculator2", 1));
        apps.add(new App("Flashlight", "Turns your device into a bright torch.", "com.surpax.ledflashlight.panel", 1));
        apps.add(new App("Class Shedule", "Helps your child remember his class shedule", "de.rakuun.MyClassSchedule.free", 1));

        apps.add(new App("Color Switch", "A colorful fun game.", "com.fortafygames.colorswitch", 0));
        apps.add(new App("Musical Instruments", "Educational app. Learn the musical instruments.", "com.appquiz.baby.musical", 0));
        apps.add(new App("Color & Draw", "A drawing app.", "com.doodlejoy.studio.kidsdoojoy", 0));
        apps.add(new App("Tile puzzle", "A cat themed puzzle game.", "com.tamco.cats", 0));

    }

    public static boolean[] getAbilities() {
        return abilities;
    }

}
