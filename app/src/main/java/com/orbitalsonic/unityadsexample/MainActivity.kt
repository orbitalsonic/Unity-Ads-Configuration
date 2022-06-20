package com.orbitalsonic.unityadsexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    private lateinit var unityAdsUtils: UnityAdsUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val adsContainer:LinearLayout = findViewById(R.id.banner_ads)
        unityAdsUtils = UnityAdsUtils(this)
        unityAdsUtils.initializeAds()

        unityAdsUtils.loadBanner(adsContainer)

        val btnAdsShow:Button = findViewById(R.id.btnAdsShow)
        btnAdsShow.setOnClickListener {
            unityAdsUtils.showInterstitialAds()
        }
    }
}