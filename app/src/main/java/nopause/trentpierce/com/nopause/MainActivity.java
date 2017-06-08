package nopause.trentpierce.com.nopause;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.analytics.FirebaseAnalytics;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

   // ImageButton buttonStart, buttonStop;
    boolean vibenabled;
    boolean adsenabled;
    NotificationManager mNotificationManager;
    boolean unlocked = false;
    private InterstitialAd interstitial;
   // IInAppBillingService mService;

    private static final String TAG = "MainActivity";
    private static final int REQUEST_INVITE = 0;

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private FirebaseAnalytics mFirebaseAnalytics;

    private TextView btt;
    private TextView usbt;
    private TextView hst;
    private Switch btSwitch;
    private Switch usbSwitch;
    private Switch hsSwitch;

    public static final String PREFS_NAME = "NPPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btSwitch = (Switch) findViewById(R.id.btSwitch);
        usbSwitch = (Switch) findViewById(R.id.usbSwitch);
        hsSwitch = (Switch) findViewById(R.id.hsSwitch);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean bt_pref = settings.getBoolean("btSwitch", false);
        boolean usb_pref = settings.getBoolean("usbSwitch", false);
        boolean hs_pref = settings.getBoolean("hsSwitch", false);
        btSwitch.setChecked(bt_pref);
        usbSwitch.setChecked(usb_pref);
        hsSwitch.setChecked(hs_pref);

        //attach a listener to check for changes in state
        btSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){


                }else{

                }
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("btSwitch", isChecked);
                editor.commit();
            }
        });
        usbSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){

                }else{

                }
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("usbSwitch", isChecked);
                editor.commit();
            }
        });
        hsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){

                }else{

                }
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("hsSwitch", isChecked);
                editor.commit();
            }
        });
        //check the current state before we display the screen
        if(btSwitch.isChecked()){

        }
        else {

        }
        if(usbSwitch.isChecked()){

        }
        else {

        }
        if(hsSwitch.isChecked()){

        }
        else {

        }



        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5038706716632727/6031392982");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

            mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                                .setMessage(getString(R.string.invitation_message))
                                //  .setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
                                //  .setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
                                .setCallToActionText(getString(R.string.invitation_cta))
                                .build();
                        startActivityForResult(intent, REQUEST_INVITE);
                }
            });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Get the invitation IDs of all sent messages
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids) {
                    Log.d(TAG, "onActivityResult: sent invitation " + id);
                }
            } else {
                // Sending failed or it was canceled, show failure message to the user
                // ...
            }
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


//    ServiceConnection mServiceConn = new ServiceConnection() {
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            mService = null;
//        }
//
//        @Override
//        public void onServiceConnected(ComponentName name,
//                                       IBinder service) {
//            mService = IInAppBillingService.Stub.asInterface(service);
//        }
//    };
    public void startButton(View view) {
        startService(new Intent(this, MainService.class));

        Snackbar.make(view, "Service Started", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "startButton");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "startButton");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Button");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
    public void stopButton(View view) {
        stopService(new Intent(this, MainService.class));

        Snackbar.make(view, "Service Stopped", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "stopButton");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "stopButton");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Button");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_view) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_share) {
            Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                    .setMessage(getString(R.string.invitation_message))
                    //  .setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
                    //  .setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
                    .setCallToActionText(getString(R.string.invitation_cta))
                    .build();
            startActivityForResult(intent, REQUEST_INVITE);
        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
