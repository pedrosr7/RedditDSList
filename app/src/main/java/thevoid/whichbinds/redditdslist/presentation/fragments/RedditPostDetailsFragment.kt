package thevoid.whichbinds.redditdslist.presentation.fragments

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.navigation.fragment.NavHostFragment
import coil.api.load
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.android.synthetic.main.fragment_redditpost_details.*
import thevoid.whichbinds.redditdslist.R
import thevoid.whichbinds.redditdslist.core.plataform.BaseFragment
import thevoid.whichbinds.redditdslist.domain.models.RedditPost

class RedditPostDetailsFragment : BaseFragment() {

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0
    private var player: SimpleExoPlayer? = null

    override fun layoutId(): Int = R.layout.fragment_redditpost_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val redditPost: RedditPost? = arguments?.getSerializable("redditPost") as RedditPost?

        redditPost?.let {
            if(redditPost.media !== null) {
                if(player == null) player = initializePlayer()
                playerView?.player = player
                val uri = Uri.parse(redditPost.media.hls_url)
                val mediaSource = buildMediaSource(uri)

                player?.let {
                    with(it) {
                        playWhenReady = this@RedditPostDetailsFragment.playWhenReady
                        repeatMode = Player.REPEAT_MODE_ONE
                        seekTo(currentWindow, playbackPosition)
                        mediaSource?.let { it1 -> prepare(it1, false, false) }
                    }
                }

                playerView?.visibility = View.VISIBLE
                imageView?.visibility = View.GONE
            }else {
                imageView?.load(redditPost.url)
                imageView?.scaleType = ImageView.ScaleType.FIT_CENTER
                playerView?.visibility = View.GONE
                imageView?.visibility = View.VISIBLE
            }

            textViewAuthor.text = redditPost.author
            textView_title.text = redditPost.title
            chipScore.text = redditPost.score.toString()
            chipComments.text = redditPost.commentCount.toString()
        }

        imageViewBack.setOnClickListener {
            NavHostFragment.findNavController(
                this@RedditPostDetailsFragment).popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    override fun onPause() {
        super.onPause()
        if (player != null && player?.isPlaying == true) {
            player?.playWhenReady = false
        }
    }

    override fun onResume() {
        super.onResume()
        if (player != null) player?.playWhenReady = true
    }

    private fun releasePlayer() {
        player?.let {
            with (it) {
                this@RedditPostDetailsFragment.playWhenReady = playWhenReady
                playbackPosition = currentPosition
                currentWindow = currentWindowIndex
                playWhenReady = false
                release()
            }
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