(ns graph.dijkstra
  (:require
    [graph.path-info :as path-info]
    [graph.graph     :as graph]))


(defn- unvisited-vertices [path-info visited-vertices]
  (remove (fn [[vertex]] (contains? visited-vertices vertex)) path-info))


(defn- vertex-with-min-distance [vertices-path-info]
  (when (not (empty? vertices-path-info))
    (first (apply min-key (fn [[_ [_ distance]]] distance) vertices-path-info))))


(defn- nearest-unvisited-vertex [path-info visited-vertices]
  (-> (unvisited-vertices path-info visited-vertices)
      (vertex-with-min-distance)))


(defn- update-distance [path-info parent-vertex neighbor-vertex graph]
  (let [parent-distance   (path-info/get-distance parent-vertex path-info)
        neighbor-distance (path-info/get-distance neighbor-vertex path-info)
        weight            (graph/get-weight parent-vertex neighbor-vertex graph)
        new-distance      (+ parent-distance weight)]
    (if (> neighbor-distance new-distance)
      (assoc path-info neighbor-vertex [parent-vertex new-distance])
      path-info)))


(defn- update-distances [path-info parent-vertex neighbors-vertices graph]
  (reduce (fn [path-info neighbor-vertex]
            (update-distance path-info parent-vertex neighbor-vertex graph))
          path-info
          neighbors-vertices))


(defn- search [start-vertex graph path-info]
  ((fn visit-neighbors [vertex visited-vertices path-info]
     (if (nil? vertex)
       path-info
       (let [neighbors-vertices (graph/get-neighbor-vertices vertex graph)
             path-info          (update-distances path-info vertex neighbors-vertices graph)
             visited-vertices   (conj visited-vertices vertex)
             vertex             (nearest-unvisited-vertex path-info visited-vertices)]
         (recur vertex visited-vertices path-info))))
   start-vertex #{} path-info))


(defn shortest-path [graph start-vertex end-vertex]
  (->> (path-info/create-path-info start-vertex graph)
       (search start-vertex graph)
       (path-info/extract-path end-vertex)))


(defn shortest-paths-one-to-many-vertices [graph start-vertex]
  (for [end-vertex (graph/get-vertices graph)
        :when      (not= end-vertex start-vertex)
        :let       [path (shortest-path graph start-vertex end-vertex)]
        :when      (not (nil?  path))]
    path))


(defn shortest-paths-many-to-many-vertices [graph]
  (reduce #(into %1 (shortest-paths-one-to-many-vertices graph %2))
          []
          (graph/get-vertices graph)))
