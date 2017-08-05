(ns concurrency-playground.ref)

(def sum   (atom 0))
(def count (atom 0))

(defn current-average []
  (/ @sum @count))

;; alternative

(def average (atom [0 0]))

(defn add-to-average [num]
  (swap! average (fn [[n d]] [(+ n num) (+ d 1)])))

;; refs

(def sum   (ref 0))
(def count (ref 0))

(defn add-to-average [num]
  (dosync
    (alter sum + num)
    (alter count + 1))
  nil)

(defn current-average []
  (dosync (/ @sum @count)))

;; thermometer

(def f (ref 0))
(def c (ref 0))

(defn c->f [c]
  (+ 32 (* c 1.8)))

(defn update-f []
  (dosync
    (let [c @c]
      (ref-set f (c->f c)))))

(defn update-temp [temp]
  (dosync
    (ref-set c temp)
    (update-f)))
