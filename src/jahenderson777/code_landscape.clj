(ns jahenderson777.code-landscape
  (:require [org.httpkit.server :as server]
            [clojure.data.json :as json]
            [clj-http.client :as client])
  (:gen-class))

(defn handler [req]
  (let [{:keys [file line char] :as data}
        (json/read-str (String. (.bytes (:body req)))
                       :key-fn keyword)]
    (println data)
    {:status  200
     :headers {"Content-Type" "text/html"}
     :body    "ok"}))

(defonce server (atom nil))

(defn stop-server! []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(defn start-server! []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "7321"))] ;(5) 
    (reset! server (server/run-server #'handler {:port port}))
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))

(defn -main [& args]
  (start-server!))
