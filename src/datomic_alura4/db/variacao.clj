(ns datomic-alura4.db.variacao
  (:require
    [datomic.api :as d]
    [schema.core :as s]
    [datomic-alura4.model :as model]))


(s/defn add!
  [conn
   produto-id :- java.util.UUID
   nome :- s/Str
   preco :- BigDecimal]
  (d/transact conn
              [{:db/id "variacao-temp"
                :variacao/id (model/uuid)
                :variacao/preco preco
                :variacao/nome nome }
               #:produto {:id produto-id
                          :variacao "variacao-temp"}]))





