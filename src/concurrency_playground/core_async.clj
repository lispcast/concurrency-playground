(ns concurrency-playground.core-async
  (:require [clojure.core.async :as async]))

(def number-chan (async/chan 100))

(def sum (atom 0))

(dotimes [_ 100]
  (async/go
    (loop []
      (let [number (async/<! number-chan)]
        (swap! sum + number))
      (recur))))

(async/go
  (doseq [x (range 1000000)]
    (async/>! number-chan x)))
