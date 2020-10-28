(ns datomic-alura2.inserir_entidade_com_ref
  (:require [datomic-alura2.db :as db]
            [datomic.api :as d]
            [datomic-alura2.model :as model]
            [clojure.pprint :refer [pprint]]))

(def conn (db/recria-banco-completo!))
(def inserir-entidades! (partial db/inserir-entidades! conn))
(def atribui-categorias! (partial db/atribui-categorias! conn))

(let [; categorias
      eletronicos (model/nova-categoria "Eletronicos")
      esportes (model/nova-categoria "Esportes")
      ; produtos
      computador  (model/novo-produto  "Computador Novo" "/computador_novo" 2499.10M)
      xadres (model/novo-produto  "Xadres" "/xadres" 700M)
      camiseta #:produto{:nome "Camiseta"
                         :slug "/camiseta"
                         :preco 3M
                         :id (model/uuid)
                         :categoria #:categoria{:nome "Roupas"
                                                :id (model/uuid)}
                         }
      id-eletronicos (:categoria/id eletronicos)
      celular #:produto{:nome "Celular"
                         :slug "/celular"
                         :preco 100M
                         :id (model/uuid)
                         :categoria [:categoria/id id-eletronicos]
                         }]
  (inserir-entidades! [eletronicos esportes computador xadres camiseta])
  (atribui-categorias! eletronicos [computador])
  (atribui-categorias! esportes [xadres])
  ; usa id da categoria incluida anteriormente
  (inserir-entidades! [celular]))

(println "busca nomes de produtos e categorias")
(db/todos-nomes-de-produtos-e-categorias (d/db conn))




