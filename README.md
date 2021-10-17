# QA reborn

元は情報リテラシー 2021 用質疑応答システムだったが、
2021 情報応用の QA システムに切り替える。

docker が楽か、データベースの共有なんかをしない限り。

## Developing

lein run の前に、.env の内容を確認し、忘れずに、

  $ source .env

してから、開発継続すること。gitignore しているデータベース接続情報がある。

# WAS (before 2021-10-13)

* どれくらいの受講生がどんな質問をし、
* どんな回答をどれくらいつけてくるか？

口だけアクティブなんとか言うようなのはたくさん。税金の無駄使い。

## Goal
- 認証は ex-typing のを使い回す。tp.melt でログインさせる。
- 回答をつけた学生、good をつけた学生を記録する。
- イメージのアップロードもできる。

## Developing

### Setup

When you first clone this repository, run:

```sh
lein duct setup
```

This will create files for local configuration, and prep your system
for the project.

### Environment

To begin developing, start with a REPL.

```sh
lein repl
```

Then load the development environment.

```clojure
user=> (dev)
:loaded
```

Run `go` to prep and initiate the system.

```clojure
dev=> (go)
:duct.server.http.jetty/starting-server {:port 3000}
:initiated
```

By default this creates a web server at <http://localhost:3000>.

When you make changes to your source files, use `reset` to reload any
modified files and reset the server.

```clojure
dev=> (reset)
:reloading (...)
:resumed
```

### Testing

Testing is fastest through the REPL, as you avoid environment startup
time.

```clojure
dev=> (test)
...
```

But you can also run tests through Leiningen.

```sh
lein test
```

## Legal

Copyright © 2021 hkimura
