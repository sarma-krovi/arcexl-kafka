package com.arcexl

import com.arcexl.dao.StockPriceTestConfiguration
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
@SpringBootTest(classes = [StockPriceApplication::class, StockPriceTestConfiguration::class])
abstract class BaseArcExlTest