package thevoid.whichbinds.redditdslist.presentation.fragments

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.fragment_redditpost.*
import kotlinx.android.synthetic.main.item_reddit_post.*

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import thevoid.whichbinds.dslist.ListState
import thevoid.whichbinds.dslist.listDSL
import thevoid.whichbinds.redditdslist.R
import thevoid.whichbinds.redditdslist.core.extensions.observe
import thevoid.whichbinds.redditdslist.core.plataform.BaseFragment
import thevoid.whichbinds.redditdslist.domain.models.RedditPost
import thevoid.whichbinds.redditdslist.presentation.viewmodels.RedditPostViewModel

class RedditPostFragment : BaseFragment() {

    private val redditPostViewModel: RedditPostViewModel by viewModel()
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0

    companion object {
        fun newInstance(): RedditPostFragment {
            return RedditPostFragment()
        }
    }

    override fun layoutId(): Int = R.layout.fragment_redditpost

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(redditPostViewModel.showLoading) { show ->
            show?.let {
                val alpha = if (it) 1.0f else  0.0f

                progressbar_main.animate()
                    .alpha(alpha)
                    .withStartAction { if (it) progressbar_main?.let { view -> view.visibility = View.VISIBLE } }
                    .withEndAction { if (!it) progressbar_main?.let { view -> view.visibility = View.GONE } }
                    .duration = 1000
            }
        }



/*        val view: View = inflater.inflate(R.layout.item_reddit_post, null)*/
        recyclerView.layoutManager = LinearLayoutManager(context)

        listDSL<String, RedditPost> {
            recyclerView = this@RedditPostFragment.recyclerView

            load {
                when(it) {
                    ListState.REFRESH -> print("")
                    ListState.PREPEND -> redditPostViewModel.getPosts(after = null, before = before)
                    ListState.APPEND -> redditPostViewModel.getPosts(after = after, before = null)
                }
            }

            observe(redditPostViewModel.redditPost) { posts ->
                posts?.let {
                    for (value in it) {
                        row {
                            id = value.key
                            content = value
                            viewType = R.layout.item_reddit_post
                            viewBind { redditPost, itemView ->
                                val title: TextView? =
                                    itemView.findViewById(R.id.textView_title)

                                val image: ImageView? =
                                    itemView.findViewById(R.id.imageView)

                                val playerView: PlayerView? =
                                    itemView.findViewById(R.id.playerView)

                                val cardView: MaterialCardView? =
                                    itemView.findViewById(R.id.cardView_post)


                                if (redditPost?.media !== null) {
                                    val player = initializePlayer()
                                    playerView?.player = player
                                    val uri = Uri.parse(redditPost.media.hls_url)
                                    val mediaSource = buildMediaSource(uri)
                                    with(player) {
                                        volume = 0f
                                        playWhenReady = this@RedditPostFragment.playWhenReady
                                        seekTo(currentWindow, playbackPosition)
                                        repeatMode = Player.REPEAT_MODE_ONE
                                        mediaSource?.let { it1 -> prepare(it1, false, false) }
                                    }


                                    playerView?.visibility = View.VISIBLE
                                    image?.visibility = View.GONE
                                } else {
                                    image?.load(redditPost?.url)
                                    playerView?.visibility = View.GONE
                                    image?.visibility = View.VISIBLE
                                }

                                title?.text = redditPost?.title

                                cardView?.transitionName =
                                    "shared_element_container(${redditPost?.key})"

                                cardView?.setOnClickListener {
                                    val bundle = bundleOf("redditPost" to redditPost)
                                    findNavController().navigate(
                                        R.id.action_redditPostFragment_to_redditPostDetailsFragment,
                                        bundle
                                    )
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    private fun releasePlayer(player: ExoPlayer) {
        with (player) {
            playWhenReady = playWhenReady
            playbackPosition = currentPosition
            currentWindow = currentWindowIndex
            release()
        }
    }
    private fun initializePlayer(): SimpleExoPlayer = SimpleExoPlayer.Builder(requireContext()).build()

    private fun buildMediaSource(uri: Uri): MediaSource? {
        val dataSourceFactory: DataSource.Factory =
            DefaultDataSourceFactory(context, "exoplayer-codelab")
        return HlsMediaSource.Factory(dataSourceFactory)
            .createMediaSource(uri)
    }

}