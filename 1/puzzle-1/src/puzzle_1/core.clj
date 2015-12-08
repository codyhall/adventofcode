(ns puzzle-1.core)


(def data (slurp "/Users/Hallca/adventofcode/1/data.txt"))

(def answer-one (reduce + data))

(def answer-two (+ 1  (.indexOf (into [] (reductions + data)) -1)))
