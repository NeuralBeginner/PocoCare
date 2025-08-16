# PocoCare

**Outil de diagnostic, nettoyage et optimisation pour HyperOS (Poco X7 Pro)**

PocoCare est une application Android native conçue spécifiquement pour optimiser les performances des appareils Poco X7 Pro sous HyperOS. L'application fournit des outils de diagnostic système, de nettoyage de stockage, d'optimisation de batterie et d'audit des permissions.

## 🎯 Objectifs MVP

- **Diagnostic système** : Analyse complète des performances et de l'état du système
- **Nettoyage de stockage** : Suppression intelligente des fichiers temporaires et caches
- **Optimisation batterie** : Gestion avancée de l'alimentation et des processus en arrière-plan
- **Audit des permissions** : Surveillance et gestion des permissions d'applications
- **Interface utilisateur moderne** : Design Material 3 avec Jetpack Compose

## 🏗️ Architecture

L'application suit une architecture modulaire pour faciliter la maintenance et les tests :

```
app/
├── metrics/          # Module de métriques système
├── storage/          # Module de nettoyage de stockage  
├── battery/          # Module d'optimisation batterie
├── permissions/      # Module d'audit des permissions
└── ui/              # Composants UI partagés
```

## 🛠️ Stack Technique

- **Langage** : Kotlin 100%
- **UI Framework** : Jetpack Compose + Material 3
- **Architecture** : MVVM + Repository Pattern
- **Injection de dépendances** : Hilt
- **Base de données** : Room
- **Programmation asynchrone** : Coroutines + Flow
- **Tests** : JUnit 4/5 + Espresso + Compose Testing

## 🚀 Roadmap

### MVP (v0.1) - Q1 2024
- [x] Structure projet Android
- [ ] Interface utilisateur de base
- [ ] Module de métriques système
- [ ] Fonctionnalités de nettoyage basiques

### v0.2 - Q2 2024
- [ ] Optimisation batterie avancée
- [ ] Audit des permissions
- [ ] Notifications intelligentes
- [ ] Thèmes personnalisables

### v0.3 - Q3 2024
- [ ] Widgets homescreen
- [ ] Automatisation des tâches
- [ ] Rapports détaillés
- [ ] Mode développeur

## 🔧 Instructions de Build

### Prérequis
- **JDK** : 21 (recommandé) ou 17 minimum
- **Android Studio** : Hedgehog (2023.1.1) ou plus récent
- **Android Gradle Plugin** : 8.2+
- **Kotlin** : 1.9.22+

### Build Local
```bash
# Cloner le repository
git clone https://github.com/NeuralBeginner/PocoCare.git
cd PocoCare

# Build debug
./gradlew assembleDebug

# Lancer les tests
./gradlew testDebug

# Installer sur un appareil connecté
./gradlew installDebug
```

### Configuration Android Studio
1. Ouvrir le projet dans Android Studio
2. Synchroniser Gradle (automatique)
3. Configurer un émulateur ou connecter un appareil physique
4. Lancer avec `Run > Run 'app'`

## 🤝 Contributions

Nous accueillons les contributions de la communauté ! Avant de soumettre une PR majeure :

1. **Ouvrez une issue** pour discuter des changements proposés
2. **Suivez les conventions** de code Kotlin et Android
3. **Ajoutez des tests** pour les nouvelles fonctionnalités
4. **Mettez à jour la documentation** si nécessaire

Consultez [CONTRIBUTING.md](CONTRIBUTING.md) pour les guidelines détaillées.

## 📄 Licence

Ce projet est sous licence Apache 2.0 - voir le fichier [LICENSE](LICENSE) pour plus de détails.

## 🔒 Sécurité

Pour signaler des vulnérabilités de sécurité, consultez [SECURITY.md](SECURITY.md).

## 📞 Support

- **Issues** : [GitHub Issues](https://github.com/NeuralBeginner/PocoCare/issues)
- **Discussions** : [GitHub Discussions](https://github.com/NeuralBeginner/PocoCare/discussions)

---

*Développé avec ❤️ pour la communauté Poco X7 Pro*
