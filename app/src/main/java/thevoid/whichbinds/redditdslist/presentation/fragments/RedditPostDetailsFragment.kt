package thevoid.whichbinds.redditdslist.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_redditpost_details.*
import thevoid.whichbinds.redditdslist.R
import thevoid.whichbinds.redditdslist.core.plataform.BaseFragment

class RedditPostDetailsFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_redditpost_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageViewBack.setOnClickListener {
            NavHostFragment.findNavController(
                this@RedditPostDetailsFragment).navigateUp()
        }
    }

}