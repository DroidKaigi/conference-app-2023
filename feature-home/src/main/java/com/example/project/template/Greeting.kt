package com.example.project.template

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.project.template.ui.theme.Androidprojecttemplate2022Theme

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Androidprojecttemplate2022Theme {
        Greeting("Android")
    }
}
