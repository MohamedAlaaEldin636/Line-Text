package my.ym.line_text.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import my.ym.line_text.LineBasicTextField
import my.ym.line_text.LineText
import my.ym.line_text.LineTextField

@Preview(showBackground = true)
@Composable
private fun PreviewDependOnStyle() {
    Box(modifier = Modifier.padding(24.dp)) {
        LineText(
            modifier = Modifier.border(width = 1.dp, color = Color.Blue),
            text = "Hello Line Text !",
            style = TextStyle(
                // try 20, 50, etc...
                lineHeight = 30.sp
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewForceHeight() {
    Box(modifier = Modifier.padding(24.dp)) {
        LineText(
            modifier = Modifier.border(width = 1.dp, color = Color.Blue),
            text = "Hello Line Text !",
            exactLineHeight = { 20.dp },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewForceHeight2() {
    Box(modifier = Modifier.padding(24.dp)) {
        val shape = RoundedCornerShape(8.dp)

        LineBasicTextField(
            modifier = Modifier.border(width = 1.dp, color = Color.Blue, shape = shape),
            value = "Hello Line Text !",
            onValueChange = {},
            lineHeight = { 20.dp },
            decorationBox = { innerTextField ->
                Column(
                    modifier = Modifier
                        .background(color = Color.Gray.copy(alpha = 0.2f), shape = shape)
                        .clip(shape = shape)
                        .padding(12.dp)
                ) {
                    LineText(text = "Kinda Label", style = TextStyle(fontSize = 12.sp))

                    // Respects the lineHeight given above isa.
                    innerTextField()
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewForceHeight3() {
    Box(modifier = Modifier.padding(24.dp)) {
        LineTextField(
            modifier = Modifier.border(width = 1.dp, color = Color.Blue),
            value = "Hello Line Text !",
            onValueChange = {},
            lineHeight = { 40.dp },
        )
    }
}
