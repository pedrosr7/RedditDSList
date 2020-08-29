# RedditDSList

 [![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

 RedditDSList is an application that show how useful the library [DSList](https://github.com/pedrosr7/DSList) is for created a recyclerview list in Android:
 
 DSList is:
 * Type-safe builder: DSList is base on domain-specific language (DSL), suitable for building complex hierarchical data structures in a semi-declarative way.
 * Reactive: DSList use StateFlow to handle the state of the list. This helps you load and display small chunks of data at a time.
 
 ## Quick Start
 
 ### Simple example
  ```kotlin
  listDSL<String, RedditPost> {
     recyclerView = this@MainActivity.recyclerView
      
     load {
         when(it) {
             ListState.REFRESH -> print("")
             ListState.PREPEND -> mainViewModel.getPosts(after = null, before = before)
             ListState.APPEND -> mainViewModel.getPosts(after = after, before = null)
         }
     }

     observe(redditPostLiveData) { posts ->
         posts?.let {
             for (value in listOfRedditPost) {
                 row {
                     id = key
                     content = value
                     viewType = R.layout.item_reddit_post
                     viewBind { content, itemView ->
                         val title: TextView? =
                             itemView.findViewById(R.id.textView_title)
                         val author: TextView? =
                             itemView.findViewById(R.id.textView_author)
                         title?.text = content.title
                         author?.text = content.author
                     }
                 }
             }           
         }
     }
  }
 ```

 ### Advance example
 
 ```kotlin

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
  
               if(redditPost.media !== null) {
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
               }else {
                 image?.load(redditPost.url)
                 playerView?.visibility = View.GONE
                 image?.visibility = View.VISIBLE
               }
  
               title?.text = redditPost.title
  
               cardView?.setOnClickListener {
                 val bundle = bundleOf("redditPost" to redditPost)
                 findNavController().navigate(R.id.action_redditPostFragment_to_redditPostDetailsFragment, bundle)
               }
             }
           }
         }
       }
    }
  }

```
 
 ## Documentation of DSList
 
 To build a simple or paged list you can use different functions or properties.
 
 ### properties
 
  - **recyclerView**: Instance of RecyclerView class.
  - **rows**: List of Row type.
  - **after**: Last id of the row list.
  - **before**: First id of the row list.

 ### base
 
 First we need to provide the type arguments <`Key`,`Model`>.
 
 - **Key**: Id that is associated to each row. It can be of any type (String, Int, etc)
 - **Model**: Type of data that we want to show in the list, for example an String or data class
 
 ```kotlin
 data class RedditPost (
    val key: String,
    val title: String,
    val author: String,
 )
 ```

 ```kotlin
 listDSL<String, RedditPost> {}
 ```

 Also we need the reference to the RecyclerView visual component.
 
 ```kotlin
 listDSL<String, RedditPost> {
    recyclerView = this@MainActivity.recyclerView
 }
 ```

 And finally we can use the different functions to build our list
 
 Important: Don't forget to add the layout manager.
 
 ```kotlin
 recyclerView.layoutManager = LinearLayoutManager(this) // or GridLayoutManager(this,2)
 ```

 ### row{}
 
 The row function allows you to add the content of the list and associate it with the visual components that have been defined.
 You have four properties `id`, `content`, `viewType` and `viewBind`.
 
 - **id**: Row identifier (optional)
 - **content**: Row content (optional)
 - **viewType**: Id of the view already designed for the items (required)
 - **viewBind**: Function that associates content with visual components (required)
 
 ```kotlin
 listDSL<String, RedditPost> {
    recyclerView = this@MainActivity.recyclerView
   
    for (value in listOfRedditPost) {
        row {
            id = key
            content = value
            viewType = R.layout.item_reddit_post
            viewBind { content, itemView ->
                val title: TextView? =
                    itemView.findViewById(R.id.textView_title)
                val author: TextView? =
                    itemView.findViewById(R.id.textView_author)
                title?.text = content.title
                author?.text = content.author
            }
        }
    }
 }
 ```

 ### observe{}
 
 The observe function allows you to update the items of the list, 
 using for this the LiveData class that allows us to create an observable data holder.
 
 ```kotlin
 val redditPostLiveData: MutableLiveData<List<RedditPost>> by lazy {
    MutableLiveData<List<RedditPost>>()
 }
 ```
 ```kotlin
 listDSL<String, RedditPost> {
    recyclerView = this@MainActivity.recyclerView
     
    observe(redditPostLiveData) { posts ->
        posts?.let {
            for (value in listOfRedditPost) {
                row {
                    id = key
                    content = value
                    viewType = R.layout.item_reddit_post
                    viewBind { content, itemView ->
                        val title: TextView? =
                            itemView.findViewById(R.id.textView_title)
                        val author: TextView? =
                            itemView.findViewById(R.id.textView_author)
                        title?.text = content.title
                        author?.text = content.author
                    }
                }
            }           
        }
    }
 }
 ```

 ### load{}
  
  The load is a reactive function that is call every time we can not scroll further.
  - **REFRESH**: Called when data is update.
  - **PREPEND**: Called when reaches the top of the list.
  - **APPEND**: Called when reaches the bottom of the list.
  
 ```kotlin
 listDSL<String, RedditPost> {
    recyclerView = this@MainActivity.recyclerView
      
    load {
        when(it) {
            ListState.PREPEND -> mainViewModel.getPosts(after = null, before = before)
            ListState.APPEND -> mainViewModel.getPosts(after = after, before = null)
        }
    }

    observe(redditPostLiveData) { posts ->
        posts?.let {
            for (value in listOfRedditPost) {
                row {
                    id = key
                    content = value
                    viewType = R.layout.item_reddit_post
                    viewBind { content, itemView ->
                        val title: TextView? =
                            itemView.findViewById(R.id.textView_title)
                        val author: TextView? =
                            itemView.findViewById(R.id.textView_author)
                        title?.text = content.title
                        author?.text = content.author
                    }
                }
            }           
        }
    }
 }
 ```
 ## Created by Pedro Sánchez Ramírez
 
 ## License

    Copyright (c) 2020, DSList Contributors.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
