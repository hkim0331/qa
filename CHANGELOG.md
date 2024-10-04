# Question and Answers

## Unreleased
- 全文検索（投稿や回答があったときにデータベースをアップデートする仕組み）
- qa, qa-all で ol の自動番号の代わりにテーブルの id にしたらどうか？
- (reset) で毎回、クラッシュ。lein repl だとクラッシュは減る。
  duct じゃなく、VScode の REPL がダメか？
- いいねにアラートつけるか
```clj
[:a {:href (str "/good/" (:id q) "/" (:id a))
     :onclick "alert('いいと思うところは何？ Markdown で書けないか'); return true;"}
    goods]
```
- 質問を出したユーザは質問をクローズできるのは？ -> 他の人が不幸になりそう。
- /md 来た人をログ --- ログよりもデータベースに入れる方がいいか？
- https://download.oracle.com/java/21/latest/jdk-21_linux-x64_bin.deb を入れるか。
- base.html から logout ボタン削った方が良くないか？
- mp.melt は need VPN だった。
- (reset) はエラーでも (halt) (go) はいける。=> *ns* を確認。
- filter の初期値を環境変数でもつ。admin がコントロールできる。
  filter から self を抜いて適用する。


## v2.9.72 / 2024-10-05

- css for pre.code.
- QA_DEV=true 時の認証は (= login "hkimura") のみ。
- WARNING: abs already refers to: #'clojure.core/abs in namespace: medley.core が出なくなった。

```
   [dev.weavejester/medley "1.8.1"]
   [com.taoensso/timbre "6.5.0"]
```
## v2.8.728 / 2024-09-20

- nginx の websocket 関連のコードを nginx.conf に置くのをやめてみた。

```
#        map $http_upgrade $connection_upgrade {
#            default upgrade;
#            ''      close;
#        }
```

- session identity: nil がおかしい。

```log
24-09-19 20:45:14 app INFO [duct.middleware.web:16] - :duct.middleware.web/request {:request-method :get, :uri "/qs", :query-string nil}
24-09-19 20:45:14 app DEBUG [qa.middleware:30] - probe session identity: nil
Producer@1ac76d00 eof=false"], :multipart-params {}, :scheme :http, :request-method :get, :session {}}
```
- qa.middleware で飛ばされている。

```log
24-09-19 20:01:12 app INFO [qa.handler.auth:41] - login success
24-09-19 20:01:12 app INFO [duct.middleware.web:16] - :duct.middleware.web/request {:request-method :get, :uri "/qs", :query-string nil}
24-09-19 20:01:12 app INFO [qa.middleware:20] - unauthorized-handler: unauthenticated
24-09-19 20:01:12 app INFO [duct.middleware.web:16] - :duct.middleware.web/request {:request-method :get, :uri "/login", :query-string nil}
```
ZZ
- fixed: nginx.conf の半端な websocket 設定を削除した。

- updated libraries

| :file       | :name                                   | :current | :latest |
|------------ | --------------------------------------- | -------- | --------|
| project.clj | com.fasterxml.jackson.core/jackson-core | 2.17.0   | 2.17.2  |
|             | com.github.seancorfield/next.jdbc       | 1.3.925  | 1.3.939 |
|             | hato/hato                               | 0.9.0    | 1.0.0   |
|             | org.clojure/clojure                     | 1.11.3   | 1.12.0  |
|             | org.postgresql/postgresql               | 42.7.3   | 42.7.4  |
|             | ring/ring                               | 1.10.0   | 1.12.2  |

- errored

```sh
; Execution error (FileNotFoundException) at ring.adapter.jetty/eval18634$loading (jetty.clj:1).
; Could not locate ring/websocket__init.class, ring/websocket.clj or ring/websocket.cljc on classpath.
```
- fixed

```clj
   ;; [ring "1.12.2"] ;; was 1.10.0
   [ring/ring-anti-forgery "1.3.1"]
   [ring/ring-core "1.12.2"]
   [ring/ring-defaults "0.5.0"]
   [ring/ring-jetty-adapter "1.12.2"]
```

## v2.7.719 / 2024-09-12

- gave up to clearing up the last page contents after preview-> submit.
  instead, introduce dev.preview class and provide css.

## v2.7.710 / 2024-09-12

- color pre code

```css
pre code {
  word-wrap: break-word;
  background-color: #f4f4f4;
  padding: 5px;
  font-size: 16px;
}
```

## v2.7.703 / 2024-08-26
Compiling with jdk17 instead of preparing docker?
- Delete `Makrdown Preview` button.
- make uberjar
```
uberjar:
	JAVA_HOME=/opt/homebrew/Cellar/openjdk@17/17.0.12/libexec/openjdk.jdk/Contents/Home \
  lein uberjar
```

- docker-compose.ymml:
```
  image: clojure:temurin-17-lein-jammy
```

## v2.6.697 / 2024-04-20
- markdown 道場の切り替え。mp.melt にリンクする。

## v2.6.693 / 2024-04-20
- マージミス。
- docker-compose.yml: image: postgres:14.11
  ```
    environment:
      QA_DEV: true
      - docker user root, not vscode.
  ```
- bind mount /root/.m2, not /home/vscode/.m2
- updated bump-version.sh, updating CHANGELOG.md.

## v2.5.681 / 2024-04-16
- core/question-start を環境変数 QA_STARTで。
- lein clean
- clj -Tantq outdated

| :file       | :name                                   | :current | :latest |
| ----------- | --------------------------------------- | -------- | ------- |
| project.clj | cheshire/cheshire                       | 5.12.0   | 5.13.0  |
|             | clojure.java-time/clojure.java-time     | 1.3.0    | 1.4.2   |
|             | com.fasterxml.jackson.core/jackson-core | 2.15.2   | 2.17.0  |
|             | com.github.seancorfield/next.jdbc       | 1.3.894  | 1.3.925 |
|             | duct/core                               | 0.8.0    | 0.8.1   |
|             | markdown-clj/markdown-clj               | 1.11.7   | 1.12.1  |
|             | org.clojure/clojure                     | 1.11.1   | 1.11.2  |
|             | org.postgresql/postgresql               | 42.6.0   | 42.7.3  |
|             | ring/ring                               | 1.10.0   | 1.12.1  |

- ring をアップデートすると jetty その他もアップデート必要になる。1.10.0 に止めよう。

```
[ring "1.10.0"]
  [ring/ring-jetty-adapter "1.10.0"]
    [org.eclipse.jetty/jetty-server "9.4.51.v20230217"]
      [org.eclipse.jetty/jetty-http "9.4.51.v20230217"]
        [org.eclipse.jetty/jetty-util "9.4.51.v20230217"]
      [org.eclipse.jetty/jetty-io "9.4.51.v20230217"]
  [ring/ring-servlet "1.10.0"]

[ring "1.12.1"]
  [org.ring-clojure/ring-jakarta-servlet "1.12.1"]
  [ring/ring-jetty-adapter "1.12.1"]
    [org.eclipse.jetty.websocket/websocket-jetty-server "11.0.20"]
      [org.eclipse.jetty.websocket/websocket-jetty-api "11.0.20"]
      [org.eclipse.jetty.websocket/websocket-jetty-common "11.0.20"]
        [org.eclipse.jetty.websocket/websocket-core-common "11.0.20"]
      [org.eclipse.jetty.websocket/websocket-servlet "11.0.20"]
        [org.eclipse.jetty.websocket/websocket-core-server "11.0.20"]
      [org.eclipse.jetty/jetty-servlet "11.0.20"]
        [org.eclipse.jetty/jetty-security "11.0.20"]
      [org.eclipse.jetty/jetty-webapp "11.0.20"]
        [org.eclipse.jetty/jetty-xml "11.0.20"]
    [org.eclipse.jetty/jetty-server "11.0.20"]
      [org.eclipse.jetty.toolchain/jetty-jakarta-servlet-api "5.0.2"]
      [org.eclipse.jetty/jetty-http "11.0.20"]
        [org.eclipse.jetty/jetty-util "11.0.20"]
      [org.eclipse.jetty/jetty-io "11.0.20"]
```

```
dev=> (go)
Execution error (ClassNotFoundException) at
jdk.internal.loader.BuiltinClassLoader/loadClass (BuiltinClassLoader.java:641).
java.util.SequencedCollection
```

## 2.4.19 - 2024-01-02
- /goods li じゃなく、id を表示する。
- FIXED: Makefile
  docker/duct/duct.zip で Makefile を上書きしたか、
  make uberjar
  make deploy
  のエントリーがなくなっていた。

## 2.4.18 - 2023-10-29
- logout ボタンを page から questions-page に移動。
- make uberjar が遅いのは docker のボリュームマウントではなかった。
  ボリュームマウントをやめても変わらずに遅い。
  - ローカルを jvm17 でいく。
  - サーバーを jvm17 にする。
  - uberjar 作るのは時代遅れか？

## 2.4.17 - 2023-10-06
- start 2023.
- 2023-10-01 以降の Q だけ表示する。

```clojure
;; qa.handler.core:
(def ^:private questions-start "2023-10-01")
```

- antq upgrade

| :file       | :name                             | :current | :latest |
| ----------- | --------------------------------- | -------- | --------|
| project.clj | com.github.seancorfield/next.jdbc | 1.3.883  | 1.3.894 |
|             | markdown-clj/markdown-clj         | 1.11.5   | 1.11.7  |


## 2.4.16.1 - 2023-09-24
- display update-at in `/about` page.

## 2.4.16 - 2023-09-23
### Fixme
m24 で作った uberjar は app.melt で動かない。
devcontainer で uberjar 作りは長い時間がかかる。app.melt では動く。

### Changed
antq upgrade
```shell
| buddy/buddy-hashers                     | 1.8.158  | 2.0.167 |
| cheshire/cheshire                       | 5.11.0   | 5.12.0  |
| clojure.java-time/clojure.java-time     | 1.2.0    | 1.3.0   |
| com.fasterxml.jackson.core/jackson-core | 2.14.2   | 2.15.2  |
| com.github.seancorfield/next.jdbc       | 1.3.865  | 1.3.883 |
| integrant/repl                          | 0.3.2    | 0.3.3   |
| markdown-clj/markdown-clj               | 1.11.4   | 1.11.5  |
```


## 2.3.15 - 2023-09-23
### Added
- .devcontainer/devcontainer.json
- docker-compose.yml
### No class error
m24(java 21) でメークした jar が app.melt で動かない。
同じソースを nuc.local でメークしたものは動くのだが。
docker コンテナで作った jar は動く。

```shell
ubuntu@app:~/qa$ ./start.sh
...
Exception in thread "main" java.lang.NoClassDefFoundError: java/util/SequencedCollection
...
```

## 2.3.12 - 2023-09-23
- /about ページ。
- /my-goods/:login 大きなフォント、改行入れてインフォーマティブに。
- 「👍のクリックで回答表示」は about メニューに幅を確保のために削除。
- fix typo in "最近の投稿^C"

## 2.3.0 - 2023-09-20
- firefox(117.0) で qa にログインできる。
  nginx 通さないダイレクト通信だとログインできるので、問題は nginx にあったとみた方がいい。
  kali の firefox は大丈夫だった。

## v2.5.681 / 2024-04-16
- let good anchors for admin only transparent

## 2.2.11 - 2023-05-16
- bootstrap@5.3.0-alpha3
- link-underline-light で見かけを軽くした。

## 2.2.10 - 2023-05-16
- Q を 2023-04-01 以降のものに絞る
- preview の意味を短く表示
- (def ^:private version ...)
- bootstrap 4.5.0 -> 5.2.3

## 2.2.9 - 2023-04-18
- メニューを markdown から markdown 道場へ

## 2.2.8 - 2023-04-14
### drop table
```
qa=# \d
                 List of relations
 Schema |        Name        |   Type   |  Owner
------- | ------------------ | -------- | ---------
 public | answers            | table    | postgres
 public | answers_id_seq     | sequence | postgres
 public | goods              | table    | postgres
 public | goods_id_seq       | sequence | postgres
 public | questions          | table    | postgres
 public | questions_id_seq   | sequence | postgres
 public | ragtime_migrations | table    | postgres
 public | readers            | table    | postgres
 public | readers_id_seq     | sequence | postgres
 public | schema_migrations  | table    | postgres
(10 rows)
```

### antq upgrade

|       :file |                             :name | :current | :latest |
| ----------- | --------------------------------- | -------- | ------- |
| project.clj | com.github.seancorfield/next.jdbc |  1.3.862 | 1.3.865 |
|             |                         ring/ring |    1.9.6 |  1.10.0 |

### Removed
- deploy.sh use `make deploy`

## 2.2.7 - 2023-04-10
### Changed
- view.page/readers-page の dedupe を distinct に変更

## 2.2.6 - 2023-03-29
readers を重複をなくした名前順ではなく、読んだ順にした。長すぎる時はやめよう。
- boundary.readers/fetch-readers で distinct をやめ、
- view.page/readers-page に dedupe を挟んだ。連続するものは一つに。
  => 一度だけ現れるってのは？

## v2.5.681 / 2024-04-16
- added Makefile

  % make deploy

## 2.2.4 - 2023-03-21
- bump vesion up
- preview before submission
- no auth when dev mode
- use env-var? `config` usage in duct
- ommit login auth by export QA_DEV=true
- `:duct.server.http/jetty {:port 3003}` this is same with qa.melt
  changed start.sh and stop.sh simultaneously.

## 2.1.3 - 2023-03-05
- update libraries

## 2.0.5 - 2022-10-15

## 2.0.3 - 2022-10-13
- keyword をやめてみた。効果なし。2.0.2 に戻す。

## 2.0.2 - 2022-10-13
- firefox でログインできない？そんな馬鹿な？
  家 Mac で再現できた。なぜだ？ 理由がわからん。py99 へは firefox ログインできる。
  proxy 通さない直 qa はこれまたログインできる。
  proxy が問題？そんなことあるかなあ？
  duct か？ py99 は luminus.

## 2.0.1 - 2022-10-13
- 昨年のまま、l22 データベースを使っていた。qa データベースに以降。
  一般人にはわかるまい。成功したみたいだ。

## 2.0.0 - 2022-09-26
- login を db から api に変更した。

## 1.9.0 - 2022-08-09
- resources/db/grading.sqlite3

## 1.8.0 - 2022-08-08
- announce

## 1.7.9 - 2022-08-06
- DRY! `/since` redirects `/since/"today"`.

## 1.7.8 - 2022-08-06
- added `/since`, reset to `since today`.

## 1.7.7 - 2022-07-23
- wrap at 80 columns.

## 1.7.6 - 2022-07-18
- answers ページに q-id を表示する。
- post /md したユーザを readers テーブルに記録。
  表示は /readers/md/0 で。

## 1.7.5 - 2022-07-17
- /md に訪れたユーザ名を表示

## 1.7.4 - 2022-07-17
### Fixed
- deploy 後、毎回 /since/yyyy-mm-dd は面倒だ。
### Changed
- /yogthos/markdown-clj#supported-syntax を直接リンク。
- Answers の右に練習場へのリンク

## 1.7.3 - 2022-07-17
- forgot html escaping
- ボタンの色を統一する。QA too は btn-success.

## 1.7.2 - 2022-07-16
- auth to /md page

## 1.7.1 - 2022-07-16
- improve /md descriptions text

## 1.7.0 - 2022-07-15
### Added
- /md, /md-post markdown 練習ページ。


## 1.6.2 - 2022-07-10
- 文言修正

## 1.6.1 - 2022-07-10

| | | |
|-:|-:|-:|
|com.fasterxml.jackson.core/jackson-core |   2.13.1 |  2.13.3 |
|markdown-clj/markdown-clj |   1.11.1 |  1.11.2 |
|org.postgresql/postgresql |   42.3.5 |  42.4.0 |

## 1.6.0 - 2022-07-03
### Changed
- app.melt で systemctl stop qa できてない。
  systemctl ではなく、restart.sh だとリスタートできる。
  qa.service としたらログはどこへ行く？
  https://jyn.jp/systemd-log-to-file/
  systemd 240 からは append をsystemd に追加できる。

```
StandardOutput=append:/home/ubuntu/qa/log/qa.log
StandardError=append:/home/ubuntu/qa/log/qa.log
```

## v2.5.681 / 2024-04-16
### Changed
- q/a のテキストエリアの高さを 2 倍、200px
- /since を hkimura オンリーに

## 1.5.3 - 2022-07-02
### Added
- get /since/yyyy-mm-dd, yyyy-mm-dd からのページの読者を表示。

## 1.5.2 - 2022-06-26
- /readers

## 1.5.1 - 2022-06-25
### Added
- /qs と /as に readers リンク。それまでにそのページを訪れた人の全リスト。

## 1.5.0 - 2022-06-25
### Changed
- アクセスログをとる。誰がどこをアクセスしたか。
  ログレベル REPORT で書き出す。
- views/questions-page に login を引数に加えた。

## 1.4.2 - 2022-06-23
- goods の timestamp 表示

## 1.4.1 - 2022-06-05
### Changed
- replace ok() with confirm('message')

## v2.5.681 / 2024-04-16

clj -Tantq outdated

| | | |
|-:|-:|-:|
| com.github.seancorfield/next.jdbc | 1.2.761 | 1.2.780 |
| duct/lein-duct | 0.12.2 | 0.12.3 |
| markdown-clj/markdown-clj | 1.10.9 | 1.11.1 |
| org.clojure/clojure | 1.10.3 | 1.11.1 |
| org.postgresql/postgresql | 42.3.2 | 42.3.5 |

## 1.3.8 - 2022-04-26
- /as/:n に top へのリンク
- ol の li じゃなく、id でリスト。p に変更したが、ちょっと空きすぎか？

## 1.3.7 - 2022-04-17
- /qs で 2022-04 以降の Q を表示する。それ以前のものは /all か /ps-all を設ける。
  日付比較するには引数の文字列をキャストする必要がある。

  ["select * from questions where ts > ?::DATE order by id desc" date]

## 2022-04-13
### Resumed
- 回答ついてない質問には 0 を表示する。「回答ついてない質問を探す」に便利。

## 2022-03-31
### Fixed
- did not display reply count
  builder-fn relating bug. fixed.
  is it good/bad to display `0`?

## 1.3.5 - 2022-03-29
### Fixed
- 最初のログインに必ず失敗する
  残していた過去の login フォームに飛ぶリンクがあった。
- 最近のいいねで internal server error
  builder-fn 問題。

## v2.5.681 / 2024-04-16
- debug ログを精選する
- リファクタリング

## 1.3.3 - 2022-03-29
### Fixed
- good で internal server error
  builder-fn を渡していない関数があった。
  (ds db) を全て (ds-opt db) に変更してバグフィックス。

## 1.3.2 - 2022-03-29
- 1.3.0 を変更する。全面的に markdown を採用した。
- question-edit-page を削除。

## 1.3.1 - 2022-03-25
### Added
- table は無条件に枠線

## 1.3.0 - 2022-03-25
### Added
- answer が ## で始まっていたら markdown と思って処理する。

## 1.2.1 - 2022-03-25
### Refactor
- 'nick' -> 'login'

## 1.2.0 - 2022-03-25
### Changed
- index ページからログイン。

## 1.1.0
- fix typo

## 1.0.1
- improved utils.clj

## 1.0.0
- restart 2022 version from 1.0.0

## 0.9.0 - 2022-01-20
- lein ancient

## 0.8.1 - 2021-12-10
- to top をすべてのページに（多くのページに）
- 最近の goods のページ
- 新規投稿ボタンを上の方に
- .env を読まずに開発できる方法。
- ブラウザ幅に合わせて表示


## 0.7.9 - 2021-11-22
### Changed
- Q は 54 文字、A は 66 文字でラップ。wrap のコードは r99c から持ってきた。

## 0.7.8 - 2021-11-12
### Changed
- answers-page の pre.font-size larger を medium に変更。
- hr 引いて h4 Answer に変更。
- improved placeholder

## 0.7.7 - 2021-11-06
### Changed
- Q も pre で。
- textarea の幅 100%
- my-good は h2 やめて p

## 0.7.6 - 2021-11-05
### Removed
- test/qa/auth_test.clj
### Changed
- admin 用の "👍" を "&nbsp;"で Zoom 時にも見えなくする。
- (reset) してもエラーが出なくなった。auth_test.clj の削除と lein clean の後。
- p から pre で as is 表示に変更。
  それに伴い、過去に入力してもらった<br>をリプレース後に表示。

## 0.7.5 - 2021-11-05
### Added
- ついた回答数を Q ごとに表示。

## 0.7.4 - 2021-10-26
### Changed
- reverse order of questions
- replace 'who?' with '👍'

## 0.7.3 - 2021-10-25
qa.melt でスタートしない。
### Fixed
- 3030 ではなく、3003 だった。config.edn に
  duct.server.http/jetty {:port 3003}
  しておくと、環境変数 PORT よりも優先するのかな？そうとすれば説明つく。

## 0.7.2 - 2021-10-25
### Changed
- デフォルトの jetty ポートを 3030。ローカル開発でぶつからないよう。
- コメントを answers-page からつける。独立したページに飛ぶのをやめた。
### Removed
- 上によって、answer-page が必要なくなった。まだ消してない。該当箇所をコメントアウトしたのみ。

## 0.7.1 - 2021-10-16
### Fixed
- html-escape を hiccup.core/html-escape に変更したために、
 それまで &lt; だけ見てればよかった unescape-br を
 &gt; も戻すようにしないとバランスが取れない。
- app.melt に 0.7.1 プッシュしたが表示は 0.7.0 のまま。次のバージョンアップで直そう。
### Changed
- (timbre/set-level :info)

## 0.7.0 - 2021-10-16
- goods テーブルの q_id コラムにデータを入れる。
- ボタンの変更。new -> new question, questions -> QA Top

## 0.6.9 - 2021-10-16
### Fixed
- 第3の方法で。エンドポイントを /goods/q/a に変更した。

## 0.6.8 - 2021-10-15
いいねのリダイレクト先がずれてる。原因究明のためのバージョン。
### BUG
/as に渡すべきは q-id なのに a-id を渡している。
考えるフィックスは3つ。
- (get-in req [:headers "referer"]) 中の文字列から参照すべき a-id を割り出す。
- goods を呼ぶときに a-id を引数として追加する。
- もう一つ、goods テーブルには a-id も入れてるな。
 0.6.8 まではコラムはあっても、利用していない。


## 0.6.7 - 2021-10-14
### Removed
- 「必要なら」の行を消す。
### Changed
- after pushing good button, returns back to the original page.
### Added
- "「👍」は一回答に一回だけです。"

## 0.6.6 - 2021-10-14
### Changed
- textarea height: 100px;

## 0.6.5 - 2021-10-14
### Changed
- resumeed article order. old -> new

## 0.6.3 - 2021-10-14
### Fixed
- 本番サーバで動かなかった理由はコードではなく、データベースのテーブルにあった。
 answers テーブルにコラム g が欠落していた。
 教訓：古いマイグレーションコード（動作を確認できないやつ）を残すな、信じるな。

## v2.5.681 / 2024-04-16
まだ本番サーバーで回答ができない。開発PC ではできたはずだが？
- hotfix 0.6.2 start

## 0.6.1 - 2021-10-13
- start to work as https://qa.melt.kyutech.ac.jp/
- git rm --cached

## 0.6.0 - 2021-10-13
- r99c で認証できる。
- github で再開。


## WAS (before 2021-10-13)

## Unreleased
- test をきちんと書けるように。
- test で duct.database/sql を捕まえたいぞ。
- github
- docker? docker-compose? docker してた方が開発が楽か？
- イメージファイルをアップロードできる。
- 短すぎる質問をリジェクト。

## 0.5.1 - 2021-08-20
- マージし直し、日本語解説の youtube へのリンクに変更。

## 0.5.0 - 2021-08-20
- マージをミスった。

## 0.4.6.1 - 2021-07-12
### Changed
- question も <br> で改行。/as ね。
- hkimura: q/a = 28/221, s/r = 81/1376 は a/q, r/s にして平常点に。

## 0.4.6 - 2021-07-12
### Changed
- /as answers-page 注意事項に右の Admin やめて、hkimura ユーザにだけ、
  goods の右に who?
- /my-goods から questions/answers, sent/received を表示。
### added
- boundary/answers/count-my-answers
- boundary/questions/count-my-questions

## 0.4.5.3 - 2021-07-09
### Added
-- /recents と /my-goods に auth.

## 0.4.5.2 - 2021-07-09
### Addded
- escape-html を /recents に。

## 0.4.5.1 - 2021-07-09
### Bugfix of 0.4.5
- 引数は id ではない。q-id を持っていかないと。

## 0.4.5 - 2021-07-09
### Added
- recent-answers

## 0.4.4.1 - 2021-07-09
### Changed
- qa.views.page/answers-page Admin を赤で。

## 0.4.4 - 2021-07-08
### Added
- qa のページ、nick をクリックで goods の send/get を表示

## 0.4.3.2 - 2021-07-08
- qa.view.page/ss
  nick question link の順とする。

## 0.4.3.1 - 2021-07-08
### Changed
- qa.view.page/ss
    "文字列 s の n 文字以降を '👉' でリプレースした文字列を返す。
     文字列長さが n に満たない時は文字列に'👉'を足す。"

## 0.4.3 - 2021-07-08
### Changed
- ニックネームをクリックで QA ページへ。

## 0.4.2 - 2021-07-08
### Added
- unwscape-br answer でのみ、<br> 復活させる。

## 0.4.1 - 2021-07-08
### Added
- /admin, /admin-goods 誰が good したかを表示する。

## 0.4.0 - 2021-07-05
- [:a {:href "/qs" :class "btn btn-success btn-sm"} "questions"]]
- /qs 回答の表示順。jdbc/query でかけるのだが、jdbc/find-by-keys で書けない。


## 0.3.4 - 2021-06-28
### Changed
- /qs の並びを新着順に変更。

## 0.3.3 - 2021-06-23
- オープン戦終了。page.clj から必要のない li を削除。

## 0.3.2 - 2021-06-19
- systemd
- 全角スペースを page.clj から剥ぎ取る。
  時々、zenkaku は全角スペースを表示しない。

## 0.3.1 - 2021-06-19
- ログインにバグ。ニックネームが "" の学生がある。
  アカウント作成時にはじかなくちゃ。=> ex-typing

## 0.3.0 - 2021-06-19
- いろんなメッセージは index に移動する。
- 注意事項("/")をリンク。
- submit に confirm
- qa.melt で動作確認。
- いいねを実装する。

## 0.2.2 - 2021-06-18
- "/" の扱い。index へ飛ばして、2001 年宇宙の旅とする。
- html をエスケープする。
- page.clj の version を bump-versionup で書き換える。
- サウンドを自動再生する。firefox 以外は自動再生が許されていない。
  https://gray-code.com/javascript/auto-play-the-audio/

## 0.2.1 - 2021-06-18
- qa.melt にデプロイ。オープン戦開始。

## 0.2.0 - 2021-06-18
- 回答を投稿できる。
- 回答を表示できる。

## 0.1.3 - 2021-06-18
- style.css -> styles.css
- n 番目の質問に回答する /a/:n エンドポイント。

## 0.1.2 - 2021-06-18
- 質問を表示し、新規作成できる。
- /as/:n エンドポイント。

## 0.1.1 - 2021-06-18
- middleware auth をセット。
- initdb.d/{up,down}.bb で initdb.d/*.sql をリプレース。
- ログインできる。
- ページのボトムに logout ボタン。

## 0.1.0 - 2021-06-17
- 開発スタート
- git flow init
- ex-typing のデータで認証する。
- table 定義(sql)
- question form ("/q")
