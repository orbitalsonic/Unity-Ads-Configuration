package com.orbitalsonic.unityadsexample


import android.app.Activity
import android.content.Context
import android.widget.LinearLayout
import com.unity3d.ads.UnityAds
import com.unity3d.services.banners.BannerErrorInfo
import com.unity3d.services.banners.BannerView
import com.unity3d.services.banners.UnityBannerSize

class UnityAdsUtils(context: Context) {

    // set testMode false for production
    private val testMode = true
    private var bannerView: BannerView? = null

    private val mContext: Context = context



    fun initializeAds(){
        UnityAds.initialize(mContext, mContext.getString(R.string.unity_ads_id), testMode)
    }


    fun showInterstitialAds() {
        if (UnityAds.isReady(mContext.getString(R.string.unity_interstitial_id))) {
            UnityAds.show(mContext as Activity?, mContext.getString(R.string.unity_interstitial_id))
        }
    }

    fun loadBanner(adsContainer:LinearLayout){
        bannerView = BannerView(mContext as Activity?, mContext.getString(R.string.unity_banner_id), UnityBannerSize(320, 50))
        bannerView!!.listener = object : BannerView.IListener {
            override fun onBannerLoaded(p0: BannerView?) {
                adsContainer.addView(p0)
            }

            override fun onBannerClick(p0: BannerView?) {

            }

            override fun onBannerFailedToLoad(p0: BannerView?, p1: BannerErrorInfo?) {
            }

            override fun onBannerLeftApplication(p0: BannerView?) {

            }

        }
        bannerView!!.load()
    }


}