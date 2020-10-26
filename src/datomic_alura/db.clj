(ns datomic-alura.db
  (:require [datomic.api :as d]))

(def db-uri "datomic:dev://localhost:4334/ecommerce")

(defn abre-conexao []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn apaga-banco []
  (d/delete-database db-uri))


(def schema [{:db/ident         :produto/nome
              :db/valueType     :db.type/string
              :db/cardinality   :db.cardinality/one
              :db/doc           "O nome de um produto"}

             {:db/ident         :produto/slug
              :db/valueType     :db.type/string
              :db/cardinality   :db.cardinality/one
              :db/doc           "O caminho para acessar esse produto via http"}

             {:db/ident         :produto/preco
              :db/valueType     :db.type/bigdec
              :db/cardinality   :db.cardinality/one
              :db/doc           "O preço de um produto com precisão monetária"}
             ])

(defn cria-schema [conn]  (d/transact conn schema))

(defn recria-banco []
  (apaga-banco)
  (let [conn (abre-conexao)]
    (cria-schema conn)
    conn))

(defn todos-produtos [db]
  (d/q '[:find ?entidade
         :where [?entidade :produto/nome]] db))

; o $ é o db
(defn todos-produtos-por-slug [db slug]
  (let [query '[:find ?entidade
                :in $ ?slug-buscado 
                :where [?entidade :produto/slug ?slug-buscado]]
        ]
    (d/q query db slug)))


(defn todos-slugs [db]
  (let [query '[:find ?slug-buscado
                :where [_ :produto/slug ?slug-buscado]]
        ]
    (d/q query db)))


(defn todos-produtos-por-preco [db]
  (let [query '[:find ?nome ?preco
                :where 
                  [?e :produto/preco ?preco]
                  [?e :produto/nome ?nome]]]
    (d/q query db)))

