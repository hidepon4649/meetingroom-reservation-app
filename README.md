# 会議室予約システム

## 概要

このアプリケーションは、Spring Boot, Thymeleaf, MySQL を使用した会議室予約システムです。
会議室の登録
ユーザの登録
会議室の予約、予約取消

## 使用技術

- **フロントエンド**: Thymeleaf, Bootstrap
- **バックエンド**: Spring Boot, Gradle
- **データベース**: MySQL
- **インフラ**: Docker(コンテナ化), Docker Compose

## セットアップ手順

```bash
# リポジトリをクローン
git clone https://github.com/hidepon4649/🔴🔴🔴🔴🔴🔴.git
```

## アプリケーション起動手順

```bash
# docker-composeのディレクトリに移動して、下記２つのコマンドを実行して下さい。
docker compose build
docker compose up -d

# ブラウザから下記URL
http://localhost:8080/🔴🔴🔴🔴🔴🔴
# 初期管理ユーザ id/pw
🔴🔴🔴🔴🔴🔴/password
```

<!--
TODO:主な機能を紹介する
## 主な機能
- 共通機能(一般ユーザ、管理ユーザ)
  - ログイン・認証認可(JWT)
  - 出勤・退勤の登録
  - 月次帳票のPDF出力
- 管理機能(管理ユーザ)
  - ユーザの新規登録・編集・削除
  - 出勤・退勤の修正
  - 操作履歴の閲覧
-->

<!--
TODO:デモ画面のキャプチャを載せる
## デモ
![ログイン画面](./docs/login.png)
![出退勤登録](./docs/attendance.png)
-->
