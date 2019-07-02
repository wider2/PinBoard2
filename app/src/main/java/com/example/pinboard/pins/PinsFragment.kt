package com.example.pinboard.pins

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.pinboard.MainActivity
import com.example.pinboard.MainActivity.Companion.SELECTED_PIN
import com.example.pinboard.R
import com.example.pinboard.details.DetailsFragment
import com.example.pinboard.model.PinModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_main.*

class PinsFragment : Fragment() {

    companion object {
        const val TAG: String = "PinsFragment"
    }

    private var pinList: List<PinModel> = ArrayList<PinModel>()
    val tvOutput by lazy { view!!.findViewById<TextView>(R.id.tvOutput) }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initialize the RV. 'Apply' makes it works simplier
        pinsRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = PinAdapter(pinList, onDataListener)
            setHasFixedSize(true)
        }
        pinsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.d(TAG, getString(R.string.reached_end))
                    Toast.makeText(context, getString(R.string.reached_end), Toast.LENGTH_LONG).show()
                }
            }
        })
        showListOfPins();
    }

    val onDataListener = object : OnDataListener {
        override fun profileClicked(pinModel: PinModel) {
            Log.wtf(TAG, pinModel.id)
            SELECTED_PIN = pinModel

            try {
                activity!!.supportFragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .setCustomAnimations(
                        R.anim.enter_from_right,
                        R.anim.exit_to_left,
                        R.anim.enter_from_left,
                        R.anim.exit_to_right
                    )
                    .add(R.id.fragment_container, DetailsFragment(), DetailsFragment::class.java.simpleName)
                    .addToBackStack(DetailsFragment::class.java.simpleName)
                    .commit()
            } catch (e: IllegalStateException) {
                Log.e(PinsFragment::class.java.simpleName, e.message)
            }
        }
    }

    fun showListOfPins() {
        pinList = MainActivity.OFFLINE_PINS_LIST
        if (!pinList.isEmpty() && pinList.size > 0) {
            tvOutput.setText("");
            pinsRecyclerView.adapter = PinAdapter(pinList, onDataListener)
        }
    }


}