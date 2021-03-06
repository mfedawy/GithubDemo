package com.example.githubdemo.ui.gtihub_repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubdemo.base.BaseResponseConsumer
import com.example.githubdemo.base.BaseViewModel
import com.example.githubdemo.network.RequestState
import com.example.githubdemo.network.models.RepositoriesModel
import com.example.githubdemo.ui.GithubRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject


class RepositoriesViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : BaseViewModel() {

    private val _repos = MutableLiveData<List<RepositoriesModel>>()
    val onRepoUpdated: LiveData<List<RepositoriesModel>> = _repos

    fun getRepositories() {
        githubRepository.getRepositoriesData()
            .doOnSubscribe { disposable.add(it) }
            .subscribe(object : BaseResponseConsumer() {
                override fun loading() {
                    _loading.value = true
                }

                override fun <T> success(data: T) {
                    _loading.value = false
                    _repos.value = data as List<RepositoriesModel>
                }

                override fun error(msg: String) {
                    _loading.value = false
                    _error.value = msg
                }

            })
    }

}