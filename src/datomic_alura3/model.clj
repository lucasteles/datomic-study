(ns datomic-alura3.model
  (:require [schema.core :as s]))


(s/defschema Categoria
  #:categoria {:nome s/Str
               :id    java.util.UUID })

(s/defschema Produto
  #:produto {:nome s/Str
             :slug s/Str
             :preco BigDecimal
             :id  java.util.UUID
             (s/optional-key :produto/palavra-chave) [s/Str]
             (s/optional-key :produto/categoria) Categoria })

(defn uuid [] (java.util.UUID/randomUUID))

(s/defn novo-produto :- Produto
  ([nome  :- s/Str
    slug  :- s/Str
    preco :- BigDecimal]
   (novo-produto (uuid) nome slug preco))
  ([uuid  :- java.util.UUID
    nome  :- s/Str
    slug  :- s/Str
    preco :- BigDecimal]
   #:produto {:id uuid
              :nome nome
              :slug slug
              :preco preco}))

(s/defn nova-categoria :- Categoria
  ([nome :- s/Str ]
   (nova-categoria (uuid) nome))
  ([uuid :- java.util.UUID nome :- s/Str ]
   #:categoria { :id uuid
                :nome nome }))
