(ns datomic-alura4.db.config
  (:require
    [datomic.api :as d]
    [schema.core :as s]
    [clojure.walk :as walk]
    [clojure.set :as cset]
    [datomic-alura4.db.produto :as db.produto]
    [datomic-alura4.db.categoria :as db.categoria]
    [datomic-alura4.model :as model]))

(def db-uri "datomic:dev://localhost:4334/ecommerce")

(defn abre-conexao []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn apaga-banco []
  (d/delete-database db-uri))


(def schema [{:db/ident       :produto/nome
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "O nome de um produto"}

             {:db/ident       :produto/slug
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "O caminho para acessar esse produto via http"}

             {:db/ident       :produto/preco
              :db/valueType   :db.type/bigdec
              :db/cardinality :db.cardinality/one
              :db/doc         "O preço de um produto com precisão monetária"}

             {:db/ident       :produto/palavra-chave
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/many }

             {:db/ident       :produto/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity}

             {:db/ident       :produto/categoria
              :db/valueType   :db.type/ref
              :db/cardinality :db.cardinality/one}

             {:db/ident       :produto/estoque
              :db/valueType   :db.type/long
              :db/cardinality :db.cardinality/one}

             {:db/ident       :produto/digital
              :db/valueType   :db.type/boolean
              :db/cardinality :db.cardinality/one}

             {:db/ident       :produto/variacao
              :db/valueType   :db.type/ref
              :db/isComponent true
              :db/cardinality :db.cardinality/many}

             {:db/ident       :produto/visualizacoes
              :db/valueType   :db.type/long
              :db/noHistory   true
              :db/cardinality :db.cardinality/one}

             {:db/ident       :variacao/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity}
             {:db/ident :variacao/nome
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one }
             {:db/ident :variacao/preco
              :db/valueType   :db.type/bigdec
              :db/cardinality :db.cardinality/one}

             {:db/ident       :categoria/nome
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one }

             {:db/ident       :categoria/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity}

             {:db/ident       :venda/produto
              :db/valueType   :db.type/ref
              :db/cardinality :db.cardinality/one}
             {:db/ident       :venda/quantidade
              :db/valueType   :db.type/long
              :db/cardinality :db.cardinality/one}
             {:db/ident       :venda/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity}
             {:db/ident       :venda/situacao
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one}
             ])


(defn recria-banco []
  (apaga-banco)
  (let [conn (abre-conexao)]
    (d/transact conn schema)
    conn))



(defn cria-dados-exemplo [conn]
  (let [; categorias
        eletronicos (model/nova-categoria "Eletronicos")
        esportes (model/nova-categoria "Esportes")
        ; produtos
        computador (model/novo-produto  "Computador Novo" "/computador_novo" 2499.10M 10)
        xadres (model/novo-produto  "Xadres" "/xadres" 700M 0)
        celular (model/novo-produto  "Celular" "/celular" 100M 12)
        teclado (model/novo-produto  "Teclado" "/teclado" 100M 0)
        jogo (assoc (model/novo-produto  "Jogo digital" "/jogo" 100M 0) :produto/digital true)]
    (db.categoria/atualiza! conn [eletronicos esportes])
    (db.produto/atualiza! conn [computador xadres celular teclado jogo])
    (db.categoria/atribui! conn eletronicos [computador celular teclado jogo])
    (db.categoria/atribui! conn esportes [xadres])))





