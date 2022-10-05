package com.covergenius.mca_sdk_android.presentation.views.product

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.covergenius.mca_sdk_android.R
import com.covergenius.mca_sdk_android.common.utils.Log
import com.covergenius.mca_sdk_android.presentation.theme.*
import com.covergenius.mca_sdk_android.common.utils.Separator
import com.covergenius.mca_sdk_android.common.utils.center
import com.covergenius.mca_sdk_android.data.remote.dto.ProductDetail
import com.covergenius.mca_sdk_android.data.remote.dto.fromJson
import com.covergenius.mca_sdk_android.presentation.views.components.MyCoverButton
import com.covergenius.mca_sdk_android.presentation.views.components.MyCoverTemplate
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductInfoScreen(onContinuePressed: () -> Unit, productJson: String?) {
    val pagerState = rememberPagerState(pageCount = 3)

//    val product = productJson?.fromJson(ProductDetail::class.java)

    Log.d("Productinfo", "product name ${productJson}")

    MyCoverTemplate(
        content =  {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(12.dp)
            ) {
                Tabs(pagerState = pagerState)
                Box(Modifier.height(10.dp))
                Column(Modifier.weight(1f)) {
                    TabsContent(pagerState = pagerState)
                }

                Column {
                    Box(
                        modifier = Modifier
                            .height(1.dp)
                            .background(colorGreyLight)
                            .fillMaxWidth()
                    )
                }


                Box(Modifier.padding(top = 12.dp)) {
                    Separator()
                }

               MyCoverButton(buttonText = "Continue", onPressed = onContinuePressed)
            }
        }
    )
}


@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val titles = listOf(
        "How it Works",
        "Benefits",
        "How to Claim",
    )

    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = colorBackground,
        indicator = { },
        divider = {}
    ) {

        titles.forEachIndexed { index, _ ->

            val selected = pagerState.currentPage == index

            Tab(
                selected = selected,
                selectedContentColor = colorPrimaryLight,
                unselectedContentColor = colorSpaceGray,
                modifier = Modifier
                    .background(if (selected) colorPrimaryBg else colorWhite)
                    .border(
                        BorderStroke(0.5.dp, colorGreyLight)
                    ),
                onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                text = {
                    Text(
                        text = titles[index],
                        style = if (selected) MaterialTheme.typography.h1.center() else MaterialTheme.typography.body1.center(),
                    )
                })

        }
    }
}


@ExperimentalPagerApi
@Composable
fun TabsContent(pagerState: PagerState) {
    HorizontalPager(state = pagerState, modifier = Modifier.background(colorPrimaryBg)) { page ->
        when (page) {
            0 -> TabsContentScreen(
                perks = listOf(
                    "Get this Auto insurance plan",
                    "Provide Vehicle Detail",
                    "Get your Insurance Certificate",
                    "Inspect your vehicle, form anywhere",
                ),
                icon = R.drawable.how_it_works
            )
            1 -> TabsContentScreen(
                perks = listOf(
                    "3rd Party Bodily Injury",
                    "3rd Party Property Damage Up to 1 Million",
                    "Own Accident Damage",
                    "Excess Buy Back",
                    "Theft",
                ),
                icon = R.drawable.benefits
            )
            2 -> TabsContentScreen(
                perks = listOf(
                    "Take Pictures of damage",
                    "Track Progress of your Claim",
                    "Get paid",
                ),
                icon = R.drawable.how_to_claim
            )
        }
    }
}


@ExperimentalPagerApi
@Composable
fun TabsContentScreen(perks: List<String>, icon: Int) {
    Column(

        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            Modifier
                .padding(bottom = 20.dp)
                .size(100.dp)
        ) {
            Image(

                painter = painterResource(id = icon),
                contentDescription = "",
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.Center)
            )
        }

        Box(Modifier.padding(vertical = 25.dp, horizontal = 5.dp)) {
            Separator()
        }
        LazyColumn(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            items(perks.size) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(color = colorGreen, shape = CircleShape)
                            .size(6.dp)
                    )
                    Box(Modifier.width(10.dp))
                    Text(
                        text = perks[it],
                        style = MaterialTheme.typography.body1.copy(colorSpaceGray)
                    )
                }
            }
        }
    }
}