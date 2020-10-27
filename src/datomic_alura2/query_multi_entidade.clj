(ns datomic-alura2.query-multi-entidade
  (:require [datomic-alura2.db :as db]
            [datomic.api :as d]
            [datomic-alura2.model :as model]
            [clojure.pprint :refer [pprint]]))

(def conn (db/recria-banco))
(d/transact conn db/schema-categoria)

(let [; categorias
      eletronicos (model/nova-categoria "Eletronicos")
      esportes (model/nova-categoria "Esportes")
      ; produtos
      computador  (model/novo-produto  "Computador Novo" "/computador_novo" 2499.10M)
      xadres (model/novo-produto  "Xadres" "/xadres" 700M)
      celular (model/novo-produto  "celular" "/celular" 100M) ]
  @(d/transact conn [eletronicos esportes computador xadres celular])
  @(d/transact conn [[:db/add
                      [:produto/id (:produto/id computador)]
                      :produto/categoria [:categoria/id (:categoria/id eletronicos)]]

                     [:db/add
                      [:produto/id (:produto/id celular)]
                      :produto/categoria [:categoria/id (:categoria/id eletronicos)]]

                     [:db/add
                      [:produto/id (:produto/id xadres)]
                      :produto/categoria [:categoria/id (:categoria/id esportes)]]] ))

(println "busca nomes de produtos e categorias")
(db/todos-nomes-de-produtos-e-categorias (d/db conn))



(println "busca nomes de produtos e categorias")
(db/todos-produtos-por-categoria (d/db conn) "Eletronicos")
(db/todos-produtos-por-categoria (d/db conn) "Esportes")

(db/todos-produtos-por-categoria-completo (d/db conn) "Eletronicos")

(db/todos-produtos-por-categoria-backward (d/db conn) "Eletronicos")
