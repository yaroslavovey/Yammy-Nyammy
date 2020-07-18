package com.ph00.data.repositories_impl

import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ph00.data.Constants.Companion.FLOW_RETRY_DELAY
import com.ph00.data.api.ShopApi
import com.ph00.data.entities.ProductEntity
import com.ph00.data.toModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductsRepositoryImplTest {

    private val shopApi = mock<ShopApi>()
    private val repository = ProductsRepositoryImpl(shopApi)

    private val product = ProductEntity(
        id = 1,
        title = "Title",
        price = 200,
        desc = "Descr",
        imageURL = "someURL"
    )

    private val productList = listOf(product, product, product)

    @Test
    fun `getProductListByCategory flow emits successfully`() = runBlocking {

        whenever(shopApi.getProductListByCategory("0"))
            .thenReturn(productList)

        repository
            .getProductListByCategory("0")
            .collect { retrievedList ->
                assertEquals(retrievedList, productList.map { it.toModel() })
            }
    }

    @Test
    fun `getProductListByCategory flow throws exception even after retries`() = runBlockingTest {

        whenever(shopApi.getProductListByCategory("0"))
            .thenThrow(RuntimeException())

        var error: Throwable? = null

        repository
            .getProductListByCategory("0")
            .catch { error = it }
            .collect()

        assertNotEquals(null, error)
    }

    @Test
    fun `getProductListByCategory after 2'nd retry emits successfully`() =
        runBlockingTest {

            var shouldThrowError = true

            whenever(shopApi.getProductListByCategory("0"))
                .doAnswer { if (shouldThrowError) throw RuntimeException() else productList }

            repository
                .getProductListByCategory("0")
                .onEach { retrievedList ->
                    assertEquals(retrievedList, productList.map { it.toModel() })
                }
                .launchIn(this)

            advanceTimeBy(FLOW_RETRY_DELAY)
            shouldThrowError = false

        }

    @Test
    fun `getProductById flow emits successfully`() = runBlocking {

        whenever(shopApi.getProductById(0))
            .thenReturn(product)

        repository
            .getProductById(0)
            .collect { retrievedProduct ->
                assertEquals(retrievedProduct, product.toModel())
            }
    }

    @Test
    fun `getProductById flow throws exception even after retries`() = runBlockingTest {

        whenever(shopApi.getProductById(0))
            .thenThrow(RuntimeException())

        var error: Throwable? = null

        repository
            .getProductById(0)
            .catch { error = it }
            .collect()

        assertNotEquals(null, error)
    }

    @Test
    fun `getProductById after 2'nd retry emits successfully`() =
        runBlockingTest {

            var shouldThrowError = true

            whenever(shopApi.getProductById(0))
                .doAnswer { if (shouldThrowError) throw RuntimeException() else product }

            repository
                .getProductById(0)
                .onEach { retrievedProduct ->
                    assertEquals(retrievedProduct, product.toModel())
                }
                .launchIn(this)

            advanceTimeBy(FLOW_RETRY_DELAY)
            shouldThrowError = false

        }


    @Test
    fun `getProductListByIds flow emits successfully`() = runBlocking {

        whenever(shopApi.getProductListByIds(listOf(1,2,3)))
            .thenReturn(productList)

        repository
            .getProductListByIds(listOf(1,2,3))
            .collect { retrievedList ->
                assertEquals(retrievedList, productList.map { it.toModel() })
            }
    }

    @Test
    fun `getProductListByIds flow throws exception even after retries`() = runBlockingTest {

        whenever(shopApi.getProductListByIds(listOf(1,2,3)))
            .thenThrow(RuntimeException())

        var error: Throwable? = null

        repository
            .getProductListByIds(listOf(1,2,3))
            .catch { error = it }
            .collect()

        assertNotEquals(null, error)
    }

    @Test
    fun `getProductListByIds after 2'nd retry emits successfully`() =
        runBlockingTest {

            var shouldThrowError = true

            whenever(shopApi.getProductListByIds(listOf(1,2,3)))
                .doAnswer { if (shouldThrowError) throw RuntimeException() else productList }

            repository
                .getProductListByIds(listOf(1,2,3))
                .onEach { retrievedList ->
                    assertEquals(retrievedList, productList.map { it.toModel() })
                }
                .launchIn(this)

            advanceTimeBy(FLOW_RETRY_DELAY)
            shouldThrowError = false

        }

}