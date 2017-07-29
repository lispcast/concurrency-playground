(ns concurrency-playground.future)

(def book-count (future (do
                          (Thread/sleep 5000)
                          42)))

(println "Doing some work. Cleaning the garage.")

(println "Done.")

(println "We have" @book-count " books.")
