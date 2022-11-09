package com.example.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.view.ContentInfoCompat.Flags
import com.example.service.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var startServiceIntent: Intent
    lateinit var bindServiceIntent: Intent
    lateinit var myService :MyService
    //서비스가 살았는지 죽었는지
    var isService:Boolean = false
    var flags = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//     스타트서비스
        binding.btnStart.setOnClickListener {
            startServiceIntent = Intent(this, MyService::class.java)
            if(flags ==true){
                startServiceIntent.action = MyService.ACTION_CREATE
            }else{
                startServiceIntent.action = MyService.ACTION_DELETE
            }
            startService(startServiceIntent)
        }
        binding.btnStop.setOnClickListener {
            stopService(startServiceIntent)
        }

        //바인드서비스 처리방법
        val connection = object : ServiceConnection{
            //바인드서비스와 연결이 되면 onServiceConnected가 콜백되고 거기서 IBinder를 보내준다다
           override fun onServiceConnected(p0: ComponentName?, ibinder: IBinder?) {
            val myBinder = ibinder as MyService.MyBinder
            myService = myBinder.getService()
            isService = true
                Log.d("Service", "connected BindService")

                myService.create("여기는 메인액티비티")
                Toast.makeText(applicationContext, "${myService.bindData}", Toast.LENGTH_SHORT).show()
                Log.d("Service", "${myService.bindData}")
           }
            //bindservice 연결이 되면 콜백
            override fun onServiceDisconnected(p0: ComponentName?) {
                Log.d("Service", "disconnected BindService")
            }
        }

        binding.btnBind.setOnClickListener{
            bindServiceIntent = Intent(this, MyService::class.java)
            bindService(bindServiceIntent, connection, Context.BIND_AUTO_CREATE)
        }
        binding.btnUnbind.setOnClickListener{
            if(isService ==true){
                unbindService(connection)
                isService == false
            }
        }

    }
}