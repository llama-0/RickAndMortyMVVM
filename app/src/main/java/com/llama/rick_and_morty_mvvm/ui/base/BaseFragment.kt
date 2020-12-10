package com.llama.rick_and_morty_mvvm.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.llama.rick_and_morty_mvvm.R

abstract class BaseFragment<
        ScreenState : RefreshableScreenState<*>,
        SupportedCommandType : Command,
        ViewModel : BaseViewModel<ScreenState, SupportedCommandType>>( // why name the `BaseViewModel` as `RefreshableViewModel` ?
    @LayoutRes val layoutResId: Int,
    viewModelClass: Class<ViewModel>
) : Fragment(layoutResId) {

    protected open val viewModel: ViewModel by lazy {
        ViewModelProvider(this).get(viewModelClass)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        subscribeToViewModelObservables()
    }

    private fun subscribeToViewModelObservables() {
        val modelObserver = Observer(this::renderView)
        viewModel.modelUpdate.observe(viewLifecycleOwner, modelObserver)
        val commandObserver = Observer(this::executeCommand)
        viewModel.commandsLiveData.observe(viewLifecycleOwner, commandObserver)

    }

    protected abstract fun renderView(model: ScreenState)

    protected open fun executeCommand(command: SupportedCommandType) {
        showUnderDevelopmentMessage()
    }

    private fun showUnderDevelopmentMessage() {
        Snackbar.make(requireView(), getString(R.string.under_development), Snackbar.LENGTH_SHORT)
            .show()
    }
}