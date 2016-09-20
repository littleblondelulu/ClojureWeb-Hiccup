(ns clojure-web.core
  (:require  [clojure.string :as str]
             [compojure.core :as c]
             [ring.adapter.jetty :as j]
             [hiccup.core :as h])
  (:gen-class))

(defn parse-purchases []
  (let [purchases (slurp "purchases.csv")
        purchases (str/split-lines purchases)
        purchases (map (fn [line]
                         (str/split line #",")) purchases)
        header (first purchases)
        purchases (rest purchases)
        purchases (map (fn [line]
                         (zipmap header line))
                       purchases)]
    purchases))

;customer_id,date,credit_card,cvv,category
(defn purchases-html [purchases]
  [:table {:class "table"}
   [:thead
    [:tr
     [:td "Customer ID"]
     [:td "date"]
     [:td "Credit Card"]
     [:td "CVV"]
     [:td "Category"]
     ]
    ]
   [:tbody
    (map (fn [purchase]
           [:tr
            [:td (get purchase "customer_id")]
            [:td (get purchase "date")]
            [:td (get purchase "credit_card")]
            [:td (get purchase "cvv")]
            [:td (get purchase "category")]
            ]
           )
         purchases)
    ]])

;get a category using A/N function
(defn get-categories []
  (let [purchases (parse-purchases)
        categories (set (map #(get % "category") purchases))]
    categories))

;add links to filter categories
(defn nav-html [categories]
  [:nav {:class "navbar navbar-default"} [:div {:class "container-fluid"}
                                          [:ul {:class "nav navbar-nav"}
                                           [:li
                                            [:a {:href "/"} "All"]]
                                           (map (fn [category]
                                                  [:li
                                                   [:a {:href (str "/" category)} category]]) categories)]]])

;store state in nil atom
(defonce server (atom nil))

;specify port 3000 instead
;jetty returns a server
;change it to reset -- to get the value at the specific state in atoms -- call that value @server
(defn -main []
  (when @server
    (.stop @server))
  ;reset the server value back to a new webserver
  (reset! server (j/run-jetty app {:port 3000 :join? false})))


