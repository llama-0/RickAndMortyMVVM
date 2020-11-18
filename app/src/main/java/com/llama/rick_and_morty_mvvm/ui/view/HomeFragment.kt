package com.llama.rick_and_morty_mvvm.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.llama.rick_and_morty_mvvm.App
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.progressbar_layout.*
import kotlinx.android.synthetic.main.recycler_error_layout.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var viewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        refreshRecyclerViewData()
    }

    private fun initViewModel() {
        activity?.application.let {
            if (it is App) {
                viewModel = ViewModelProvider(this, it.factory).get(HomeViewModel::class.java)
            }
        }
    }

    /*
    * 1. как работает лайвдата?
    * 2. почему setUpNormalView иногда со второго раза появляется?
    * 3. почему progressbar постоянно Visible до тех пор, пока не загрузятся данные,
    *   если зайти в приложение с изначально выключенным интернетом, хотя по логам
    *   isLoading == false !?!
    * Бонусы: 1. вернулась к состоянию лишних запросов при повороте экрана
    *         2. по кнопке retry без интернета больше не показываю снекбар
    *         3. всё ещё есть логика во фрагменте, а надо убрать
    *         4. общий класс не создан
    * */
    private fun refreshRecyclerViewData() {
        viewModel.dataList.observe(viewLifecycleOwner, {
            initAdapter(it)
            Log.d(TAG, "refreshRecyclerViewData: initing daapter for the `first` time")
        })
        viewModel.errorState.observe(viewLifecycleOwner, {
            showErrorLayout()
        })
        viewModel.loadState.observe(viewLifecycleOwner, {
            progress_bar_layout.visibility = it
        })
    }

    // через vm вызов снекбара сделать.
    private fun initAdapter(list: List<SimpleCharacter>) {
        setUpNormalView()
        rv_items.adapter = HomeAdapter(list) { character ->
            showSnackbar(rv_items, character.name)
        }
    }

    // getCharacters явно вызывать. на onError менять value

    private fun showErrorLayout() {
        Log.d("TAG", "showErrorLayout: inside")
        setUpErrorView()
        btn_retry.setOnClickListener {
//            viewModel.onButtonClicked()
//                    .observe(viewLifecycleOwner, {
//                showSnackbar(fragment_home_layout, getString(R.string.check_internet_connection_message))
//            })
//            viewModel.onButtonClickedDone()
            viewModel.dataList.observe(viewLifecycleOwner, {
                Log.d(TAG, "refreshRecyclerViewData: initing adapter for the `second` time")
                initAdapter(it)
            })
            // how to intercept connection timeout ?
        }
    }

    private fun setUpNormalView() {
        recycler_error_layout.visibility = View.GONE
//        progress_bar_layout.visibility = View.GONE
        rv_items.visibility = View.VISIBLE
    }

    private fun setUpErrorView() {
        recycler_error_layout.visibility = View.VISIBLE
        rv_items.visibility = View.GONE
    }

    private fun showSnackbar(view: View, message: String) {
        Snackbar.make(
                view,
                message,
                Snackbar.LENGTH_SHORT
        ).show()
    }

    companion object {
        private const val TAG = "TAG"
    }
}