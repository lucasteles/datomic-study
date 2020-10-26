(ns datomic-alura.timetravel
  (:require [datomic-alura.db :as db]
            [datomic.api :as d]
            [datomic-alura.model :as model]
            [clojure.pprint :refer [pprint]]))


(defn now [] (java.util.Date.))

(def conn (db/recria-banco))

;seed
(let [computador  (model/novo-produto "Computador Novo" "/computador_novo" 2499.10M)
      impressora  (model/novo-produto "Impressora" "/impressora" 1200M)
      produtos [computador impressora]
      resultado @(d/transact conn produtos)]
  resultado)

(def before (now))

(let [teclado  (model/novo-produto "Teclado Mecanico" "/teclado" 500M)
      calculadora { :produto/nome "Calculadora" :produto/preco 20M}
      produtos [teclado calculadora]
      resultado @(d/transact conn produtos)]
  (:db-after resultado))

(def after (now))
(def db (d/db conn))

(count (db/todos-produtos-completos db))
; busca antes
(count (db/todos-produtos-completos (d/as-of db before)))
; busca depois
(count (db/todos-produtos-completos (d/as-of db after)))


