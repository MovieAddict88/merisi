package net.openvpn.openvpn;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class AdMobManager {

    private static final String TAG = "AdMobManager";
    private static String API_URL;

    private static ConcurrentHashMap<String, String> adUnitIds = new ConcurrentHashMap<>();

    public static void initialize(Context context) {
        initialize(context, null);
    }

    public static void initialize(Context context, final AdUnitIdsListener listener) {
        API_URL = context.getString(R.string.admob_api_url);
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
				@Override
				public void onInitializationComplete(InitializationStatus initializationStatus) {
					Log.d(TAG, "AdMob SDK initialized.");
				}
			});
        fetchAdUnitIds(listener);
    }

    public interface AdUnitIdsListener {
        void onAdUnitIdsFetched();
    }

    private static void fetchAdUnitIds(final AdUnitIdsListener listener) {
        new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						URL url = new URL(API_URL);
						HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
						try {
							InputStream in = urlConnection.getInputStream();
							BufferedReader reader = new BufferedReader(new InputStreamReader(in));
							StringBuilder result = new StringBuilder();
							String line;
							while ((line = reader.readLine()) != null) {
								result.append(line);
							}
							JSONObject jsonObject = new JSONObject(result.toString());
							if (jsonObject.getBoolean("success")) {
								JSONObject ads = jsonObject.getJSONObject("ads");
								Iterator<String> keys = ads.keys();
								while (keys.hasNext()) {
									String key = keys.next();
									adUnitIds.put(key, ads.getString(key));
								}
								if (listener != null) {
									listener.onAdUnitIdsFetched();
								}
							}
						} finally {
							urlConnection.disconnect();
						}
					} catch (IOException | JSONException e) {
						Log.e(TAG, "Failed to fetch ad unit IDs", e);
					}
				}
			}).start();
    }

    public static String getAdUnitId(String adName) {
        return adUnitIds.get(adName);
    }
}
