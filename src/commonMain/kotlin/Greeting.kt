class Greeting {
    private val platform = Platform()

    fun greet() = "Hello ${platform.name}"
}