(ns concurrency-playground.deref)

deref ;; dereference

(def the-future (future (+ 3 4)))

(deref the-future);; => 7
@the-future;; => 7

(read-string "the-future")
;; => the-future

(read-string "(deref the-future)")
;; => (deref the-future)

(read-string "@the-future")
;; => (clojure.core/deref the-future)

(def the-promise (promise))

(deref the-promise 1000 :timeout)
