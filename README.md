# Luxury Home Listing

App that fetches a list of luxury homes from a mock API, stores them in a Room database, and displays them in a Jetpack Compose-based UI. The app should also include a "Favorite" feature, allowing users to mark a home as a favorite, which updates the local database.

## App Assembly

The application is built and assembled using the following configuration:

- **Android Studio Version**: Koala | 2024.1.1 Patch 1  
  Build #AI-241.18034.62.2411.12071903  
- **Gradle Version**: 8.7  
  Distribution URL: `https://services.gradle.org/distributions/gradle-8.7-bin.zip`

Ensure that your development environment matches these specifications for smooth setup and assembly of the application.


## Features

- **Property Listings**: Display a list of luxury properties with images and descriptions.
- **Favorites**: Save preferred properties.

## Technologies Used

- **Kotlin**.
- **MVVM Architecture**: Ensures clean separation of concerns and maintainability.
- **Dagger/Hilt**: Dependency injection for streamlined and modular development.
- **Retrofit**: HTTP client for API communication.
- **Room Database**: Local caching for offline functionality.
- **Flow and ViewModel**: Reactive and lifecycle-aware UI updates.

## Class Overview: `HomeMediator`

The `HomeMediator` class is a custom implementation of `RemoteMediator` used to handle paging data from a remote source and caching it in a local database. It integrates seamlessly with the Android Paging library and Room database for efficient data loading and management.

### Key Responsibilities

1. **Data Loading**: Fetches paginated home data from the remote source using `GetHomesUseCase`.
2. **Local Caching**: Caches the fetched data into a Room database (`HomeDatabase`) for offline access.
3. **Pagination Handling**:
   - Refresh: Clears existing data and fetches the latest homes.
   - Prepend: Stops further data loading at the beginning of the list.
   - Append: Fetches the next page of data based on the last item's key.
4. **Favorite Preservation**: Ensures that the "favorite" status of homes is preserved during data refresh.

This class enables smooth, efficient, and robust data fetching and display for the luxury home listings.

## Class Overview: `HomesMockInterceptor`

The `HomesMockInterceptor` class is a custom `Interceptor` used to simulate responses for the `/homes` endpoint. It is particularly useful for testing and debugging the app without relying on a live backend.

### Key Responsibilities

1. **Mock Response Generation**:
   - Simulates a list of homes (`HomeDto`) with attributes like name, price, image URL, and location.
   - Generates mock data dynamically using predefined lists and random values.
   
2. **Error Simulation**:
   - Provides an option to simulate server errors based on the application settings (`AppSettings.isShowError`).
   - Returns a 500 error with a custom error message for testing error-handling scenarios.

3. **Endpoint Validation**:
   - Ensures only the `/homes` endpoint is intercepted. Throws an exception for unknown endpoints.

4. **Customizable Pagination**:
   - Parses `start` and `pageSize` query parameters from the request URL to create paginated mock data.

### Implementation Highlights

- **Dynamic Data Generation**:
  - Home details (e.g., names, prices, and locations) are dynamically generated based on predefined lists.
  - Prices are calculated using random values and rounding.

- **Integration with Moshi**:
  - Uses Moshi for JSON serialization to format the list of `HomeDto` objects into a JSON response.

- **Simulated Errors**:
  - Returns a 500 HTTP response with an error message when error simulation is enabled.
  - Returns a 200 HTTP response with a mock JSON body when errors are disabled.

This class provides a powerful mechanism for offline testing, mock data visualization, and simulating real-world API behaviors in the luxury home listings app.

## Class Overview: `HomeViewModel`

The `HomeViewModel` class serves as the ViewModel for the home screen of the application, managing UI state and handling business logic related to displaying and interacting with a list of homes.

### Key Responsibilities

1. **Data Flow Management**:
   - Retrieves a paginated flow of `HomeEntity` objects using the Paging library.
   - Maps `HomeEntity` to UI-specific data (`HomeItemData`) using domain and UI mappers.

2. **User Interaction**:
   - Toggles the "favorite" status of a home using `ToggleFavoriteHomeUseCase`.
   - Toggles the simulated error state using `AppSettings.toggleShowError()`.
   - Clears the list of homes using `ClearHomeListUseCase`.

3. **Error State Management**:
   - Observes the `isShowError` state from `AppSettings` to dynamically update the UI.

4. **Lifecycle Awareness**:
   - Caches paginated data using `cachedIn(viewModelScope)` for efficiency and lifecycle awareness.

### Implementation Highlights

- **Paging Integration**:
  - Leverages the Paging 3 library to efficiently load and manage large datasets.
  - Maps paginated data to UI-specific models for presentation.

- **Reactive State Management**:
  - Uses Kotlin `Flow` and `stateIn` to expose reactive streams of data and UI state.
  - Ensures immediate updates to the UI when the error state or favorite status changes.

- **Scoped Launch**:
  - Uses `viewModelScope` to safely handle asynchronous operations within the ViewModel's lifecycle.

This class acts as the bridge between the data layer and the UI, ensuring efficient data handling, responsiveness, and a clean separation of concerns in the luxury home listings app.

