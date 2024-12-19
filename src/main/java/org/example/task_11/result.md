Было:

```kotlin
@Service
class FeatureToggleServiceImpl(
    private val featureToggleApiClient: FeatureToggleApiReactorClient,
) {

    suspend fun getAvailableFeatures(features: Set<String>, headers: Headers): Set<String> {
        return features.takeIf { it.isNotEmpty() }
            ?.let { getFeatures(headers, features) }
            ?: emptySet()
    }

    suspend fun allFeatures(headers: Headers): Set<String> =
        getAvailableFeatures(ALL_FEATURES, headers)

    suspend fun hasFeature(feature: String, headers: Headers): Boolean =
        feature in getAvailableFeatures(setOf(feature), headers)

    private suspend fun getFeatures(headers: Headers, features: Set<String>): Set<String> =
        runCatchingCancellable {
            featureToggleApiClient.get(FeignHeaders.of(headers), features, true)
                .awaitSingle()
                .features
                .toSet()
        }.getOrElse {
            logger.error(it) { "Failed to get features $features for conditions: $headers" }
            emptySet()
        }

    companion object : KLogging() {
        const val REGION_FILTER_FEATURE = "regionOffersFilter"
        const val PARTNER_LAST_SEEN_AMOUNT = "partnerLastSeenAmount"
        
        val ALL_FEATURES: Set<String> = setOf(REGION_FILTER_FEATURE, PARTNER_LAST_SEEN_AMOUNT)
    }
}
```

Стало:

```kotlin
interface FeatureToggleService {
    suspend fun getAvailableFeatures(features: Set<String>, headers: Headers): Set<String>
    suspend fun allFeatures(headers: Headers): Set<String>
    suspend fun hasFeature(feature: String, headers: Headers): Boolean
}

@Service
class FeatureToggleServiceImpl(
    private val featureToggleApiClient: FeatureToggleApiReactorClient,
    private val featureProperties: FeatureProperties,
) : FeatureToggleService {

    override suspend fun getAvailableFeatures(features: Set<String>, headers: Headers): Set<String> {
        return features.takeIf { it.isNotEmpty() }
            ?.let { getFeatures(headers, features) }
            ?: emptySet()
    }

    override suspend fun allFeatures(headers: Headers): Set<String> =
        getAvailableFeatures(featureProperties.allFeatures, headers)

    override suspend fun hasFeature(feature: String, headers: Headers): Boolean =
        feature in getAvailableFeatures(setOf(feature), headers)

    private suspend fun getFeatures(headers: Headers, features: Set<String>): Set<String> =
        runCatchingCancellable {
            featureToggleApiClient.get(FeignHeaders.of(headers), features, true)
                .awaitSingle()
                .features
                .toSet()
        }.getOrElse {
            logger.error(it) { "Failed to get features $features for conditions: $headers" }
            emptySet()
        }

    companion object : KLogging()
}
```