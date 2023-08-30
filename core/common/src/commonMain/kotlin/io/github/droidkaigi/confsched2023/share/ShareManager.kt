package io.github.droidkaigi.confsched2023.share

/**
 * Platform sharing feature wrapper
 */
interface ShareManager {

    /**
     * Call platform sharing feature
     *
     * @param text share text
     */
    fun share(text: String)
}
