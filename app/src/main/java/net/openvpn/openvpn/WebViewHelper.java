package net.openvpn.openvpn; // Make sure this package name matches your project

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewHelper {

    private WebView webView;
    private final Context context;

    public interface ChallengeListener {
        void onChallengeSolved(String cookie, String userAgent);
    }

    public WebViewHelper(Context context) {
        this.context = context;
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void solveChallenge(final String url, final ChallengeListener listener) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                webView = new WebView(context);
                WebSettings settings = webView.getSettings();

                // Mimic a real browser
                settings.setJavaScriptEnabled(true);
                settings.setDomStorageEnabled(true);
                settings.setDatabaseEnabled(true);
                settings.setLoadsImagesAutomatically(false);

                final String userAgent = settings.getUserAgentString();
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String finishedUrl) {
                        super.onPageFinished(view, finishedUrl);
                        String cookies = CookieManager.getInstance().getCookie(finishedUrl);

                        Log.d("WebViewHelper", "URL: " + finishedUrl);
                        Log.d("WebViewHelper", "Cookies: " + cookies);

                        // Check if we got a valid cookie before proceeding
                        if (cookies != null && !cookies.isEmpty()) {
                            if (listener != null) {
                                // Pass back the cookie and the user agent
                                listener.onChallengeSolved(cookies, userAgent);
                            }

                            // Clean up the WebView to release resources
                            webView.destroy();
                            webView = null;
                        }
                    }
                });
                webView.loadUrl(url);
            }
        });
    }
}
