(ns concurrency-playground.cache)

(defonce cache (atom {}))

(defn save! [key value]
  (swap! cache assoc key [value (System/currentTimeMillis)])
  nil)

(defn fetch [key]
  (first (get @cache key)))

(defn age [key]
  (let [[_ t] (get @cache key [nil 0])]
    (- (System/currentTimeMillis) t)))

(defn clear! []
  (reset! cache {}))
