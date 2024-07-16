(ns gilded.core)

(def fixture
  [{:name "+5 Dexterity Vest", :quality 20, :sell-in 10}
   {:name "Aged Brie", :quality 0, :sell-in 2}
   {:name "Elixir of the Mongoose", :quality 7, :sell-in 5}
   {:name "Sulfuras, Hand of Ragnaros", :quality 80, :sell-in 0}
   {:name "Sulfuras, Hand of Ragnaros", :quality 80, :sell-in -1}
   {:name "Backstage passes to a TAFKAL80ETC concert", :quality 20, :sell-in 15}
   {:name "Backstage passes to a TAFKAL80ETC concert", :quality 49, :sell-in 10}
   {:name "Backstage passes to a TAFKAL80ETC concert", :quality 49, :sell-in 5}
   {:name "Conjured Mana Cake", :quality 6, :sell-in 3}])

(defn make-store [items]
  (assert (vector? items))
  (->> items
       (map (fn [item] (atom item)))
       vec))

(defn item-seq [store]
  (->> store
       (map deref)))
;; ---

(defn update-quality! [store]
  (doseq [item store]
    (if (and (not (= (:name @item)
                     "Aged Brie"))
             (not (= (:name @item)
                     "Backstage passes to a TAFKAL80ETC concert")))

      (when (> (:quality @item) 0)
        (when (not (= (:name @item) "Sulfuras, Hand of Ragnaros"))
          (swap! item update :quality #(- % 1))))

      (when (< (:quality @item) 50)
        (swap! item update :quality #(+ % 1))
        (when (= (:name @item) "Backstage passes to a TAFKAL80ETC concert")
          (when (< (:sell-in @item) 11)
            (when (< (:quality @item) 50)
              (swap! item update :quality #(+ % 1))))
          (when (< (:sell-in @item) 6)
            (when (< (:quality @item) 50)
              (swap! item update :quality #(+ % 1)))))))

    (when (not (= (:name @item) "Sulfuras, Hand of Ragnaros"))
      (swap! item update :sell-in #(- % 1)))

    (when (< (:sell-in @item) 0)
      (if (not (= (:name @item) "Aged Brie"))
        (if (not (= (:name @item) "Backstage passes to a TAFKAL80ETC concert"))
          (when (> (:quality @item) 0)
            (when (not (= (:name @item) "Sulfuras, Hand of Ragnaros"))
              (swap! item update :quality #(- % 1))))
          (swap! item update :quality #(- % %)))
        (when (< (:quality @item) 50)
          (swap! item update :quality #(+ % 1)))))))
