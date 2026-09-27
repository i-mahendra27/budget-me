package com.budgetsave.ui.screens.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.budgetsave.data.local.db.entity.CategoryEntity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategoryScreen(
    categoryId: Long?,
    onNavigateBack: () -> Unit,
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableStateOf("restaurant") }
    var selectedColor by remember { mutableStateOf("#FF9800") }
    var nameError by remember { mutableStateOf<String?>(null) }

    val isEditing = categoryId != null

    LaunchedEffect(categoryId) {
        if (categoryId != null) {
            val category = viewModel.getCategoryById(categoryId)
            category?.let {
                name = it.name
                selectedIcon = it.icon
                selectedColor = it.color
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Edit Category" else "Add Category") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            if (name.isBlank()) {
                                nameError = "Name is required"
                                return@TextButton
                            }
                            scope.launch {
                                if (isEditing) {
                                    viewModel.updateCategory(
                                        CategoryEntity(
                                            id = categoryId!!,
                                            name = name,
                                            icon = selectedIcon,
                                            color = selectedColor
                                        )
                                    )
                                } else {
                                    viewModel.addCategory(name, selectedIcon, selectedColor)
                                }
                                onNavigateBack()
                            }
                        }
                    ) {
                        Text("Save")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    nameError = null
                },
                label = { Text("Category Name") },
                modifier = Modifier.fillMaxWidth(),
                isError = nameError != null,
                supportingText = nameError?.let { { Text(it) } }
            )

            Text(
                text = "Icon",
                style = MaterialTheme.typography.labelLarge
            )

            IconPickerGrid(
                selectedIcon = selectedIcon,
                onIconSelected = { selectedIcon = it },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Color",
                style = MaterialTheme.typography.labelLarge
            )

            ColorPicker(
                selectedColor = selectedColor,
                onColorSelected = { selectedColor = it },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
