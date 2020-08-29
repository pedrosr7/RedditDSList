package thevoid.whichbinds.redditdslist.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import thevoid.whichbinds.redditdslist.R
import thevoid.whichbinds.redditdslist.core.extensions.setupWithNavController


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) setupBottomNavigationBar()


    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(R.navigation.nav_graph_reddit_post, R.navigation.nav_graph_post)
        bottomNavigation.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )
    }


    private fun hideSystemUi(playerView: PlayerView?) {
        playerView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }


    //--------------------------------------------------------------------------------------------//
    //***************************************EXAMPLES*********************************************//
    //--------------------------------------------------------------------------------------------//



        /*

        swipeTo(recyclerView) {
            left {
                backgroundColor = getColor(R.color.colorDefaultRightSwipe)
                icon = ContextCompat.getDrawable(this@MainActivity, thevoid.whichbinds.redditdslistlib.R.drawable.ic_archive)
                text = PaintText("hola mundo", margin = 120f)
                swiped = {
                    lis.adapter.removeAt(it.adapterPosition)
                }
            }
            right {
                text = PaintText("hola mundo", margin = 100f, textColor = getColor(
                    R.color.colorPrimary
                ))
            }
        }

        dragTo(recyclerView) { viewHolder, target ->
            //lis.adapter.itemMove(viewHolder.adapterPosition, target.adapterPosition)
        }
    }*/



    private fun showSnackbar(itemView: View, text: String) {
        Snackbar
            .make(itemView, text, Snackbar.LENGTH_LONG)
            .show()
    }

    //---------------------------------------SWIPE------------------------------------------------//

    /*private fun exampleOfSwipeLeftOrRightDefault() {
        listManager.enableSwipeTo(
            directionTo = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) { viewHolder: RecyclerView.ViewHolder, direction: Int ->
            when (direction){
                ItemTouchHelper.LEFT -> listManager.listViewAdapter.removeAt(viewHolder.adapterPosition)
                ItemTouchHelper.RIGHT -> listManager.listViewAdapter.removeAt(viewHolder.adapterPosition)
            }
        }
    }

    private fun exampleOfSwipeLeftDefault() {
        listManager.enableSwipeTo(
            directionTo = ItemTouchHelper.LEFT
        ) { viewHolder: RecyclerView.ViewHolder, _: Int ->
            listManager.listViewAdapter.removeAt(viewHolder.adapterPosition)
        }
    }

    private fun exampleOfSwipeLeftDefaultWithIconBackgroudDefinition() {
        listManager.enableSwipeTo(
            directionTo = ItemTouchHelper.LEFT,
            backgroundColorLeft = getColor(R.color.colorPrimaryDark),
            iconLeft = ContextCompat.getDrawable(this,
                R.drawable.ic_attachment
            )
        ) { viewHolder: RecyclerView.ViewHolder, direction: Int ->
            listManager.listViewAdapter.removeAt(viewHolder.adapterPosition)
        }
    }

    private fun exampleOfSwipeLeftWithText() {
        listManager.enableSwipeTo(
            directionTo = ItemTouchHelper.LEFT,
            textRight = "Read later"
        ) { viewHolder: RecyclerView.ViewHolder, direction: Int ->
            listManager.listViewAdapter.removeAt(viewHolder.adapterPosition)
        }
    }

    private fun exampleOfSwipeRightWithTextAndWithoutIcon() {
        listManager.enableSwipeTo(
            directionTo = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
            textLeft = "Read later",
            textRight = "Mark as read",
            iconLeft = null
        ) { viewHolder: RecyclerView.ViewHolder, direction: Int ->
            listManager.listViewAdapter.removeAt(viewHolder.adapterPosition)
        }
    }

    private fun exampleOfSwipeRightWithCustomText() {
        val paint = Paint()
        paint.color = Color.BLUE
        paint.isAntiAlias = true
        paint.textSize = 90f
        listManager.enableSwipeTo(
            directionTo = ItemTouchHelper.RIGHT,
            iconLeft = ContextCompat.getDrawable(this,
                R.drawable.ic_attachment
            ),
            textLeft = "Read later",
            paintLeft = paint
        ) { viewHolder: RecyclerView.ViewHolder, direction: Int ->
            listManager.listViewAdapter.removeAt(viewHolder.adapterPosition)
        }
    }

    private fun exampleOfSwipeRightWithCustomDraw(){

        listManager.enableSwipeTo(
            directionTo = ItemTouchHelper.RIGHT,
            onDraw = {

                val itemView = it.viewHolder.itemView
                val itemHeight = itemView.bottom - itemView.top

                val backgroundColor = getColor(R.color.colorDefaultRightSwipe)
                val icon: Drawable? = ContextCompat.getDrawable(this,
                    R.drawable.ic_archive
                )

                // Draw the red delete background
                val background = ColorDrawable()
                background.color = backgroundColor
                background.setBounds(itemView.left + it.dX.toInt(), itemView.top, itemView.left, itemView.bottom)
                background.draw(it.c)

                icon?.let { iconRes ->
                    // Calculate position of delete icon
                    val iconTop = itemView.top + (itemHeight - iconRes.intrinsicHeight) / 2
                    val iconMargin = (itemHeight - iconRes.intrinsicHeight) / 2
                    val iconLeft = itemView.left + iconMargin
                    val iconRight = itemView.left + iconMargin + iconRes.intrinsicWidth
                    val iconBottom = iconTop + iconRes.intrinsicHeight

                    // Draw the delete icon
                    iconRes.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    iconRes.draw(it.c)
                }
            }
        ) { viewHolder: RecyclerView.ViewHolder, direction: Int ->
            listManager.listViewAdapter.removeAt(viewHolder.adapterPosition)
        }
    }

    //---------------------------------------DRAG-------------------------------------------------//
    private fun exampleOfDrag() {
        listManager.enableDragTo()
    }*/
}
