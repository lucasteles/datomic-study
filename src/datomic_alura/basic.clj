(ns datomic-alura.basic
  (:require [datomic-alura.db :as db]
            [datomic.api :as d]
            [datomic-alura.model :as model]))

(def conn (db/recria-banco))

(let [computador  (model/novo-produto "Computador Novo" "/computador_novo" 2499.10M)
      impressora  (model/novo-produto "Impressora" "/impressora" 1200M)
      teclado  (model/novo-produto "Teclado Mecanico" "/teclado" 500M)
      produtos [computador impressora teclado]]
  (d/transact conn produtos))

(d/transact conn [(model/novo-produto "Televisao" "/tv" 12000M)])
; response from datomic
; {:db-before datomic.db.Db@e34a31a,
;  :db-after datomic.db.Db@868c0282,
;  :tx-data [
;   #datom[13194139534313 50 #inst "2020-10-26T14:26:56.144-00:00" 13194139534313 true]
;   #datom[17592186045418 72 "Televisao" 13194139534313 true]
;   #datom[17592186045418 73 "/tv" 13194139534313 true]
;   #datom[17592186045418 74 12000M 13194139534313 true]],
;  :tempids {-9223301668109598113 17592186045418}}>

(def db (d/db conn))
(d/q '[:find ?entidade
       :where [?entidade :produto/nome]] db)

; campos nao sao obrigatorios por padrao
(let [calculadora { :produto/nome "calculadora com 4 operações"}]
  (d/transact conn [calculadora]))

; nao pode usar nulo, se nao quiser o valor deixa sem
; (let [radio-relogio { :produto/nome "Radio com relógio" :produto/slug nil}]
;   (d/transact conn [radio-relogio]))


; update
(let [conn (db/recria-banco)
      celular-barato (model/novo-produto "Celular barato" "/celular-barato" 8888M)
      resultado @(d/transact conn [celular-barato])
      id (-> resultado :tempids vals first)
      alterar-preco @(d/transact conn [[:db/add id :produto/preco 100M]])
      remover-slug @(d/transact conn [[:db/retract id :produto/slug ]])
      ]
  { :inicial resultado :alterado alterar-preco :sem-slug remover-slug })

; {:inicial
;  {:db-before datomic.db.Db@b21d84c2,
;   :db-after datomic.db.Db@7490a23a,
;   :tx-data
;   [
; #datom[13194139534313 50 #inst "2020-10-26T15:30:45.882-00:00" 13194139534313 true] 
; #datom[17592186045418 72 "Celular barato" 13194139534313 true] 
; #datom[17592186045418 73 "/celular-barato" 13194139534313 true] 
; #datom[17592186045418 74 8888M 13194139534313 true]],
;   :tempids {-9223301668109598094 17592186045418}},
;  :alterado
;  {:db-before datomic.db.Db@7490a23a,
;   :db-after datomic.db.Db@6ceeb00b,
;   :tx-data
;   [
; #datom[13194139534315 50 #inst "2020-10-26T15:30:45.886-00:00" 13194139534315 true] 
; #datom[17592186045418 74 100M 13194139534315 true] 
; #datom[17592186045418 74 8888M 13194139534315 false]],
;   :tempids {}},
;  :sem-slug
;  {:db-before datomic.db.Db@6ceeb00b,
;   :db-after datomic.db.Db@f3862c54,
;   :tx-data
;   [
; #datom[13194139534316 50 #inst "2020-10-26T15:30:45.897-00:00" 13194139534316 true] 
; #datom[17592186045418 73 "/celular-barato" 13194139534316 false]],
;   :tempids {}}}


