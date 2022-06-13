package com.mcecelja.pocket.ui.posts

import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mcecelja.pocket.Pocket
import com.mcecelja.pocket.data.Page
import com.mcecelja.pocket.data.PreferenceManager
import com.mcecelja.pocket.data.dto.ResponseMessage
import com.mcecelja.pocket.data.dto.post.CategoryDTO
import com.mcecelja.pocket.data.dto.post.PostDTO
import com.mcecelja.pocket.enums.PreferenceEnum
import com.mcecelja.pocket.services.CategoryService
import com.mcecelja.pocket.services.PostService
import com.mcecelja.pocket.ui.BaseViewModel
import com.mcecelja.pocket.utils.RestUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsViewModel : BaseViewModel() {

    private val _posts: MutableLiveData<List<PostDTO>> = MutableLiveData<List<PostDTO>>()
    val posts: LiveData<List<PostDTO>> = _posts

    private val _categories: MutableLiveData<List<CategoryDTO>> =
        MutableLiveData<List<CategoryDTO>>()
    val categories: LiveData<List<CategoryDTO>> = _categories

    private val _categoryFilter: MutableLiveData<String> = MutableLiveData<String>()
    val categoryFilter: LiveData<String> = _categoryFilter

    private val _titleFilter: MutableLiveData<String> = MutableLiveData<String>()
    val titleFilter: LiveData<String> = _titleFilter

    fun setPosts() {

        val apiCall =
            RestUtil.createService(
                PostService::class.java,
                PreferenceManager.getPreference(PreferenceEnum.TOKEN)
            ).getPosts(categoryFilter.value, titleFilter.value)

        changeVisibility(View.VISIBLE)

        apiCall.enqueue(object : Callback<ResponseMessage<Page<PostDTO>>> {
            override fun onResponse(
                call: Call<ResponseMessage<Page<PostDTO>>>,
                response: Response<ResponseMessage<Page<PostDTO>>>
            ) {

                changeVisibility(View.INVISIBLE)

                if (response.isSuccessful) {

                    if (response.body()?.errorCode != null) {
                        setError(response.body()!!.errorCode)
                    } else {
                        _posts.postValue(response.body()?.payload?.content)
                    }
                }
            }

            override fun onFailure(
                call: Call<ResponseMessage<Page<PostDTO>>>,
                t: Throwable
            ) {

                changeVisibility(View.INVISIBLE)

                Toast.makeText(
                    Pocket.application,
                    "Get posts failed!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
    }

    fun setCategories() {

        val apiCall =
            RestUtil.createService(
                CategoryService::class.java,
                PreferenceManager.getPreference(PreferenceEnum.TOKEN)
            ).getCategories()

        changeVisibility(View.VISIBLE)

        apiCall.enqueue(object : Callback<ResponseMessage<Page<CategoryDTO>>> {
            override fun onResponse(
                call: Call<ResponseMessage<Page<CategoryDTO>>>,
                response: Response<ResponseMessage<Page<CategoryDTO>>>
            ) {

                changeVisibility(View.INVISIBLE)

                if (response.isSuccessful) {

                    if (response.body()?.errorCode != null) {
                        setError(response.body()!!.errorCode)
                    } else {
                        _categories.postValue(response.body()?.payload?.content)
                    }
                }
            }

            override fun onFailure(
                call: Call<ResponseMessage<Page<CategoryDTO>>>,
                t: Throwable
            ) {

                changeVisibility(View.INVISIBLE)

                Toast.makeText(
                    Pocket.application,
                    "Get categories failed!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
    }

    fun setTitleFilter(title: String) {
        _titleFilter.postValue(title)
    }

    fun setCategoryFilter(category: String) {
        _categories.value?.let { it ->
            val categoryDTO = it.firstOrNull { it.name == category }
            if (categoryDTO != null) {
                _categoryFilter.postValue(categoryDTO.id)
            } else {
                _categoryFilter.postValue(null)
            }
        }
    }
}