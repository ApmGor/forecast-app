package ru.apmgor.presentation.main.components.chart

import android.content.Context
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import ru.apmgor.data.R

internal fun LineDataSet.configureDataSet(context: Context, isDarkMode: Boolean) {
    setDrawValues(false)
    setDrawCircles(false)
    mode = LineDataSet.Mode.CUBIC_BEZIER
    setDrawFilled(true)
    fillDrawable = if (!isDarkMode)
            context.getDrawable(R.drawable.gradient_chart_light)
        else
            context.getDrawable(R.drawable.gradient_chart_night)
    color = if (!isDarkMode)
            ContextCompat.getColor(context,R.color.purple_400)
        else
            ContextCompat.getColor(context,R.color.purple_800)
    lineWidth = 0.001f
}

internal fun YAxis.configureLeftYAxis(
    isDarkMode: Boolean,
    colorWhite: Int,
    colorBlack: Int
) {
    setDrawAxisLine(false)
    setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
    textColor = if (isDarkMode) {
        colorWhite
    } else {
        colorBlack
    }
    yOffset = -7f
    enableGridDashedLine(16f,24f,0f)
    axisMinimum = 0f
    setLabelCount(3,true)
}

internal fun XAxis.configureXAxis(
    isDarkMode: Boolean,
    context: Context,
    colorWhite: Int,
    colorBlack: Int
) {
    setDrawGridLines(false)
    position = XAxis.XAxisPosition.BOTTOM
    setDrawAxisLine(false)
    textColor = if (isDarkMode) {
        colorWhite
    } else {
        colorBlack
    }
    axisMinimum = -8f
    isGranularityEnabled = true
    granularity = 15f
    valueFormatter = object : ValueFormatter() {
        val arrayLabels = context.resources.getStringArray(R.array.cart_x_labels)
        override fun getFormattedValue(value: Float): String {
            return arrayLabels[value.toInt()/15]
        }
    }
}

internal fun Legend.configureLegend(
    isDarkMode: Boolean,
    colorWhite: Int,
    colorBlack: Int
) {
    isEnabled = true
    textColor = if (isDarkMode) {
        colorWhite
    } else {
        colorBlack
    }
    yOffset = -4f
    verticalAlignment = Legend.LegendVerticalAlignment.TOP
    horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
    orientation = Legend.LegendOrientation.HORIZONTAL
    setDrawInside(false)
}
