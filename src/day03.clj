(ns day03
  (:require
   [clojure.string :as str]))

(defn read-input [] (slurp "../resources/day03.txt"))

(defn parse-muls [input]
  (->> input
       (re-seq #"mul\((\d+),(\d+)\)")
       (map (fn [[_ a b]] (vector (parse-long a) (parse-long b))))))

(defn part1 [input]
  (->> input
       (parse-muls)
       (map #(apply * %))
       (apply +)))

(defn part2 [input]
  (->> input
    ; add the implicit do at the start
    (str "do()")
    ; split around the don't first
    (#(str/split % #"don't\(\)"))
    ; then split at the first do() and ignore before it
    (map #(str/split % #"do\(\)" 2))
    ; convert to pairs of longs and flatten
    (reduce
      (fn [acc v]
         (let [s (nth v 1 nil)]
            (if (some? s) (concat (parse-muls s) acc) acc)))
      '()
    )
    (map (partial apply *))
    (apply +)
  ))

(defn solve []
  (let [input (read-input)]
    (println "part1:" (part1 input))
    (println "part2:" (part2 input))))

(solve)
