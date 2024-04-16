(ns gilded.main-test
  (:require [clojure.java.shell :as sh]
            [clojure.string :as str]
            [clojure.test :refer [deftest is testing]]
            [gilded.main :as sut]))

(defn unmodified?
  [file]
  (str/blank? (:out (apply sh/sh
                      (into (str/split "git status --porcelain --" #" ")
                            [file])))))

(deftest thirty-days-test
  (testing "30 days"
    (spit "test/gilded/30-days.txt" (with-out-str (sut/-main "30")))
    (is (unmodified? "test/gilded/30-days.txt"))))
