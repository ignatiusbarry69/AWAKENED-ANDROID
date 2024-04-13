package com.dicoding.courseschedule.ui.home

import android.content.Context
import androidx.lifecycle.*
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.util.QueryType

class HomeViewModel(repository: DataRepository): ViewModel() {

    private val _queryType = MutableLiveData<QueryType>()

    val nearestCourseLiveData: LiveData<Course?> = Transformations.switchMap(_queryType) { queryType ->
        repository.getNearestSchedule(queryType)
    }

    init {
        _queryType.value = QueryType.CURRENT_DAY
    }

    fun setQueryType(queryType: QueryType) {
        _queryType.value = queryType
    }

}


class HomeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = DataRepository.getInstance(context)
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository!!) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
