(ns graph.core-test
  (:require [clojure.test :refer :all]
            [graph.generator :refer :all]
            [graph.dijkstra :refer :all]
            [graph.distance  :refer :all]))


(deftest all
    (is (= `(:1 :5 :2)
           (shortest-path {:4 [[:2 4]], :3 [[:4 2]], :5 [[:3 4] [:2 9]], :2 [], :1 [[:5 7]]} :1 :2)))
    (is (= `(:1 :5 :3 :4 :2)
           (shortest-path {:4 [[:2 4]], :3 [[:4 1]], :5 [[:3 3] [:2 9]], :2 [], :1 [[:5 7]]} :1 :2)))
    (is (= 3 (eccentricity {:4 [[:2 4]], :3 [[:4 2]], :5 [[:3 4] [:2 9]], :2 [], :1 [[:5 7]]} :1)))
    (is (= 3 (diameter {:4 [[:2 4]], :3 [[:4 2]], :5 [[:3 4] [:2 9]], :2 [], :1 [[:5 7]]})))
    (is (= 1 (radius {:4 [[:2 4]], :3 [[:4 2]], :5 [[:3 4] [:2 9]], :2 [], :1 [[:5 7]]})))
    (is (not (nil? (generate-graph 3 6))))
    (is (nil? (generate-graph 3 1)))
    (is (nil? (generate-graph 2 3))))
