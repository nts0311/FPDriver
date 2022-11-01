package com.sonnt.fpdriver

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import com.sonnt.fpdriver.di.AppModule
import com.sonnt.fpdriver.features.auth.LoginActivity
import com.sonnt.fpdriver.message.SessionExpiredEvent
import com.sonnt.fpdriver.network.stomp.StompMessageHub
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class FpDriverApplication: Application() {

    lateinit var currentActivity: Activity

    override fun onCreate() {
        super.onCreate()
        instance = this
        setActivitiesListener()
        EventBus.getDefault().register(this)
    }

    fun setActivitiesListener() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(
                activity: Activity,
                savedInstanceState: Bundle?
            ) {
                currentActivity = activity
            }

            override fun onActivityStarted(activity: Activity) {
                currentActivity = activity
            }
            override fun onActivityResumed(activity: Activity) {
                currentActivity = activity
            }
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}

        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: SessionExpiredEvent) {
        showLoginScreen()
    }

    fun showLoginScreen() {
        val intent = Intent(instance, LoginActivity::class.java)
        startActivity(intent)
    }

    fun setUpApplicationAfterLoggingIn() {
        AppModule.provideStompMessageHub().reconnect()
    }

    companion object {
        lateinit var instance: FpDriverApplication
    }
}