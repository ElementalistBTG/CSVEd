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
import model.CSVUnit
import util.ViewModel
import util.testData

class MainViewModel : ViewModel() {

    private val disposables = CompositeDisposable()

    private val dataSubject: BehaviorSubject<MutableList<CSVUnit>> = BehaviorSubject.createDefault(mutableListOf())

//    private lateinit var _data: Flowable<List<CSVUnit>>
//    val data: Flowable<List<CSVUnit>>
//        get() = _data

    init {
//        dataSubject.subscribe { systems ->
//            _data = just(systems)
//        }.addTo(disposables)
//        getObservableFromList(EditCSV().openFile())
        //getObservableFromList(testData).subscribe{println(it)}
    }

//    fun populateTable() {
//        _data = EditCSV().openFile().toFlowable()
//    }

    fun clearData() {
        disposables.clear()
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


}