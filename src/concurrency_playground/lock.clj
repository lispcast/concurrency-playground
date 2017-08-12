(ns concurrency-playground.lock)

(defonce log-lock (Object.))

(defn log [& args]
  (locking log-lock
    (doseq [ch (apply str args)]
      (print ch))
    (println "")))

(do
  (let [t1 (Thread. (fn []
                      (dotimes [x 100]
                        (log "Thread 1 xxxxxxxxxxxxxxxxxxxxxxxxxx"))))
        t2 (Thread. (fn []
                      (dotimes [x 100]
                        (log "Thread 2 yyyyyyyyyyyyyyyyyyyyyyyyyy"))))]
    (.start t1)
    (.start t2)))
