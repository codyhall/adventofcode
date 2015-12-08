(ns puzzle-2.core)

(defn parse-dimensions [x]
  ;; creates vectors of box dimensions
  (into [] (map read-string  (clojure.string/split x #"x")))
  )


(def data   (map parse-dimensions
                 (clojure.string/split-lines
                  (slurp "/Users/Hallca/adventofcode/2/data.txt"))))

(take 10 data)
;; Find sides

(defn compute-paper-need [[l w h]]
  (let [s1 (* 2 l w)
        s2 (* 2 w h)
        s3 (* 2 h l )
        min (* 0.5 (min s1 s2 s3))
        ]
    (+ s1 s2 s3 min)))

;;2*l*w + 2*w*h + 2*h*l

;; Total square feet of paper needed.
(reduce + (map compute-paper-need data))



;; part 2

(defn compute-ribbon-need [[l w h]]
  (let [sorted (sort [l w h])
        s1 (first sorted)
        s2 (second sorted)
        p (+ s1 s1 s2 s2)
        a (* l w h)]
    (+ p a)))

(reduce + (map compute-ribbon-need data))
