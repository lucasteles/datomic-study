(ns datomic-alura2.query-single
  (:require [datomic-alura2.db :as db]
            [datomic.api :as d]
            [datomic-alura2.model :as model]
            [clojure.pprint :refer [pprint]]))

(def conn (db/recria-banco))

(def uuid-computer (model/uuid))
(let [computador  (model/novo-produto uuid-computer "Computador Novo" "/computador_novo" 2499.10M)
      impressora  (model/novo-produto "Impressora" "/impressora" 1200M)
      teclado  (model/novo-produto  "Teclado Mecanico" "/teclado" 500M)
      calculadora { :produto/id (model/uuid) :produto/nome "Calculadora" :produto/preco 20M}
      produtos [computador impressora teclado calculadora]]
  (d/transact conn produtos))

(def dbid-computador 
    (->> (d/db conn)
        db/todos-produtos
        ffirst
        :db/id))

(def db (d/db conn))
(println "todos os produtos")
(db/todos-produtos db)

; lookup refs
(println "buscando por db/id")
(db/busca-por-dbid db dbid-computador)

(println "buscando por produto id =" uuid-computer)
(db/busca-por-id-produto db uuid-computer)

