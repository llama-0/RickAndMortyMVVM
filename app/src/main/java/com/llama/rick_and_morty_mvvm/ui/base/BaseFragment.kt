package com.llama.rick_and_morty_mvvm.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.llama.rick_and_morty_mvvm.App
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.ui.command.Command

abstract class BaseFragment<
        ScreenState : BaseScreenState,
        CommandType : Command,
        ViewModel : BaseViewModel<ScreenState, CommandType>>(
    viewModelClass: Class<ViewModel>
) : Fragment() {

    private lateinit var app: App

    override fun onAttach(context: Context) {
        super.onAttach(context)
        app = requireContext().applicationContext as App
    }

    protected open val viewModel: ViewModel by lazy {
        ViewModelProvider(this, app.factory).get(viewModelClass)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToViewModelObservables()
    }

    private fun subscribeToViewModelObservables() {
        val modelObserver = Observer(this::renderView)
        viewModel.screenStateLiveData.observe(viewLifecycleOwner, modelObserver)
        val commandObserver = Observer(this::executeCommand)
        viewModel.commandsLiveData.observe(viewLifecycleOwner, commandObserver)

    }

    protected abstract fun renderView(screenState: ScreenState)

    protected open fun executeCommand(command: CommandType) {
        showUnderDevelopmentMessage()
    }

    protected fun showSnackbar(view: View, message: String) {
        Snackbar.make(
            view,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun showUnderDevelopmentMessage() {
        showSnackbar(requireView(), getString(R.string.under_development))
    }
}