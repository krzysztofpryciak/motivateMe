package com.veo.motivateme;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.adform.sdk.interfaces.AdListener;
import com.adform.sdk.interfaces.AdStateListener;
import com.adform.sdk.pub.views.AdInline;

public class MainActivity extends AppCompatActivity {

    public TextView textQuote;
    public ImageView imageView;
    public TypedArray img;

    private AdInline adInline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textQuote = (TextView) findViewById(R.id.quoteTextView);
        imageView = (ImageView) findViewById(R.id.imageViewBackground);

        Resources res = getResources();
        String[] quotes = res.getStringArray(R.array.quotes);
        String[] authors = res.getStringArray(R.array.authors);

        int randomText = (int) (Math.random() * 48);
        String tempQuote = quotes[randomText];
        String tempAuthor = authors[randomText];

        img = getResources().obtainTypedArray(R.array.images);

        int randomImg = (int) (Math.random() * 5);

        imageView.setImageResource(img.getResourceId(randomImg, -1));

        textQuote.setText(tempQuote);
        textQuote.append("\n - \n");
        textQuote.append(tempAuthor);


        adInline = (AdInline) findViewById(R.id.view_ad_inline);

        adInline.setListener(new AdListener() {
            @Override
            public void onAdLoadSuccess(AdInline adInline) {
            }

            @Override
            public void onAdLoadFail(AdInline adInline, String s) {
            }
        });
        adInline.setStateListener(new AdStateListener() {
            @Override
            public void onAdVisibilityChange(AdInline adInline, boolean b) {

            }

            @Override
            public void onAdOpen(AdInline adInline) {

            }

            @Override
            public void onAdClose(AdInline adInline) {

            }
        });
        adInline.setMasterTagId(745588); // wymagane - id Placementu

/*        adInline.setBannerAnimationType(AdformEnum.AnimationType.SLIDE); // opcjonalne
        adInline.setModalPresentationStyle(AdformEnum.AnimationType.SLIDE); // opcjonalne
        adInline.setDimOverlayEnabled(true); // opcjonalne*/

        adInline.loadAd(); // wymagane
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adInline != null)
            adInline.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (adInline != null)
            adInline.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adInline != null)
            adInline.destroy();
    }
}
