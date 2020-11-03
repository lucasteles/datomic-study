(ns datomic-alura4.model
  (:require [schema.core :as s]
            [datomic-alura2.model :as model]))

(defn keyof [schema]
  (apply s/enum (keys schema)))

(defn partial-schema [schema]
  (into {} (map (fn [[k v]] [(s/optional-key k) v]) schema)))

(s/defschema Categoria
  #:categoria {:nome s/Str
               :id   java.util.UUID })

(s/defschema Produto
  #:produto {:nome s/Str
             :slug s/Str
             :preco BigDecimal
             :id  java.util.UUID
             :estoque s/Num
             :digital s/Bool
             (s/optional-key :produto/palavra-chave) [s/Str]
             (s/optional-key :produto/visualizacoes) s/Num
             (s/optional-key :produto/categoria) Categoria
             (s/optional-key :produto/variacao) [s/Any] })

(defn uuid [] (java.util.UUID/randomUUID))

(s/defn novo-produto :- Produto
  ([nome         :- s/Str
    slug         :- s/Str
    preco        :- BigDecimal
    estoque      :- s/Num]
   (novo-produto (uuid) nome slug preco estoque))
  ([uuid         :- java.util.UUID
    nome         :- s/Str
    slug         :- s/Str
    preco        :- BigDecimal
    estoque      :- s/Num]
   #:produto {:id uuid
              :nome nome
              :slug slug
              :preco preco
              :estoque estoque
              :digital false}))

(s/defn nova-categoria :- Categoria
  ([nome :- s/Str ]
   (nova-categoria (uuid) nome))
  ([uuid :- java.util.UUID nome :- s/Str ]
   #:categoria {:id uuid
                :nome nome }))




