package my.ym.line_text_app.preview

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import my.ym.line_text.LineBasicTextField
import my.ym.line_text_app.R

@Preview(showBackground = true)
@Composable
private fun ShowcaseIssues() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val arStyle = TextStyle(
            fontFamily = FontFamily(
                Font(
                    resId = R.font.cairo_bold,
                    weight = FontWeight.Bold,
                    style = FontStyle.Normal,
                )
            ),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 24.sp
        )

        val enStyle = arStyle.copy(
            fontFamily = FontFamily(
                Font(
                    resId = R.font.barlow_bold,
                    weight = FontWeight.Bold,
                    style = FontStyle.Normal,
                )
            )
        )

        val shape = RoundedCornerShape(4.dp)
        val borderModifier = Modifier.border(width = 1.dp, color = Color.Blue, shape = shape)

        Text(
            text = "Normal Composables",
            color = Color(0xFFA70000),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )

        Row {
            BasicTextField(
                modifier = borderModifier,
                value = "Hello & Welcome",
                onValueChange = {},
                textStyle = enStyle
            )

            BasicTextField(
                modifier = borderModifier,
                value = "مرحبا أهلا وسهلا",
                onValueChange = {},
                textStyle = arStyle
            )
        }

        Text(
            text = "Line Based Composables",
            color = Color(0xFF009102),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )

        Row {
            LineBasicTextField(
                modifier = borderModifier,
                value = "Hello & Welcome",
                onValueChange = {},
                textStyle = enStyle
            )

            LineBasicTextField(
                modifier = borderModifier,
                value = "مرحبا أهلا وسهلا",
                onValueChange = {},
                textStyle = arStyle
            )
        }

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = "Clearly the alignment and the height based on line height, Gives the same look & feel in both languages",
            color = Color(0xFF8C0091),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}
