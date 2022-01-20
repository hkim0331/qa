# Question and Answers

## Unreleased
- 本番でログがうるさすぎ。
21-10-25 02:57:34 app INFO [duct.database.sql.hikaricp:30] -
:duct.database.sql/query {:query ["select * from answers
where q_id=? order by id" 10], :elapsed 1}
- question-edit-page は必要か？

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

## 0.6.2-SNAPSHOT
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
- ex-typing のデータで認証する。
- table 定義(sql)
- question form ("/q")

## 0.1.0-SNAPSHOT - 2021-06-17
- 開発スタート
- git flow init
