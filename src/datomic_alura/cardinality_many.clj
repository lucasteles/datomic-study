(ns datomic-alura.cardinality-many
  (:require [datomic-alura.db :as db]
            [datomic.api :as d]
            [datomic-alura.model :as model]
            [clojure.pprint :refer [pprint]]))


(def conn (db/recria-banco))
(println "adiciona schema de muitas chaves na entidade")
(d/transact conn db/schema-com-palavra-chave)

(let [computador  (model/novo-produto "Computador Novo" "/computador_novo" 2499.10M)
      celular-caro  (model/novo-produto "Celular caro" "/celular-caro" 1200M)
      celular-barato  (model/novo-produto "Celular barato" "/celular-barato" 500M)
      produtos [computador celular-caro celular-barato]]
  (d/transact conn produtos))

(defn id-por-slug [slug]
  (->
    (d/db conn)
    (db/todos-produtos-por-slug slug) 
    ffirst))

(def id-computador (id-por-slug "/computador_novo"))

(println "adiciona palavra chave no computador")
(d/transact conn [[:db/add id-computador :produto/palavra-chave "desktop"]
                  [:db/add id-computador :produto/palavra-chave "computador"]])

(println "computador com 2 palavra chave")
(db/todos-produtos-completos (d/db conn))


(println "computador com 1 palavra chave")
(d/transact conn [[:db/retract id-computador :produto/palavra-chave "computador"]])
(db/todos-produtos-completos (d/db conn))


(def id-celular-caro (id-por-slug "/celular-caro"))
(def id-celular-barato (id-por-slug "/celular-barato"))

(println "adiciona palavra chave nos celulares")
(d/transact conn [[:db/add id-celular-caro :produto/palavra-chave "celular"]
                  [:db/add id-celular-barato :produto/palavra-chave "celular"] ])

(println "celulares com palavra chave")
(db/todos-produtos-completos (d/db conn))

(println "busca celulares por palavra chave")
(db/todos-produtos-por-palavra-chave (d/db conn) "celular")

