package com.example.demo.views.view.fragments.details

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.divum.ibn.interfaces.OnFragmentInteractionListener
import com.example.demo.R
import com.example.demo.databinding.FragmentDetailsBinding
import com.example.demo.views.interfaces.DashBoardListener
import com.example.demo.views.models.Articles
import com.example.demo.views.utils.ItemDecorator
import com.example.demo.views.utils.dpToPx
import com.example.demo.views.view.fragments.dashboard.DashBoardAdapter
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.android.ext.android.get


class DetailsFragment : Fragment(), DashBoardListener {

    val args: DetailsFragmentArgs by navArgs()
    private val adapter by lazy { DashBoardAdapter() }
    lateinit var activityListener: OnFragmentInteractionListener
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
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
        val viewModel = get<DetailsViewModel>()
        viewModel.getViewListFromNewsFeed(args.root, args.articles)
        viewModel.newsLists.observe(viewLifecycleOwner) {
            adapter.data = it
            adapter.notifyDataSetChanged()
        }
        iv_back.setOnClickListener {
            activityListener.goBack()
        }
        tv_title.text = args.root.articles!![0].url
        binding = FragmentDetailsBinding.bind(view)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        binding.rvDetails.adapter = adapter
        binding.rvDetails.addItemDecoration(ItemDecorator(0f, 0f, 12f.dpToPx(requireContext()), 0f))
        adapter.listener = this
    }

    companion object {

    }

    override fun onClickBookmark(article: Articles) {

    }

    override fun onClickArticle(article: Articles) {
    }
}