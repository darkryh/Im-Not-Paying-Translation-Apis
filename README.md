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

## Supported Languages

**Im Not Paying Translation APIs** supports a wide range of languages for translation. Below is the complete list of supported languages:

| Language     | Code  | Language             | Code    | Language              | Code    |
|--------------|-------|----------------------|---------|-----------------------|---------|
| Afrikaans    | `af`  | Albanian             | `sq`    | Amharic               | `am`    |
| Arabic       | `ar`  | Armenian             | `hy`    | Azerbaijani           | `az`    |
| Basque       | `eu`  | Belarusian           | `be`    | Bengali               | `bn`    |
| Bosnian      | `bs`  | Bulgarian            | `bg`    | Catalan               | `ca`    |
| Cebuano      | `ceb` | Chinese (Simplified) | `zh-CN` | Chinese (Traditional) | `zh-TW` |
| Corsican     | `co`  | Croatian             | `hr`    | Czech                 | `cs`    |
| Danish       | `da`  | Dutch                | `nl`    | English               | `en`    |
| Esperanto    | `eo`  | Estonian             | `et`    | Filipino              | `tl`    |
| Finnish      | `fi`  | French               | `fr`    | Frisian               | `fy`    |
| Galician     | `gl`  | Georgian             | `ka`    | German                | `de`    |
| Greek        | `el`  | Gujarati             | `gu`    | Haitian Creole        | `ht`    |
| Hausa        | `ha`  | Hawaiian             | `haw`   | Hebrew                | `iw`    |
| Hindi        | `hi`  | Hmong                | `hmn`   | Hungarian             | `hu`    |
| Icelandic    | `is`  | Igbo                 | `ig`    | Indonesian            | `id`    |
| Irish        | `ga`  | Italian              | `it`    | Japanese              | `ja`    |
| Javanese     | `jw`  | Kannada              | `kn`    | Kazakh                | `kk`    |
| Khmer        | `km`  | Korean               | `ko`    | Kurdish               | `ku`    |
| Kyrgyz       | `ky`  | Lao                  | `lo`    | Latin                 | `la`    |
| Latvian      | `lv`  | Lithuanian           | `lt`    | Luxembourgish         | `lb`    |
| Macedonian   | `mk`  | Malagasy             | `mg`    | Malay                 | `ms`    |
| Malayalam    | `ml`  | Maltese              | `mt`    | Maori                 | `mi`    |
| Marathi      | `mr`  | Mongolian            | `mn`    | Myanmar               | `my`    |
| Nepali       | `ne`  | Norwegian            | `no`    | Nyanja                | `ny`    |
| Odia         | `or`  | Pashto               | `ps`    | Persian               | `fa`    |
| Polish       | `pl`  | Portuguese           | `pt`    | Punjabi               | `pa`    |
| Romanian     | `ro`  | Russian              | `ru`    | Samoan                | `sm`    |
| Scots Gaelic | `gd`  | Serbian              | `sr`    | Sesotho               | `st`    |
| Shona        | `sn`  | Sindhi               | `sd`    | Sinhala               | `si`    |
| Slovak       | `sk`  | Slovenian            | `sl`    | Somali                | `so`    |
| Spanish      | `es`  | Sundanese            | `su`    | Swahili               | `sw`    |
| Swedish      | `sv`  | Tajik                | `tg`    | Tamil                 | `ta`    |
| Tatar        | `tt`  | Telugu               | `te`    | Thai                  | `th`    |
| Turkish      | `tr`  | Turkmen              | `tk`    | Ukrainian             | `uk`    |
| Urdu         | `ur`  | Uyghur               | `ug`    | Uzbek                 | `uz`    |
| Vietnamese   | `vi`  | Welsh                | `cy`    | Xhosa                 | `xh`    |
| Yiddish      | `yi`  | Yoruba               | `yo`    | Zulu                  | `zu`    |

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
runBlocking {
    val translator = Translator(AndroidDriver(context))
    translator.init(Language.English, Language.Japanese)

    val translatedText: String = translator.translate("Hi this is expected to be Japanese")
    println(translatedText)

    disposableSituation {
      translator.release()
    }
}
```

## Usage

### Desktop Example
[Compose Example](https://github.com/darkryh/Im-Not-Paying-Translation-Apis/blob/master/desktopApp/src/main/java/com/ead/lib/imnotpayingtranslationapis/desktopapp/Main.kt)
```kotlin
runBlocking {
    val driver = DesktopDriver()
    val translator = Translator(driver)

    translator.init(Language.English, Language.Spanish)
    val translatedText: String = translator.translate("Hi this is expected to be Spanish")
    println(translatedText)

    disposableSituation {
        translator.release()
    }
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
