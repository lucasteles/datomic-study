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
                       {:db/ident       :produto/categoria
                        :db/valueType   :db.type/ref
                        :db/cardinality :db.cardinality/one}

                       {:db/ident       :categoria/nome
                        :db/valueType   :db.type/string
                        :db/cardinality :db.cardinality/one }

                       {:db/ident       :categoria/id
                        :db/valueType   :db.type/uuid
                        :db/cardinality :db.cardinality/one
                        :db/unique      :db.unique/identity}])


(defn recria-banco []
  (apaga-banco)
  (let [conn (abre-conexao)]
    (d/transact conn schema)
    conn))

(defn recria-banco-completo! []
  (let [conn (recria-banco)]
    (d/transact conn schema-categoria)
    conn))

(defn atribui-categorias! [conn categoria produtos]
  (let [add-categoria
        (fn [produto] [:db/add
                       [:produto/id (:produto/id produto)]
                       :produto/categoria [:categoria/id (:categoria/id categoria)]])
        transactions (map add-categoria produtos)]
    @(d/transact conn transactions)))

(defn inserir-entidades! [conn produtos]
  @(d/transact conn produtos))

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

(defn todos-nomes-de-produtos-e-categorias [db]
  (let [query '[:find ?nome ?categoria
                :keys produto categoria
                :where
                [?e :produto/nome ?nome]
                [?e :produto/categoria ?categoria-id]
                [?categoria-id :categoria/nome ?categoria]
                ]]
    (d/q query db)))


(defn todos-produtos-por-categoria [db nome-categoria]
  (let [query '[:find (pull ?produto
                            [:produto/nome
                             :produto/slug
                             {:produto/categoria [ :categoria/nome ]}
                             ])
                :in $ ?nome-categoria
                :where
                [?categoria :categoria/nome ?nome-categoria]
                [?produto :produto/categoria ?categoria]
                ]]
    (d/q query db nome-categoria)))

(defn todos-produtos-com-categoria [db]
  (let [query '[:find (pull ?produto [* {:produto/categoria [*]}])
                :where [?produto :produto/id]]]
    (d/q query db)))

(defn todos-produtos-por-categoria-backward [db nome-categoria]
  (let [query '[:find (pull ?categoria [:categoria/nome
                                        { :produto/_categoria [:produto/nome :produto/slug] } ])
                :in $ ?nome-categoria
                :where [?categoria :categoria/nome ?nome-categoria]]]
    (d/q query db nome-categoria)))

; datomic atua por conjuntos, conta o mesmo preco apenas uma vez
; por isso usar o with
(defn resumo-produtos [db]
  (d/q '[:find  (min ?preco) (max ?preco) (count ?preco)
         :with ?e
         :where [?e :produto/preco ?preco]] db))

(defn resumo-produtos-por-categoria [db]
  (d/q '[:find ?nome (min ?preco) (max ?preco) (count ?preco) (sum ?preco)
         ; :keys categoria minimo maximo quantidade total
         :with ?produto
         :where [?produto :produto/preco ?preco]
                [?produto :produto/categoria ?categoria]
                [?categoria :categoria/nome ?nome]] 
       db))

(defn todos-produtos-mais-caros [db] 
  (d/q '[:find (pull ?produto [*])
         :where [(q '[:find (max ?preco)
                      :where [_ :produto/preco ?preco]] 
                    $) [[?preco]] ]
                [?produto :produto/preco ?preco]] db))


