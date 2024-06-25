package my.ym.line_text

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import my.ym.line_text.extensions.LineInnerTextField

/**
 * ## What's this
 *
 * - Almost the same as [BasicTextField] composable, but with advanced feature of making the [lineHeight]
 * controls this component according to available height.
 *
 * @param value same as in [Text].
 * 
 * @param onValueChange same as in [Text].
 *
 * @param lineHeight used to determine exact line height.
 *
 * @param textStyle same as in [Text].
 *
 * @param modifier same as in [BasicTextField].
 *
 * @param enabled same as in [BasicTextField].
 *
 * @param readOnly same as in [BasicTextField].
 *
 * @param keyboardOptions same as in [BasicTextField].
 *
 * @param keyboardActions same as in [BasicTextField].
 *
 * @param singleLine same as in [BasicTextField].
 *
 * @param maxLines same as in [BasicTextField].
 *
 * @param minLines same as in [BasicTextField].
 *
 * @param visualTransformation same as in [BasicTextField].
 *
 * @param onTextLayout same as in [BasicTextField].
 *
 * @param interactionSource same as in [BasicTextField].
 *
 * @param cursorBrush same as in [BasicTextField].
 *
 * @param decorationBox same as in [BasicTextField].
 */
@Composable
internal fun LineBasicTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(Color.Black),
    textStyle: TextStyle = LocalTextStyle.current,
    lineHeight: @Composable () -> Dp = { textStyle.lineHeight.value.dp },
    decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit =
        @Composable { innerTextField -> innerTextField() },
) {
    var linesCount by remember { mutableIntStateOf(1) }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        onTextLayout = {
            linesCount = it.lineCount

            onTextLayout(it)
        },
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
        decorationBox = { innerTextField ->
            decorationBox {
                BoxWithConstraints {
                    val calculatedLineHeight = lineHeight()

                    LineInnerTextField(
                        innerTextField = innerTextField,
                        lineHeight = calculatedLineHeight,
                        linesCount = linesCount,
                        maxWidth = maxWidth,
                        maxHeight = if (singleLine) {
                            calculatedLineHeight
                        }else {
                            maxHeight
                        },
                        singleLine = singleLine
                    )
                }
            }
        },
    )
}

/**
 * ## What's this
 *
 * - Almost the same as [BasicTextField] composable, but with advanced feature of making the [lineHeight]
 * controls this component according to available height.
 *
 * @param value same as in [Text].
 *
 * @param onValueChange same as in [Text].
 *
 * @param lineHeight used to determine exact line height.
 *
 * @param textStyle same as in [Text].
 *
 * @param modifier same as in [BasicTextField].
 *
 * @param enabled same as in [BasicTextField].
 *
 * @param readOnly same as in [BasicTextField].
 *
 * @param keyboardOptions same as in [BasicTextField].
 *
 * @param keyboardActions same as in [BasicTextField].
 *
 * @param singleLine same as in [BasicTextField].
 *
 * @param maxLines same as in [BasicTextField].
 *
 * @param minLines same as in [BasicTextField].
 *
 * @param visualTransformation same as in [BasicTextField].
 *
 * @param onTextLayout same as in [BasicTextField].
 *
 * @param interactionSource same as in [BasicTextField].
 *
 * @param cursorBrush same as in [BasicTextField].
 *
 * @param decorationBox same as in [BasicTextField].
 */
@Composable
internal fun LineBasicTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(Color.Black),
    textStyle: TextStyle = LocalTextStyle.current,
    lineHeight: @Composable () -> Dp = { textStyle.lineHeight.value.dp },
    decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit =
        @Composable { innerTextField -> innerTextField() },
) {
    var linesCount by remember { mutableIntStateOf(1) }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        onTextLayout = {
            linesCount = it.lineCount

            onTextLayout(it)
        },
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
        decorationBox = { innerTextField ->
            decorationBox {
                BoxWithConstraints {
                    val calculatedLineHeight = lineHeight()

                    LineInnerTextField(
                        innerTextField = innerTextField,
                        lineHeight = calculatedLineHeight,
                        linesCount = linesCount,
                        maxWidth = maxWidth,
                        maxHeight = if (singleLine) {
                            calculatedLineHeight
                        }else {
                            maxHeight
                        },
                        singleLine = singleLine
                    )
                }
            }
        },
    )
}
