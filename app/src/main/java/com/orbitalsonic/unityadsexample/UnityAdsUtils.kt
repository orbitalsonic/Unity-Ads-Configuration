package com.orbitalsonic.unityadsexample


import android.app.Activity
import android.util.Log
import android.widget.LinearLayout
import com.unity3d.ads.IUnityAdsInitializationListener
import com.unity3d.ads.IUnityAdsLoadListener
import com.unity3d.ads.IUnityAdsShowListener
import com.unity3d.ads.UnityAds.*
import com.unity3d.ads.UnityAdsShowOptions
import com.unity3d.ads.metadata.MediationMetaData
import com.unity3d.ads.metadata.PlayerMetaData
import com.unity3d.services.banners.BannerErrorInfo
import com.unity3d.services.banners.BannerView
import com.unity3d.services.banners.BannerView.IListener
import com.unity3d.services.banners.UnityBannerSize


class UnityAdsUtils(activity: Activity) {

    private val LOGTAG = "AdsInformation"

    // set testMode false for production
    private val testMode = true

    private val mActivity: Activity = activity
    private var ordinal = 1

    private var bottomBanner: BannerView? = null

    fun initializeAds() {
        initialize(
            mActivity,
            mActivity.getString(R.string.unity_ads_id),
            testMode,
            object : IUnityAdsInitializationListener {
                override fun onInitializationComplete() {
                    Log.v(LOGTAG, "Unity Ads initialization complete")
                }

                override fun onInitializationFailed(
                    error: UnityAdsInitializationError,
                    message: String
                ) {
                    Log.e(LOGTAG, "Unity Ads initialization failed: [$error] $message")
                }
            })
    }

    fun loadAd() {
        val playerMetaData = PlayerMetaData(mActivity)
        playerMetaData.setServerId("rikshot")
        playerMetaData.commit()
        val ordinalMetaData = MediationMetaData(mActivity)
        ordinalMetaData.setOrdinal(ordinal)
        ordinalMetaData.commit()
        load(mActivity.getString(R.string.unity_interstitial_id), object : IUnityAdsLoadListener {
            override fun onUnityAdsAdLoaded(placementId: String) {
                Log.v(LOGTAG, "Ad for $placementId loaded")
            }

            override fun onUnityAdsFailedToLoad(
                placementId: String,
                error: UnityAdsLoadError,
                message: String
            ) {
                Log.e(LOGTAG, "Ad for $placementId failed to load: [$error] $message")
            }
        })
    }

    fun showInterstitialAds() {
        show(
            mActivity,
            mActivity.getString(R.string.unity_interstitial_id),
            UnityAdsShowOptions(),
            object : IUnityAdsShowListener {
                override fun onUnityAdsShowFailure(
                    placementId: String,
                    error: UnityAdsShowError,
                    message: String
                ) {
                    Log.e(LOGTAG, "onUnityAdsShowFailure: $error - $message")
                }

                override fun onUnityAdsShowStart(placementId: String) {
                    Log.v(LOGTAG, "onUnityAdsShowStart: $placementId")
                }

                override fun onUnityAdsShowClick(placementId: String) {
                    Log.v(LOGTAG, "onUnityAdsShowClick: $placementId")
                }

                override fun onUnityAdsShowComplete(
                    placementId: String,
                    state: UnityAdsShowCompletionState
                ) {
                    Log.v(LOGTAG, "onUnityAdsShowComplete: $placementId")

                }
            })
    }

    fun  loadBanner(adsContainer:LinearLayout){
        bottomBanner = BannerView(mActivity, mActivity.getString(R.string.unity_banner_id), UnityBannerSize(320, 50))
        bottomBanner?.listener = bannerListener
        bottomBanner?.load()
        adsContainer.addView(bottomBanner)
    }

    fun hideBanner(){
        bottomBanner?.removeAllViews()
        if (bottomBanner != null) {
            bottomBanner = null
        }
    }

    // Listener for banner events
    private val bannerListener: IListener = object : IListener {
        override fun onBannerLoaded(bannerAdView: BannerView) {
            Log.v(LOGTAG, "onBannerLoaded: " + bannerAdView.placementId)
        }

        override fun onBannerFailedToLoad(bannerAdView: BannerView, errorInfo: BannerErrorInfo) {
            Log.e(
                LOGTAG,
                "Unity Ads failed to load banner for " + bannerAdView.placementId + " with error: [" + errorInfo.errorCode + "] " + errorInfo.errorMessage
            )
        }

        override fun onBannerClick(bannerAdView: BannerView) {
            Log.v(LOGTAG, "onBannerClick: " + bannerAdView.placementId)
        }

        override fun onBannerLeftApplication(bannerAdView: BannerView) {
            Log.v(LOGTAG, "onBannerLeftApplication: " + bannerAdView.placementId)
        }
    }


}