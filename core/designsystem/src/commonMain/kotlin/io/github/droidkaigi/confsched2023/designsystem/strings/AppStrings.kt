package io.github.droidkaigi.confsched2023.designsystem.strings

sealed class AppStrings : Strings<AppStrings>(Bindings) {
    object Retry : AppStrings()

    private object Bindings : StringsBindings<AppStrings>(
        Lang.Japanese to { item, _ ->
            when (item) {
                Retry -> "リトライ"
            }
        },
        Lang.English to { item, bindings ->
            when (item) {
                Retry -> "Retry"
            }
        },
        default = Lang.Japanese
    )
}
