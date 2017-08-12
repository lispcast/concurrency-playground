(ns concurrency-playground.thread)


(def signal (atom true))

(def p (promise))

(def thread (java.lang.Thread. (fn []
                                 (while @signal
                                   (println 1 2 3)
                                   (Thread/sleep 1000))
                                 (deliver p 10))))

(.start thread)

(comment
  (System/exit 0))

