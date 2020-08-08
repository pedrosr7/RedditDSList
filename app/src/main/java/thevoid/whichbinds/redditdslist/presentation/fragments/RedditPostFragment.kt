package thevoid.whichbinds.redditdslist.presentation.fragments

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.fragment_redditpost.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import thevoid.whichbinds.dslist.ListState
import thevoid.whichbinds.dslist.listPaged
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

        recyclerView.layoutManager = LinearLayoutManager(context)

        listPaged<String, RedditPost> {
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

                                val author: TextView? =
                                    itemView.findViewById(R.id.textView_author)

                                val image: ImageView? =
                                    itemView.findViewById(R.id.imageView)

                                val playerView: PlayerView? =
                                    itemView.findViewById(R.id.playerView)

                                val cardView: MaterialCardView? =
                                    itemView.findViewById(R.id.cardView_post)


                                if(redditPost.media !== null) {
                                    val player = initializePlayer()
                                    playerView?.player = player
                                    val uri = Uri.parse(redditPost.media.hls_url)
                                    val mediaSource = buildMediaSource(uri)
                                    with(player) {
                                        playWhenReady = this@RedditPostFragment.playWhenReady
                                        seekTo(currentWindow, playbackPosition)
                                        mediaSource?.let { it1 -> prepare(it1, false, false) }
                                    }


                                    playerView?.visibility = View.VISIBLE
                                    image?.visibility = View.GONE
                                }else {
                                    image?.load(redditPost.url)
                                    playerView?.visibility = View.GONE
                                    image?.visibility = View.VISIBLE
                                }

                                title?.text = redditPost.author
                                author?.text = redditPost.title

                                cardView?.setOnClickListener {
                                    findNavController().navigate(R.id.redditPostDetailsFragment)
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