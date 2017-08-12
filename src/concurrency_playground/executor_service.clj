(ns concurrency-playground.executor-service
  (:import [java.util.concurrent ExecutorService Executors]))

(defonce service (Executors/newFixedThreadPool 4))

(def numbers (range 1000000))

(def groups (partition (/ (count numbers) 4) numbers))

(def sums (map (fn [ns]
                 (.submit ^ExecutorService service
                   ^Callable (fn []
                               (reduce + 0 ns))))
            groups))

(apply + (map deref sums))

