package ru.apmgor.data.mappers

import ru.apmgor.data.R

enum class WindDirection {
    N,
    NNE,
    NE,
    ENE,
    E,
    ESE,
    SE,
    SSE,
    S,
    SSW,
    SW,
    WSW,
    W,
    WNW,
    NW,
    NNW;


    fun mapWindDirectionToDrawableDay() =
        when(this) {
            N -> R.drawable.arrow_n
            NNE -> R.drawable.arrow_nne
            NE -> R.drawable.arrow_ne
            ENE -> R.drawable.arrow_ene
            E -> R.drawable.arrow_e
            ESE -> R.drawable.arrow_se
            SE -> R.drawable.arrow_se
            SSE -> R.drawable.arrow_sse
            S -> R.drawable.arrow_s
            SSW -> R.drawable.arrow_ssw
            SW -> R.drawable.arrow_sw
            WSW -> R.drawable.arrow_wsw
            W -> R.drawable.arrow_w
            WNW -> R.drawable.arrow_wnw
            NW -> R.drawable.arrow_nw
            NNW -> R.drawable.arrow_nnw
        }

    fun mapWindDirectionToDrawableNight() =
        when(this) {
            N -> R.drawable.arrow_n_white
            NNE -> R.drawable.arrow_nne_white
            NE -> R.drawable.arrow_ne_white
            ENE -> R.drawable.arrow_ene_white
            E -> R.drawable.arrow_e_white
            ESE -> R.drawable.arrow_ese_white
            SE -> R.drawable.arrow_se_white
            SSE -> R.drawable.arrow_sse_white
            S -> R.drawable.arrow_s_white
            SSW -> R.drawable.arrow_ssw_white
            SW -> R.drawable.arrow_sw_white
            WSW -> R.drawable.arrow_wsw_white
            W -> R.drawable.arrow_w_white
            WNW -> R.drawable.arrow_wnw_white
            NW -> R.drawable.arrow_nw_white
            NNW -> R.drawable.arrow_nnw_white
        }

}