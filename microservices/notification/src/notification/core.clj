(ns sre-commerce.notification
  (:import (org.apache.kafka.clients.consumer KafkaConsumer ConsumerConfig)
           (org.apache.kafka.common.serialization StringDeserializer)
           (java.time Duration)
           (java.util Properties)))

(defn create-consumer []
  (let [props (doto (Properties.)
                (.put ConsumerConfig/BOOTSTRAP_SERVERS_CONFIG "localhost:9092")
                (.put ConsumerConfig/GROUP_ID_CONFIG "notifications")
                (.put ConsumerConfig/KEY_DESERIALIZER_CLASS_CONFIG StringDeserializer)
                (.put ConsumerConfig/VALUE_DESERIALIZER_CLASS_CONFIG StringDeserializer))]
    (KafkaConsumer. props)))

(defn consume-messages [consumer topic]
  (doto consumer
    (.subscribe [topic]))
  (while true
    (let [records (.poll consumer (Duration/ofSeconds 1))]
      (doseq [record records]
        (println (str "Received notification: " (.value record)))))))

(defn -main []
  (let [consumer (create-consumer)]
    (consume-messages consumer "notifications")))

;; Entry point for the application
(defn start []
  (println "Starting Notification Service...")
  (-main))

(start)

