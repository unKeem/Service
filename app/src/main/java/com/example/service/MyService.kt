package com.example.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyService : Service() {
    var bindData:String? = null
    companion object{
        const val ACTION_CREATE = "create"
        const val ACTION_DELETE = "delete"
    }

    inner class MyBinder : Binder(){
        fun getService(): MyService{
            return this@MyService
        }
    }

    override fun onCreate() {
        Log.d("Service", "서비스 onCreate기능이 작동")
        super.onCreate()
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d("Service", "서비스 onBind기능이 작동")
        return MyBinder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("Service", "서비스 onStartComand 처리됨")
        when(intent?.action){
            ACTION_CREATE->{Log.d("Service", "서비스 start기능이 작동")}
            ACTION_DELETE->{Log.d("Service", "서비스 start기능이 작동")}
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("Service", "서비스 onUnbind기능이 작동")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.d("Service", "서비스 onDestroy기능이 작동")
        super.onDestroy()
    }
    fun create(data: String){
        Log.d("Service", "바인드서비스 create함수 ${data}처리됨")
        bindData = data+"*****"
    }
}