package my.ym.line_text.extensions

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtMost
import my.ym.line_text.LineTextField
import my.ym.line_text.LineOutlinedTextField
import my.ym.line_text.LineBasicTextField

/**
 * - Represents the container that calls `innerTextField` composable inside [LineBasicTextField]
 * & [LineTextField] & [LineOutlinedTextField], but with additional adjustments to overcome font issue.
 *
 * @param innerTextField composable that is required to be called.
 *
 * @param lineHeight the line height for [innerTextField].
 *
 * @param linesCount the lines count for [innerTextField].
 *
 * @param maxWidth the maxWidth that the component can have.
 *
 * @param maxHeight the max height that the component can have.
 *
 * @param singleLine if represents a single line or multi-line.
 */
@Composable
internal fun LineInnerTextField(
    innerTextField: @Composable () -> Unit,
    lineHeight: Dp,
    linesCount: Int,
    maxWidth: Dp,
    maxHeight: Dp,
    singleLine: Boolean,
) {
    Layout(
        measurePolicy = { measurables, constraints ->
            val toBeUsedMaxHeight = lineHeight.times(linesCount)
                .coerceAtMost(maxHeight).roundToPx()

            val toBeUsedConstraints = constraints.copy(
                maxHeight = toBeUsedMaxHeight,
                minHeight = toBeUsedMaxHeight,
                maxWidth = maxWidth.roundToPx()
            )

            val placeables = measurables.map {
                it.measure(toBeUsedConstraints)
            }
            val layoutWidth = placeables.maxOf { it.measuredWidth }
            val layoutHeight = placeables.sumOf { it.measuredHeight }
            layout(layoutWidth, layoutHeight) {
                placeables.forEach { it.place(0, 0) }
            }
        },
        content = {
            Box(
                modifier = Modifier
                    .heightFixedAndAlignContent(
                        lineHeight = lineHeight,
                        linesCount = if (singleLine) 1 else linesCount,
                        maxHeight = maxHeight,
                    )
            ) {
                innerTextField()
            }
        }
    )
}
