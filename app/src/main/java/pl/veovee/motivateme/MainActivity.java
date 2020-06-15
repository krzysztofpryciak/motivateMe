package pl.veovee.motivateme;

import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;



public class MainActivity extends AppCompatActivity {

    private TextView textQuote;
    private ImageView imageView;
    private TypedArray img;
    private ImageButton refreshButton;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    private boolean clickable = true;

    private static final String AD_UNIT_ID = "ca-app-pub-7884752702118496/7243692599";

//    google test ID
//    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";

    /* private AdInline adInline; */

    private void clickAdActive(){
        final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                clickable = true;
                        }
        }, 10000);
    }

    private void randomQuoteDay(String[] array){

        int randomText = (int) (Math.random() * 560);
        System.out.println(randomText);
        int randomImg = (int) (Math.random() * 43);
        String tempQuote = array[randomText];

        imageView.setImageResource(img.getResourceId(randomImg, -1));
        textQuote.setText(Html.fromHtml(tempQuote));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textQuote = (TextView) findViewById(R.id.quoteTextView);
        imageView = (ImageView) findViewById(R.id.imageViewBackground);
        refreshButton = (ImageButton) findViewById(R.id.imageButton);

        Resources res = getResources();
        final String[] quotes = res.getStringArray(R.array.quotes);
        img = res.obtainTypedArray(R.array.images);

        randomQuoteDay(quotes);

        refreshButton.setOnClickListener(v -> {
            randomQuoteDay(quotes);
            if(clickable) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                clickable = false;
                clickAdActive();
            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        /*  google interstitial ad  */

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(AD_UNIT_ID);
        mInterstitialAd.loadAd(new AdRequest.Builder()
                .build());
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
        }, 1000);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        /*  google banner 320x100 ad  */

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });


    }
}
