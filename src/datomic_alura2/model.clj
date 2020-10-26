(ns datomic-alura2.model)

(defn uuid [] (java.util.UUID/randomUUID))

(defn novo-produto 
  ([nome slug preco]
   (novo-produto (uuid) nome slug preco))
  ([uuid nome slug preco]
  #:produto {:id uuid
             :nome nome
             :slug slug
             :preco preco}))


