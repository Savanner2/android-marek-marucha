package com.example.marekmarucha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MarekMaruchaApp() {
    val data = marekList
    val visibleState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    Scaffold(
        topBar = {
            MarekTopBar()
        }
    ) { it ->
        LazyColumn(contentPadding = it){
            itemsIndexed(data){index, marek ->
                AnimatedVisibility(
                    visibleState = visibleState,
                    enter = fadeIn(
                        animationSpec = tween(1000)
                    ),
                    exit = fadeOut()
                ) {
                    MarekCard(
                        marek = marek,
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.padding_small))
                            .animateEnterExit(
                                enter = slideInHorizontally(
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioLowBouncy,
                                        stiffness = Spring.StiffnessVeryLow
                                    ),
                                    initialOffsetX = { it * (index + 1) * if (index % 2 == 0) 1 else -1 }
                                ),
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun MarekCard(marek: Marek, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false)}

    Card(
        shape = RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp),
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
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MarekTopBar(modifier: Modifier = Modifier) {
    val haptics = LocalHapticFeedback.current

    val duration = 1000
    val party = remember { mutableStateOf(false)}
    val infiniteTransition = rememberInfiniteTransition()
    val color by infiniteTransition.animateColor(
        initialValue = Color(0xffff0002),
        targetValue = Color(0xff662d8e),
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = duration
                Color(0xffff0002) at 0
                Color(0xfff7921c) at duration/6
                Color(0xffffeb1e) at duration/6 * 2
                Color(0xff3ab34c) at duration/6 * 3
                Color(0xff2ba9e3) at duration/6 * 4
                Color(0xff2c3093) at duration/6 * 5
                Color(0xff662d8e) at duration
            },
            repeatMode = RepeatMode.Reverse
        ),
        label = "",
    )

    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .combinedClickable(
                        onClick = {},
                        onLongClick = {
                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                            party.value = !party.value
                        }
                    )

            ){
                Image(
                    painterResource(id = R.drawable.marucha),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(60.dp)
                        .padding(end = 8.dp)
                )
                Text(
                    text = "Marucha",
                    style = MaterialTheme.typography.headlineLarge
                )
            }


        },
        colors = if(!party.value) TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                else TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = color),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MarekMaruchaTheme {
        MarekMaruchaApp()
    }
}