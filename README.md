# Product Browser App

A **Kotlin Multiplatform (KMP)** application that lets users browse, search, and view product details. The app runs on both **Android** and **iOS** from a single shared codebase, using Compose Multiplatform for a unified UI layer.

---

## Business Requirements

| Requirement | Description |
|---|---|
| **Product Listing** | Display a scrollable list of products fetched from a remote API with product image, title, price, and rating. |
| **Product Detail** | Navigate to a detail screen showing full product information — images, title, brand, category, rating, price, and description. |
| **Search** | Search products by keyword via the API with debounced input (500 ms) for efficient network usage. |
| **Category Filtering** | Filter the product list by category using filter chips. |
| **Cross-Platform** | A single shared codebase that compiles and runs natively on both Android and iOS. |
| **Error Handling** | Graceful handling of network errors (timeout, server error, serialization, not found) with a retry mechanism. |

**Data Source:** [DummyJSON Products API](https://dummyjson.com/products)

---

## Project Architecture Overview

The app follows **Clean Architecture** principles with a clear separation into three layers:

```
┌─────────────────────────────────────────────────────────────────┐
│                        Presentation                             │
│  Compose UI  ←→  ViewModel (StateFlow)  ←→  UiState / UiEvent  │
└────────────────────────────┬────────────────────────────────────┘
                             │  Use Cases
┌────────────────────────────▼────────────────────────────────────┐
│                          Domain                                 │
│          Repository Interface  ·  Model  ·  Use Cases           │
└────────────────────────────┬────────────────────────────────────┘
                             │  Repository Impl
┌────────────────────────────▼────────────────────────────────────┐
│                           Data                                  │
│     DTO  ·  Mapper  ·  Remote DataSource  ·  API (Ktor)         │
└─────────────────────────────────────────────────────────────────┘
```

### Module Structure

```
ProductBrowser/
├── androidApp/              # Android entry point (MainActivity)
├── iosApp/                  # iOS entry point (Xcode project)
└── shared/                  # KMP shared module
    └── src/
        ├── commonMain/      # Shared business logic & UI
        │   └── kotlin/com/sandeep/productbrowser/
        │       ├── core/
        │       │   ├── common/       # Constants & string resources
        │       │   ├── di/           # Manual dependency injection (AppModule)
        │       │   ├── dispatcher/   # Coroutine dispatcher abstraction
        │       │   ├── network/      # Ktor HttpClient factory & SafeApiCall
        │       │   └── result/       # ApiResponse & AppError sealed types
        │       ├── data/
        │       │   ├── dto/          # Network data transfer objects
        │       │   ├── mapper/       # DTO → Domain model mappers
        │       │   ├── remote/       # API interface & implementation, data source
        │       │   └── repository/   # Repository implementation
        │       ├── domain/
        │       │   ├── model/        # Domain models (Product)
        │       │   ├── repository/   # Repository interface
        │       │   └── usecase/      # Business use cases
        │       └── presentation/
        │           ├── components/   # Reusable Compose UI components
        │           ├── navigation/   # Navigation graph & Screen routes
        │           ├── product/      # ProductList & ProductDetail screens
        │           ├── productlist/  # ViewModel, UiState, UiEvent
        │           └── productdetail/# ViewModel, UiState, UiEvent
        ├── androidMain/     # Android-specific implementations (Ktor OkHttp engine)
        ├── iosMain/         # iOS-specific implementations (Ktor Darwin engine)
        ├── commonTest/      # Shared unit tests
        ├── androidHostTest/ # Android-specific tests
        └── iosTest/         # iOS-specific tests
```

### Key Technology Choices

| Concern | Technology |
|---|---|
| **Shared UI** | Compose Multiplatform |
| **Networking** | Ktor Client (OkHttp engine on Android, Darwin engine on iOS) |
| **JSON Parsing** | kotlinx.serialization |
| **State Management** | StateFlow + ViewModel |
| **Image Loading** | Kamel (multiplatform image loading) |
| **Navigation** | Jetpack Navigation Compose (multiplatform) |
| **Dependency Injection** | Manual DI via `AppModule` singleton |
| **Testing** | kotlin.test + kotlinx-coroutines-test |

### Data Flow

```
User Action → UiEvent → ViewModel → UseCase → Repository → API (Ktor)
                                                    ↓
UI ← StateFlow ← ViewModel ← ApiResponse ← DTO → Mapper → Domain Model
```

---

## How to Build and Run

### Prerequisites

- **JDK 11** or higher
- **Android Studio** (latest stable) with KMP plugin installed
- **Xcode 15+** (for iOS, macOS only)
- **Kotlin Multiplatform Mobile plugin** for Android Studio
- **CocoaPods** (if applicable) or the Xcode project is pre-configured

### Android

1. **Clone the repository:**
   ```bash
   git clone https://github.com/sandy10/Product-Browser-App.git
   cd Product-Browser-App
   ```

2. **Open in Android Studio:**
   - Open the project root directory in Android Studio.
   - Wait for Gradle sync to complete.

3. **Run the app:**
   - Select the `androidApp` run configuration.
   - Choose an emulator or connected device.
   - Click **Run ▶** or use:
     ```bash
     ./gradlew :androidApp:installDebug
     ```

4. **Run tests:**
   ```bash
   ./gradlew :shared:allTests
   ```

### iOS

1. **Build the shared framework:**
   ```bash
   ./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
   ```

2. **Open the Xcode project:**
   ```bash
   open iosApp/iosApp.xcodeproj
   ```

3. **Run in Xcode:**
   - Select an iOS Simulator target (e.g., iPhone 16).
   - Click **Run ▶** (`Cmd + R`).

> [!NOTE]
> iOS builds require macOS with Xcode installed. The shared framework is compiled as a static framework named `Shared`.

---

## Bonus Features Implemented

- **Bonus 1 (Local Caching)**: The `ProductRepository` caches API responses (products, individual product details, search queries) with a 5-minute Time-To-Live (TTL). On network failures, the app automatically falls back to stale cached data to provide offline resilience.
- **Bonus 2 (iOS Previews)**: Shared UI components like `ProductCard` include `@Preview` annotations (`org.jetbrains.compose.ui.tooling.preview.Preview`), allowing cross-platform UI previews natively in Fleet and Android Studio for both Android and iOS targets.
- **Category Filtering**: Products can be filtered dynamically on the client side using selectable category chips.

---

## Trade-offs and Assumptions

### Trade-offs

| Decision | Trade-off |
|---|---|
| **Manual DI over Koin/Hilt** | Simpler setup with no third-party DI framework, but requires manual wiring in `AppModule`. ViewModels correctly inject `DispatcherProvider` for testability. This is acceptable for the current app size but would benefit from a DI framework as the app scales. |
| **Kotlin `object` for string constants instead of platform `strings.xml` / `Localizable.strings`** | Allows sharing strings across both platforms from `commonMain` without platform-specific resource configuration. The trade-off is losing native localization tooling. |
| **In-Memory Caching** | The app implements in-memory caching with a 5-minute TTL for product lists and searches. On network failure, it gracefully falls back to stale cached data, improving offline resilience without the overhead of a full SQL database like Room/SQLDelight. |
| **Shared ViewModel across navigation** | `ProductListViewModel` and `ProductDetailViewModel` are created once and passed through the navigation graph, rather than being scoped per-screen. |
| **Error Message Mapping** | Error messages shown to the user are correctly mapped from technical `AppError` types to localized, human-readable strings via `toUserMessage()` extension. |

### Assumptions

- The **DummyJSON API** (`https://dummyjson.com/`) is available and stable during usage.
- The `brand` field in the API response can be `null` for some products, so it defaults to an empty string via the mapper.
- **No authentication** is required for the API endpoints used.
- The app targets **Android API 24+** and **iOS (arm64 / Simulator arm64)**.
- A **search debounce of 500ms** provides a reasonable balance between responsiveness and reducing unnecessary API calls.
- The product list fetches **all products** (`limit=0`) at once and relies on client-side category filtering, which is acceptable given the small dataset size (~194 products).