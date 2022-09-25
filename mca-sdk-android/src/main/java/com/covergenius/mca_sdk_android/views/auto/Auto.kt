package com.covergenius.mca_sdk_android.views.auto

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.covergenius.mca_sdk_android.R
import com.covergenius.mca_sdk_android.ui.theme.*
import com.covergenius.mca_sdk_android.utils.Separator
import com.covergenius.mca_sdk_android.utils.Toolbar
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun AutoInsuranceForm() {
    val pagerState = rememberPagerState(pageCount = 3)

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(12.dp)) {
        Toolbar(onBackPressed = {}, onCancelPressed = {})
        Column(Modifier.padding(vertical = 12.dp)) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier
                    .width(160.dp)
                    .height(35.dp)
            )
        }
        Tabs(pagerState = pagerState)
        Column(Modifier.weight(1f)) {
            TabsContent(pagerState = pagerState)
        }

        Column() {
            Box(modifier = Modifier
                .height(1.dp)
                .background(colorGreyLight)
                .fillMaxWidth())
        }

        Separator()

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp), onClick = {}, colors = ButtonDefaults.buttonColors(backgroundColor = colorPrimary)) {
            Text("Continue", style = MaterialTheme.typography.body1.copy(color = colorWhite))
        }

        Image(
            painter = painterResource(id = R.drawable.powered_by),
            contentDescription = "",
            modifier = Modifier
                .width(160.dp)
                .height(20.dp)
        )
    }
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
        backgroundColor = colorWhite,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier
                    .tabIndicatorOffset(tabPositions[pagerState.currentPage])
            )
        },
    ) {

        titles.forEachIndexed { index, _ ->

            val selected = pagerState.currentPage == index

            Tab(
                selected = selected,
                selectedContentColor = colorPrimary,
                unselectedContentColor = colorNavyBlue,
                onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                text = {
                    Text(
                        text = titles[index],
                        style = if (selected) MaterialTheme.typography.h1 else MaterialTheme.typography.body1
                    )
                })

        }
    }
}


@ExperimentalPagerApi
@Composable
fun TabsContent(pagerState: PagerState) {
    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> TabsContentScreen(
                perks = listOf(
                    "Get this Auto insurance plan",
                    "PRovide Vehicle Detail",
                    "Get your Insurance Certificate",
                    "Inspect your vehicle, form anywhere"
                ),
                icon = R.drawable.how_it_works
            )
            1 -> TabsContentScreen(
                perks = listOf("Get this Auto insurance plan"),
                icon = R.drawable.benefits
            )
            2 -> TabsContentScreen(
                perks = listOf("Get this Auto insurance plan"),
                icon = R.drawable.how_to_claim
            )
        }
    }
}


@ExperimentalPagerApi
@Composable
fun TabsContentScreen(perks: List<String>, icon: Int) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "",
            modifier = Modifier.size(80.dp)
        )

        Separator()

        LazyColumn(Modifier.padding(10.dp)) {
            items(perks.size) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .background(color = colorPrimary, shape = CircleShape)
                            .size(8.dp)
                    )
                    Box(Modifier.width(10.dp))
                    Text(text = perks[it])
                }
            }
        }
    }
}