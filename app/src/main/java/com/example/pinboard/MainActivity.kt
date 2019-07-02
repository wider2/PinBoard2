package com.example.pinboard

import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.pinboard.model.PinModel
import com.example.pinboard.pins.PinsFragment
import com.example.pinboard.utils.ConnectivityReceiver
import com.example.pinboard.utils.addFragmentSafely
import com.example.pinboard.utils.replaceFragmentSafely
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.subjects.CompletableSubject
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector, MainContract.View,
    ConnectivityReceiver.ConnectivityReceiverListener {

    private lateinit var snackBar: Snackbar

    val textViewTop by lazy { findViewById<TextView>(R.id.textViewTop) }

    val imageViewRefresh by lazy { findViewById<ImageView>(R.id.imageViewRefresh) }

    val progressBar by lazy { findViewById<ProgressBar>(R.id.progressBar) }

    companion object {
        const val TAG: String = "MainActivity"
        var SELECTED_PIN: PinModel? = null
        var OFFLINE_PINS_LIST: List<PinModel> = listOf()
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var presenter: MainContract.Presenter

    val onDestroyCompletable = CompletableSubject.create()!!


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.subscribeEvent()

        if (savedInstanceState == null) { //fragment initialize
            addFragmentSafely(
                fragment = PinsFragment(),
                tag = PinsFragment::class.java.simpleName,
                containerViewId = R.id.fragment_container,
                allowStateLoss = true
            )
        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showSnackBarMessage(isConnected)
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    private fun showSnackBarMessage(isConnected: Boolean) {
        if (::snackBar.isInitialized) { //isInitialized property one can check initialization state of a lateinit variable.
        } else {
            snackBar = Snackbar.make(textViewTop, "", Snackbar.LENGTH_INDEFINITE)
        }
        if (!isConnected) {
            snackBar = Snackbar
                .make(textViewTop, getString(R.string.no_connection), Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(resources.getColor(R.color.yellow))
                .setDuration(BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.network_settings), object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        val intent = Intent(Settings.ACTION_SETTINGS)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                })

            val sbView = snackBar.getView()
            val textView = sbView.findViewById(android.support.design.R.id.snackbar_text) as TextView
            textView.setTextColor(Color.WHITE)
            snackBar.show()
        } else {
            snackBar.dismiss()
        }
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onDestroy() {
        onDestroyCompletable.onComplete()
        super.onDestroy()
    }

    override fun resultFailed(message: String) {
        if (OFFLINE_PINS_LIST.isEmpty()) {
            textViewTop.setText(message)
            hideProgress()

            imageViewRefresh.visibility = View.VISIBLE
            imageViewRefresh.setOnClickListener({ result ->
                presenter.subscribeEvent()
            })
        }
    }

    override fun showEventList(pinList: List<PinModel>) {
        hideProgress()
        imageViewRefresh.visibility = View.GONE
        textViewTop.setText("")
        OFFLINE_PINS_LIST = pinList

        replaceFragmentSafely(
            fragment = PinsFragment(),
            tag = PinsFragment::class.java.simpleName,
            containerViewId = R.id.fragment_container,
            allowStateLoss = true
        )
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

}
