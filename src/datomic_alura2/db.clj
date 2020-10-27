(ns datomic-alura2.db
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

             {:db/ident         :produto/palavra-chave
              :db/valueType     :db.type/string
              :db/cardinality   :db.cardinality/many }

             {:db/ident         :produto/id
              :db/valueType     :db.type/uuid
              :db/cardinality   :db.cardinality/one
              :db/unique        :db.unique/identity}

             ])

(def schema-categoria [
                       {:db/ident       :categoria/nome
                        :db/valueType   :db.type/string
                        :db/cardinality :db.cardinality/one }

                       {:db/ident       :categoria/id
                        :db/valueType   :db.type/uuid
                        :db/cardinality :db.cardinality/one
                        :db/unique      :db.unique/identity}])


(defn cria-schema [conn]  (d/transact conn schema))

(defn recria-banco []
  (apaga-banco)
  (let [conn (abre-conexao)]
    (cria-schema conn)
    conn))


; busca todos os atributos
(defn todos-produtos [db]
  (let [query '[:find (pull ?e [*])
                :where [?e :produto/id]]]
    (d/q query db)))

(defn busca-por-dbid [db id]
  (d/pull db '[*] id))

(defn busca-por-id-produto [db id]
  (d/pull db '[*] [:produto/id id]))

(defn todas-categorias [db]
  (let [query '[:find (pull ?e [*])
                :where [?e :categoria/id]]]
    (d/q query db)))

