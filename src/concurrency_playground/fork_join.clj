(ns concurrency-playground.fork-join
  (:import [java.util.concurrent
            RecursiveTask
            ForkJoinPool]))

(defonce pool (ForkJoinPool.))

(defn summation [numbers]
  (proxy [RecursiveTask] []
    (compute []
      (if (<= (count numbers) 512)
        (reduce + 0 numbers)
        (let [half (quot (count numbers) 2)
              f1 (summation (subvec numbers 0 half))
              f2 (summation (subvec numbers half))]
          (.fork f2)
          (let [a1 (.compute f1)]
            (+ a1 (.join f2))))))))

(defn sum [numbers]
  (.invoke pool (summation (vec numbers))))

(sum (range 1000000))
