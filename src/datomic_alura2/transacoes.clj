(ns datomic-alura2.transacoes
  (:require [datomic-alura2.db :as db]
            [datomic.api :as d]
            [datomic-alura2.model :as model]
            [clojure.pprint :refer [pprint]]))

(def conn (db/recria-banco-completo!))
(let [tx-schema [{:db/ident       :tx-data/ip
                  :db/valueType   :db.type/string
                  :db/cardinality :db.cardinality/one }]]
  (d/transact conn tx-schema))


(def ip "192.168.1.42")
(let [computador (model/novo-produto  "Computador Novo" "/computador_novo" 10000M)
      xadres (model/novo-produto  "Xadres" "/xadres" 700M)
      celular (model/novo-produto  "Celular" "/celular" 10000M)
      teclado (model/novo-produto  "Teclado" "/teclado" 100M)]
    (d/transact conn [celular teclado])
    (d/transact conn [computador xadres 
                     [:db/add "datomic.tx" :tx-data/ip ip]]))

(defn todos-produtos-do-ip [db ip] 
  (d/q '[:find (pull ?produto [*])
         :in $ ?ip
         :where [?transacao :tx-data/ip ?ip]
                [?produto :produto/id _ ?transacao]] db ip))


(todos-produtos-do-ip (d/db conn) ip)
