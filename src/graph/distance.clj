(ns graph.distance
  (:require
    [graph.dijkstra :as dijkstra]))


(defn- extremum-path [paths extremum-fn]
  (when (not (empty? paths))
    (apply extremum-fn count paths)))


(defn- min-path [paths]
  (extremum-path paths min-key))


(defn- max-path [paths]
  (extremum-path paths max-key))


(defn- distance [path]
  (dec (count path)))


(defn eccentricity [graph start-vertex]
  (some-> (dijkstra/shortest-paths-one-to-many-vertices graph start-vertex)
          (max-path)
          (distance)))


(defn radius [graph]
  (some-> (dijkstra/shortest-paths-many-to-many-vertices graph)
          (min-path)
          (distance)))


(defn diameter [graph]
  (some-> (dijkstra/shortest-paths-many-to-many-vertices graph)
          (max-path)
          (distance)))
