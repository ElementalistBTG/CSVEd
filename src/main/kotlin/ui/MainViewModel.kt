package ui

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Flowable.just
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.internal.operators.observable.ObservableCreate
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.toFlowable
import io.reactivex.rxjava3.kotlin.toObservable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import model.CSVUnit
import util.ViewModel
import util.testData

class MainViewModel : ViewModel() {

    private val _dataList = mutableListOf<CSVUnit>()
    val dataList: List<CSVUnit>
        get() = _dataList

    fun openFile() {
        getObservableFromList(EditCSV().openFile()).subscribe { _dataList.add(it) }
    }

    private fun getObservableFromList(myList: List<CSVUnit>) =
        Observable.create<CSVUnit> { emitter ->
            myList.forEach { row ->
                if (row.name == "") {
                    emitter.onError(Exception("There's no value to show"))
                }
                emitter.onNext(row)
            }
            emitter.onComplete()
        }

    var itemsFlow : Flow<List<CSVUnit>> = emptyFlow()

    fun openNewFile(){
       itemsFlow = flow {
            val items = EditCSV().openFile()
            emit(items)
        }
    }


}