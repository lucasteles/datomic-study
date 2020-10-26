(ns datomic-alura.queries-predicates
  (:require [datomic-alura.db :as db]
            [datomic.api :as d]
            [datomic-alura.model :as model]
            [clojure.pprint :refer [pprint]]))


(def conn (db/recria-banco))

;seed
(let [computador  (model/novo-produto "Computador Novo" "/computador_novo" 2499.10M)
      impressora  (model/novo-produto "Impressora" "/impressora" 1200M)
      teclado  (model/novo-produto "Teclado Mecanico" "/teclado" 500M)
      calculadora { :produto/nome "Calculadora" :produto/preco 20M}
      produtos [computador impressora teclado calculadora]]
  (d/transact conn produtos))

(def db (d/db conn))
(db/todos-produtos-por-preco db 1000M)
(db/todos-produtos-por-preco db 2000M)



