# graph

Generate graph

    (generate-graph 3 5)

Find the shortest path

    (def graph (generate-graph 3 5))
    
    (shortest-path graph (first (keys graph)) (second (keys graph)))

Calculate the eccentricity

    (def graph (generate-graph 3 5))
    
    (eccentricity graph (first (keys graph)))

Calculate the radius

    (def graph (generate-graph 3 5))

    (radius graph)

Calculate the diameter

    (def graph (generate-graph 3 5))

    (diameter graph)
