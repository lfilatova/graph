(ns graph.path-info
  (:require
    [graph.graph :as graph]))


(defn- init-path-info [vertices]
  (reduce #(assoc %1 %2 [nil ##Inf]) {} vertices))


(defn- init-start-vertex [path-info vertex]
  (assoc path-info vertex [nil 0]))


(defn extract-path [end-vertex path-info]
  ((fn build-path [vertex path]
     (if (nil? vertex)
       (when (> (count path) 1) (reverse path))
       (recur (first (get path-info vertex))
              (conj path vertex))))
   end-vertex []))


(defn get-distance [vertex path-info]
  (second (get path-info vertex)))


(defn create-path-info [start-vertex graph]
  (-> (graph/get-vertices graph)
      (init-path-info)
      (init-start-vertex start-vertex)))
