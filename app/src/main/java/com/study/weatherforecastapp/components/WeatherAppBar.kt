package com.study.weatherforecastapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.study.weatherforecastapp.navigation.AppScreens

@Composable
fun WeatherAppBar(
    title: String = "City, CT",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 5.dp,
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val showDropButtons = remember {
        mutableStateOf(false)
    }

    if (showDropButtons.value) {
        ShowSettingsDropDownMenu(showDropButtons = showDropButtons, navController = navController)
    }

    TopAppBar(
        title = { Text(text = title) },
        elevation = elevation,
        modifier = Modifier.padding(3.dp),
        backgroundColor = Color.Transparent,
        navigationIcon = {
             if(icon != null) {
                 IconButton(onClick = { onButtonClicked.invoke() }) {
                     Icon(
                         imageVector = icon,
                         contentDescription = "Arrow back icon",
                         tint = MaterialTheme.colors.onSecondary
                     )
                 }
             }
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = { onAddActionClicked.invoke() }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "App Bar Search Icon")
                }

                IconButton(onClick = { showDropButtons.value = true }) {
                    Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "App Bar Menu Icon")
                }
            }
        }
    )
}

@Composable
fun ShowSettingsDropDownMenu(showDropButtons: MutableState<Boolean>, navController: NavController) {
    var expanded by remember {
        mutableStateOf(true)
    }
    val items = listOf(
        DropDownMenuItemRepresentation(Icons.Default.Info, "About", AppScreens.AboutScreen.name),
        DropDownMenuItemRepresentation(Icons.Default.FavoriteBorder, "Favorite", AppScreens.FavoriteScreen.name),
        DropDownMenuItemRepresentation(Icons.Default.Settings, "Settings", AppScreens.SettingsScreen.name)
    )
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(align = Alignment.TopEnd)
        .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                showDropButtons.value = false },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {
            items.forEach { item ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    showDropButtons.value = false
                }) {
                    Row(modifier = Modifier.clickable {
                        navController.navigate(route = item.route)
                    }) {
                        Icon(imageVector = item.icon, contentDescription = "${item.title} icon", tint = Color.LightGray)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = item.title,
                            fontWeight = FontWeight.W300
                        )
                    }
                }
            }

        }
    }    
}

data class DropDownMenuItemRepresentation(val icon: ImageVector, val title: String, val route: String)
