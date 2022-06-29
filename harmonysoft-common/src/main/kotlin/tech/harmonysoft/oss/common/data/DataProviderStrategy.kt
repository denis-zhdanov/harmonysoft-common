package tech.harmonysoft.oss.common.data

/**
 * There are use-cases when processing logic can be defined via common interface for accessing particular data.
 * For example, consider a state which is essentially a bag of key-value pairs. Underlying implementation
 * might be a [Map], array or any other data structure. All of the can be decorated by this interface
 *
 * @param DATA_HOLDER    data holder type
 * @param KEY            data key type
 */
fun interface DataProviderStrategy<DATA_HOLDER, KEY> {

    fun getData(dataHolder: DATA_HOLDER, key: KEY): Any?
}