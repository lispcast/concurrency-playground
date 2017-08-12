(ns concurrency-playground.reducers
  (:require [clojure.core.reducers :as r]))

(def numbers (vec (range 1000000)))

(reduce + 0 numbers)
;; => 499999500000

(r/fold + numbers)
;; => 499999500000

(r/fold + (r/filter even? numbers));; => 249999500000
