package nopause.trentpierce.com.nopause;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Developed by Trent Pierce for Pierce Holdings LLC
 *
 *Copyright 2014 Pierce Holdings LLC
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */


public class HeadsetObserverService extends Service {

    private static final String TAG = "HeadsetObserverService";
    public static final String PREFS_NAME = "NPPrefs";

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Create a filter. We are interested in the Intent.ACTION_HEADSET_PLUG action
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_HEADSET_PLUG);

        // Register a new HeadsetReceiver
        registerReceiver(new HeadsetReceiver(), filter);

        // We return START_STICKY. If our service gets destroyed, Android will try to restart it when resources are available.
        return START_STICKY;
    }

    private class HeadsetReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                Toast.makeText(context, "HS Detected", Toast.LENGTH_LONG).show();
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                boolean hs_pref = settings.getBoolean("hsSwitch", false);
                int state = intent.getIntExtra("state", -1);
                switch (state) {

                    case 0:
                        Log.d("unplugged", "Headset was unplugged");

                        if (!hs_pref) {
                            context.stopService(new Intent(context, MainService2.class));
                        }
//                        } else {
//                            context.stopService(new Intent(context, MainService.class));
//                        }
                        // Cancel notification here

                        break;
                    case 1:
                        Log.d("plugged", "Headset is plugged");
                        if (!hs_pref) {
                            context.startService(new Intent(context, MainService2.class));
                        }
//                        } else {
//                            context.startService(new Intent(context, MainService.class));
//                        }
                        // Show notification

                        break;
                    default:
                        Log.w("uh", "I have no idea what the headset state is");
                }
            }
        }
    }
}