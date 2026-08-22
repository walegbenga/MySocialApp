package com.mysocialapp.android.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable fun HomeScreen(viewModel: HomeViewModel) { val state by viewModel.state.collectAsState(); Surface { Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) { Text("MySocialApp", style = MaterialTheme.typography.headlineMedium); Spacer(Modifier.height(12.dp)); Text("Foundation is ready. No social features are enabled yet."); Spacer(Modifier.height(24.dp)); Text("API: ${state.healthStatus}"); Spacer(Modifier.height(12.dp)); Button(onClick = viewModel::checkApi, enabled = !state.isLoading) { Text(if (state.isLoading) "Checking…" else "Check API health") } } } }
