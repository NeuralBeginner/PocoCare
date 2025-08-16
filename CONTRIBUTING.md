# Guide de Contribution

Merci de votre intérêt pour contribuer à PocoCare ! Ce guide vous aidera à comprendre comment participer efficacement au développement du projet.

## 🚀 Comment commencer

1. **Fork** le repository
2. **Clone** votre fork localement
3. **Créez une branche** pour votre contribution
4. **Développez** vos changements
5. **Testez** votre code
6. **Soumettez** une Pull Request

## 📋 Avant de commencer

### Ouvrez une issue
Pour les contributions majeures, ouvrez d'abord une issue pour discuter :
- Nouvelles fonctionnalités
- Changements d'architecture
- Modifications importantes de l'UI

### Types de contributions
- 🐛 **Bug fixes** : Corrections de bugs
- ✨ **Features** : Nouvelles fonctionnalités
- 📚 **Documentation** : Améliorations de la documentation
- 🎨 **Style** : Améliorations de l'interface utilisateur
- ⚡ **Performance** : Optimisations de performance
- 🧪 **Tests** : Ajout ou amélioration des tests

## 🛠️ Configuration de développement

### Prérequis
- JDK 21 ou 17
- Android Studio Hedgehog+
- Git

### Setup local
```bash
git clone https://github.com/VOTRE_USERNAME/PocoCare.git
cd PocoCare
./gradlew build
```

## 📝 Standards de code

### Kotlin
- Suivez les [conventions Kotlin officielles](https://kotlinlang.org/docs/coding-conventions.html)
- Utilisez ktlint pour le formatage automatique
- Longueur maximale de ligne : 120 caractères
- Indentation : 4 espaces

### Architecture
- Suivez l'architecture MVVM
- Utilisez Jetpack Compose pour l'UI
- Implémentez le Repository Pattern
- Séparez la logique métier des composants UI

### Commits
Utilisez le format [Conventional Commits](https://conventionalcommits.org/) :
```
type(scope): description

feat(battery): add battery optimization algorithm
fix(ui): resolve crash on navigation
docs(readme): update installation instructions
```

Types acceptés :
- `feat`: nouvelles fonctionnalités
- `fix`: corrections de bugs
- `docs`: documentation
- `style`: formatage, pas de changement de code
- `refactor`: refactoring de code
- `test`: ajout/modification de tests
- `chore`: tâches de maintenance

## 🧪 Tests

### Exécution des tests
```bash
# Tests unitaires
./gradlew testDebug

# Tests d'interface
./gradlew connectedAndroidTest

# Tests de lint
./gradlew lintDebug
```

### Écriture de tests
- Ajoutez des tests unitaires pour la logique métier
- Utilisez Compose Testing pour les tests UI
- Maintenez une couverture de test raisonnable

## 📤 Pull Requests

### Checklist avant soumission
- [ ] Le code compile sans erreurs
- [ ] Tous les tests passent
- [ ] Le linting passe sans warnings
- [ ] La documentation est mise à jour si nécessaire
- [ ] Les changements suivent les standards du projet

### Format de PR
```markdown
## Description
Brève description des changements

## Type de changement
- [ ] Bug fix
- [ ] New feature
- [ ] Documentation update
- [ ] Performance improvement

## Tests
- [ ] Tests existants passent
- [ ] Nouveaux tests ajoutés
- [ ] Tests manuels effectués

## Screenshots (si applicable)
[Ajoutez des captures d'écran pour les changements UI]
```

### Taille des PRs
- Préférez les **petites PRs** focalisées
- Une PR = une fonctionnalité/correction
- Évitez les PRs avec plus de 400 lignes changées

## 🔍 Processus de review

1. **Soumission** : Créez votre PR avec une description claire
2. **Review automatique** : Les tests CI doivent passer
3. **Review humaine** : Au moins une approbation requise
4. **Merge** : Squash and merge par défaut

### Critères de review
- Fonctionnalité correcte
- Code lisible et maintenable
- Tests appropriés
- Performance acceptable
- Respect des standards

## 🌐 Internationalisation

Pour les contributions UI :
- Utilisez les string resources (pas de texte hardcodé)
- Testez avec différentes langues si possible
- Considérez les layouts RTL

## 🐛 Signalement de bugs

### Template d'issue
```markdown
**Description du bug**
Description claire et concise du problème

**Étapes pour reproduire**
1. Aller à '...'
2. Cliquer sur '...'
3. Voir l'erreur

**Comportement attendu**
Description du comportement attendu

**Captures d'écran**
Si applicable

**Environnement**
- Appareil : [ex. Poco X7 Pro]
- OS : [ex. HyperOS 1.0.1]
- Version app : [ex. 0.1.0]
```

## 💬 Communication

- **Issues** : Discussions techniques, bugs, feature requests
- **Discussions** : Questions générales, aide
- **Reviews** : Feedback constructif et respectueux

## 📄 Licence

En contribuant, vous acceptez que vos contributions soient licenciées sous Apache 2.0.

---

Merci de contribuer à PocoCare ! 🙏