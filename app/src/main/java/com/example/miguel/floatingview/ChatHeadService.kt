package com.example.miguel.floatingview

import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import jp.co.recruit_lifestyle.android.floatingview.FloatingViewManager
import android.util.DisplayMetrics
import jp.co.recruit_lifestyle.android.floatingview.FloatingViewListener
import android.support.v4.app.NotificationCompat
import android.view.Gravity
import android.R.attr.gravity
import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.FrameLayout
import android.support.v4.content.ContextCompat.getSystemService








class ChatHeadService : Service(), FloatingViewListener {
    companion object {
        val TAG: String = ChatHeadService::class.java.simpleName
    }

    private var floatingViewManager: FloatingViewManager? = null
    private var windowManager: WindowManager? = null
    private var mFrameLayout: FrameLayout? = null

    val EXTRA_CUTOUT_SAFE_AREA = "cutout_safe_area"
    private val NOTIFICATION_ID = 9083150


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        val inflater = LayoutInflater.from(this)
        val iconView = inflater.inflate(R.layout.widget_chat, null, false) as ImageView

        iconView.setOnClickListener {
            inflateMainView()
        }

        val metrics = DisplayMetrics()
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        windowManager!!.defaultDisplay.getMetrics(metrics)

        floatingViewManager = FloatingViewManager(this, this)
        floatingViewManager!!.setActionTrashIconImage(R.drawable.ic_bookmark_it_logo)
        floatingViewManager!!.setFixedTrashIconImage(R.drawable.ic_bookmark_it_logo)
        if (intent != null) {
            floatingViewManager!!.setSafeInsetRect(intent.getParcelableExtra(EXTRA_CUTOUT_SAFE_AREA))
        }

        val options = FloatingViewManager.Options()

        options.overMargin = ((0 * metrics.density).toInt())

        floatingViewManager!!.addViewToWindow(iconView, options)

        startForeground(NOTIFICATION_ID, createNotification(this))

        return START_REDELIVER_INTENT
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun inflateMainView() {

        val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.BOTTOM or Gravity.START

        mFrameLayout = FrameLayout(this)

        windowManager?.addView(mFrameLayout, params)

        val layoutInflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layoutInflater.inflate(R.layout.super_layout, mFrameLayout)
    }

    private fun removeHead() {
        if (floatingViewManager != null) {
            floatingViewManager?.removeAllViewToWindow()
            floatingViewManager = null
            stopSelf()
        }
    }

    private fun createNotification(context: Context): Notification {
        val builder = NotificationCompat.Builder(context, "asdas")
        builder.setWhen(System.currentTimeMillis())
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentTitle("Titulo")
        builder.setContentText("Content Text")
        builder.setOngoing(true)
        builder.priority = NotificationCompat.PRIORITY_MIN
        builder.setCategory(NotificationCompat.CATEGORY_SERVICE)

        return builder.build()
    }

    override fun onFinishFloatingView() {
        stopSelf()
        Log.d(TAG, "onFinishFloatingView")
    }

    override fun onDestroy() {
        removeHead()

        super.onDestroy()
    }

    override fun onTouchFinished(isFinishing: Boolean, x: Int, y: Int) {
        if (isFinishing) {
            Log.d(TAG, "isFinishing")
        } else {
            Log.d(TAG, "else isFinishing")
        }
    }
}