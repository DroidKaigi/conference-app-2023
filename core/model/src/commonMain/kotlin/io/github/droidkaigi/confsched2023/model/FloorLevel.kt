package io.github.droidkaigi.confsched2023.model

public enum class FloorLevel(
    public val floorName: String,
) {
    Basement("B1F"),
    Ground("1F"),
    ;

    public companion object
}
