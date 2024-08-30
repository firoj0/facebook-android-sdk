/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.facebook.appevents.iap

import android.content.Context
import com.facebook.FacebookPowerMockTestCase
import com.facebook.FacebookSdk
import com.facebook.appevents.iap.InAppPurchaseUtils.getClass
import com.facebook.appevents.iap.InAppPurchaseUtils.getMethod
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest

@PrepareForTest(FacebookSdk::class, InAppPurchaseUtils::class)
class InAppPurchaseBillingClientWrapperV5PlusTest : FacebookPowerMockTestCase() {
    private val exampleClassName =
        "com.facebook.appevents.iap.InAppPurchaseBillingClientWrapperV5PlusTest"
    private val exampleMethodName = "setup"

    @Before
    override fun setup() {
        super.setup()
        PowerMockito.mockStatic(InAppPurchaseUtils::class.java)
        whenever(getClass(anyOrNull())).thenReturn(Class.forName(exampleClassName))
        whenever(
            getMethod(
                any(),
                any(),
                any()
            )
        ).thenReturn(
            Class.forName(exampleClassName)
                .getMethod(exampleMethodName)
        )

    }

    @Test
    fun testHelperClassCanSuccessfullyCreateWrapper() {
        val mockContext: Context = mock()
        val inAppPurchaseBillingClientWrapperV5Plus =
            InAppPurchaseBillingClientWrapperV5Plus.getOrCreateInstance(mockContext)
        Assertions.assertThat(inAppPurchaseBillingClientWrapperV5Plus).isNotNull()
    }

    @Test
    fun testCantGetClass() {
        whenever(getClass(anyOrNull())).thenReturn(null)
        val mockContext: Context = mock()
        val inAppPurchaseBillingClientWrapperV5Plus =
            InAppPurchaseBillingClientWrapperV5Plus.getOrCreateInstance(mockContext)
        Assertions.assertThat(inAppPurchaseBillingClientWrapperV5Plus).isNull()
    }

    @Test
    fun testCantGetMethod() {
        whenever(
            getMethod(
                any(),
                any(),
                any()
            )
        ).thenReturn(
            null
        )
        val mockContext: Context = mock()
        val inAppPurchaseBillingClientWrapperV5Plus =
            InAppPurchaseBillingClientWrapperV5Plus.getOrCreateInstance(mockContext)
        Assertions.assertThat(inAppPurchaseBillingClientWrapperV5Plus).isNull()
    }
}
