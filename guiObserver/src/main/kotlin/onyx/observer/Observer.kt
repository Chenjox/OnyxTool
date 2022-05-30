package onyx.observer

public object Observer {

    private var _ob : StatusObserver = Mockup()
    public val ob : StatusObserver
    get() { return _ob }

    public fun setObserver(observer: StatusObserver){
        _ob = observer
    }

}

private class Mockup() : StatusObserver {
    override fun beginSection(name: String, msg: String?) {

    }

    override fun endSection(name: String, msg: String?) {

    }

    override fun finishProgress(name: String) {

    }

    override fun report(msg: String) {

    }

    override fun startProgress(name: String, msg: String, amount: Int) {

    }

    override fun updateProgress(name: String, amount: Int) {

    }

}