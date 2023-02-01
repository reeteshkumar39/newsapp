package com.example.demo.views.view.fragments.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView
import com.divum.ibn.interfaces.OnFragmentInteractionListener
import com.example.demo.R
import com.example.demo.databinding.FragmentDashboardBinding
import com.example.demo.views.base.BaseFragment
import com.example.demo.views.interfaces.DashBoardListener
import com.example.demo.views.models.Articles
import com.example.demo.views.models.Root
import com.example.demo.views.utils.ItemDecorator
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.koin.android.ext.android.get


class DashboardFragment : BaseFragment(R.layout.fragment_dashboard), DashBoardListener {
    private val TAG = "DashboardFragment"

    var rootLocal: Root? = null
    private lateinit var binding: FragmentDashboardBinding
    private val dashBoardAdapter by lazy { DashBoardAdapter() }
    lateinit var activityListener: OnFragmentInteractionListener
    var articlesBookmarked1 : MutableList<Articles> = mutableListOf()
    lateinit var historyAdapter : ArrayAdapter<String>
    lateinit var pref : SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    val finalAricleList : MutableList<Articles> = mutableListOf()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            activityListener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val viewModel = get<DashboardViewModel>()
        val itemDecorator = get<ItemDecorator>()


        pref =  requireActivity().getPreferences(Context.MODE_PRIVATE)
        editor = pref.edit()
        binding = FragmentDashboardBinding.bind(view)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        binding.rvDashboard.adapter = dashBoardAdapter
        binding.rvDashboard.addItemDecoration(itemDecorator)
        dashBoardAdapter.listener = this

        viewModel.getNewsFeed()
        viewModel.newsDataFeed.observe(viewLifecycleOwner) { root ->
            root.articles?.let { it1 -> viewModel.getViewListFromNewsFeed(it1) }
            rootLocal = root
            editor.putString("root", Gson().toJson(root, Root::class.java)).commit()

            viewModel.getSavedNews().observe(viewLifecycleOwner) { articleBookmarked ->
                finalAricleList.clear()
                rootLocal = Gson().fromJson(pref.getString("root", ""), Root::class.java)
                articlesBookmarked1 = articleBookmarked as MutableList<Articles>
                rootLocal?.articles?.forEach { it ->
                    articleBookmarked.forEach { it1 ->
                        if (it.title == it1.title) {
                            it.isBookmarked = true
                            return@forEach
                        }
                    }
                    finalAricleList.add(it)
                }
                finalAricleList.let { viewModel.getViewListFromNewsFeed(it) }
            }
        }

        viewModel.newsLists.observe(viewLifecycleOwner) { article ->
            dashBoardAdapter.data = article
            dashBoardAdapter.notifyDataSetChanged()
        }

        et_search.setOnEditorActionListener(object: TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.saveHistory(v?.text.toString())
                    v?.text = ""
                    return true;
                }
                return false;
            }
        })

        viewModel.getHistory().observe(viewLifecycleOwner) {
            val temp = mutableListOf<String>()
            it.forEach { value ->
                temp.add(value.value)
            }
            historyAdapter = ArrayAdapter<String>(
                requireContext(),
                R.layout.item_history_list,
                R.id.tv_autocomplete,
                temp.toTypedArray()
            )
            et_search.setAdapter(historyAdapter)
            historyAdapter.setNotifyOnChange(true)
            et_search.setOnTouchListener { v, event ->
                et_search.showDropDown()
                false
            }
        }


        iv_bookmark.setOnClickListener {
            activityListener.gotoFragment(
                DashboardFragmentDirections.dashboradToBookmark(articlesBookmarked1.toTypedArray(),
                    rootLocal!!
                )
            )
        }
    }


    override fun onClickBookmark(article: Articles) {
        if (article.isBookmarked) {
            binding.viewModel?.deleteArticle(article)
        } else {
            article.isBookmarked = true
            binding.viewModel?.saveArticle(article)
        }

    }

    override fun onClickArticle(article: Articles) {
        activityListener.gotoFragment(
            DashboardFragmentDirections.dashboardToDetails(rootLocal!!, article)
        )
    }
}