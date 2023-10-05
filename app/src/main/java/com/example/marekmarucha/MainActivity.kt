package com.example.marekmarucha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.marekmarucha.data.Marek
import com.example.marekmarucha.data.marekList
import com.example.marekmarucha.ui.theme.MarekMaruchaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarekMaruchaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MarekMaruchaApp()
                }
            }
        }
    }
}

@Composable
fun MarekMaruchaApp() {
    val data = marekList

    LazyColumn(){
        items(data){
            MarekCard(
                marek = it,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
fun MarekCard(marek: Marek, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false)}

    Card(
        modifier = modifier
            .clickable {
                expanded = !expanded
            }
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioHighBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            // Day label
            Text(
                text = stringResource(id = R.string.day, marek.id),
                style = MaterialTheme.typography.labelSmall
            )
            // Title
            Text(
                text = stringResource(id = marek.title),
                style = MaterialTheme.typography.titleLarge
            )
            // Image
            Image(
                painter = painterResource(id = marek.image),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            )
            // Description
            if(expanded) {
                Text(
                    text = stringResource(id = marek.description),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MarekMaruchaTheme {
        MarekMaruchaApp()
    }
}