(ns graph.graph)


(defn- max-possible-outdegree [graph]
  (dec (count graph)))


(defn- max-possible-outdegree? [[_ edges] graph]
  (= (count edges) (max-possible-outdegree graph)))


(defn get-neighbor-vertices [vertex graph]
  (map first (get graph vertex)))



(defn get-vertices-without-max-possible-outdegree [graph]
  (->> graph
       (remove #(max-possible-outdegree? % graph))
       (map first)))


(defn get-vertices [graph]
  (keys graph))


(defn- get-edges [vertex graph]
  (get graph vertex))


(defn get-weight [start-vertex end-vertex graph]
  (->> graph
      (get-edges start-vertex)
      (drop-while #(not= (first %) end-vertex))
      (first)
      (second)))


(defn add-edge [edge vertex graph]
  (update graph vertex #(conj % edge)))


(defn add-vertex [vertex graph]
  (assoc graph vertex []))


(defn graph [vertex]
  {vertex []})
