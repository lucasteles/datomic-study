(ns datomic-alura2.inserir_entidade
  (:require [datomic-alura2.db :as db]
            [datomic.api :as d]
            [datomic-alura2.model :as model]
            [clojure.pprint :refer [pprint]]))

(def conn (db/recria-banco-completo!))

(let [; categorias
      eletronicos (model/nova-categoria "Eletronicos")
      esportes (model/nova-categoria "Esportes")
      ; produtos
      computador  (model/novo-produto  "Computador Novo" "/computador_novo" 2499.10M)
      xadres (model/novo-produto  "Xadres" "/xadres" 700M)
      celular (model/novo-produto  "celular" "/celular" 100M) ]

  (db/inserir-entidades! conn [eletronicos esportes computador xadres celular])
  (db/atribui-categorias! conn eletronicos [computador celular])
  (db/atribui-categorias! conn esportes [xadres]))


(println "busca nomes de produtos e categorias")
(db/todos-produtos-por-categoria-completo (d/db conn) "Eletronicos")

