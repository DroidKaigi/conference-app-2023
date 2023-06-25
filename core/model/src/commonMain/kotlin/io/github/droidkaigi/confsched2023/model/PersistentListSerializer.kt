package io.github.droidkaigi.confsched2023.model

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = PersistentList::class)
public class PersistentListSerializer(
    private val dataSerializer: KSerializer<String>,
) :
    KSerializer<PersistentList<String>> {
    public class PersistentListDescriptor : SerialDescriptor by serialDescriptor<List<String>>() {
        @ExperimentalSerializationApi override val serialName: String =
            "kotlinx.serialization.immutable.persistentList"
    }

    override val descriptor: PersistentListDescriptor = PersistentListDescriptor()
    override fun serialize(encoder: Encoder, value: PersistentList<String>) {
        return ListSerializer(dataSerializer).serialize(encoder, value.toList())
    }

    override fun deserialize(decoder: Decoder): PersistentList<String> {
        return ListSerializer(dataSerializer).deserialize(decoder).toPersistentList()
    }
}

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = PersistentSet::class)
public class PersistentSetSerializer(
    private val dataSerializer: KSerializer<String>,
) :
    KSerializer<PersistentSet<String>> {
    public class PersistentSetDescriptor : SerialDescriptor by serialDescriptor<List<String>>() {
        @ExperimentalSerializationApi override val serialName: String =
            "kotlinx.serialization.immutable.persistentList"
    }

    override val descriptor: PersistentSetDescriptor = PersistentSetDescriptor()
    override fun serialize(encoder: Encoder, value: PersistentSet<String>) {
        return ListSerializer(dataSerializer).serialize(encoder, value.toList())
    }

    override fun deserialize(decoder: Decoder): PersistentSet<String> {
        return ListSerializer(dataSerializer).deserialize(decoder).toPersistentSet()
    }
}

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = PersistentMap::class)
public class PersistentMapSerializer(
    private val data1Serializer: KSerializer<String>,
    private val data2Serializer: KSerializer<String>,
) :
    KSerializer<PersistentMap<String, String>> {
    public class PersistentMapDescriptor :
        SerialDescriptor by serialDescriptor<Map<String, String>>() {
        @ExperimentalSerializationApi override val serialName: String =
            "kotlinx.serialization.immutable.persistentList"
    }

    override val descriptor: PersistentMapDescriptor = PersistentMapDescriptor()
    override fun serialize(encoder: Encoder, value: PersistentMap<String, String>) {
        return MapSerializer(data1Serializer, data2Serializer).serialize(encoder, value.toMap())
    }

    override fun deserialize(decoder: Decoder): PersistentMap<String, String> {
        return MapSerializer(data1Serializer, data2Serializer).deserialize(decoder)
            .toPersistentMap()
    }
}
