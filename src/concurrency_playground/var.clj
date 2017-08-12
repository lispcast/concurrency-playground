(ns concurrency-playground.var
  (:require [clj-http.client :as http]
            [clojure.test :refer :all]))

;; dynamic development

(def x 4)

(defonce users (http/get "http://users.com/users"))
(defonce state (atom {}))

(def fib (memoize
           (fn [n]
             (cond
               (zero? n)
               1

               (= 1 n)
               1

               :else
               (+ (fib (dec n))
                 (fib (- n 2)))))))

(defn fib [n]
  (if (zero? n)
    1
    (+ (fib (dec n))
      (fib (- n 2)))))

(set-handler :fib #'fib)

;; dynamic scope

(def ^:dynamic *db*)
(def ^:dynamic *context*)

(defn normalize-user [])

(defn store-user [f fjd f]
  (normalize-user))

(defn user-view []
  (println *context*))

(defn new-session []
  (gensym "session"))

(defn handler [req]
  (binding [*db* "postgres:jfjdj"
            *context* {:current-session (new-session)
                       :request req}]

    (println *db*)
    (user-view)

    ))

;; testing

(defn fetch-all-users []
  (println "Hello!")
  (http/get "http://users.com/users"))

(deftest users-not-zero
  (with-redefs [fetch-all-users (fn [] [{:user-id 1}])]
    (let [users (fetch-all-users)]
      (is (pos? (count users))))))
