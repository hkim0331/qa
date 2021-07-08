# Question and Answers

## Unreleased
- test をきちんと書けるように。
- test で duct.database/sql を捕まえたいぞ。
- github
- docker? docker-compose? docker してた方が開発が楽か？
- イメージファイルをアップロードできる。
- 短すぎる質問をリジェクト。
- 「いいね」の集計ページ
- find-by-key, :order-by option


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
