# PocoCare Ultimate – Spécification Produit & Technique Complète (Version Vision Intégrale)

> Document maître (Living Document). Cette version “complète” formalise TOUT: vision, périmètre, architecture, modèles de données, APIs internes, modules, sécurité, IA, observabilité, qualité, roadmap, backlog, tests, conformité, performance, menaces & mitigations.  
> Objectif: servir de référence unique pour orchestrer le développement incrémental jusqu’à une version “Ultimate” multi-phases.

---

## 0. Index Rapide

1. Résumé Exécutif  
2. Objectifs & Principes Directeurs  
3. Personas & Cas d’Usage  
4. Feature Set (Hiérarchie & Niveaux de Maturité)  
5. Périmètre Fonctionnel Détaillé  
6. Non-Objectifs (Explicites)  
7. Architecture Logique (Macro)  
8. Architecture Modulaire (Gradle)  
9. Diagrammes de Flux Principaux  
10. Modèles de Données & Schémas (Room / Proto / JSON)  
11. Collecte des Métriques – Spécifications Techniques  
12. Moteur de Règles & Politique (DSL)  
13. Intelligence Artificielle – Pipeline & Opérations  
14. Automations / Playbooks – Format & Exécution  
15. Sécurité & Durcissement – Menaces / Contrôles  
16. Confidentialité & Consentements  
17. Stratégies Stockage & Nettoyage (Algorithmes)  
18. Batterie & Prévision (Modèles)  
19. Performance & Optimisations (Budgets / Techniques)  
20. UI / UX – Structure, Navigation, États & Accessibilité  
21. Observabilité & Télémétrie (Logs / Metrics / Traces)  
22. Configuration, Feature Flags & Overrides  
23. Extensibilité & Plugins (Dynamic Features)  
24. Assistant IA (NLP / Intent Mapping)  
25. Stratégie de Tests (Pyramide complète)  
26. Pipelines CI/CD & Qualité Continue  
27. Sécurité Logicielle & Supply Chain (SBOM / Signatures)  
28. Gestion du Cycle de Vie des Modèles IA  
29. Roadmap Phasée & Jalons  
30. Backlog Structuré (Issues Générées)  
31. Critères de “Definition of Done” par Domaine  
32. SLA Interne & SLO Qualité  
33. Risques & Plans de Mitigation  
34. Conformité (Play Store, Privacy, Légal Basique)  
35. Plan de Communication (Release Notes / Changelog)  
36. Guide Contribution & Gouvernance  
37. Glossaire  
38. Annexes (Extraits DSL, Exemples JSON, Pseudo Code)  

---

## 1. Résumé Exécutif

PocoCare Ultimate est une application Android (15+ prête 16) d’optimisation proactive tout-en-un combinant:
- Monitoring multi-domaine (Performance, Batterie, Stockage, Réseau, Sécurité, Confidentialité)
- Analyse & Prévision (drain, saturation, anomalies)
- Moteur IA (recommandations adaptatives + explications)
- Automations scriptables (playbooks YAML signés)
- Hardening & Privacy (contrôle permissions, audit surface)
- Extensibilité (plugins dynam. + marketplace future)
- Transparence & Contrôle utilisateur (explainability, consentements granulaires)

---

## 2. Objectifs & Principes

Objectifs:
- Améliorer autonomie batterie & performance perçue
- Prévenir situations critiques (stockage plein / surchauffe)
- Réduire friction utilisateur (entretien automatisé)
- Garantir sécurité / confidentialité avec transparence

Principes:
- On-device-first (cloud optionnel)
- Sécurité, Privacy by Design
- Observabilité exhaustive (mais contrôlable)
- Dégradations gracieuses (devices low-end)
- Extensibilité contrôlée (plugins signés)

KPIs (exemples):
- Réduction moyenne drain batterie journalier ≥ 5%
- Diminution incidents “stockage saturé” ≥ 70%
- Temps moyen action corrective < 5 s (après recommandation)
- Crash-free sessions > 99.5%
- Latence génération recos < 200 ms P95

---

## 3. Personas & Cas d’Usage

Personas:
- Power User Technique (veut contrôle granulaire)
- Utilisateur Standard (veut “un bouton” → optimiser)
- Admin Entreprise (policy & conformité)
- Développeur Observabilité (debug & logs)
- Chercheur Performance (bench, data)

Cas principaux:
1. Nettoyer stockage rapidement
2. Diagnostiquer drain batterie anormal
3. Recevoir alerte surchauffe + action proposée
4. Prévoir saturation stockage avant départ voyage
5. Révoquer permissions risquées en un lot
6. Automatiser routines nocturnes (clean + compaction)
7. Visualiser historique anomalies & actions entreprises
8. Interroger l’assistant: “Pourquoi la batterie chute ?”

---

## 4. Feature Set – Niveaux de Maturité

Niveaux:
- Core: Collecte basique + dashboard
- Enhanced: Recommandations règles + nettoyage sûr
- Advanced: Prévisions + stockage intelligent + privacy scan
- Intelligent: Recos ML + bandit adaptatif
- Autonomous: Automations planifiées + RL + plugin écosystème

---

## 5. Périmètre Fonctionnel Détaillé (Table Synthèse)

| Domaine | Sous-Fonction | Description | Niveau |
|---------|---------------|-------------|--------|
| Metrics | CPU/RAM/IO | Flows + sampling adaptatif | Core→Advanced |
| Stockage | Cache Scan | Taille, classes (safe/mod) | Core |
| Stockage | Déduplication | Hash BLAKE3 chunk + index Bloom | Advanced |
| Batterie | Profil drain | Fenêtres glissantes + forecast | Enhanced→Advanced |
| Recos | Rules Engine | Seuils paramétrables | Core |
| Recos | ML Scoring | TFLite model multi-label | Intelligent |
| Automations | Playbooks | YAML signés / dry-run | Advanced→Autonomous |
| Sécurité | Permission Audit | Granted / requested | Core |
| Sécurité | Intégrité | Hash modules, anti-tamper | Advanced |
| Confidentialité | Tracker Scan | Pattern host / signature | Advanced |
| Réseau | Anomalies trafic | Volume / non-TLS | Advanced |
| IA | RL Bandit | Optimise ordonnancement actions | Autonomous |
| Assist | Chat Intent | Mapping NAT → actions | Intelligent |
| Plugins | Dynamic Features | Chargement signés | Autonomous |

---

## 6. Non-Objectifs

- Root / privilèges systèmes (pas de kill forcé processes)
- Sniffing réseau profond sans VPN/service dédié
- Forçage scheduler kernel
- Récupération données privées hors permission explicite
- Surveillance en arrière-plan intrusive non consentie

---

## 7. Architecture Logique (Macro)

```
Utilisateurs -> UI Compose -> ViewModels -> UseCases -> Repositories -> Collectors / Services -> System APIs
                                               |                     |
                                               |                     -> ML / Policies
                                               -> Automation Engine -> Action Executors
                                               -> Telemetry -> Storage / Export
```

---

## 8. Architecture Modulaire (Gradle)

Groupes:
- core:* (fondations)
- feature:* (fonctionnalités UI + domain feature)
- tools:* (tests, bench)
- integration:* (optionnel future)
- plugins:* (chargés dynamiquement)

Dépendances (extrait):
```
core:foundation <- aucune
core:domain <- core:foundation
core:data <- core:domain
core:ml <- core:foundation
core:policy <- core:foundation
feature:metrics <- core:data + core:domain
feature:recommendations <- core:domain + core:ml + core:policy
app <- toutes features
```

---

## 9. Diagrammes de Flux (Principaux)

### 9.1 Génération Recommandation

```
Collectors -> Repos (Flow) -> Snapshot Aggregator -> Rule Engine -> Candidates
                                                    -> Feature Extractor -> ML Scorer -> Prioritized List
```

### 9.2 Pipeline Nettoyage Stockage

```
Trigger (user / automation) -> Scanner -> Classification -> Plan Actions -> Confirmation -> Executor -> Report (Telemetry + Timeline)
```

### 9.3 Playbook Execution

```
Policy Trigger -> Preconditions Check -> Dry Run -> Consent? -> Execute Steps (idempotence) -> Audit Log -> Success/Failure Handling
```

---

## 10. Modèles de Données

### 10.1 Room Entities (exemples)

```kotlin
@Entity(tableName = "metric_samples")
data class MetricSampleEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val type: String,           // CPU, RAM, BATTERY, STORAGE
  val valueJson: String,      // JSON sérialisé
  val ts: Long
)

@Entity(tableName = "recommendations")
data class RecommendationEntity(
  @PrimaryKey val recId: String,
  val category: String,
  val title: String,
  val description: String,
  val priority: Int,
  val state: String,         // NEW, ACCEPTED, REJECTED, EXECUTED
  val ts: Long
)

@Entity(tableName = "actions_log")
data class ActionLogEntity(
  @PrimaryKey(autoGenerate = true) val id: Long,
  val actionType: String,
  val payloadJson: String,
  val result: String,
  val success: Boolean,
  val startedAt: Long,
  val durationMs: Long
)
```

### 10.2 Proto DataStore (config.proto)

```
message UserConfig {
  bool telemetry_opt_in = 1;
  bool ai_model_opt_in = 2;
  bool automation_enabled = 3;
  map<string, double> thresholds = 4;
  repeated string disabled_recommendation_ids = 5;
}
```

### 10.3 Snapshot (In-Memory)

```kotlin
data class SystemSnapshot(
  val cpuLoad: CpuLoad,
  val ram: RamUsage,
  val battery: BatteryState,
  val storage: StorageSummary,
  val network: NetworkStats?,
  val permissions: PermissionStatusSummary,
  val timestamp: Instant
)
```

---

## 11. Collecte des Métriques – Détails

| Type | Source | Fréquence | Stratégie Adaptative |
|------|--------|-----------|----------------------|
| CPU | /proc/stat + Process | 5s | Baisse à 15s écran off |
| RAM | Debug / ActivityManager | 5s | Idem |
| Batterie | Intent + BatteryManager | Event + 30s | Réduit si stable |
| Stockage | StatFs + segment scan | 1h | Désactivé < 10% batterie |
| Réseau | TrafficStats | 10s | 30s écran off |
| Permissions | PackageManager diff | 1h | On-change si possible |

Journalisation anomalies quand deltas > seuil (ex: CPU > 85% 3 cycles consécutifs).

---

## 12. Moteur de Règles & DSL

### 12.1 Syntaxe YAML Simplifiée

```yaml
version: 1
rules:
  - id: high.cpu
    when:
      all:
        - metric.cpu.avgPercent >= 85
        - metric.cpu.window5m.trend == "up"
    then:
      recommend:
        category: "performance"
        title: "CPU élevé"
        actionHint: "analyzeTopApps"
        priority: HIGH
```

Opérateurs:
- Comparaison: >, >=, <, <=, ==, !=
- Fonctions: trend, percentChange, windowAvg(n)
- Logique: all, any, not

Conflits: priorité -> specificity (nb conditions) -> riskLevel.

---

## 13. Intelligence Artificielle

### 13.1 Features (Exemples)

| Feature | Description |
|---------|-------------|
| cpu_avg_1m | Moyenne CPU 1 minute |
| cpu_trend_5m | Variation (slope) CPU |
| ram_pressure | ratio used/total |
| storage_free_pct | pourcentage libre |
| battery_drain_rate | Δ% / Δt normalisé |
| active_recs_count | nombre recos récentes |
| action_accept_ratio | taux acceptation (user) |

### 13.2 Modèle

- Type: Multi-label classification (actions candidates)
- Format: TFLite int8 quantized
- Entrée: vecteur float (normalisé)
- Sortie: logits pour chaque action id stable
- Réordonnancement: logistic + cost-aware scoring

### 13.3 RL Bandit (Étape future)
- Algorithme: LinUCB ou Thompson Sampling
- Reward: (amélioration batterie + Δram + user satisfaction weightés)
- Exploration decay avec epsilon adaptatif

### 13.4 Explainability
- Approche: Feature importance par permutation locale
- Output: Top 3 features déclencheurs

---

## 14. Automations / Playbooks

### 14.1 Schéma Playbook

```yaml
id: storage.preventive.clean
version: 1
triggers:
  - type: forecast.storage
    operator: "<="
    value: 3d
preconditions:
  - battery_percent >= 20
  - user_consent_automation == true
steps:
  - action: clearCache
    mode: SAFE
  - action: recomputeStorageIndex
  - action: emitNotification
    params:
      message: "Nettoyage préventif effectué"
rollback: none
auditing: true
risk: LOW
```

Exécution Transactionnelle:
- Validation → Dry run plan → Acquire lock → Execute steps serial → Release → Log

Idempotence: chaque action documente un hash d’impact (évite re-run inutile).

---

## 15. Sécurité & Durcissement

Menaces (STRIDE simplifié):

| Catégorie | Exemple | Mitigation |
|-----------|---------|------------|
| Spoofing | Fausse policy injectée | Signature Ed25519 + hash |
| Tampering | Altération modèle IA | Signature + version manifest |
| Repudiation | Nier action destructive | Audit log immuable (append-only) |
| Info Disclosure | Exfiltration metrics | Chiffrement store + consent |
| DoS | Collecte saturant CPU | Scheduler adaptatif |
| Elevation | Plugin arbitraire | Signature + sandbox (restricted classloader) |

Chiffrement:
- DataStore sensible: master key Android Keystore
- Logs: non chiffrés (par défaut) mais rotation

Anti-Tamper:
- Vérification signature APK + integrity digest des modules critiques au démarrage

---

## 16. Confidentialité & Consentements

Consent Flags:
- telemetry_opt_in
- ai_model_opt_in
- network_scan_opt_in
- automation_enabled

Modes:
- Private Mode: Pas de télémetrie externe, IA offline seulement
- Transparency Panel: Voir dernière 50 décisions IA (règles+features)

Droit à l’oubli:
- Bouton “Purger Historique” → supprime tables metrics, recos, actions (conserve config minimale)

---

## 17. Stockage – Algorithmes

### 17.1 Déduplication
1. Scan media directories (scopes)
2. Chunking (taille 1 MB) -> BLAKE3 digest
3. Bloom Filter membership test (accélération)
4. Index hash -> occurrences
5. Classement par (taille totale libérable)

### 17.2 “Cold Data”
- Heuristique: dernier accès (metadata si accessible) + absence ouverture > 45j
- Score = decay(lastAccess) * sizeWeight

### 17.3 Prévision Saturation
- Modèle linéaire: projection daily delta (EMA)
- Si free_pct < threshold OR days_to_full <= N -> trigger reco

---

## 18. Batterie – Modèles

Drain Model:
- Input: séquence battery_percent(t), screen_state, network_active
- Sliding window 30 min
- Forecast: RemainTime = (current_percent / avg_drain_rate)
Surchauffe:
- Temp > mean(last15m)+2σ OU absolute > 42°C → alerte

---

## 19. Performance & Optimisations

Budgets:
- Cold Start < 800 ms (Pixel 7), < 1200 ms (device moyen)
- Recos Generation < 150 ms P90
- Memory stable < 120 MB
- Background overhead CPU < 2% moyenne
Techniques:
- Baseline Profiles (macrobenchmark)
- Lazy column virtualization
- Suspend-friendly collectors (pause écran off prolongé)
- Batching DB writes (Room transaction group)

---

## 20. UI / UX

Navigation (Graph):
```
Dashboard
  -> Performance
  -> Batterie
  -> Stockage
  -> Sécurité
  -> Confidentialité
  -> Automations
  -> Timeline
  -> Assistant IA
  -> Paramètres
```

Cards Dashboard (ex):
- CPU & RAM
- Batterie (niveau + forecast)
- Stockage (libre + tendance)
- Recos (top 3)
- Sécurité (permissions à risque)
- Actions rapides (Nettoyer, Mode Éco)

États:
- Loading (squelettes)
- Empty (aucune reco)
- Warning (color semantic)
Accessibilité:
- ContentDescription
- Focus order logique
- Dynamic color (Monet) + fallback palette

---

## 21. Observabilité

Logs Structurés (JSON bref):
```json
{
  "t":"2025-08-16T12:00:00Z",
  "lvl":"INFO",
  "comp":"RecoEngine",
  "msg":"Generated recommendations",
  "count":3,
  "latency_ms":42
}
```

Metrics internes (in-app):
- recos_generated_total
- actions_executed_total
- action_success_ratio
- model_inference_latency_ms (histogram)
- forecast_error_battery (MAE)

Traces (OTel style minimal):
- span: playbook.execute
- span: ml.inference
Export:
- NDJSON bundle compressé (user initiated)

---

## 22. Configuration & Feature Flags

Niveaux:
1. Build-time (Gradle constants)
2. Runtime local (DataStore)
3. Remote (signed JSON manifest)

Merge order: build < remote < local override (dev panel)

Exemple remote manifest snippet:
```json
{
  "version": 3,
  "features": {
    "ml.recommendations": true,
    "privacy.scan": false
  },
  "thresholds": {
    "cpu.high": 85,
    "ram.high": 80
  }
}
```

Signature: JSON + detached signature Ed25519.

---

## 23. Extensibilité Plugins

Plugin Manifest (JSON):
```json
{
  "id":"plugin.example.benchmark",
  "version":"1.0.0",
  "minAppVersion":"2.0.0",
  "permissions":["metrics.read","actions.invoke"],
  "checksum":"sha256:...",
  "signature":"ed25519:..."
}
```

Chargement:
- Vérif signature
- Policy sandbox (liste classes exposées)
- Registration via ServiceLoader style interface

---

## 24. Assistant IA (NLP)

Intent Pipeline:
```
User Utterance -> Preprocess -> Intent Classifier (fastText / regex hybrid) 
 -> Slot Extractor -> Action Planner -> Confirmation -> Executor
```

Exemples Intent:
- “Nettoie les caches agressivement” → intent: CLEAN_CACHE, mode=AGGRESSIVE
- “Pourquoi la batterie descend vite ?” → intent: EXPLAIN_BATTERY_DRAIN → Explanation builder

Fallback:
- Si confiance < seuil → Suggestion clarifications.

---

## 25. Stratégie de Tests

| Niveau | Outils | Contenu | Cible Coverage |
|--------|-------|---------|----------------|
| Unit | JUnit + Truth/Kotest | Domain, règles, parsers | 90% domain |
| Property | KotlinCheck / jqwik | Policy conditions | Invariants |
| Integration | Instrumented + Robolectric | Repos + DB + flows | 80% repos |
| UI Compose | Espresso/Compose Test | Navigation, états | Critique flows |
| Performance | Macro/Micro benchmark | Startup, recos latency | Budgets |
| Security | Scripts custom | Tamper, injection path | Scénarios clés |
| Fuzz | Jazzer / custom harness | DSL parser | Robustesse |
| ML Validation | Offline harness | Drift detection | Distribution stable |
| End-to-End | Gradle task scenario | Playbook run cycle | Happy path |

---

## 26. CI/CD Pipeline (Étapes)

1. Lint / Detekt / KtLint
2. Build (assembleDebug, assembleRelease)
3. Unit tests
4. Instrumented tests (matrix)
5. Security static (Semgrep)
6. SBOM (CycloneDX)
7. Upload artifacts (APK + mapping + SBOM)
8. Benchmark (nightly)
9. Drift Report ML (nightly)
10. Release tagging (semver + changelog)
11. Signature APK (Play Signing / local keystore)

Quality Gate: échec si:
- Coverage domain < 85%
- Detekt severity ≥ threshold
- Tests échoués
- SBOM génération échouée

---

## 27. Supply Chain Sécurité

- Verrou versions (version catalog)
- Attestations build (future: SLSA attestation)
- SBOM cyclonedx.json commit
- Dépendances critiques hashées (option verification-metadata)
- Revue manuelle PR qui introduit libs natives / crypto

---

## 28. Cycle Vie Modèles IA

Étapes:
1. Collect features anonymisées (opt-in)
2. Entraînement offline
3. Évaluation (latence, précision)
4. Signature modèle
5. Distribution (download + verify)
6. Activation (A/B flag)
7. Monitoring (drift & adoption)
8. Retrait (rollback si drift > seuil)

Drift Metric: JS Divergence sur top features distributions (window 7d vs baseline)

---

## 29. Roadmap Phasée (Indicative)

| Phase | Durée Estimée | Objectifs |
|-------|---------------|-----------|
| V1 (MVP Étendu) | 4-6 semaines | Collecte basique + nettoyage safe + recos règles |
| V1.1 | 2-3 semaines | Forecast stockage simple + assistant intents basiques |
| V1.2 | 3-4 semaines | Dédup média + drain model + log viewer |
| V1.3 | 4 semaines | ML recos + explainability initiale + policy DSL avancée |
| V1.4 | 4 semaines | Playbooks complets + automation scheduler |
| V2.0 | 6 semaines | Plugins dynamiques + privacy scanner + baseline perf |
| V2.1 | 6 semaines | RL bandit + drift dashboards + security hardening avancé |
| V3.0 | 8+ semaines | Fédéré, marketplace plugins, co-pilot local LLM léger |

---

## 30. Backlog Structuré (Résumé Catégorisé)

Categories (tags):
- phase-v1, phase-v1.1, etc.
- domain-metrics
- domain-storage
- domain-battery
- domain-security
- ai-ml
- automation
- privacy
- performance
- observability
- plugin
- compliance

(Le backlog détaillé sera transformé en issues GitHub — voir demande future.)

---

## 31. Definition of Done (DoD) par Domaine

| Domaine | DoD |
|---------|-----|
| Feature | Code + tests unit + doc module + entry changelog |
| Rule | Couvert par test condition vrai/faux + doc YAML exemple |
| ML | Modèle signé + latence mesurée + drift baseline fixée |
| Automation | Dry run validé + audit log inscrit + rollback (si prévu) |
| Security | Menaces documentées + contrôle testé |
| Storage | Simulation taille libérée + mesure post-action |
| UI | Tests UI principaux + accessibilité tags basiques |
| Performance | Bench P95 capturé + respects budgets |
| Observabilité | Log + metric + éventuelle trace définis |

---

## 32. SLA Interne & SLO

| Objet | SLO |
|-------|-----|
| Latence recos P95 | < 200 ms |
| Crash-free sessions | > 99.5% |
| Exécution playbook standard | < 3 s |
| Mise à jour modèle IA -> Activation | < 24 h (après publication) |
| Fausses alertes stockage | < 5% |

---

## 33. Risques & Mitigation (Top 10)

| # | Risque | Impact | Prob | Mitigation |
|---|--------|--------|------|-----------|
| 1 | Drift ML | Mauvaises recos | M | Monitoring + fallback rules |
| 2 | Faux positifs nettoyage | Perte fonction cachée | M | Mode simulation + confirmations |
| 3 | Impact batterie collecte | Drain | M | Sampler adaptatif |
| 4 | Rejet store (privacy) | Blocage distribution | L | Consent clair + docs |
| 5 | Vuln plugin | Exploit | L | Signature stricte + sandbox |
| 6 | Complexité code | Maintenance dure | M | Modules propres + lint |
| 7 | Latence inference | UX dégradée | M | Quantization + caching |
| 8 | Crash parsing policies | Bloque automations | L | Fuzz + validation schéma |
| 9 | Incohérence recos | Perte confiance utilisateur | M | Explanation layer |
| 10 | Sur-engorgement logs | Espace disque | M | Rotation & quotas |

---

## 34. Conformité & Légal (Base)

- Consentements explicites (opt-in IA avancée, collecte anonyme)
- Politique de confidentialité (section locale)
- Fourniture mécanisme purge data
- Aucune collecte IMEI / identifiants sensibles
- Respect limitations API Android (pas d’utilisation private API)

---

## 35. Communication & Changelog

Changelog Format (Keep a CHANGELOG):
- Added, Changed, Deprecated, Removed, Fixed, Security

Release Notes PR auto-générées depuis commit messages (Conventional Commits).

---

## 36. Contribution & Gouvernance

Branches:
- main (stable)
- develop (optionnel)
- feature/*
- fix/*
Process:
- PR review 1+ reviewer
- CI vert obligatoire
- Security-critical → reviewer sécurité
- Templates issues (bug_report.md, feature_request.md)

---

## 37. Glossaire (Sélection)

| Terme | Définition |
|-------|------------|
| Drift | Variation distribution features ML |
| Playbook | Script d’actions automatisées |
| Bandit | Algorithme RL simple décision action |
| Bloom Filter | Structure prob. membership test |
| Cold Data | Fichiers peu accédés sur fenêtre |
| Forecast Error | Écart prévision vs réel |
| Intent (NLP) | Représentation action utilisateur |
| Policy DSL | Langage déclaratif de règles |

---

## 38. Annexes

### 38.1 Extrait Policy Complexe

```yaml
id: compound.storage.forecast.clean
when:
  all:
    - forecast.storage.days_to_full <= 5
    - storage.free_percent <= 18
    - battery.percent >= 25
    - time.local_hour in [1,2,3,4]
then:
  recommend:
    category: storage
    title: "Nettoyage préventif"
    priority: HIGH
    actions:
      - playbook: storage.preventive.clean
explain: "Saturation probable <5 jours, fenêtre nocturne"
```

### 38.2 Pseudo-code Bandit

```kotlin
class LinUCBAgent(
  private val alpha: Double
) {
  fun select(context: Vector, actions: List<Action>): Action {
    return actions.maxBy { a ->
      val est = a.theta dot context
      val bonus = alpha * sqrt(context.t() * invA(a) * context)
      est + bonus
    }
  }
}
```

### 38.3 Exemple Export NDJSON

```
{"t":"2025-08-16T10:00:00Z","type":"metric","cpu":64.2}
{"t":"2025-08-16T10:00:05Z","type":"recommendation","id":"high.cpu","priority":90}
{"t":"2025-08-16T10:01:00Z","type":"action","action":"clearCache","bytesFreed":1234567}
```

### 38.4 Checklist Release

- [ ] Bump versionName / versionCode
- [ ] Générer SBOM
- [ ] Exécuter tests perf
- [ ] Confirmer drift ML stable
- [ ] Mettre à jour CHANGELOG
- [ ] Signature APK
- [ ] Vérifier docs mise à jour

---

## 39. Statut & Prochaines Étapes

Statut actuel: MVP étendu en cours (PR #2).  
Étapes suivantes proposées:
1. Stabiliser collecte & recos règles (tests + QA)
2. Introduire prévision stockage simple
3. Assistant IA intents basiques
4. Playbooks YAML minimal
5. Conception modèle ML (features baseline + dataset builder)

---

## 40. Résumé Final

Ce document formalise la vision totale et les étapes concrètes pour aller d’un MVP à un écosystème intelligent, automatisé et extensible, tout en respectant sécurité, performance et confidentialité. L’implémentation sera incrémentale; chaque phase doit respecter DoD, tests et objectifs qualité.

---

Fin du document – Version: 1.0 (Vision Complète)