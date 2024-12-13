# Im Not Paying Translation APIs

**Im Not Paying Translation APIs** is a Kotlin-based library that provides a platform-independent way to translate text using drivers that adapt to various platforms such as Android and Desktop. This library is designed for developers who need an easy and flexible solution for translating text without paying for translation APIs.

---

## Features

- **Core Module**: Abstracts the core functionalities, providing reusable components for all platforms.
- **Android Driver**: Implementation for Android applications with seamless integration.
- **Desktop Driver**: Implementation for desktop applications using KCEF.
- Supports dynamic initialization, translation, and resource cleanup.
- Asynchronous and lightweight.

---

## Modules Overview

### 1. Core Module
- Defines the basic `Driver` interface and core models.
- Provides an abstract `Translator` class for platform-agnostic usage.

### 2. Android Driver
- Platform-specific implementation using `WebView` for translation.
- Includes lifecycle management to handle initialization and resource cleanup.

### 3. Desktop Driver
- Uses the Chromium Embedded Framework (via KCEF) for desktop translations.
- Provides seamless translation with support for resource cleanup.

---

## Getting Started

### Installation

Add the following dependencies to your `build.gradle.kts` file:

#### Core Module
```kotlin
implementation("io.github.darkryh.translator:translator-core:$imNotPayingTranslationApisVersion")
```

#### Android Driver
```kotlin
implementation("io.github.darkryh.translator:translator-android:$imNotPayingTranslationApisVersion")
```

#### Desktop Driver
```kotlin
implementation("io.github.darkryh.translator:translator-desktop:$imNotPayingTranslationApisVersion")
```

---

## Usage

### Android Example
```kotlin
val translator = Translator(AndroidDriver(context))
translator.init(Language.Spanish, Language.English)

val translatedText = translator.translate("Hola")
println(translatedText)

translator.release()
```

## Usage

### Desktop Example
```kotlin
runBlocking {
val driver = DesktopDriver()
val translator = Translator(driver)
    
    translator.init(Language.Spanish, Language.English)
    val translatedText = translator.translate("Hola")
    println(translatedText)
    
}
disposableSituation {
    translator.release()
}
```

---

## Development

### Gradle Configuration
The library uses Gradle for dependency management and build automation. Key plugins include:
- Kotlin JVM and Android plugins
- Maven Publishing for OSSRH deployment.

## Contributing

Contributions are welcome! To contribute:
1. Fork the repository.
2. Create a new branch (`git checkout -b feature-name`).
3. Commit your changes (`git commit -m "Add feature"`).
4. Push your branch (`git push origin feature-name`).
5. Open a pull request.

Ensure your contributions follow the project's coding standards and guidelines.

---

## License

This project is licensed under the **Apache License 2.0**. See the [LICENSE](https://www.apache.org/licenses/LICENSE-2.0) file for more details.

---

## Author

- **Xavier Alexander Torres Calderón**
    - Email: [alex_torres-xc@hotmail.com](mailto:alex_torres-xc@hotmail.com)
    - GitHub: [darkryh](https://github.com/darkryh)

---

## Acknowledgements

- Built with [Kotlin](https://kotlinlang.org/).
- Uses [KCEF](https://github.com/datlag/kcef) for desktop translation integration.
- Inspired by the goal of providing accessible, cost-effective translation solutions.

---

Happy Translating! 🎉
