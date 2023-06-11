package ru.apmgor.presentation.main.components.chart

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import ru.apmgor.data.R
import ru.apmgor.domain.model.MinutelyForecast

@Composable
fun PrecipitationChart(
    precipitationUnit: String,
    minutely: List<MinutelyForecast>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isDarkMode = isSystemInDarkTheme()
    val lineData = remember(minutely) {
        val entries = minutely.mapIndexed { index, elem ->
            Entry(
                index.toFloat(),
                elem.precipitation
            )
        }
        val dataSet = LineDataSet(
            entries,
            context.getString(
                R.string.chart_legend_name,
                precipitationUnit
            )
        )
        dataSet.configureDataSet(context, isDarkMode)
        LineData(dataSet)
    }

    AndroidView(
        factory = { cont ->
            LineChart(cont).apply {
                val colorWhite = ContextCompat.getColor(cont, R.color.transparent_white)
                val colorBlack = ContextCompat.getColor(cont, R.color.transparent_black)
                setTouchEnabled(false)
                axisRight.isEnabled = false
                axisLeft.configureLeftYAxis(isDarkMode, colorWhite, colorBlack)
                xAxis.configureXAxis(isDarkMode, cont, colorWhite, colorBlack)
                description.isEnabled = false
                legend.configureLegend(isDarkMode, colorWhite, colorBlack)
                this.invalidate()
            }
        },
        update = { it.data = lineData },
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(vertical = 8.dp, horizontal = 16.dp)
    )
}