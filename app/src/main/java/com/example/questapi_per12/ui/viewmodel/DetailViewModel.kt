package com.example.questapi_per12.ui.viewmodel

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questapi_per12.model.Mahasiswa
import com.example.questapi_per12.repository.MahasiswaRepository
import com.example.questapi_per12.ui.view.DestinasiDetail
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailUiState {
    data class Success(val mahasiswa: Mahasiswa) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val mhs: MahasiswaRepository
) : ViewModel() {

    var mahasiswaDetailState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    private val _nim: String = checkNotNull(savedStateHandle[DestinasiDetail.NIM])

    init {
        getMahasiswabyNim()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getMahasiswabyNim() {
        viewModelScope.launch {
            mahasiswaDetailState = DetailUiState.Loading
            mahasiswaDetailState = try {
                val mahasiswa = mhs.getMahasiswabyNim(_nim)
                DetailUiState.Success(mahasiswa)
            } catch (e: IOException) {
                DetailUiState.Error
            } catch (e: HttpException) {
                DetailUiState.Error
            }
        }
    }
}