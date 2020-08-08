package thevoid.whichbinds.redditdslist.presentation.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.google.android.material.transition.MaterialContainerTransform
import thevoid.whichbinds.redditdslist.R
import thevoid.whichbinds.redditdslist.core.plataform.BaseFragment

class RedditPostDetailsFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_redditpost_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_graph_reddit_post
            duration = 50000
            scrimColor = Color.YELLOW
            //setAllContainerColors(requireContext().themeColor(R.attr.colorSurface))
        }
    }


}