(ns concurrency-playground.delay)

(def coffee (delay (do
                     (println "Brewing a new pot.")
                     (Thread/sleep 5000)
                     :coffee)))

(comment
 (let [coffee (delay (do
                       (println "Brewing a new pot.")
                       (Thread/sleep 5000)
                       :coffee))]
   (dotimes [x 5]
     (.start (Thread. (fn []
                        (Thread/sleep (rand-int 10000))
                        (println x "I'm here!")
                        (println x "I'm grabbing coffee!")

                        (println @coffee)

                        (println x "Sip.")
                        ))))))
