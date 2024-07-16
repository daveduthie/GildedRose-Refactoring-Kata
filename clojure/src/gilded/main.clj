(ns gilded.main
  (:require [gilded.core :as x]))

(defn item->str [item]
  (str (:name item) ", " (:sell-in item) ", " (:quality item)))

(defn print-store [store]
  (println "name, sellIn, quality")
  (doseq [item (x/item-seq store)]
    (println (item->str item)))
  (println))

(defn -main [& args]
  (let [n-days (if (nil? (first args))
                 2
                 (Long/parseLong (first args)))
        store (x/make-store x/fixture)]
    (reduce (fn [store day]
              (println "-------- day" day "--------")
              (print-store store)
              (x/update-quality! store))
            store (range (inc n-days)))))
