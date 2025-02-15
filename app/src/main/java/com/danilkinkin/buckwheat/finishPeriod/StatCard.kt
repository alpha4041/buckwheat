package com.danilkinkin.buckwheat.finishPeriod

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.danilkinkin.buckwheat.ui.BuckwheatTheme

@OptIn(ExperimentalUnitApi::class)
@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    contentPadding: PaddingValues = PaddingValues(vertical = 16.dp, horizontal = 24.dp),
    colors: CardColors = CardDefaults.cardColors(),
    valueFontSize: TextUnit = MaterialTheme.typography.titleLarge.fontSize,
    content: @Composable ColumnScope.() -> Unit = {},
    backdropContent: @Composable () -> Unit = {},
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        colors = colors,
    ) {
        val textColor = LocalContentColor.current

        Box(
            Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()) {
                backdropContent()
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(contentPadding)
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.displayLarge,
                    fontSize = valueFontSize,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = false,
                    lineHeight = TextUnit(0.2f, TextUnitType.Em)
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = textColor.copy(alpha = 0.6f),
                    overflow = TextOverflow.Ellipsis,
                    softWrap = false,
                )
                Spacer(modifier = Modifier.height(4.dp))

                CompositionLocalProvider(
                    LocalContentColor provides textColor,
                ) {
                    Column(
                        //modifier = Modifier.fillMaxWidth(),
                        content = content,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    BuckwheatTheme {
        StatCard(
            value = "value",
            label = "label"
        )
    }
}
