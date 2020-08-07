package thevoid.whichbinds.redditdslist.core.extensions

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import thevoid.whichbinds.redditdslist.core.plataform.BaseFragment

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().func().commit()

fun BaseFragment.close() = parentFragmentManager.popBackStack()

val BaseFragment.appContext: Context? get() = activity?.applicationContext