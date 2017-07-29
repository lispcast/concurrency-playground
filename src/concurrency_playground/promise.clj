(ns concurrency-playground.promise)

(def whiteboard (promise))

(doto (Thread. (fn []
                 (Thread/sleep 5000)
                 (deliver whiteboard 400)))
  .start)

(while (not (realized? whiteboard))
  (println "Doing some work.")
  (Thread/sleep 200))

(println "We have" (deref whiteboard) "books.")



