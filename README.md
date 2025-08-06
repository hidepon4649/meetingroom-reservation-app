# 会議室予約システム

## 概要

このアプリケーションは、Spring Boot, Thymeleaf, RDB(MySQL) を使用した会議室予約システムです。

社内の会議室予約を管理する為のシンプルな Web アプリケーションです。

主な機能

- ログイン
- 会議室予約
  - 予約登録
  - 予約変更
  - 予約取消
  - 予約カレンダー表示
- 管理機能(管理ユーザのみ)
  - ユーザの登録
  - ユーザの変更
  - ユーザの削除
  - 会議室の登録
  - 会議室の変更
  - 会議室の削除

開発現場で利用頻度の高い以下の要素を実装しており、  
新人研修の課題プログラムに適していると思います。

- 認証認可
- 画面から投入したデータの妥当性チェック
- データベースへの登録/更新/削除
- 画像アップロード
- カレンダー UI

## 使用技術

- **フロントエンド**: Thymeleaf, Bootstrap
- **バックエンド**: Spring Boot, Gradle
- **データベース**: MySQL
- **インフラ**: Docker(コンテナ化), Docker Compose

## セットアップ手順

```bash
# リポジトリをクローン
git clone https://github.com/hidepon4649/meetingroom-reservation-app.git
```

## アプリケーション起動手順

```bash
# docker-composeのディレクトリに移動して、下記２つのコマンドを実行して下さい。
docker compose build
docker compose up -d

# ブラウザから下記URL
http://localhost:8080/login
# 初期管理ユーザ id/pw
admin@example.com/password
```

<!--
TODO:デモ画面のキャプチャを載せる
## デモ
![ログイン画面](./docs/login.png)
![予約登録](./docs/xxx.png)
![予約カレンダー表示](./docs/xxx.png)
![ユーザの登録](./docs/xxx.png)
![会議室の登録](./docs/xxx.png)
-->
