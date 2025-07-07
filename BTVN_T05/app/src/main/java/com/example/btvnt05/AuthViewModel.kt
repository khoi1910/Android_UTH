package com.example.btvnt05

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _authState = mutableStateOf(AuthState.IDLE)
    val authState: State<AuthState> = _authState

    private val _user = mutableStateOf<User?>(null)
    val user: State<User?> = _user

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    init {
        // Kiểm tra user đã đăng nhập chưa
        checkCurrentUser()
    }

    private fun checkCurrentUser() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            _user.value = User(
                uid = currentUser.uid,
                name = currentUser.displayName ?: "User",
                email = currentUser.email ?: "",
                profileImageUrl = currentUser.photoUrl?.toString() ?: ""
            )
            _authState.value = AuthState.SUCCESS
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            try {
                _authState.value = AuthState.LOADING

                val credential = GoogleAuthProvider.getCredential(idToken, null)
                val result = auth.signInWithCredential(credential).await()

                result.user?.let { firebaseUser ->
                    _user.value = User(
                        uid = firebaseUser.uid,
                        name = firebaseUser.displayName ?: "User",
                        email = firebaseUser.email ?: "",
                        profileImageUrl = firebaseUser.photoUrl?.toString() ?: ""
                    )
                    _authState.value = AuthState.SUCCESS
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Đăng nhập thất bại"
                _authState.value = AuthState.ERROR
            }
        }
    }

    fun logout() {
        auth.signOut()
        _user.value = null
        _authState.value = AuthState.IDLE
        _errorMessage.value = ""
    }
}