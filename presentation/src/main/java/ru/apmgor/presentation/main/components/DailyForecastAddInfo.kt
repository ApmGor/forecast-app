package ru.apmgor.presentation.main.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ru.apmgor.data.R
import ru.apmgor.domain.model.DailyForecast
import ru.apmgor.presentation.main.DAILY_ADD_INFO_CLOSE

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DailyForecastAddInfo(
    tabNumber: Int,
    tempUnit: String,
    precipitationUnit: String,
    windUnit: String,
    pressureUnit: String,
    windNames: Array<String>,
    windDir: Array<String>,
    daily: List<DailyForecast>,
    setSelectedTab: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(tabNumber)
    val scope = rememberCoroutineScope()

    Column(modifier = modifier.padding(top = 8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                divider = {},
                modifier = Modifier.weight(1f)
            ) {
                daily.forEachIndexed { index, it ->
                    Tab(
                        text = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.requiredHeight(35.dp)
                            ) {
                                TabText(it.dt.takeWhile { it != ' ' })
                                TabText(it.dt.filter { it.isDigit() })
                            }
                        },
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        selected = (index == pagerState.currentPage)

                    )
                }
            }
            IconButton(
                onClick = { setSelectedTab(DAILY_ADD_INFO_CLOSE) }
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(R.string.icon_close_daily_add_info)
                )
            }
        }
        Divider(modifier = Modifier.padding(horizontal = 16.dp))
        HorizontalPager(
            pageCount = daily.size,
            state = pagerState,
        ) { page ->
            val day = daily[page]
            Column {
                PagerElement(
                    headText = day.weather.weatherDesc,
                    trailingText = "${day.tempDay}/${day.tempNight}°$tempUnit",
                    painterId = day.weather.weatherIcon
                ) {
                    Text(text = windNames[day.wind.windNameIndex], fontSize = 12.sp)
                }
                PagerElement(
                    headText = stringResource(id = R.string.daily_precipitation),
                    trailingText = "%1$.1f%2\$s".format(day.rain ?: 0.0, precipitationUnit)
                )
                PagerElement(
                    headText = stringResource(R.string.daily_pop),
                    trailingText = "%1\$d%%".format(day.pop)
                )
                PagerElement(
                    headText = stringResource(R.string.daily_wind),
                    trailingText = "%1$.1f%2\$s %3\$s"
                        .format(day.wind.windSpeed, windUnit, windDir[day.wind.windDirectionIndex]),
                    painterId = if (isSystemInDarkTheme())
                        day.wind.windArrowIconNight
                    else
                        day.wind.windArrowIconDay
                )
                PagerElement(
                    headText = stringResource(R.string.daily_pressure),
                    trailingText = "${day.pressure}${pressureUnit}"
                )
                PagerElement(
                    headText = stringResource(R.string.daily_humidity),
                    trailingText = "${day.humidity}%"
                )
                PagerElement(
                    headText = stringResource(R.string.daily_uvi),
                    trailingText = "%1$.1f".format(day.uvi)
                )
                PagerElement(
                    headText = stringResource(id = R.string.sunrise_desc),
                    trailingText = day.sunrise
                )
                PagerElement(
                    headText = stringResource(id = R.string.sunset_desc),
                    trailingText = day.sunset
                )
            }
        }
    }
}

@Composable
private fun TabText(text: String) {
    Text(
        text = text,
        fontSize = 12.sp,
        modifier = Modifier.requiredWidth(20.dp)
    )
}

@Composable
private fun PagerElement(
    headText: String,
    trailingText: String,
    painterId: Int? = null,
    support: @Composable (() -> Unit)? = null
) {
    ListItem(
        headlineContent = {
            Text(text = headText, fontSize = 14.sp)
        },
        supportingContent = support,
        trailingContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = trailingText,
                    fontSize = 14.sp
                )
                if (painterId != null) {
                    Image(
                        painter = painterResource(id = painterId),
                        contentDescription = stringResource(R.string.weather_image),
                        modifier = Modifier
                            .size(width = 30.dp, height = 20.dp)
                            .padding(start = 8.dp)
                    )
                }
            }
        }
    )
    Divider(modifier = Modifier.padding(horizontal = 16.dp))
}