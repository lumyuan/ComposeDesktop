package com.compose.app.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.compose.app.util.sortByName
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import java.io.File

@Stable
val localFile: MutableState<File> by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    mutableStateOf(File.listRoots()[0])
}

@Composable
fun MainScaffold(
    modifier: Modifier = Modifier,
    toolbar: @Composable ColumnScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Row(
        modifier = modifier.fillMaxSize()
    ) {
        Spacer(Modifier.size(8.dp))
        Column(
            modifier = Modifier.fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            toolbar()
        }
        Spacer(
            modifier = Modifier
                .padding(start = 8.dp, top = 16.dp, end = 8.dp, bottom = 16.dp)
                .fillMaxHeight().width(1.dp)
                .background(color = Color.Gray.copy(alpha = .5f), shape = RoundedCornerShape(1.dp)),
        )
        Column(
            modifier = Modifier.fillMaxSize()
                .weight(1f)
        ) {
            content()
        }
        Spacer(Modifier.size(8.dp))
    }
}

/**
 * 入口窗口
 */
@Composable
@Preview
fun WinMain() {
    MaterialTheme {
        Surface(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.background,
        ) {
            MainScaffold(
                toolbar = {
                    MainToolBar()
                },
                content = {
                    MainContent()
                }
            )
        }
    }
}

@Composable
fun MainToolBar() {
    val selectDiskCharacter = remember {
        mutableStateOf(0)
    }
    Spacer(modifier = Modifier.size(16.dp))
    File.listRoots()?.onEachIndexed { index, file ->
        DiskItem(selectDiskCharacter, index, file)
    }
    Spacer(modifier = Modifier.size(16.dp))
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DiskItem(selectDiskCharacter: MutableState<Int>, index: Int, file: File) {
    val bgColor =
        if (selectDiskCharacter.value == index) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent
    val iconColor =
        if (selectDiskCharacter.value == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
    Surface(
        color = bgColor,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                localFile.value = file
                selectDiskCharacter.value = index
            }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource("icons/ic_disk.png"),
                null,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.size(16.dp))
            val path = file.path
            var title = ' '
            if (path.length > 1) {
                title = path[0]
            }
            Text(text = "$title:", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.MainContent() {
    Spacer(modifier = Modifier.size(16.dp))
    Card(
        shape = RoundedCornerShape(100.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState()).padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
        ) {
            val separator = File.separator
            val split = localFile.value.path.split(separator)
            val realPath = StringBuilder()
            split.onEachIndexed { index, name ->
                if (name.isNotEmpty()) {
                    realPath.append(name).append(separator)
                    FileNameItem(index, name, index == split.size - 1, realPath.toString())
                }
            }
        }
    }
    Spacer(modifier = Modifier.size(8.dp))
    val lazyStaggeredGridState = remember {
        mutableStateOf(LazyStaggeredGridState())
    }
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(1),
        modifier = Modifier.weight(1f),
        state = lazyStaggeredGridState.value
    ) {
        val files = localFile.value.listFiles { _, name ->
            !name.startsWith("$")
        }?.sortByName()
        files?.onEachIndexed { index, file ->
            item {
                FileItem(index, file, lazyStaggeredGridState)
            }
        }
    }
    Spacer(modifier = Modifier.size(16.dp))
}

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun FileItem(index: Int, file: File, lazyStaggeredGridState: MutableState<LazyStaggeredGridState>) {
    Row(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).clickable(
            onClickLabel = file.path
        ) {
            if (file.isDirectory) {
                localFile.value = file
                lazyStaggeredGridState.value = LazyStaggeredGridState()
            } else {

            }
        }
    ) {
        val painter = if (file.isDirectory) {
            painterResource("icons/ic_folder.png")
        } else {
            painterResource("icons/ic_file.png")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(painter = painter, null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = file.name)
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun FileNameItem(index: Int, name: String, isLast: Boolean, realPath: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clip(RoundedCornerShape(8.dp)).clickable {
            localFile.value = File(realPath)
        }.padding(4.dp)
    ) {
        val textColor = if (isLast) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
        Text(name, style = MaterialTheme.typography.bodyLarge, color = textColor)
        if (!isLast) {
            Spacer(modifier = Modifier.size(8.dp))
            Icon(painter = painterResource("icons/ic_angle_right.png"), null, modifier = Modifier.size(12.dp))
        }
    }
}
