package dizzcode.com.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dizzcode.com.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadeAppPreview() {
   // WelcomeScreen("User")
    LemonadeApp()
}

@Composable
fun WelcomeScreen(name: String, onStartClicked: () -> Unit) {
    Column {
        Text(text = "Welcome $name!")
        Button(
            onClick = onStartClicked
        ) {
            Text(text = "Start")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonadeApp() {
// Current step the app is displaying (remember allows the state to be retained
    // across recompositions).
    var currentStep by remember { mutableStateOf(1) }

    var squeezeCount by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lemonade",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            color = MaterialTheme.colorScheme.background
        ) {

            when (currentStep) {
                1 -> {
                    FlowViewImageWithText(
                        imageResId = R.drawable.lemon_tree,
                        contentDescriptionResourceId = R.string.lemon_tree,
                        titleResId = R.string.tap_the_lemon_tree_to_select_a_lemon,
                        onImageClick = {
                            squeezeCount = (2..4).random()
                            currentStep = 2
                        },
                        squeezeCount = squeezeCount
                    )
                }

                2 -> {
                    FlowViewImageWithText(
                        imageResId = R.drawable.lemon_squeeze,
                        contentDescriptionResourceId = R.string.lemon,
                        titleResId = R.string.keep_tapping_the_lemon_to_squeeze_it,
                        onImageClick = {
                            squeezeCount--
                            if (squeezeCount == 0) {
                                currentStep = 3
                            }
                        },
                        squeezeCount = squeezeCount
                    )
                }

                3 -> {
                    FlowViewImageWithText(
                        imageResId = R.drawable.lemon_drink,
                        contentDescriptionResourceId = R.string.glass_of_lemonade,
                        titleResId = R.string.tap_the_lemonade_to_drink_it,
                        onImageClick = {
                            currentStep = 4
                        },
                        squeezeCount = squeezeCount
                    )
                }

                4 -> {
                    FlowViewImageWithText(
                        imageResId = R.drawable.lemon_restart,
                        contentDescriptionResourceId = R.string.empty_glass,
                        titleResId = R.string.tap_the_empty_glass_to_start_again,
                        onImageClick = {
                            currentStep = 1
                        },
                        squeezeCount = squeezeCount
                    )
                }

            }

        }

    }
}

@Composable
fun FlowViewImageWithText(
    imageResId: Int,
    contentDescriptionResourceId: Int,
    titleResId: Int,
    squeezeCount: Int,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val title: String = if (squeezeCount == 0) stringResource(id = titleResId) else stringResource(id = titleResId) + " ($squeezeCount)"

    Box{
        Column (
            modifier = modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Button(
                onClick = onImageClick,
                shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner_radius)),
                colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.tertiaryContainer),
                modifier = modifier.clip(RoundedCornerShape(8.dp))
            ){
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = stringResource(id = contentDescriptionResourceId),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(220.dp)
                        .padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
