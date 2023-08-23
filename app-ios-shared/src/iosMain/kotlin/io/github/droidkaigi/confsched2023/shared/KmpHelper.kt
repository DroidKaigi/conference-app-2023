package io.github.droidkaigi.confsched2023.shared

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ObjCProtocol
import kotlinx.cinterop.getOriginalKotlinClass

@OptIn(BetaInteropApi::class)
fun KmpEntryPoint.get(objCProtocol: ObjCProtocol): Any {
    val kClazz = getOriginalKotlinClass(objCProtocol)!!
    return get(kClazz)
}