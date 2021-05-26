package com.example.myapplicationbluetooth

import android.app.DownloadManager
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var mBlueAdapter : BluetoothAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBlueAdapter = BluetoothAdapter.getDefaultAdapter()
        if(mBlueAdapter==null){
            statusBluetooth.text = "Блютуз недоступен"
        }
        else{
            statusBluetooth.text = "Блютуз включен"
        }
        onBtn.setOnClickListener{
            if(!mBlueAdapter!!.isEnabled){
                showToast("Включение bluetooth")
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent, REQUEST_ENABLE_BT)
            }
            else{
                showToast("Включён")
            }
        }
        discoverableBtn.setOnClickListener{
            if(!mBlueAdapter!!.isDiscovering){
                showToast("Сделайте устройство видимым для других")
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
                startActivityForResult(intent, REQUEST_DISCOVER_BT)
            }
        }
        offBtn.setOnClickListener{
            if(!mBlueAdapter!!.isEnabled){
                mBlueAdapter!!.disable()
                showToast("Отклоючение")
            }
            else{
                showToast("Включите блютуз")
            }
        }
        pairedBtn.setOnClickListener{
            if(!mBlueAdapter!!.isEnabled){
                pairedTv.text = "Сопряженные устройства"
                val devices = mBlueAdapter!!.bondedDevices
                for(device in devices){
                    pairedTv.append("""Device: ${device.name}, $device""".trimIndent())
                }
            }
            else {
                showToast("Вруби блютуз")
            }
        }
    }

    private fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }
    companion object{
        private const val REQUEST_ENABLE_BT = 0
        private const val REQUEST_DISCOVER_BT = 1
    }
}