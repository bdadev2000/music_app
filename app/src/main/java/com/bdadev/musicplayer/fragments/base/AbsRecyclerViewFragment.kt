package com.bdadev.musicplayer.fragments.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bdadev.musicplayer.R
import com.bdadev.musicplayer.databinding.FragmentMainRecyclerBinding
import com.bdadev.musicplayer.interfaces.IScrollHelper
import com.bdadev.musicplayer.util.ThemedFastScroller.create
import com.google.android.material.transition.MaterialFadeThrough

abstract class AbsRecyclerViewFragment<A : RecyclerView.Adapter<*>, LM : RecyclerView.LayoutManager> :
    AbsMainActivityFragment(R.layout.fragment_main_recycler), IScrollHelper {
    private var _binding: FragmentMainRecyclerBinding? = null
    private val binding get() = _binding!!
    protected var adapter: A? = null
    protected var layoutManager: LM? = null
    val shuffleButton get() = binding.btnShuffle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainRecyclerBinding.bind(view)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        enterTransition = MaterialFadeThrough().addTarget(binding.rcv)
        reenterTransition = MaterialFadeThrough().addTarget(binding.rcv)
        initLayoutManager()
        initAdapter()
        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {
        binding.rcv.apply {
            layoutManager = this@AbsRecyclerViewFragment.layoutManager
            adapter = this@AbsRecyclerViewFragment.adapter
            create(this)
        }
    }

    private fun initLayoutManager() {
        layoutManager = createLayoutManager()
    }

    protected abstract fun createLayoutManager(): LM

    private fun initAdapter() {
        adapter = createAdapter()
        adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkIsEmpty()
            }
        })
    }

    private fun checkIsEmpty() {
        binding.txtEmpty.setText(emptyMessage)
        binding.txtEmpty.isVisible = adapter!!.itemCount == 0
    }

    protected open val emptyMessage: Int
        @StringRes get() = R.string.empty


    @NonNull
    protected abstract fun createAdapter(): A

    protected fun invalidateAdapter() {
        initAdapter()
        checkIsEmpty()
        binding.rcv.adapter = adapter
    }

    protected fun invalidateLayoutManager() {
        initLayoutManager()
        binding.rcv.layoutManager = layoutManager
    }

    val recyclerView get() = binding.rcv

    val container get() = binding.root

    override fun scrollToTop() {
        recyclerView.scrollToPosition(0)
//        binding.appBarLayout.setExpanded(true, true)
    }
}