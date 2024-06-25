package my.ym.line_text.extensions

import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.coerceAtMost

/**
 * - Usually used with texts components only.
 *
 * @param lineHeight the exact height you want it to be per line.
 *
 * @param linesCount number of lines of that text.
 *
 * @param maxHeight if Used and is < ([lineHeight] * [linesCount]) then aligning won't be used as
 * in such a case a scroll behaviour should handle it instead.
 */
internal fun Modifier.heightFixedAndAlignContent(
    lineHeight: Dp,
    linesCount: Int,
    maxHeight: Dp? = null,
): Modifier {
    val totalHeight = lineHeight * linesCount

    val exceededMaxHeight = maxHeight != null && maxHeight < totalHeight

    val exactHeight = if (maxHeight != null) {
        totalHeight.coerceAtMost(maxHeight)
    }else {
        totalHeight
    }

    return if (exceededMaxHeight) {
        this.height(exactHeight)
    }else {
        this.height(exactHeight)
            .wrapContentHeightCenterVertically()
    }
}

/**
 * - Centers the component vertically.
 */
private fun Modifier.wrapContentHeightCenterVertically() = layout { measurable, constraints ->
    val wrappedConstraints = Constraints(
        minWidth = constraints.minWidth,
        minHeight = constraints.minHeight,
        maxWidth = constraints.maxWidth,
        maxHeight = constraints.maxHeight,
    )

    val wrappedPlaceable = measurable.measure(wrappedConstraints)

    val wrapperWidth = wrappedPlaceable.width
        .coerceIn(constraints.minWidth, constraints.maxWidth)
    val wrapperHeight = wrappedPlaceable.height
        .coerceIn(constraints.minHeight, constraints.maxHeight)

    val maxConstraints = Constraints(
        minWidth = constraints.minWidth,
        minHeight = 0,
        maxWidth = constraints.maxWidth,
        maxHeight = Int.MAX_VALUE,
    )

    /** Must be called. */
    measurable.measure(maxConstraints)

    layout(
        wrapperWidth,
        wrapperHeight
    ) {
        val size = IntSize(wrapperWidth - wrappedPlaceable.width, wrapperHeight - wrappedPlaceable.height)

        val position = IntOffset(0, Alignment.CenterVertically.align(0, size.height))

        wrappedPlaceable.place(position)
    }
}
