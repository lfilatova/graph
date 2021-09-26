(ns graph.generator
  (:require
    [graph.graph :as graph]
    [clojure.set :as set]))


(defn- generate-vertices [amount-vertices]
  (map #(keyword (str %))
       (range 1 (inc amount-vertices))))


(defn- generate-edge [start-vertex end-vertex]
  (let [weight (inc (rand-int 10))]
    [start-vertex [end-vertex weight]]))


(defn- random-start-vertex [graph]
  (-> (graph/get-vertices-without-max-possible-outdegree graph)
      (rand-nth)))


(defn- random-end-vertex [start-vertex graph]
  (let [vertices          (graph/get-vertices graph)
        neighbor-vertices (graph/get-neighbor-vertices start-vertex graph)]
    (-> (set/difference (set vertices) #{start-vertex} neighbor-vertices)
        (seq)
        (rand-nth))))


(defn- generate-edges [graph amount-edges]
  (if (zero? amount-edges)
    graph
    (let [start-vertex  (random-start-vertex graph)
          end-vertex    (random-end-vertex start-vertex graph)
          [vertex edge] (generate-edge start-vertex end-vertex)]
      (recur (graph/add-edge edge vertex graph)
             (dec amount-edges)))))


(defn- generate-random-edge [first-vertex second-vertex]
  (let [vertices     (shuffle [first-vertex second-vertex])
        start-vertex (first vertices)
        end-vertex   (second vertices)]
    (generate-edge start-vertex end-vertex)))


(defn- add-vertex [graph first-vertex]
  (let [second-vertex (rand-nth (graph/get-vertices graph))
        [vertex edge] (generate-random-edge first-vertex second-vertex)
        graph         (graph/add-vertex first-vertex graph)]
    (graph/add-edge edge vertex graph)))


(defn- generate-directed-acyclic-graph [amount-vertices]
  (let [vertices           (shuffle (generate-vertices amount-vertices))
        vertex             (first vertices)
        graph              (graph/graph vertex)
        not-added-vertices (rest vertices)]
    (reduce #(add-vertex %1 %2)
            graph
            not-added-vertices)))


(defn- generate-simple-directed-graph [amount-vertices amount-edges]
  (let [graph (generate-directed-acyclic-graph amount-vertices)]
    (generate-edges graph
                    (- amount-edges (dec amount-vertices)))))


(defn generate-graph [amount-vertices amount-edges]
  (let [min-amount-edges (dec amount-vertices)
        max-amount-edges (* amount-vertices (dec amount-vertices))]
    (if (or (< amount-edges min-amount-edges)
            (> amount-edges max-amount-edges))
      (println (format "Graph can not be created because for %s amount of vertices the amount of edges must be from %s to %s"
                       amount-vertices min-amount-edges max-amount-edges))
      (generate-simple-directed-graph amount-vertices amount-edges))))

