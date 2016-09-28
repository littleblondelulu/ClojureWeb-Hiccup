app isn't defined. in our class work, we defined app as our route collection.
Because we have no routes defined, we're not using any of the cool functions you've written to render out the site.

(ns purchases-clojure.core
  (:require  [clojure.string :as str]
             [compojure.core :as c]
             [ring.adapter.jetty :as j]
             [hiccup.core :as h])
  (:gen-class))

(c/defroutes app
             (c/GET "/" []
               "Hello, world!"))

(c/defroutes app
             (c/GET "/" []
               (h/html [:html
                        [:body
                         [:h2 "Hello, world!"]]])))

(defn purchases-html [category]
  (let [purchases (read-purchases)
        purchases (if (= 0 (count country))
           purchases;
           (filter (fn [purchases]
                         (= (get purchases "category") category))
                       purchases))]
    [:ol
     (map (fn [purchase]
            [:li (str (get purchase "customer_id") " " (get purchase "date") " " (get purchase "credit_card") " " (get purchase "csv") " " (get purchase "category")) ])
          purchases)]))


(c/defroutes app
              (c/GET "/:category{.*}" [category]
                (h/html [:html
                         [:body
                          (purchases-html category)]])))


;store state in nil atom
(defonce server (atom nil))

;specify port 3000 instead
;jetty returns a server
;change it to reset -- to get the value at the specific state in atoms -- call that value @server
(defn -main []
  (reset! server))
  (reset! server(j/run-jetty app {:port 3000 :join? false})))


;Create a project with purchases.csv in it
;Read the file and parse it into a vector of lines
;Split each line into vectors (delimited by commas)
;Separate the header from the rest of the lines
;Make each line a hash map that associates each item in the header with each item in the line
;Ask the user to type a category
;The possible categories are: Furniture, Alcohol, Toiletries, Shoes, Food, Jewelry
;Read a line of input from the user;
;Filter the vector so it only contains hash maps whose category equals the input from the user
;Use the clojure json library to convert the map to json
;Save the results into a file called filtered_purchases.json