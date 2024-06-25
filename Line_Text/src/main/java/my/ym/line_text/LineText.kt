package my.ym.line_text

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import my.ym.line_text.extensions.heightFixedAndAlignContent

/**
 * ## What's this
 *
 * - Almost the same as [Text] composable, but with advanced feature of making the [exactLineHeight]
 * controls this component according to available height.
 *
 * ## Note
 *
 * - If no text by default no size at all so if wanna make Room for this either use
 * " " (empty space) as text or use min width as .widthIn(min = ).
 *
 * @param text same as in [Text].
 *
 * @param exactLineHeight used to determine exact line height.
 *
 * @param style same as in [Text].
 *
 * @param modifier same as in [Text].
 *
 * @param color same as in [Text].
 *
 * @param fontSize same as in [Text].
 *
 * @param fontStyle same as in [Text].
 *
 * @param fontWeight same as in [Text].
 *
 * @param fontFamily same as in [Text].
 *
 * @param letterSpacing same as in [Text].
 *
 * @param textDecoration same as in [Text].
 *
 * @param textAlign same as in [Text].
 *
 * @param lineHeight same as in [Text].
 *
 * @param overflow same as in [Text].
 *
 * @param softWrap same as in [Text].
 *
 * @param maxLines same as in [Text].
 *
 * @param minLines same as in [Text].
 *
 * @param onTextLayout same as in [Text].
 */
@Composable
fun LineText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    exactLineHeight: @Composable () -> Dp = { style.lineHeight.value.dp },
) {
    var linesCount by remember { mutableIntStateOf(1) }

    Text(
        text = text,
        modifier = modifier
            .heightFixedAndAlignContent(
                lineHeight = exactLineHeight(),
                linesCount = linesCount,
            ),
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = {
            linesCount = it.lineCount

            onTextLayout(it)
        },
        style = style,
    )
}

/**
 * ## What's this
 *
 * - Almost the same as [Text] composable, but with advanced feature of making the [exactLineHeight]
 * controls this component according to available height.
 *
 * ## Note
 *
 * - If no text by default no size at all so if wanna make Room for this either use
 * " " (empty space) as text or use min width as .widthIn(min = ).
 *
 * @param text same as in [Text].
 *
 * @param exactLineHeight used to determine exact line height.
 *
 * @param style same as in [Text].
 *
 * @param modifier same as in [Text].
 *
 * @param color same as in [Text].
 *
 * @param fontSize same as in [Text].
 *
 * @param fontStyle same as in [Text].
 *
 * @param fontWeight same as in [Text].
 *
 * @param fontFamily same as in [Text].
 *
 * @param letterSpacing same as in [Text].
 *
 * @param textDecoration same as in [Text].
 *
 * @param textAlign same as in [Text].
 *
 * @param lineHeight same as in [Text].
 *
 * @param overflow same as in [Text].
 *
 * @param softWrap same as in [Text].
 *
 * @param maxLines same as in [Text].
 *
 * @param minLines same as in [Text].
 *
 * @param onTextLayout same as in [Text].
 *
 * @param inlineContent same as in [Text].
 */
@Composable
fun LineText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    exactLineHeight: @Composable () -> Dp = { style.lineHeight.value.dp },
    inlineContent: Map<String, InlineTextContent> = mapOf()
) {
    var linesCount by remember { mutableIntStateOf(1) }

    Text(
        text = text,
        modifier = modifier
            .heightFixedAndAlignContent(
                lineHeight = exactLineHeight(),
                linesCount = linesCount,
            ),
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = {
            linesCount = it.lineCount

            onTextLayout(it)
        },
        style = style,
        inlineContent = inlineContent
    )
}
