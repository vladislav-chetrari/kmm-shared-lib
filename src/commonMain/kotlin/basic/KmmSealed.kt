package basic

sealed class KmmSealed {
    object Obj : KmmSealed()
    class Clazz(val someStr: String) : KmmSealed()
    data class DataClazz(val someStr: String, val someInt: Int) : KmmSealed()
}