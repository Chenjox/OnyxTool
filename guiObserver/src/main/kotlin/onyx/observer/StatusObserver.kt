package onyx.observer

public interface StatusObserver {

    /**
     * Begins a Section with the specified name
     */
    public fun beginSection(name: String, msg: String? = null)

    /**
     * End the Section with the specified name and all of its child sections
     */
    public fun endSection(name: String, msg: String? = null)

    /**
     * Reports a msg inside the current section
     */
    public fun report(msg: String)

    /**
     * Starts a progress bar inside the current section with the specified name
     */
    public fun startProgress(name: String, msg: String, amount: Int = 100)

    /**
     * Updates a progress bar inside the current section with the specified name by the amount
     */
    public fun updateProgress(name: String, amount: Int = 0)

    /**
     * Finishes a progressbar
     */
    public fun finishProgress(name: String)

}