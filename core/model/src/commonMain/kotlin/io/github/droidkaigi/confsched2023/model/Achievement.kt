package io.github.droidkaigi.confsched2023.model

public enum class Achievement(public val id: String, public val sha256: String) {
    ArcticFox(
        id = "Arctic Fox",
        sha256 = "8370e6e6326c36f24b22f92a47ded5c3f1897aad46dfd9527016d2f3034d5f6e",
    ),
    Bumblebee(
        id = "Bumblebee",
        sha256 = "91b87cf77270a8df73d759282741a7e7a16c53c836c399006a82fb9f5d06025c",
    ),
    Chipmunk(
        id = "Chipmunk",
        sha256 = "7fd60aa90d14695e466a2ff181e883b1e77c4d8c88a3c4bd64025a47779edd1a",
    ),
    Dolphin(
        id = "Dolphin",
        sha256 = "cf27745b0afa6d34e6b45fa1940ddef63982f5e01c71c093b871f0d6ffe6f14d",
    ),
    ElectricEel(
        id = "Electric Eel",
        sha256 = "4e52d5a2f091279b365576319ee18384b3340e61f6c97a549ad8a7940c51d0bb",
    ),
    ;
    public companion object {
        public fun ofOrNull(id: String): Achievement? {
            return entries.find { it.id == id }
        }
    }
}
