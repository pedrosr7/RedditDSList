package thevoid.whichbinds.redditdslist.presentation.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import thevoid.whichbinds.dslist.*
import thevoid.whichbinds.redditdslist.R
import thevoid.whichbinds.redditdslist.core.extensions.appContext
import thevoid.whichbinds.redditdslist.core.extensions.itemMove
import thevoid.whichbinds.redditdslist.core.extensions.removeAt
import thevoid.whichbinds.redditdslist.core.plataform.BaseFragment
import thevoid.whichbinds.redditdslist.domain.models.Post

@ExperimentalCoroutinesApi
class PostFragment : BaseFragment() {

    var editTextType: TextInputEditText? = null
    var editTextTitle: TextInputEditText? = null
    var editTextDescription: TextInputEditText? = null

    private val postsLiveData: MutableLiveData<Post> by lazy {
        MutableLiveData<Post>()
    }

    companion object {
        fun newInstance(): PostFragment = PostFragment()
    }

    override fun layoutId(): Int = R.layout.fragment_post

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPostForm()
        setPostList()
    }

    private fun setPostList() {
        recyclerViewPost.layoutManager = LinearLayoutManager(context)
        val postsDSL = listDSL<String, Post> {
            recyclerView = this@PostFragment.recyclerViewPost
            observe(postsLiveData) { postsIn ->
                postsIn?.let {
                    row {
                        content = it
                        viewType = R.layout.item_post
                        viewBind { post, itemView ->
                            val title: TextView? =
                                itemView.findViewById(R.id.textView_title)

                            val description: TextView? =
                                itemView.findViewById(R.id.textView_description)

                            val type: TextView? =
                                itemView.findViewById(R.id.textView_type)

                            title?.text = post?.title
                            description?.text = post?.description
                            type?.text = post?.type

                        }
                    }
                }
            }
        }

        swipeTo(recyclerViewPost) {
            left {
                backgroundColor = context?.let { getColor(it, R.color.colorRed) }
                icon = this@PostFragment.context?.let { ContextCompat.getDrawable(it, R.drawable.ic_delete) }
               // text = PaintText("hola mundo", margin = 130f)
                swiped = {
                    postsDSL.adapter.removeAt(it.adapterPosition)
                }
            }
        }

        dragTo(recyclerViewPost) { viewHolder, target ->
            postsDSL.adapter.itemMove(viewHolder.adapterPosition, target.adapterPosition)
        }

    }

    private fun setPostForm() {
        val manager = GridLayoutManager(context, 2)
        manager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position) {
                    in 0..1 -> 1
                    else -> 2
                }
            }
        }
        recyclerView.layoutManager = manager

        listDSL<String, String> {
            recyclerView = this@PostFragment.recyclerView

            row {
                content = "Title"
                viewType = R.layout.item_text_field
                viewBind { content, itemView ->

                    val layoutText: TextInputLayout? =
                        itemView.findViewById(R.id.textFieldSettings)
                    editTextTitle = itemView.findViewById(R.id.textFieldInput)

                    layoutText?.hint = content

                    editTextTitle?.isSingleLine = true

                }
            }

            row {
                content = "Type"
                viewType = R.layout.item_text_field
                viewBind { content, itemView ->

                    val layoutText: TextInputLayout? =
                        itemView.findViewById(R.id.textFieldSettings)
                    editTextType = itemView.findViewById(R.id.textFieldInput)

                    layoutText?.hint = content
                    editTextType?.inputType
                }
            }

            row {
                content = "Description"
                viewType = R.layout.item_text_field
                viewBind { content, itemView ->

                    val layoutText: TextInputLayout? =
                        itemView.findViewById(R.id.textFieldSettings)
                    editTextDescription = itemView.findViewById(R.id.textFieldInput)

                    layoutText?.hint = content

                    editTextDescription?.minLines = 4
                    editTextDescription?.maxLines = 4
                }
            }

            row {
                content = "New post"
                viewType = R.layout.item_text_field
                viewBind { content, itemView ->

                    val button: Button? =
                        itemView.findViewById(R.id.textButton)

                    button?.text = content
                    appContext?.getColor(R.color.colorAccent)?.let { button?.setBackgroundColor(it) }

                    button?.setOnClickListener {
                        val title = editTextTitle?.text?.toString()
                        val description = editTextDescription?.text?.toString()
                        val type = editTextType?.text?.toString()

                        if(!title.isNullOrBlank() && !description.isNullOrBlank() && !type.isNullOrBlank()){
                            postsLiveData.postValue(Post(
                                title = title ,
                                description =  description,
                                type = type
                            ))
                            editTextTitle?.text?.clear()
                            editTextDescription?.text?.clear()
                            editTextType?.text?.clear()
                        }
                    }
                }
            }
        }
    }
}