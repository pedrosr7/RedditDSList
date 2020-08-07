package thevoid.whichbinds.redditdslist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import thevoid.whichbinds.dslist.listPaged
import thevoid.whichbinds.redditdslist.R
import thevoid.whichbinds.redditdslist.core.plataform.BaseFragment


class SettingsFragment : BaseFragment() {

    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }

    override fun layoutId(): Int = R.layout.fragment_settings


    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = GridLayoutManager(context, 2)
        manager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 2) 2 else 1
            }
        }
        recyclerView.layoutManager = manager

        listPaged<String, String> {
            recyclerView = this@SettingsFragment.recyclerView

            row {
                content = "Enable swipe"
                viewType = R.layout.item_settings_switch_right
                viewBind { content, itemView ->
                    val switch: Switch? =
                        itemView.findViewById(R.id.switch_settings)

                    switch?.text = content

                    //switch?.layoutParams = ViewGroup.LayoutParams()

                }
            }

            row {
                content = "Enable swipe"
                viewType = R.layout.item_settings_switch_right
                viewBind { content, itemView ->
                    val switch: Switch? =
                        itemView.findViewById(R.id.switch_settings)

                    switch?.text = content

                    //switch?.layoutParams = ViewGroup.LayoutParams()

                }
            }

            row {
                viewType = R.layout.item_settings_text_field
                viewBind { content, itemView ->
                    val editText: TextInputEditText? =
                        itemView.findViewById(R.id.textFieldInputSettings)

                   // editText?.text = content

                    //switch?.layoutParams = ViewGroup.LayoutParams()

                }
            }
        }
    }
}