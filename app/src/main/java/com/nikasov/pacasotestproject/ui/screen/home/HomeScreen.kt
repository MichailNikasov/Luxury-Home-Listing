package com.nikasov.pacasotestproject.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.nikasov.pacasotestproject.ui.widget.homeItem.HomeItemData
import com.nikasov.pacasotestproject.ui.widget.homeItem.HomeItemWidget
import com.nikasov.pacasotestproject.ui.widget.loader.LoaderWidget

@Composable
fun HomeScreen(
    homes: LazyPagingItems<HomeItemData>,
    eventHandler: (HomeScreenEvent) -> Unit,
    showError: Boolean
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val listState = rememberLazyListState()

    LaunchedEffect(homes.loadState) {
        if (homes.loadState.refresh is LoadState.Error) {
            snackbarHostState.showSnackbar((homes.loadState.refresh as LoadState.Error).error.toString())
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            AppTopBar(
                showError = showError,
                onShowError = { eventHandler(HomeScreenEvent.ToggleShowError) },
                onClear = { eventHandler(HomeScreenEvent.ClearList) }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        HomeList(
            state = listState,
            homes = homes,
            modifier = Modifier.padding(padding),
            onToggleFavorite = { eventHandler(HomeScreenEvent.ToggleFavorite(it)) },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeList(
    state: LazyListState,
    homes: LazyPagingItems<HomeItemData>,
    onToggleFavorite: (Int) -> Unit,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        PullToRefreshBox(
            isRefreshing = homes.loadState.refresh is LoadState.Loading,
            onRefresh = { homes.refresh() },
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                state = state,
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    key = homes.itemKey { it.id },
                    count = homes.itemCount,
                ) { index ->
                    val item = homes[index]
                    item?.let {
                        HomeItemWidget(
                            item = item,
                            onFavoriteClicked = { onToggleFavorite(item.id) },
                        )
                    }
                }
                when (homes.loadState.append) {
                    LoadState.Loading -> item { LoaderWidget(Modifier.fillMaxWidth()) }
                    else -> Unit
                }
                when (homes.loadState.refresh) {
                    LoadState.Loading -> item { LoaderWidget(Modifier.fillParentMaxSize()) }
                    else -> Unit
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppTopBar(
    showError: Boolean,
    onShowError: () -> Unit,
    onClear: () -> Unit,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground
        ),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Bad response",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Checkbox(checked = showError, onCheckedChange = {
                    onShowError()
                })
                Spacer(modifier = Modifier.width(4.dp))
                VerticalDivider(
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 20.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(onClick = {
                    onClear()
                }) {
                    Text(
                        text = "Clear",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    )
}