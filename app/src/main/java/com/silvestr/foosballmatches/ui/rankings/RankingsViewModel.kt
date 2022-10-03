package com.silvestr.foosballmatches.ui.rankings

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.silvestr.foosballmatches.data.Ranking
import com.silvestr.foosballmatches.domain.RankingsInteractor
import com.silvestr.foosballmatches.ui.rankings.RankingsFragment.Companion.DEFAULT_SORT_TYPE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RankingsViewModel @Inject constructor(private val rankingsInteractor: RankingsInteractor) :
        ViewModel() {

    val rankings: MutableLiveData<List<Ranking>> = MutableLiveData()
    private var disposable: CompositeDisposable = CompositeDisposable()
    var sortType: SortType = DEFAULT_SORT_TYPE

    init {
        loadRankings()
    }

    fun loadRankings() {
        disposable.add(rankingsInteractor.getRankings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            rankings.value = it.sortedBy { ranking ->
                                when (sortType) {
                                    SortType.PLAYED -> ranking.played
                                    SortType.WON -> ranking.won
                                }
                            }.asReversed()
                        },
                        {
                            Log.d("RankingsViewModel", "Error: unable to load rankings")
                        }
                ))
    }

    fun sortRankings(sortType: SortType) {
        this.sortType = sortType

        val sortedRankings = when (sortType) {
            SortType.WON -> {
                rankings.value?.sortedBy { it.won }?.asReversed()
            }
            SortType.PLAYED -> {
                rankings.value?.sortedBy { it.played }?.asReversed()
            }
        }

        sortedRankings?.let {
            rankings.postValue(it)
        }
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

}