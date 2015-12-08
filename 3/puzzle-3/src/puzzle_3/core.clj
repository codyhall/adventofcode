(ns puzzle-3.core)

;; let's create a map containing the changes in directions as simple [x y] vectors that we can add.


(def movements {\v [-1 0] \^ [1 0] \< [0 -1] \> [0 1]})

(def data
  (remove (fn [x] (= \newline x))
          (slurp "/Users/Hallca/adventofcode/3/data.txt")))

(defn instruction-to-movement [x]
  (get movements x))

(defn move [[x0 y0] [x y]]
  [(+ x0 x) (+ y0 y)]
  )

;; distinct places Mr.Clause will go.

(let [directions (map instruction-to-movement data)]
  (count (into #{} (concat (reductions move [0 0] directions)))))


;; Now with robo-santa

(let [santa (map instruction-to-movement (take-nth 2 data))
      robo (map instruction-to-movement (take-nth 2 (rest data)))]
  (count (into #{}
               (concat
                (reductions move [0 0] santa)
                (reductions move [0 0] robo)
                ))))
