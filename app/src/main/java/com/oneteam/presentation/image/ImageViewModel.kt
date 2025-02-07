package com.oneteam.presentation.image

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.oneteam.core.dispatchers.Dispatcher
import com.oneteam.core.util.NetworkHelper
import com.oneteam.core.viewmodel.BaseViewModel
import com.oneteam.domain.model.Image
import com.oneteam.domain.use_case.GetImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class ImageViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
    private val networkHelper: NetworkHelper,
    appDispatcher: Dispatcher
) : BaseViewModel(appDispatcher) {

    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean> = _isConnected

    init {
        checkNetworkState()
    }

    private fun checkNetworkState() {
        _isConnected.value = networkHelper.isNetworkConnected()
    }

    private val _search = MutableStateFlow("")
    val search: StateFlow<String> get() = _search

    fun emptySearch(){
        _search.value = ""
    }

    fun getImages(query: String): Flow<PagingData<Image>> {
        return getImagesUseCase.execute(query).cachedIn(viewModelScope)
    }


    fun onSearchQueryChanged(search: String) {
        _search.value = search
    }

}