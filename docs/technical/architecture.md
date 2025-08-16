# 🏗️ Architecture Technique - PocoCare Ultimate

## 🎯 Vue d'ensemble

PocoCare Ultimate adopte une **architecture modulaire Android moderne** basée sur les principes de Clean Architecture, MVVM et Dependency Injection. Chaque module est autonome, testable et interchangeable.

---

## 📐 Principes Architecturaux

### 🧱 Modularité
- **Séparation claire** : Chaque domaine fonctionnel dans son module
- **Faible couplage** : Interfaces et contrats bien définis
- **Forte cohésion** : Responsabilité unique par module
- **Extensibilité** : Ajout de modules sans impact sur l'existant

### 🔄 Reactive Programming
- **Coroutines** : Asynchrone non-bloquant
- **Flow** : Streams de données réactifs
- **LiveData/StateFlow** : État UI réactif
- **Event Bus** : Communication inter-modules

### 🧪 Testabilité
- **Dependency Injection** : Hilt pour l'injection
- **Interfaces** : Mocking et substitution facile
- **Repository Pattern** : Abstraction couche données
- **Use Cases** : Logique métier isolée

---

## 🏗️ Structure Globale

```
PocoCare/
├── app/                          # Application principale
│   ├── src/main/kotlin/
│   │   ├── di/                   # Configuration Hilt
│   │   ├── navigation/           # Navigation Compose
│   │   └── MainActivity.kt
│   └── build.gradle
├── core/                         # Utilitaires partagés
│   ├── common/                   # Extensions, utils
│   ├── database/                 # Configuration Room
│   ├── datastore/               # Configuration DataStore
│   └── network/                 # Configuration réseau
└── modules/                      # Modules fonctionnels
    ├── collecte-metrics/         # 📊 Collecte métriques
    ├── stockage-donnees/         # 💾 Stockage données
    ├── moteur-regles/           # ⚙️ Moteur règles
    ├── ia-ml/                   # 🤖 Intelligence artificielle
    ├── automations-playbooks/   # 🔄 Automations
    ├── securite-permissions/    # 🔒 Sécurité
    ├── configuration-flags/     # 🚩 Configuration
    ├── observabilite-logs/      # 📊 Observabilité
    ├── ui-ux/                   # 🎨 Interface utilisateur
    └── privacy-compliance/      # 🔐 Conformité privacy
```

---

## 🧩 Architecture par Module

### 📊 Module Standard
```
module-name/
├── src/main/kotlin/
│   ├── data/                    # Couche données
│   │   ├── local/              # Room, DataStore
│   │   ├── remote/             # APIs, services
│   │   ├── repository/         # Implémentation Repository
│   │   └── mapper/             # Conversion données
│   ├── domain/                  # Couche métier
│   │   ├── model/              # Entités business
│   │   ├── repository/         # Interfaces Repository
│   │   └── usecase/            # Use Cases
│   ├── presentation/            # Couche présentation
│   │   ├── ui/                 # Composables UI
│   │   ├── viewmodel/          # ViewModels
│   │   └── state/              # États UI
│   └── di/                      # Configuration DI
├── src/test/                    # Tests unitaires
└── src/androidTest/             # Tests instrumentés
```

### 🔗 Communication Inter-Modules

```kotlin
// 1. Interfaces publiques
interface MetricsCollector {
    fun collectBatteryMetrics(): Flow<BatteryMetrics>
}

// 2. Event Bus pour communication
sealed class SystemEvent {
    object BatteryLow : SystemEvent()
    data class MetricCollected(val metric: Metric) : SystemEvent()
}

// 3. Dependency Injection
@Module
@InstallIn(SingletonComponent::class)
object ModuleBindings {
    @Binds
    abstract fun bindMetricsCollector(
        impl: MetricsCollectorImpl
    ): MetricsCollector
}
```

---

## 💾 Couche Données

### 🗄️ Room Database
```kotlin
@Database(
    entities = [
        BatteryMetric::class,
        SystemMetric::class,
        UserAction::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class PocoCareDatabase : RoomDatabase() {
    abstract fun batteryDao(): BatteryDao
    abstract fun systemDao(): SystemDao
    abstract fun userActionDao(): UserActionDao
}

// Configuration avec migration automatique
@Provides
@Singleton
fun provideDatabase(@ApplicationContext context: Context): PocoCareDatabase {
    return Room.databaseBuilder(
        context,
        PocoCareDatabase::class.java,
        "pococare_database"
    )
    .addMigrations(MIGRATION_1_2)
    .fallbackToDestructiveMigration() // Dev seulement
    .build()
}
```

### ⚙️ DataStore Proto
```protobuf
// user_preferences.proto
syntax = "proto3";

option java_package = "com.neuralbeginner.pococare.datastore";

message UserPreferences {
  bool dark_mode = 1;
  int32 collection_frequency_minutes = 2;
  repeated string enabled_modules = 3;
  ThresholdSettings thresholds = 4;
}

message ThresholdSettings {
  int32 battery_low_threshold = 1;
  int32 cpu_high_threshold = 2;
  int32 memory_high_threshold = 3;
}
```

### 🔄 Repository Pattern
```kotlin
interface BatteryRepository {
    suspend fun saveBatteryMetric(metric: BatteryMetric)
    fun observeBatteryMetrics(): Flow<List<BatteryMetric>>
    suspend fun getBatteryTrends(days: Int): List<BatteryTrend>
}

@Singleton
class BatteryRepositoryImpl @Inject constructor(
    private val batteryDao: BatteryDao,
    private val batteryCollector: BatteryCollector
) : BatteryRepository {
    
    override suspend fun saveBatteryMetric(metric: BatteryMetric) {
        batteryDao.insert(metric)
    }
    
    override fun observeBatteryMetrics(): Flow<List<BatteryMetric>> {
        return batteryDao.observeAll()
    }
}
```

---

## 🎨 Couche Présentation

### 🖼️ Jetpack Compose UI
```kotlin
@Composable
fun BatteryDashboard(
    viewModel: BatteryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    LazyColumn {
        item {
            BatteryLevelCard(
                level = uiState.batteryLevel,
                trend = uiState.trend,
                onClick = { viewModel.showBatteryDetails() }
            )
        }
        
        item {
            BatteryOptimizationCard(
                suggestions = uiState.suggestions,
                onApplySuggestion = viewModel::applySuggestion
            )
        }
    }
}

@Composable
fun BatteryLevelCard(
    level: Int,
    trend: BatteryTrend,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Batterie",
                style = MaterialTheme.typography.headlineSmall
            )
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$level%",
                    style = MaterialTheme.typography.displayMedium
                )
                
                TrendIndicator(trend = trend)
            }
        }
    }
}
```

### 🎭 ViewModels & État
```kotlin
@HiltViewModel
class BatteryViewModel @Inject constructor(
    private val getBatteryMetrics: GetBatteryMetricsUseCase,
    private val optimizeBattery: OptimizeBatteryUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(BatteryUiState())
    val uiState: StateFlow<BatteryUiState> = _uiState.asStateFlow()
    
    init {
        collectBatteryMetrics()
    }
    
    private fun collectBatteryMetrics() {
        viewModelScope.launch {
            getBatteryMetrics()
                .catch { exception ->
                    _uiState.update { 
                        it.copy(error = exception.message)
                    }
                }
                .collect { metrics ->
                    _uiState.update {
                        it.copy(
                            batteryLevel = metrics.level,
                            trend = metrics.trend,
                            isLoading = false
                        )
                    }
                }
        }
    }
    
    fun applySuggestion(suggestion: BatteryOptimization) {
        viewModelScope.launch {
            optimizeBattery(suggestion)
                .onSuccess {
                    // Handle success
                }
                .onFailure { exception ->
                    // Handle error
                }
        }
    }
}

data class BatteryUiState(
    val batteryLevel: Int = 0,
    val trend: BatteryTrend = BatteryTrend.STABLE,
    val suggestions: List<BatteryOptimization> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)
```

---

## 🔄 Couche Métier (Domain)

### 🎯 Use Cases
```kotlin
@Singleton
class CollectBatteryMetricsUseCase @Inject constructor(
    private val batteryRepository: BatteryRepository,
    private val preferencesRepository: PreferencesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(): Flow<BatteryMetric> = withContext(dispatcher) {
        val frequency = preferencesRepository.getCollectionFrequency()
        
        flow {
            while (currentCoroutineContext().isActive) {
                val metric = batteryRepository.collectCurrentMetrics()
                emit(metric)
                delay(frequency)
            }
        }
    }
}

@Singleton
class OptimizeBatteryUseCase @Inject constructor(
    private val batteryOptimizer: BatteryOptimizer,
    private val rulesEngine: RulesEngine,
    private val logger: Logger
) {
    suspend operator fun invoke(
        optimization: BatteryOptimization
    ): Result<Unit> = runCatching {
        logger.info("Applying battery optimization: ${optimization.type}")
        
        // Vérifier les règles
        val rules = rulesEngine.getActiveRules()
        val applicableRules = rules.filter { 
            it.appliesTo(optimization) 
        }
        
        // Appliquer l'optimisation
        batteryOptimizer.apply(optimization, applicableRules)
        
        logger.info("Battery optimization applied successfully")
    }
}
```

### 🏛️ Entités Business
```kotlin
@Parcelize
data class BatteryMetric(
    val id: String = UUID.randomUUID().toString(),
    val timestamp: Instant,
    val level: Int,
    val temperature: Float,
    val voltage: Float,
    val current: Float,
    val capacity: Int,
    val cycleCount: Int,
    val health: BatteryHealth,
    val chargingStatus: ChargingStatus,
    val powerSource: PowerSource
) : Parcelable

enum class BatteryHealth {
    GOOD, FAIR, POOR, CRITICAL, UNKNOWN
}

enum class ChargingStatus {
    CHARGING, DISCHARGING, NOT_CHARGING, FULL
}

enum class PowerSource {
    AC, USB, WIRELESS, BATTERY
}

data class BatteryTrend(
    val direction: TrendDirection,
    val percentage: Float,
    val timeWindow: Duration
)

enum class TrendDirection {
    IMPROVING, STABLE, DECLINING
}
```

---

## 🔧 Configuration & Dependency Injection

### 💉 Hilt Setup
```kotlin
@HiltAndroidApp
class PocoCareApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        
        // Initialize crash reporting
        // Initialize analytics (privacy-compliant)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PocoCareDatabase {
        return Room.databaseBuilder(
            context,
            PocoCareDatabase::class.java,
            "pococare_database"
        )
        .addMigrations(*DatabaseMigrations.ALL_MIGRATIONS)
        .build()
    }
    
    @Provides
    fun provideBatteryDao(database: PocoCareDatabase): BatteryDao =
        database.batteryDao()
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}
```

---

## 🔒 Sécurité & Privacy

### 🛡️ Architecture Sécurisée
```kotlin
// Chiffrement des données sensibles
@Singleton
class SecureDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    
    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    
    fun storeSecureValue(key: String, value: String) {
        sharedPreferences.edit()
            .putString(key, value)
            .apply()
    }
}

// Anti-tamper et intégrité
object IntegrityChecker {
    fun verifyAppIntegrity(context: Context): Boolean {
        // Vérification signature
        // Vérification checksums
        // Détection root/debug
        return true
    }
}
```

---

## 📊 Performance & Monitoring

### ⚡ Optimisations Performance
```kotlin
// LazyLoading avec Paging
@Composable
fun MetricsList(
    viewModel: MetricsViewModel = hiltViewModel()
) {
    val lazyPagingItems = viewModel.metricsFlow.collectAsLazyPagingItems()
    
    LazyColumn {
        items(lazyPagingItems) { metric ->
            MetricItem(metric = metric)
        }
    }
}

// Cache intelligent
@Singleton
class CacheManager @Inject constructor() {
    private val memoryCache = LruCache<String, Any>(100)
    
    fun <T> getOrPut(key: String, provider: suspend () -> T): T {
        return memoryCache[key] as? T ?: runBlocking {
            provider().also { memoryCache.put(key, it) }
        }
    }
}
```

### 📈 Métriques & Observabilité
```kotlin
// Structured logging
@Singleton
class StructuredLogger @Inject constructor() {
    
    fun logMetricCollection(
        module: String,
        metric: String,
        duration: Duration,
        success: Boolean
    ) {
        val logData = mapOf(
            "timestamp" to Instant.now().toString(),
            "module" to module,
            "metric" to metric,
            "duration_ms" to duration.inWholeMilliseconds,
            "success" to success,
            "thread" to Thread.currentThread().name
        )
        
        Log.i("METRICS", Json.encodeToString(logData))
    }
}
```

---

Cette architecture garantit **évolutivité**, **maintenabilité** et **performance** tout en respectant les principes de **Clean Architecture** et les **best practices Android**.