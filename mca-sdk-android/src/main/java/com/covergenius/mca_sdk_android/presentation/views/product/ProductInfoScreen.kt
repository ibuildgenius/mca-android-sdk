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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.covergenius.mca_sdk_android.R
import com.covergenius.mca_sdk_android.presentation.theme.*
import com.covergenius.mca_sdk_android.common.utils.Separator
import com.covergenius.mca_sdk_android.common.utils.center
import com.covergenius.mca_sdk_android.data.remote.dto.ProductDetail
import com.covergenius.mca_sdk_android.presentation.views.components.MyCoverButton
import com.covergenius.mca_sdk_android.presentation.views.components.MyCoverTemplate
import com.covergenius.mca_sdk_android.presentation.views.html.HtmlText
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductInfoScreen(
    onContinuePressed: () -> Unit,
    navigator: NavController,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(pageCount = 3)

    val product = viewModel.product.value

    MyCoverTemplate(
        onCanceledPressed = {navigator.popBackStack()},
        onBackPressed = {navigator.popBackStack() },
        content =  {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(12.dp)
            ) {
                Tabs(pagerState = pagerState)
                Box(Modifier.height(10.dp))
                Column(Modifier.weight(1f)) {
                    TabsContent(pagerState = pagerState, product)
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
fun TabsContent(pagerState: PagerState, product: ProductDetail?) {

    HorizontalPager(state = pagerState, modifier = Modifier.background(colorPrimaryBg)) { page ->
        when (page) {
            0 -> TabsContentScreen(
                perks = product?.howItWorks,
                icon = R.drawable.how_it_works
            )
            1 -> TabsContentScreen(
                perks = "",
                icon = R.drawable.benefits
            )
            2 -> TabsContentScreen(
                perks = product?.howToClaim,
                icon = R.drawable.how_to_claim
            )
        }
    }
}


@ExperimentalPagerApi
@Composable
fun TabsContentScreen(perks: String?, icon: Int) {
    val data = if(!perks.isNullOrEmpty()) perks else ""
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

        HtmlText(html = data)

    /*
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
        }*/
    }
}