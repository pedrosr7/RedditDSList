package thevoid.whichbinds.redditdslist.presentation.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialFadeThrough
import thevoid.whichbinds.redditdslist.R
import thevoid.whichbinds.redditdslist.core.extensions.appContext
import thevoid.whichbinds.redditdslist.core.plataform.BaseFragment

class RedditPostDetailsFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_redditpost_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_graph_reddit_post
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
            scrimColor = Color.YELLOW
            //setAllContainerColors(requireContext().themeColor(R.attr.colorSurface))
        }

       // enterTransition = MaterialFadeThrough()

        requireActivity().onBackPressedDispatcher.addCallback {
            NavHostFragment.findNavController(
                this@RedditPostDetailsFragment).navigateUp()
        }
    }

}