import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    // KMM - Koin Call
    // Kotlin methods called in Swift/ObjectC starts with do prefix.
    init() {
        HelperKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}