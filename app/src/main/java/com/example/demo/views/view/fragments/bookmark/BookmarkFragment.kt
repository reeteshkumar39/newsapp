package com.example.demo.views.view.fragments.bookmark

import android.content.ClipData
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.divum.ibn.interfaces.OnFragmentInteractionListener
import com.example.demo.R
import com.example.demo.databinding.FragmentBookmarkBinding
import com.example.demo.databinding.FragmentDetailsBinding
import com.example.demo.views.interfaces.DashBoardListener
import com.example.demo.views.models.Articles
import com.example.demo.views.models.Root
import com.example.demo.views.utils.ItemDecorator
import com.example.demo.views.utils.dpToPx
import com.example.demo.views.view.fragments.dashboard.DashBoardAdapter
import com.example.demo.views.view.fragments.dashboard.DashboardFragmentDirections
import com.example.demo.views.view.fragments.details.DetailsFragmentArgs
import com.example.demo.views.view.fragments.details.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_bookmark.*
import org.koin.android.ext.android.get


class BookmarkFragment : Fragment(), DashBoardListener {
    lateinit var activityListener: OnFragmentInteractionListener
    val args: BookmarkFragmentArgs by navArgs()
    private val adapter by lazy { DashBoardAdapter() }
    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var itemDecorator: ItemDecorator
    lateinit var viewModel: BookmarksViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            activityListener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemDecorator = get()
        viewModel = get()
        args.articles.let { viewModel.getBookmarksList(it.asList()) }
        viewModel.bookmarksList.observe(viewLifecycleOwner, {
            if(it.isEmpty()) tv_no_bookmarks.visibility = View.VISIBLE else tv_no_bookmarks.visibility = View.GONE
            adapter.data = it
            adapter.notifyDataSetChanged()
        })
        binding = FragmentBookmarkBinding.bind(view)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        binding.rvBookmark.adapter = adapter
        binding.rvBookmark.addItemDecoration(itemDecorator)
        adapter.listener = this


        iv_back.setOnClickListener{
            activityListener.goBack()
        }

        viewModel.getSavedNews().observe(viewLifecycleOwner,{
            viewModel.getBookmarksList(it)
        })
    }

    override fun onClickBookmark(article: Articles) {
        viewModel.deleteArticle(article)
    }

    override fun onClickArticle(article: Articles) {
        activityListener.gotoFragment(
           BookmarkFragmentDirections.bookmarkToDetails(args.root, article)
        )
    }
}