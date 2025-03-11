(ns qa.view.page
  (:require
   [ataraxy.response :as response]
   [clojure.string :as str]
   [hiccup.page :refer [html5]]
   [hiccup.form
    :refer
    [form-to text-field password-field submit-button text-area hidden-field]]
   [hiccup.util :refer [escape-html]]
   [markdown.core :refer [md-to-html-string]]
   [ring.util.anti-forgery :refer [anti-forgery-field]]))

(def ^:private version "v2.9-SNAPSHOT")
(def ^:private updated "2024-09-26 06:26:09")

(def ^:private wrap-at 80)

;; from r99c.route.home/wrap
(defn- wrap-aux
  [n s]
  (if (< (count s) n)
    s
    (str (subs s 0 n) "\n" (wrap-aux n (subs s n)))))

(defn- wrap
  "fold string `s` at column `n`"
  [n s]
  (str/join "\n" (map (partial wrap-aux n) (str/split-lines s))))

(defn- ss
  "文字列 s の n 文字以降を切り詰めた文字列を返す。
   文字列長さが n に満たない時はそのまま。"
  [n s]
  (subs s 0 (min n (count s))))

(defn- date-time
  "timestamp 文字列から YYYY/MM/DD hh:mm:ss を抜き出す"
  [tm]
  (subs (str tm) 0 19))

(defn page [& contents]
  [::response/ok
   (html5
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]]
    #_[:link
       {:rel "stylesheet"
        :type "text/css"
        :href "/css/bootstrap.min.css"}]
    [:link
     {:href "https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      :rel  "stylesheet"
      :integrity "sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
      :crossorigin "anonymous"}]
    [:link
     {:rel "stylesheet"
      :type "text/css"
      :href "/css/styles.css"}]
    [:script {:type "text/javascript"}
     "function ok() {return window.confirm('OK?');}"]
    [:title "QA"]
    [:body
     [:div {:class "container"}
      contents
      [:p]
      ;; [:p [:a {:href "/logout" :class "btn btn-warning btn-sm"} "logout"]]
      [:hr]
      "programmed by hkimura. " version]])])

(defn about-page
  []
  (page
   [:h2 "QA:About"]
   [:p]
   [:img {:src "/images/odyssey.jpg"}]
   [:p "version: " version [:br]
    "update: " updated]))

(defn index-page [req]
  (page
   [:h2 "QA"]
   #_[:p "昨年度リテラシー、情報処理応用受講者は"
    [:a {:href "https://l22.melt.kyutech.ac.jp/register"}
     "アカウントを作り直し"] "してください。(need VPN)"]
   [:div.text-danger (:flash req)]
   (form-to
    [:post "/login"]
    (anti-forgery-field)
    (text-field {:placeholder "アカウント"} "login")
    (password-field {:placeholder "パスワード"} "password")
    (submit-button "login"))
   [:br]
   [:div {:class "row"}
    [:div {:class "col-3"}
     [:a {:href "https://www.youtube.com/watch?v=JktXHKx3r20"}
      [:img {:src "images/odyssey.jpg" :id "odyssey"}]] [:br]
     [:p {:class "sm"} "2001年宇宙の旅"]]
    [:div {:class "col-9"}
     [:p "聞いたことは忘れる。" [:br]
      "やったことは覚える。" [:br]
      "人に教えたことは身に付く。"]]]
   [:audio {:src "sounds/sorry-dave.mp3"
            :autoplay false
            :controls "controls"}]
   [:div
    [:ul
     [:li "回答しやすい質問をする。回答できる質問には回答する。"]
     [:li "質問はテキスト、回答は Markdown で。"]
     [:li "「いいね 👍」は一回答にひとり一回だけです。"]
     [:li "ログイン時の Invalid anti-forgery token は認証切れ。再読み込みで。"]]]))

(defn question-new-page []
  (page
   [:h2 "QA: Create a Question"]
   [:p "具体的な質問じゃないと回答つけにくい。"
    "短すぎる質問も長すぎる質問と同じく受信しない。"
    [:a {:href "/"} "注意事項"]]
   (form-to {:enctype "multipart/form-data"
             :onsubmit "return confirm('その質問は具体的か？')"}
            [:post "/q"]
            (anti-forgery-field)
            (text-area {:id "question"
                        :placeholder "テキストで。60 文字以内に改行するように。マークダウン不可。"}
                       "question")
            [:br]
            (submit-button {:class "btn btn-primary btn-sm"} "submit"))))

;; 回答がついてなかったら 0 を表示する。
(defn- answer-count
  [cs q_id]
  (:count (first (filter #(= (:q_id %) q_id) cs)) 0))

(defn questions-page [qs cs]
  (page
   [:h2 "QA: Questions"]
   [:p "すべての QA に目を通すのがルール。"]
   [:p
    [:a {:href "/recents" :class "btn btn-success btn-sm"} "最近の投稿"]
    "&nbsp;"
    [:a {:href "/goods" :class "btn btn-warning btn-sm"} "最近のいいね"]
    "&nbsp;"
    [:a {:href "/q" :class "btn btn-primary btn-sm"} "new question"]
    "&nbsp;"
    [:a {:href "/about" :class "btn btn-primary btn-sm"} "About"]
    "&nbsp;"
    ;; [:a {:href "/md" :class "btn btn-info btn-sm"} "markdown道場"]
    ;; "&nbsp;"
    [:a {:href "/logout" :class "btn btn-warning btn-sm"} "logout"]]
   [:p [:a.link-underline-light
        {:href "/readers/qs/0"}
        "readers"]]
   (for [q qs]
     [:p
      (:id q)
      ", "
      (escape-html (-> (:q q) str/split-lines first))
           ;;(escape-html (ss 30 (:q q)))
      "&nbsp;"
      [:a.link-underline-light
       {:href (str "/my-goods/" (:nick q))}
       (:nick q)]
      "&nbsp;"
      [:a.link-underline-light
       {:href (str "/as/" (:id q))}
       (str " 👉 " (answer-count cs (:id q)))]])
   [:p [:a {:href "/q" :class "btn btn-primary btn-sm"} "new question"]]))

;;👁️🚀✔️☑️➰➿⚯☞⍇⍈

(defn- goods
  [n]
  (repeat n "👍"))

;; 0.7.6, p ではなく pre でメッセージを表示したことに伴い、
;; 過去に入れてもらった <br> を取り除く。
(defn- my-escape-html [s]
  (-> (str/replace s #"<br>" "")
      escape-html))

(defn answers-page [q answers nick]
  (page
   [:h2 "QA: Answers"]
   [:div [:a {:href "/qs" :class "btn btn-success btn-sm"} "QA Top"]]
   [:h4 (:id q) ", " (:nick q) "さんの質問 " (date-time (:ts q)) ","]
   [:pre {:class "question"} (my-escape-html (wrap wrap-at (:q q)))]
   [:p [:a.link-underline-light
        {:href (str "/readers/as/" (:id q))}
        "readers"]]
   [:hr]
   [:h4 "Answers"]
   (for [a answers]
     (let [goods (goods (:g a))]
       [:div
        [:p [:span {:class "nick"} (:nick a)] "'s answer " (date-time (:ts a)) ","]
        (md-to-html-string (:a a))
        [:p [:a.link-underline-light
             {:href (str "/good/" (:id q) "/" (:id a))}
             goods]
         (when (= nick "hkimura")
           [:a.link-underline-light
            {:href (str "/who-goods/" (:id a))}
            " &nbsp; "])]]))
   [:p
    ;; form の内側に [:a] で道場をリンクしている。submit 先で分岐できれば、
    ;; タイプしたメッセージをプレビューできるか？
    (form-to
     ;;{:enctype "multipart/form-data"
     ;; :onsubmit "return confirm('その回答で OK ですか？')"}
     ;;[:post "/a"]
     [:post "/markdown-preview"]
     (anti-forgery-field)
     (hidden-field "q_id" (:id q))
     (text-area {:id "answer"
                 :placeholder "markdown OK"}
                "answer")
     [:br]
     ; [:a {:href "https://mp.melt.kyutech.ac.jp"
     ;      :class "btn btn-info btn-sm"}
     ;  "Markdown Preview"]
     "&nbsp;"
     (submit-button {:class "btn btn-primary btn-sm"} "preview")
     [:p "自分のマークダウンを preview で確認して投稿する"])]
   [:p]
   [:p [:a {:href "/qs" :class "btn btn-success btn-sm"} "QA Top"]]))

(defn admin-page []
  (page
   [:h2 "QA Admin"]
   [:p "who goods?"]
   (form-to
    [:post "/admin/goods"]
    (anti-forgery-field)
    "good " (text-field {:id "n" :size 3} "n")
    " "
    (submit-button {:class "btn btn-primary btn-sm"} "submit"))))

(defn goods-page [goods]
  (page
   [:h2 "QA: goods"]
   [:table
    (for [g goods]
      [:tr
       [:td (:nick g)]
       [:td (date-time (:ts g))]])]))

(defn recents-page [answers]
  (page
   [:h2 "QA: recent answers"]
   [:p [:a {:href "/qs" :class "btn btn-success btn-sm"} "QA Top"]]
   (for [a answers]
     [:p
      (:q_id a)
      ", "
      (date-time (:ts a))
      " "
      [:a.link-underline-light
       {:href (str "/as/" (:q_id a))}
       (escape-html (ss 28 (:a a)))]
      "..." (:nick a)])
   [:p [:a {:href "/qs" :class "btn btn-success btn-sm"} "QA Top"]]))

(defn recent-goods-page [answers]
  (page
   [:h2 "QA: recent goods"]
   [:p [:a {:href "/qs" :class "btn btn-success btn-sm"} "QA Top"]]
   (for [a answers]
     [:p
      (:q_id a)
      ", "
      (date-time (:ts a))
      " "
      [:a.link-underline-light
       {:href  (str "/as/" (:q_id a))}
       (ss 28 (:q a)) "..."]])))

(defn readers-page [readers since]
  (page
   [:h2 "QA: Who read since " since]
   [:p "ほんと、みんな、QA 読まないんだな。点数稼ぎの 👍 は心が冷えるよ。"]
   [:p (->> (mapv :login readers)
            ;; 2.2.5, 2023-03-29
            ;; dedupe
            ;; 2.2.6, 2023-04-10
            distinct
            (interpose " ")
            (apply str))
    "(合計 " (count readers) ")"]))

(def ^:private markdown-clj-url "https://github.com/yogthos/markdown-clj")

(defn markdown-page [login]
  (page
   [:h2 "Markdown 道場"]
   [:p "powered by markdown-clj "
    [:a {:href (str markdown-clj-url "#supported-syntax")}
     (str "&lt;" markdown-clj-url ">")]]
   (form-to
    [:post "/md"]
    (anti-forgery-field)
    (text-area {:id "md"
                :placeholder
                (str login "さん専用マークダウン練習ページ。"
                     "練習しないとできるようにならないよ。")}
               "md")
    (submit-button {:class "btn btn-info btn-sm"} "preview"))))

(defn markdown-preview-page [md]
  (page
   [:h2 "Markdown 道場(Preview)"]
   [:p "powered by markdown-clj "
    [:a {:href (str markdown-clj-url "#supported-syntax")}
     (str "&lt;" markdown-clj-url ">")]]
   [:hr]
   (md-to-html-string md)
   [:hr]
   [:p "Markdown 道場へはブラウザの「戻る」で。"]
   [:p [:a {:href "/qs" :class "btn btn-success btn-sm"} "QA top"]]))

(defn points-page [name sid ret]
  (page
   [:h2 "Points " name " " sid]
   (for [item ret]
     [:p (str item)])))

(defn preview-page [{:strs [q_id answer] :as req}]
  (page
   [:h2 "Check Your Markdown"]
   [:div {:class "preview"} (md-to-html-string answer)]
   (form-to
    [:post "/a"]
    (anti-forgery-field)
    (hidden-field "q_id" q_id)
    (hidden-field "answer" answer)
    (submit-button {:class "btn btn-info btn-sm"} "投稿"))
   [:p "投稿ボタンを押さない限り、QA には反映しない。" [:br]
    "思ったとおりじゃない時はブラウザの「戻る」で修正後に投稿する。"]))
