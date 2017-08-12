(ns concurrency-playground.agent)

(def sum (agent 0))

(def numbers [1 2 4 6 33 4 5 6 7 77 54 3 2])

(doseq [x numbers]
  (send sum + x))

(await sum)

(println @sum)

(def sums (map agent (repeat 10 0)))
(def numbers (range 1000000))

(do
  (println "About to send.")
  (doseq [[x agent] (map vector numbers (cycle sums))]
    (send agent + x))

  (println "Finished sending.")
  (apply await-for 10000 sums)
  (println "Waited.")
  (println "Answer:" (apply + (map deref sums))))

(def sums (map agent (repeat 10 0)))
(def numbers (agent (range 1000000)))

(defn dequeue-and-add [sum-agent]
  (letfn [(add [current-sum x]
            (let [new-sum (+ current-sum x)]

              (send numbers dequeue)

              new-sum))
          (dequeue [xs]
            (when (not (empty? xs))
              (send sum-agent add (first xs))
              (rest xs)))]
    (send numbers dequeue)))

(do
  (doseq [sum-agent sums]
    (dequeue-and-add sum-agent))

  (loop []
    (when (not (empty? @numbers))
      (Thread/sleep 1000)
      (recur)))

  (println (apply + (map deref sums))))

(set-error-handler! (first sums) (fn [agent exception]
                                   ))
