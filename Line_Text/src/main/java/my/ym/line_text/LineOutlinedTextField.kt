package my.ym.line_text

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.OutlinedTextFieldDefaults.DecorationBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import my.ym.line_text.extensions.LineInnerTextField

private val OutlinedTextFieldTopPadding = 8.dp

/**
 * ## What's this
 *
 * - Almost the same as [BasicTextField] composable, but with advanced feature of making the [lineHeight]
 *  * controls this component according to available height.
 *
 * ## Note
 *
 * - Implementation is an exact copy of [OutlinedTextField], as immediate call like [LineText]
 * & [LineBasicTextField] isn't available as `decorationBox` isn't an available parameter,
 * So might need maintenance in future version updates to compose.
 *
 * - Additional params are used instead of using [colors], as it can't be accessed, However a
 * future better workaround for this will be made soon isa.
 *
 * @param value same as in [Text].
 *
 * @param onValueChange same as in [Text].
 *
 * @param lineHeight used to determine exact line height.
 *
 * @param textStyle same as in [Text].
 *
 * @param enabled same as in [BasicTextField].
 *
 * @param readOnly same as in [BasicTextField].
 * 
 * @param modifier same as in [BasicTextField].
 *
 * @param label same as in [TextField].
 *
 * @param placeholder same as in [TextField].
 *
 * @param leadingIcon same as in [TextField].
 *
 * @param trailingIcon same as in [TextField].
 *
 * @param prefix same as in [TextField].
 *
 * @param suffix same as in [TextField].
 *
 * @param supportingText same as in [TextField].
 *
 * @param isError same as in [TextField].
 *
 * @param visualTransformation same as in [TextField].
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
 * @param interactionSource same as in [BasicTextField].
 *
 * @param interactionSource same as in [TextField].
 *
 * @param shape same as in [TextField].
 *
 * @param colors same as in [TextField].
 *
 * @param textSelectionColors same as in [TextField].
 *
 * @param errorCursorColor same as in [TextField].
 *
 * @param cursorColor same as in [TextField].
 *
 * @param disabledTextColor same as in [TextField].
 *
 * @param errorTextColor same as in [TextField].
 *
 * @param focusedTextColor same as in [TextField].
 *
 * @param unfocusedTextColor same as in [TextField].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LineOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    textStyle: TextStyle = LocalTextStyle.current,
    lineHeight: @Composable () -> Dp = { textStyle.lineHeight.value.dp },
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    textSelectionColors: TextSelectionColors = LocalTextSelectionColors.current,
    errorCursorColor: Color = MaterialTheme.colorScheme.error,
    cursorColor: Color = MaterialTheme.colorScheme.primary,
    disabledTextColor: Color = MaterialTheme.colorScheme.onSurface,
    errorTextColor: Color = MaterialTheme.colorScheme.onSurface,
    focusedTextColor: Color = MaterialTheme.colorScheme.onSurface,
    unfocusedTextColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    var linesCount by remember { mutableIntStateOf(1) }

    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse {
        getTextColor(enabled, isError, interactionSource, disabledTextColor, errorTextColor, focusedTextColor, unfocusedTextColor).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    CompositionLocalProvider(LocalTextSelectionColors provides textSelectionColors) {
        BasicTextField(
            value = value,
            modifier = if (label != null) {
                modifier
                    // Merge semantics at the beginning of the modifier chain to ensure padding is
                    // considered part of the text field.
                    .semantics(mergeDescendants = true) {}
                    .padding(top = OutlinedTextFieldTopPadding)
            }else {
                modifier
            }
            .defaultMinSize(
                minWidth = OutlinedTextFieldDefaults.MinWidth,
                minHeight = OutlinedTextFieldDefaults.MinHeight
            ),
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = mergedTextStyle,
            cursorBrush = SolidColor(getCursorColor(isError, errorCursorColor, cursorColor).value),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            onTextLayout = {
                linesCount = it.lineCount
            },
            decorationBox = @Composable { innerTextField ->
                DecorationBox(
                    value = value,
                    visualTransformation = visualTransformation,
                    innerTextField = {
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
                    },
                    placeholder = placeholder,
                    label = label,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    prefix = prefix,
                    suffix = suffix,
                    supportingText = supportingText,
                    singleLine = singleLine,
                    enabled = enabled,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = colors,
                    container = {
                        OutlinedTextFieldDefaults.ContainerBox(
                            enabled,
                            isError,
                            interactionSource,
                            colors,
                            shape
                        )
                    }
                )
            }
        )
    }
}

/**
 * ## What's this
 *
 * - Almost the same as [BasicTextField] composable, but with advanced feature of making the [lineHeight]
 *  * controls this component according to available height.
 *
 * ## Note
 *
 * - Implementation is an exact copy of [OutlinedTextField], as immediate call like [LineText]
 * & [LineBasicTextField] isn't available as `decorationBox` isn't an available parameter,
 * So might need maintenance in future version updates to compose.
 *
 * - Additional params are used instead of using [colors], as it can't be accessed, However a
 * future better workaround for this will be made soon isa.
 *
 * @param value same as in [Text].
 *
 * @param onValueChange same as in [Text].
 *
 * @param lineHeight used to determine exact line height.
 *
 * @param textStyle same as in [Text].
 *
 * @param enabled same as in [BasicTextField].
 *
 * @param readOnly same as in [BasicTextField].
 *
 * @param modifier same as in [BasicTextField].
 *
 * @param label same as in [TextField].
 *
 * @param placeholder same as in [TextField].
 *
 * @param leadingIcon same as in [TextField].
 *
 * @param trailingIcon same as in [TextField].
 *
 * @param prefix same as in [TextField].
 *
 * @param suffix same as in [TextField].
 *
 * @param supportingText same as in [TextField].
 *
 * @param isError same as in [TextField].
 *
 * @param visualTransformation same as in [TextField].
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
 * @param interactionSource same as in [BasicTextField].
 *
 * @param interactionSource same as in [TextField].
 *
 * @param shape same as in [TextField].
 *
 * @param colors same as in [TextField].
 *
 * @param textSelectionColors same as in [TextField].
 *
 * @param errorCursorColor same as in [TextField].
 *
 * @param cursorColor same as in [TextField].
 *
 * @param disabledTextColor same as in [TextField].
 *
 * @param errorTextColor same as in [TextField].
 *
 * @param focusedTextColor same as in [TextField].
 *
 * @param unfocusedTextColor same as in [TextField].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LineOutlinedTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    textStyle: TextStyle = LocalTextStyle.current,
    lineHeight: @Composable () -> Dp = { textStyle.lineHeight.value.dp },
    shape: Shape = TextFieldDefaults.shape,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    textSelectionColors: TextSelectionColors = LocalTextSelectionColors.current,
    errorCursorColor: Color = MaterialTheme.colorScheme.error,
    cursorColor: Color = MaterialTheme.colorScheme.primary,
    disabledTextColor: Color = MaterialTheme.colorScheme.onSurface,
    errorTextColor: Color = MaterialTheme.colorScheme.onSurface,
    focusedTextColor: Color = MaterialTheme.colorScheme.onSurface,
    unfocusedTextColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    var linesCount by remember { mutableIntStateOf(1) }

    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse {
        getTextColor(enabled, isError, interactionSource, disabledTextColor, errorTextColor, focusedTextColor, unfocusedTextColor).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    CompositionLocalProvider(LocalTextSelectionColors provides textSelectionColors) {
        BasicTextField(
            value = value,
            modifier = if (label != null) {
                modifier
                    // Merge semantics at the beginning of the modifier chain to ensure padding is
                    // considered part of the text field.
                    .semantics(mergeDescendants = true) {}
                    .padding(top = OutlinedTextFieldTopPadding)
            }else {
                modifier
            }
                .defaultMinSize(
                    minWidth = OutlinedTextFieldDefaults.MinWidth,
                    minHeight = OutlinedTextFieldDefaults.MinHeight
                ),
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = mergedTextStyle,
            cursorBrush = SolidColor(getCursorColor(isError, errorCursorColor, cursorColor).value),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            onTextLayout = {
                linesCount = it.lineCount
            },
            decorationBox = @Composable { innerTextField ->
                DecorationBox(
                    value = value.text,
                    visualTransformation = visualTransformation,
                    innerTextField = {
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
                    },
                    placeholder = placeholder,
                    label = label,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    prefix = prefix,
                    suffix = suffix,
                    supportingText = supportingText,
                    singleLine = singleLine,
                    enabled = enabled,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = colors,
                    container = {
                        OutlinedTextFieldDefaults.ContainerBox(
                            enabled,
                            isError,
                            interactionSource,
                            colors,
                            shape
                        )
                    }
                )
            }
        )
    }
}

@Composable
private fun getCursorColor(isError: Boolean, errorCursorColor: Color, cursorColor: Color): State<Color> {
    return rememberUpdatedState(if (isError) errorCursorColor else cursorColor)
}

@Composable
private fun getTextColor(
    enabled: Boolean,
    isError: Boolean,
    interactionSource: InteractionSource,
    disabledTextColor: Color,
    errorTextColor: Color,
    focusedTextColor: Color,
    unfocusedTextColor: Color,
): State<Color> {
    val focused by interactionSource.collectIsFocusedAsState()

    val targetValue = when {
        !enabled -> disabledTextColor
        isError -> errorTextColor
        focused -> focusedTextColor
        else -> unfocusedTextColor
    }
    return rememberUpdatedState(targetValue)
}
