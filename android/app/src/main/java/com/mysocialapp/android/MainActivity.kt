package com.mysocialapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.*
import com.mysocialapp.android.data.remote.NetworkModule
import com.mysocialapp.android.data.repository.RemoteAuthRepository
import com.mysocialapp.android.data.repository.TokenStore
import com.mysocialapp.android.ui.*
import com.mysocialapp.android.ui.auth.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tokens = TokenStore(applicationContext)
        val repository = RemoteAuthRepository(NetworkModule.api(tokens::token))
        setContent { AppTheme {
            val vm: AuthViewModel = viewModel(factory = viewModelFactory { initializer { AuthViewModel(repository, tokens) } })
            val state by vm.state.collectAsState(); val nav = rememberNavController()
            LaunchedEffect(state.user, state.loading) { if (!state.loading) nav.navigate(if (state.user == null) "welcome" else "home") { popUpTo(0) } }
            if (state.loading && state.user == null) Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() } else NavHost(nav, startDestination = if (state.user == null) "welcome" else "home") {
                composable("welcome") { WelcomeScreen({ nav.navigate("login") }, { nav.navigate("register") }) }
                composable("login") { LoginScreen(state.loading, state.error, { nav.popBackStack() }, vm::login, { nav.navigate("register") }) }
                composable("register") { RegisterScreen(state.loading, state.error, { nav.popBackStack() }, vm::register) }
                composable("home") { state.user?.let { HomeScreen(it, { nav.navigate("profile") }, vm::logout) } }
                composable("profile") { state.user?.let { ProfileScreen(it, { nav.navigate("edit-profile") }, { nav.popBackStack() }) } }
                composable("edit-profile") { state.user?.let { user -> EditProfileScreen(user, state.loading, state.error, { nav.popBackStack() }, { vm.update(it); nav.popBackStack() }) } }
            }
        } }
    }
}
